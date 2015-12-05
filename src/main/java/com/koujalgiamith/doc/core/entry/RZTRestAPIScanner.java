package com.koujalgiamith.doc.core.entry;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.koujalgiamith.doc.core.annotations.RZTRESTService;
import com.koujalgiamith.doc.core.annotations.RZTRESTServiceMethod;
import com.koujalgiamith.doc.core.annotations.RZTRESTServiceResponseCode;
import com.koujalgiamith.doc.model.AppType;
import com.koujalgiamith.doc.model.RESTApplicationAPISpec;
import com.koujalgiamith.doc.model.RESTService;
import com.koujalgiamith.doc.model.RESTServiceMethod;
import com.koujalgiamith.doc.model.RESTServiceMethodResponseCode;
import com.koujalgiamith.doc.utils.JSONUtils;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class RZTRestAPIScanner {
	private String appName;

	public RZTRestAPIScanner(String appName) {
		this.appName = appName;
	}

	private RESTService scanClass(Class<?> className) throws Exception {
		RESTService svc = new RESTService();

		// check if the class is a service definition
		if (className.isAnnotationPresent(RZTRESTService.class)) {
			RZTRESTService serviceAnnotation = className.getAnnotation(RZTRESTService.class);

			String basePath = serviceAnnotation.path();
			if (!basePath.startsWith("/")) {
				basePath = "/" + basePath;
			}

			svc.setHeaders(serviceAnnotation.headers());
			svc.setPath(basePath);
			svc.setServiceDescription(serviceAnnotation.serviceDescription());
			svc.setServiceName(serviceAnnotation.serviceName());
			svc.setServiceClassName(className.getName());

			List<RESTServiceMethod> methods = new ArrayList<>();
			for (Method m : className.getMethods()) {
				if (m.isAnnotationPresent(RZTRESTServiceMethod.class)) {
					RZTRESTServiceMethod methodAnnotation = m.getAnnotation(RZTRESTServiceMethod.class);

					String relativePath = methodAnnotation.path();
					if (!relativePath.startsWith("/")) {
						relativePath = "/" + relativePath;
					}

					RESTServiceMethod method = new RESTServiceMethod();

					if (methodAnnotation.consumesMediaType() != null
							&& !methodAnnotation.consumesMediaType().isEmpty()) {
						method.setConsumesMediaType(methodAnnotation.consumesMediaType());
					}
					if (methodAnnotation.methodDescription() != null
							&& !methodAnnotation.methodDescription().isEmpty()) {
						method.setMethodDescription(methodAnnotation.methodDescription());
					}
					if (methodAnnotation.methodRequestClass() != null) {
						method.setMethodRequestClass(methodAnnotation.methodRequestClass().getName());
						if ("javax.validation.constraints.Null"
								.equals(methodAnnotation.methodRequestClass().getName())) {
							// if request class is not set, then this method
							// probably isn't taking an Object as a request. So,
							// ignore.
						} else {
							method.setRequestJSONStructure(buildJSONFromEmptyPojo(method.getMethodRequestClass()));
						}

					}
					if (methodAnnotation.methodResponseClass() != null) {
						method.setMethodResponseClass(methodAnnotation.methodResponseClass().getName());
						method.setResponseJSONStructure(buildJSONFromEmptyPojo(method.getMethodResponseClass()));
					}
					if (methodAnnotation.producesMediaType() != null
							&& !methodAnnotation.producesMediaType().isEmpty()) {
						method.setProducesMediaType(methodAnnotation.producesMediaType());
					}
					if (methodAnnotation.methodType() != null) {
						method.setMethodType(methodAnnotation.methodType());
					}
					if (methodAnnotation.verbs() != null && methodAnnotation.verbs().length > 0) {
						// if method has no verbs except the defaults, ignore
						// adding verb to this service method
						if (!(methodAnnotation.verbs().length == 1 && methodAnnotation.verbs()[0].isEmpty())) {
							method.setVerbs(methodAnnotation.verbs());
						}
					}

					List<RESTServiceMethodResponseCode> responseCodes = new ArrayList<>();
					if (methodAnnotation.responseCodes() != null) {
						RZTRESTServiceResponseCode[] codes = methodAnnotation.responseCodes();
						for (RZTRESTServiceResponseCode c : codes) {
							RESTServiceMethodResponseCode rCode = new RESTServiceMethodResponseCode();
							rCode.setDescription(c.description());
							rCode.setHttpCode(c.httpCode());
							rCode.setStatusCode(c.statusCode());
							responseCodes.add(rCode);
						}
						method.setResponseCodes(responseCodes);
					}
					if (methodAnnotation.version() != null) {
						method.setVersion(methodAnnotation.version());
					}
					method.setPath(relativePath);
					method.setMethodName(m.getName());
					method.setEndpoint(svc.getPath() + method.getPath());
					methods.add(method);
				}
			}
			svc.setMethods(methods);
			return svc;
		} else {
			throw new Exception("Not annotated");
		}

	}

	public RESTApplicationAPISpec scanPackage(String packageName)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		System.out.println("Building model...");

		RESTApplicationAPISpec api = new RESTApplicationAPISpec();
		List<Class<?>> classes = ClassFinder.find(packageName);
		for (Class<?> c : classes) {
			// ignore inner classes
			if (!c.getName().contains("$")) {
				RESTService svc;
				try {
					svc = scanClass(c);
					api.getServices().add(svc);
					api.setAppType(analyseAppType(c));
					api.setAppName(appName);
				} catch (Exception e) {
					if (e.getMessage().toLowerCase().contains("not annotated")) {
						System.out.println("Ignoring analysis on class " + c.getName());
					} else
						e.printStackTrace();
				}
			}
		}
		JSONUtils.printJson(api);
		return api;
	}

	private <T> String buildJSONFromEmptyPojo(String className) throws ClassNotFoundException {
		PodamFactory factory = new PodamFactoryImpl();
		@SuppressWarnings("unchecked")
		T pojoObj = (T) factory.manufacturePojoWithFullData(Class.forName(className));
		return new GsonBuilder().setPrettyPrinting().create().toJson(pojoObj);
	}

	private AppType analyseAppType(Class<?> className) {
		return AppType.JERSEY_2X;
	}
}

class ClassFinder {

	private static final char DOT = '.';

	private static final char SLASH = '/';

	private static final String CLASS_SUFFIX = ".class";

	private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

	public static List<Class<?>> find(String scannedPackage) {
		String scannedPath = scannedPackage.replace(DOT, SLASH);
		URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
		if (scannedUrl == null) {
			throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
		}
		File scannedDir = new File(scannedUrl.getFile());
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (File file : scannedDir.listFiles()) {
			classes.addAll(find(file, scannedPackage));
		}
		return classes;
	}

	private static List<Class<?>> find(File file, String scannedPackage) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		String resource = scannedPackage + DOT + file.getName();
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				classes.addAll(find(child, resource));
			}
		} else if (resource.endsWith(CLASS_SUFFIX)) {
			int endIndex = resource.length() - CLASS_SUFFIX.length();
			String className = resource.substring(0, endIndex);
			try {
				classes.add(Class.forName(className));
			} catch (ClassNotFoundException ignore) {
			}
		}
		return classes;
	}

}