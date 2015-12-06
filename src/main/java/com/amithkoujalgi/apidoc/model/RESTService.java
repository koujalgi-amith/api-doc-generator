package com.amithkoujalgi.apidoc.model;

import java.util.ArrayList;
import java.util.List;

public class RESTService {
	private String serviceName, serviceClassName, serviceDescription, path;

	public String getServiceClassName() {
		return serviceClassName;
	}

	public void setServiceClassName(String serviceClassName) {
		this.serviceClassName = serviceClassName;
	}

	private List<RESTServiceHeader> headers;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<RESTServiceHeader> getHeaders() {
		return headers;
	}

	public void setHeaders(List<RESTServiceHeader> headers) {
		this.headers = headers;
	}

	public List<RESTServiceMethod> getMethods() {
		return methods;
	}

	public void setMethods(List<RESTServiceMethod> methods) {
		this.methods = methods;
	}

	private List<RESTServiceMethod> methods = new ArrayList<>();

}
