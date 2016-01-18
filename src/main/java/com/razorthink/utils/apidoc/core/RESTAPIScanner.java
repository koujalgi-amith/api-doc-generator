package com.razorthink.utils.apidoc.core;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.GsonBuilder;
import com.razorthink.utils.apidoc.annotations.WebService;
import com.razorthink.utils.apidoc.annotations.WebServiceHeader;
import com.razorthink.utils.apidoc.annotations.WebServiceMethod;
import com.razorthink.utils.apidoc.annotations.WebServiceMethod.RESTMethodType;
import com.razorthink.utils.apidoc.annotations.WebServiceMethodURLParam;
import com.razorthink.utils.apidoc.annotations.WebServiceResponseCode;
import com.razorthink.utils.apidoc.exceptions.InvalidMethodDefinitionException;
import com.razorthink.utils.apidoc.model.RESTAPIService;
import com.razorthink.utils.apidoc.model.RESTAPIServiceHeader;
import com.razorthink.utils.apidoc.model.RESTAPIServiceMethod;
import com.razorthink.utils.apidoc.model.RESTAPIServiceMethodResponseCode;
import com.razorthink.utils.apidoc.model.RESTAPIServiceMethodURLParam;
import com.razorthink.utils.apidoc.model.RESTAPISpecification;
import com.razorthink.utils.apidoc.model.WebAppType;
import com.razorthink.utils.apidoc.utils.FileUtils;
import com.razorthink.utils.apidoc.utils.JSONUtils;

import uk.co.jemos.podam.api.AbstractRandomDataProviderStrategy;
import uk.co.jemos.podam.api.AttributeMetadata;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class RESTAPIScanner {

	// final static Logger logger = Logger.getLogger(RESTAPIScanner.class);

	private String appName;

	public RESTAPIScanner(String appName) {
		this.appName = appName;
	}

	private RESTAPIService scanClass(Class<?> className) throws Exception {
		RESTAPIService svc = new RESTAPIService();

		// check if the class is a service definition
		if (className.isAnnotationPresent(WebService.class)) {
			WebService serviceAnnotation = className.getAnnotation(WebService.class);

			String basePath = serviceAnnotation.path();
			if (!basePath.startsWith("/")) {
				basePath = "/" + basePath;
			}
			basePath = basePath.replace("//", "/");

			List<RESTAPIServiceHeader> serviceHeaders = new ArrayList<>();
			if (serviceAnnotation.headers() != null) {
				WebServiceHeader[] headers = serviceAnnotation.headers();
				for (WebServiceHeader h : headers) {
					RESTAPIServiceHeader rHeader = new RESTAPIServiceHeader();
					rHeader.setDescription(h.description());
					rHeader.setName(h.name());
					rHeader.setAllowedValues(h.valuesAllowed());
					serviceHeaders.add(rHeader);
				}
			}
			if (!serviceHeaders.isEmpty()) {
				svc.setHeaders(serviceHeaders);
			}
			if (serviceAnnotation.tags() != null && serviceAnnotation.tags().length > 0) {
				List<String> tagsList = new ArrayList<>();
				for (String tag : serviceAnnotation.tags()) {
					tagsList.add(tag);
				}
				svc.setTags(tagsList);
			}

			svc.setPath(basePath);
			svc.setServiceDescription(serviceAnnotation.serviceDescription());
			svc.setServiceName(serviceAnnotation.serviceName());
			svc.setServiceClassName(className.getName());

			List<RESTAPIServiceMethod> methods = new ArrayList<>();
			for (Method m : className.getDeclaredMethods()) {
				if (m.isAnnotationPresent(WebServiceMethod.class)) {
					WebServiceMethod methodAnnotation = m.getAnnotation(WebServiceMethod.class);

					String subRoute = methodAnnotation.path();
					if (!subRoute.startsWith("/")) {
						subRoute = "/" + subRoute;
					}
					subRoute = subRoute.replace("//", "/");

					String endpoint = basePath + subRoute;
					endpoint = endpoint.replace("//", "/");

					RESTAPIServiceMethod method = new RESTAPIServiceMethod();

					if (methodAnnotation.consumesMediaType() != null
							&& !methodAnnotation.consumesMediaType().isEmpty()) {
						method.setConsumesMediaType(methodAnnotation.consumesMediaType());
					}
					if (methodAnnotation.description() != null && !methodAnnotation.description().isEmpty()) {
						method.setMethodDescription(methodAnnotation.description());
					}
					if (methodAnnotation.requestClass() != null) {
						if ("javax.validation.constraints.Null".equals(methodAnnotation.requestClass().getName())) {
							// if request class is not set, then this method
							// probably isn't taking an Object as a request. So,
							// ignore.
						} else {
							method.setMethodRequestClass(methodAnnotation.requestClass().getName());
							method.setRequestJSONStructure(buildJSONFromEmptyPojo(method.getMethodRequestClass()));
						}

					}
					if (methodAnnotation.responseClass() != null) {
						method.setMethodResponseClass(methodAnnotation.responseClass().getName());
						method.setResponseJSONStructure(buildJSONFromEmptyPojo(method.getMethodResponseClass()));
					}
					if (methodAnnotation.producesMediaType() != null
							&& !methodAnnotation.producesMediaType().isEmpty()) {
						method.setProducesMediaType(methodAnnotation.producesMediaType());
					}
					if (methodAnnotation.type() != null) {
						method.setMethodType(methodAnnotation.type());
					}

					if (methodAnnotation.type() == RESTMethodType.GET && (!methodAnnotation.requestClass().getName()
							.equals(javax.validation.constraints.Null.class.getName()))) {
						throw new InvalidMethodDefinitionException(
								"Invalid service definition " + className.getName() + "." + m.getName()
										+ "(): Request method of type GET can't have a request class mapping.");
					}
					if (methodAnnotation.type() != RESTMethodType.GET
							&& (methodAnnotation.urlParameters().length > 0)) {
						System.out.println("Req class: " + methodAnnotation.requestClass());
						System.out.println("Params: " + methodAnnotation.urlParameters().length);
						System.out.println("Type: " + methodAnnotation.type());
						System.out.println("Method: " + className.getName() + "." + m.getName());
						throw new InvalidMethodDefinitionException("Invalid service definition " + className.getName()
								+ "." + m.getName()
								+ "(): Request method of type POST/PUT/DELETE shouldn't have urlParameters field.");
					}

					// if (methodAnnotation.urlParameters() != null &&
					// methodAnnotation.urlParameters().length > 0) {
					// // if method has no verbs except the defaults, ignore
					// // adding verb to this service method
					// if (!(methodAnnotation.urlParameters().length == 1
					// && methodAnnotation.urlParameters()[0].isEmpty())) {
					// method.setUrlParams(methodAnnotation.params());
					// }
					// }

					List<RESTAPIServiceHeader> methodHeaders = new ArrayList<>();
					if (methodAnnotation.headers() != null) {
						WebServiceHeader[] headers = methodAnnotation.headers();
						for (WebServiceHeader h : headers) {
							RESTAPIServiceHeader rHeader = new RESTAPIServiceHeader();
							rHeader.setDescription(h.description());
							rHeader.setName(h.name());
							rHeader.setAllowedValues(h.valuesAllowed());
							methodHeaders.add(rHeader);
						}
					}
					List<RESTAPIServiceMethodResponseCode> responseCodes = new ArrayList<>();
					if (methodAnnotation.responseCodes() != null) {
						WebServiceResponseCode[] codes = methodAnnotation.responseCodes();
						for (WebServiceResponseCode c : codes) {
							RESTAPIServiceMethodResponseCode rCode = new RESTAPIServiceMethodResponseCode();
							rCode.setDescription(c.description());
							rCode.setHttpCode(c.httpStatus().value());
							rCode.setStatusCode(c.statusCode());
							rCode.setType(c.type().name());
							responseCodes.add(rCode);
						}
						method.setResponseCodes(responseCodes);
					}

					List<RESTAPIServiceMethodURLParam> methodURLParams = new ArrayList<>();
					if (methodAnnotation.urlParameters() != null) {
						WebServiceMethodURLParam[] urlParams = methodAnnotation.urlParameters();
						for (WebServiceMethodURLParam h : urlParams) {
							RESTAPIServiceMethodURLParam mURLParam = new RESTAPIServiceMethodURLParam();
							mURLParam.setDesc(h.desc());
							mURLParam.setName(h.name());
							mURLParam.setOrder(h.order());
							for (String p : h.possibleValues()) {
								mURLParam.getPossibleValues().add(p);
							}
							methodURLParams.add(mURLParam);
						}
						method.setUrlParams(methodURLParams);
					}

					if (methodAnnotation.tags() != null && methodAnnotation.tags().length > 0) {
						List<String> tagsList = new ArrayList<>();
						for (String tag : methodAnnotation.tags()) {
							tagsList.add(tag);
						}
						method.setTags(tagsList);
					}

					if (!methodHeaders.isEmpty()) {
						method.setHeaders(methodHeaders);
					}

					if (methodAnnotation.version() != null) {
						method.setVersion(methodAnnotation.version());
					}
					method.setPath(subRoute);
					method.setEndpoint(endpoint);
					method.setMethodName(m.getName());
					methods.add(method);
				}
			}
			svc.setMethods(methods);
			return svc;
		} else {
			throw new Exception("Not annotated");
		}

	}

	public RESTAPISpecification scanPackage(String packageName) throws Exception {

		RESTAPISpecification api = new RESTAPISpecification();
		List<Class<?>> classes = ClassFinder.find(packageName);
		for (Class<?> c : classes) {
			// ignore inner classes
			if (!c.getName().contains("$")) {
				RESTAPIService svc;
				try {
					svc = scanClass(c);
					api.getServices().add(svc);
					System.out.println("Found webservice class: " + c.getName());
					api.setAppName(appName);
				} catch (Exception e) {
					if (e.getMessage().toLowerCase().contains("not annotated")) {
						System.out.println("Ignoring analysis on class " + c.getName());
					} else if (e instanceof InvalidMethodDefinitionException) {
						throw e;
					} else {
						System.err.println("Error: " + e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}
		return api;
	}

	public void scanPackagesAndGenerate(String[] packageNames, String destDirectorydirectory) throws Exception {
		System.out.println("Building JSON model of Web API...");
		List<RESTAPISpecification> specs = new ArrayList<RESTAPISpecification>();
		try {
			for (String pkg : packageNames) {
				specs.add(scanPackage(pkg));
			}
			RESTAPISpecification finalSpec = mergeAPISpecsOfMultiplePackages(specs);
			File f = new File(destDirectorydirectory + File.separator + "api_spec_doc.json");
			FileUtils.writeFile(f.getAbsolutePath(), JSONUtils.stringify(finalSpec));
			JSONUtils.printJson(finalSpec);
			System.out.println("Generated JSON model of Web API doc into " + f.getAbsolutePath());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		//	throw e;
		}
	}

	/**
	 * Find if there are multiple services with the same route. If so, treat
	 * them as conflicting, else merge the service models and build one API
	 * model.
	 * 
	 * @param specs
	 * @return
	 */
	private RESTAPISpecification mergeAPISpecsOfMultiplePackages(List<RESTAPISpecification> specs) {
		RESTAPISpecification spec = new RESTAPISpecification();
		if (specs == null || specs.size() == 0) {
			return spec;
		}
		spec.setAppName(specs.get(0).getAppName());
		for (RESTAPISpecification s : specs) {
			spec.getServices().addAll(s.getServices());
		}
		return spec;
	}

	private <T> String buildJSONFromEmptyPojo(String className) throws ClassNotFoundException {
		AbstractRandomDataProviderStrategy providerStrategy = new AbstractRandomDataProviderStrategy() {

			@Override
			public String getStringValue(AttributeMetadata attributeMetadata) {
				return UUID.randomUUID().toString();
			}
		};
		providerStrategy.setMemoization(true);

		PodamFactory factory = new PodamFactoryImpl(providerStrategy);

		@SuppressWarnings("unchecked")
		T pojoObj = (T) factory.manufacturePojoWithFullData(Class.forName(className));
		return new GsonBuilder().setPrettyPrinting().create().toJson(pojoObj);
	}

	@SuppressWarnings("unused")
	private WebAppType analyseAppType(Class<?> className) {
		if (className.getSuperclass().getName().equals("javax.servlet.http.HttpServlet")) {
			return WebAppType.RAW_HTTP_SERVLET;
		}
		for (Annotation a : className.getAnnotations()) {
			if (a.annotationType().getName().equals("javax.ws.rs.Path")) {
				return WebAppType.JERSEY;
			}
		}
		return WebAppType.JERSEY;
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