package com.razorthink.utils.apidoc.model;

import java.util.ArrayList;
import java.util.List;

public class RESTAPIServiceMethodURLParam {
	public String name, desc;
	public List<String> possibleValues = new ArrayList<>();
	public int order;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<String> getPossibleValues() {
		return possibleValues;
	}

	public void setPossibleValues(List<String> possibleValues) {
		this.possibleValues = possibleValues;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
