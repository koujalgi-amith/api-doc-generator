package com.razorthink.utils.apidoc.model;

import java.util.ArrayList;
import java.util.List;

public class RESTAPIService {

	private String serviceName, serviceClassName, serviceDescription, path;
	private List<String> tags;

	public String getServiceClassName()
	{
		return serviceClassName;
	}

	public void setServiceClassName( String serviceClassName )
	{
		this.serviceClassName = serviceClassName;
	}

	private List<RESTAPIServiceHeader> headers;

	public String getServiceName()
	{
		return serviceName;
	}

	public List<String> getTags()
	{
		return tags;
	}

	public void setTags( List<String> tags )
	{
		this.tags = tags;
	}

	public void setServiceName( String serviceName )
	{
		this.serviceName = serviceName;
	}

	public String getServiceDescription()
	{
		return serviceDescription;
	}

	public void setServiceDescription( String serviceDescription )
	{
		this.serviceDescription = serviceDescription;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath( String path )
	{
		this.path = path;
	}

	public List<RESTAPIServiceHeader> getHeaders()
	{
		return headers;
	}

	public void setHeaders( List<RESTAPIServiceHeader> headers )
	{
		this.headers = headers;
	}

	public List<RESTAPIServiceMethod> getMethods()
	{
		return methods;
	}

	public void setMethods( List<RESTAPIServiceMethod> methods )
	{
		this.methods = methods;
	}

	private List<RESTAPIServiceMethod> methods = new ArrayList<>();

}
