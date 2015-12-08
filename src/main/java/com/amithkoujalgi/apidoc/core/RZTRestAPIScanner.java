package com.amithkoujalgi.apidoc.core;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.amithkoujalgi.apidoc.annotations.RZTRESTService;
import com.amithkoujalgi.apidoc.annotations.RZTRESTServiceHeader;
import com.amithkoujalgi.apidoc.annotations.RZTRESTServiceMethod;
import com.amithkoujalgi.apidoc.annotations.RZTRESTServiceResponseCode;
import com.amithkoujalgi.apidoc.model.AppType;
import com.amithkoujalgi.apidoc.model.RESTApplicationAPISpec;
import com.amithkoujalgi.apidoc.model.RESTService;
import com.amithkoujalgi.apidoc.model.RESTServiceHeader;
import com.amithkoujalgi.apidoc.model.RESTServiceMethod;
import com.amithkoujalgi.apidoc.model.RESTServiceMethodResponseCode;
import com.amithkoujalgi.apidoc.utils.FileUtils;
import com.amithkoujalgi.apidoc.utils.JSONUtils;
import com.google.gson.GsonBuilder;
import uk.co.jemos.podam.api.AbstractRandomDataProviderStrategy;
import uk.co.jemos.podam.api.AttributeMetadata;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class RZTRestAPIScanner {

	private String appName;

	public RZTRestAPIScanner( String appName )
	{
		this.appName = appName;
	}

	private RESTService scanClass( Class<?> className ) throws Exception
	{
		RESTService svc = new RESTService();

		// check if the class is a service definition
		if( className.isAnnotationPresent(RZTRESTService.class) )
		{
			RZTRESTService serviceAnnotation = className.getAnnotation(RZTRESTService.class);

			String basePath = serviceAnnotation.path();
			if( !basePath.startsWith("/") )
			{
				basePath = "/" + basePath;
			}
			basePath = basePath.replace("//", "/");

			List<RESTServiceHeader> serviceHeaders = new ArrayList<>();
			if( serviceAnnotation.headers() != null )
			{
				RZTRESTServiceHeader[] headers = serviceAnnotation.headers();
				for( RZTRESTServiceHeader h : headers )
				{
					RESTServiceHeader rHeader = new RESTServiceHeader();
					rHeader.setDescription(h.description());
					rHeader.setName(h.name());
					rHeader.setAllowedValues(h.valuesAllowed());
					serviceHeaders.add(rHeader);
				}
			}
			if( !serviceHeaders.isEmpty() )
			{
				svc.setHeaders(serviceHeaders);
			}
			if( serviceAnnotation.tags() != null && serviceAnnotation.tags().length > 0 )
			{
				List<String> tagsList = new ArrayList<>();
				for( String tag : serviceAnnotation.tags() )
				{
					tagsList.add(tag);
				}
				svc.setTags(tagsList);
			}

			svc.setPath(basePath);
			svc.setServiceDescription(serviceAnnotation.serviceDescription());
			svc.setServiceName(serviceAnnotation.serviceName());
			svc.setServiceClassName(className.getName());

			List<RESTServiceMethod> methods = new ArrayList<>();
			for( Method m : className.getDeclaredMethods() )
			{
				if( m.isAnnotationPresent(RZTRESTServiceMethod.class) )
				{
					RZTRESTServiceMethod methodAnnotation = m.getAnnotation(RZTRESTServiceMethod.class);

					String subRoute = methodAnnotation.path();
					if( !subRoute.startsWith("/") )
					{
						subRoute = "/" + subRoute;
					}
					subRoute = subRoute.replace("//", "/");

					String endpoint = basePath + subRoute;
					endpoint = endpoint.replace("//", "/");

					RESTServiceMethod method = new RESTServiceMethod();

					if( methodAnnotation.consumesMediaType() != null
							&& !methodAnnotation.consumesMediaType().isEmpty() )
					{
						method.setConsumesMediaType(methodAnnotation.consumesMediaType());
					}
					if( methodAnnotation.methodDescription() != null
							&& !methodAnnotation.methodDescription().isEmpty() )
					{
						method.setMethodDescription(methodAnnotation.methodDescription());
					}
					if( methodAnnotation.methodRequestClass() != null )
					{
						if( "javax.validation.constraints.Null"
								.equals(methodAnnotation.methodRequestClass().getName()) )
						{
							// if request class is not set, then this method
							// probably isn't taking an Object as a request. So,
							// ignore.
						}
						else
						{
							method.setMethodRequestClass(methodAnnotation.methodRequestClass().getName());
							method.setRequestJSONStructure(buildJSONFromEmptyPojo(method.getMethodRequestClass()));
						}

					}
					if( methodAnnotation.methodResponseClass() != null )
					{
						method.setMethodResponseClass(methodAnnotation.methodResponseClass().getName());
						method.setResponseJSONStructure(buildJSONFromEmptyPojo(method.getMethodResponseClass()));
					}
					if( methodAnnotation.producesMediaType() != null
							&& !methodAnnotation.producesMediaType().isEmpty() )
					{
						method.setProducesMediaType(methodAnnotation.producesMediaType());
					}
					if( methodAnnotation.methodType() != null )
					{
						method.setMethodType(methodAnnotation.methodType());
					}
					if( methodAnnotation.verbs() != null && methodAnnotation.verbs().length > 0 )
					{
						// if method has no verbs except the defaults, ignore
						// adding verb to this service method
						if( !(methodAnnotation.verbs().length == 1 && methodAnnotation.verbs()[0].isEmpty()) )
						{
							method.setVerbs(methodAnnotation.verbs());
						}
					}

					List<RESTServiceMethodResponseCode> responseCodes = new ArrayList<>();
					if( methodAnnotation.responseCodes() != null )
					{
						RZTRESTServiceResponseCode[] codes = methodAnnotation.responseCodes();
						for( RZTRESTServiceResponseCode c : codes )
						{
							RESTServiceMethodResponseCode rCode = new RESTServiceMethodResponseCode();
							rCode.setDescription(c.description());
							rCode.setHttpCode(c.httpCode());
							rCode.setStatusCode(c.statusCode());
							responseCodes.add(rCode);
						}
						method.setResponseCodes(responseCodes);
					}

					List<RESTServiceHeader> methodHeaders = new ArrayList<>();
					if( methodAnnotation.headers() != null )
					{
						RZTRESTServiceHeader[] headers = methodAnnotation.headers();
						for( RZTRESTServiceHeader h : headers )
						{
							RESTServiceHeader rHeader = new RESTServiceHeader();
							rHeader.setDescription(h.description());
							rHeader.setName(h.name());
							rHeader.setAllowedValues(h.valuesAllowed());
							methodHeaders.add(rHeader);
						}
					}

					if( methodAnnotation.tags() != null && methodAnnotation.tags().length > 0 )
					{
						List<String> tagsList = new ArrayList<>();
						for( String tag : methodAnnotation.tags() )
						{
							tagsList.add(tag);
						}
						method.setTags(tagsList);
					}

					if( !methodHeaders.isEmpty() )
					{
						method.setHeaders(methodHeaders);
					}

					if( methodAnnotation.version() != null )
					{
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
		}
		else
		{
			throw new Exception("Not annotated");
		}

	}

	public RESTApplicationAPISpec scanPackages( String packageName )
			throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		System.out.println("Building model...");

		RESTApplicationAPISpec api = new RESTApplicationAPISpec();
		List<Class<?>> classes = ClassFinder.find(packageName);
		for( Class<?> c : classes )
		{
			// ignore inner classes
			if( !c.getName().contains("$") )
			{
				RESTService svc;
				try
				{
					svc = scanClass(c);
					api.getServices().add(svc);
					api.setAppName(appName);
				}
				catch( Exception e )
				{
					if( e.getMessage().toLowerCase().contains("not annotated") )
					{
						System.out.println("Ignoring analysis on class " + c.getName());
					}
					else
						e.printStackTrace();
				}
			}
		}
		JSONUtils.printJson(api);
		return api;
	}

	public void scanPackagesAndGenerate( String packageName, String destDirectorydirectory )
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException
	{
		File f = new File(destDirectorydirectory + File.separator + "api_spec_doc.json");
		FileUtils.writeFile(f.getAbsolutePath(), JSONUtils.stringify(scanPackages(packageName)));
		System.out.println("Wrote " + f.getAbsolutePath());
	}

	private <T> String buildJSONFromEmptyPojo( String className ) throws ClassNotFoundException
	{
		AbstractRandomDataProviderStrategy providerStrategy = new AbstractRandomDataProviderStrategy() {

			@Override
			public String getStringValue( AttributeMetadata attributeMetadata )
			{
				return UUID.randomUUID().toString();
			}
		};
		providerStrategy.setMemoization(true);

		PodamFactory factory = new PodamFactoryImpl(providerStrategy);

		@SuppressWarnings( "unchecked" )
		T pojoObj = (T) factory.manufacturePojoWithFullData(Class.forName(className));
		return new GsonBuilder().setPrettyPrinting().create().toJson(pojoObj);
	}

	@SuppressWarnings( "unused" )
	private AppType analyseAppType( Class<?> className )
	{
		if( className.getSuperclass().getName().equals("javax.servlet.http.HttpServlet") )
		{
			return AppType.SERVLET_3_0;
		}
		for( Annotation a : className.getAnnotations() )
		{
			if( a.annotationType().getName().equals("javax.ws.rs.Path") )
			{
				return AppType.JERSEY_2X;
			}
		}
		// System.out.println("Parent: " + className.getSuperclass().getName() +
		// "---->Child: " + className.getName());
		return AppType.JERSEY_2X;
	}
}

class ClassFinder {

	private static final char DOT = '.';

	private static final char SLASH = '/';

	private static final String CLASS_SUFFIX = ".class";

	private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

	public static List<Class<?>> find( String scannedPackage )
	{
		String scannedPath = scannedPackage.replace(DOT, SLASH);
		URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
		if( scannedUrl == null )
		{
			throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
		}
		File scannedDir = new File(scannedUrl.getFile());
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for( File file : scannedDir.listFiles() )
		{
			classes.addAll(find(file, scannedPackage));
		}
		return classes;
	}

	private static List<Class<?>> find( File file, String scannedPackage )
	{
		List<Class<?>> classes = new ArrayList<Class<?>>();
		String resource = scannedPackage + DOT + file.getName();
		if( file.isDirectory() )
		{
			for( File child : file.listFiles() )
			{
				classes.addAll(find(child, resource));
			}
		}
		else if( resource.endsWith(CLASS_SUFFIX) )
		{
			int endIndex = resource.length() - CLASS_SUFFIX.length();
			String className = resource.substring(0, endIndex);
			try
			{
				classes.add(Class.forName(className));
			}
			catch( ClassNotFoundException ignore )
			{
			}
		}
		return classes;
	}

}