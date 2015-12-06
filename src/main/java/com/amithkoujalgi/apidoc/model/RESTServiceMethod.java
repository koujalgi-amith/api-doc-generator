package com.amithkoujalgi.apidoc.model;

import java.util.List;

import com.amithkoujalgi.apidoc.core.annotations.RZTRESTServiceMethod.RESTMethodType;

public class RESTServiceMethod {
	private String methodName, methodDescription, methodResponseClass, methodRequestClass, producesMediaType,
			consumesMediaType, path, endpoint, requestJSONStructure, responseJSONStructure, version;
	private List<RESTServiceMethodResponseCode> responseCodes;

	public List<RESTServiceHeader> getHeaders() {
		return headers;
	}

	public void setHeaders(List<RESTServiceHeader> headers) {
		this.headers = headers;
	}

	private List<RESTServiceHeader> headers;

	public List<RESTServiceMethodResponseCode> getResponseCodes() {
		return responseCodes;
	}

	public void setResponseCodes(List<RESTServiceMethodResponseCode> responseCodes) {
		this.responseCodes = responseCodes;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRequestJSONStructure() {
		return requestJSONStructure;
	}

	public void setRequestJSONStructure(String requestJSONStructure) {
		this.requestJSONStructure = requestJSONStructure;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getResponseJSONStructure() {
		return responseJSONStructure;
	}

	public void setResponseJSONStructure(String responseJSONStructure) {
		this.responseJSONStructure = responseJSONStructure;
	}

	private RESTMethodType methodType;
	private String[] verbs;

	public String[] getVerbs() {
		return verbs;
	}

	public void setVerbs(String[] verbs) {
		this.verbs = verbs;
	}

	public RESTMethodType getMethodType() {
		return methodType;
	}

	public void setMethodType(RESTMethodType methodType) {
		this.methodType = methodType;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodDescription() {
		return methodDescription;
	}

	public void setMethodDescription(String methodDescription) {
		this.methodDescription = methodDescription;
	}

	public String getMethodRequestClass() {
		return methodRequestClass;
	}

	public void setMethodRequestClass(String methodRequestClass) {
		this.methodRequestClass = methodRequestClass;
	}

	public String getMethodResponseClass() {
		return methodResponseClass;
	}

	public void setMethodResponseClass(String methodResponseClass) {
		this.methodResponseClass = methodResponseClass;
	}

	public String getProducesMediaType() {
		return producesMediaType;
	}

	public void setProducesMediaType(String producesMediaType) {
		this.producesMediaType = producesMediaType;
	}

	public String getConsumesMediaType() {
		return consumesMediaType;
	}

	public void setConsumesMediaType(String consumesMediaType) {
		this.consumesMediaType = consumesMediaType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
