package com.razorthink.utils.apidoc.model;

import java.util.ArrayList;
import java.util.List;

public class RESTAPISpecification {

	private String appName;
	private List<RESTAPIService> services = new ArrayList<>();

	public String getAppName()
	{
		return appName;
	}

	public void setAppName( String appName )
	{
		this.appName = appName;
	}

	public List<RESTAPIService> getServices()
	{
		return services;
	}

	public void setServices( List<RESTAPIService> services )
	{
		this.services = services;
	}

}
