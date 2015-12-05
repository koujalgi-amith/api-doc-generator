package com.koujalgiamith.doc.model;

import java.util.ArrayList;
import java.util.List;

public class RESTApplicationAPISpec {
	private String appName;
	private AppType appType;
	private List<RESTService> services = new ArrayList<>();

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public AppType getAppType() {
		return appType;
	}

	public void setAppType(AppType appType) {
		this.appType = appType;
	}

	public List<RESTService> getServices() {
		return services;
	}

	public void setServices(List<RESTService> services) {
		this.services = services;
	}

}
