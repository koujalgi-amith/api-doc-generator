package com.amithkoujalgi.apidoc.model;

import java.util.ArrayList;
import java.util.List;

public class RESTApplicationAPISpec {

	private String appName;
	private List<RESTService> services = new ArrayList<>();

	public String getAppName()
	{
		return appName;
	}

	public void setAppName( String appName )
	{
		this.appName = appName;
	}

	public List<RESTService> getServices()
	{
		return services;
	}

	public void setServices( List<RESTService> services )
	{
		this.services = services;
	}

}
