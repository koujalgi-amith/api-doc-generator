package com.koujalgiamith.apidoc.core.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.constraints.Null;

@Documented
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RZTRESTServiceMethod {
	public String version() default "1.0";

	public String path();

	public String methodDescription();

	public RESTMethodType methodType();

	public RZTRESTServiceHeader[]headers() default {};

	public String[]verbs() default { "" };

	public String consumesMediaType() default "";

	public Class<?>methodRequestClass() default Null.class;

	public Class<?>methodResponseClass();

	public String producesMediaType() default "";

	public enum RESTMethodType {
		GET, PUT, POST, DELETE
	}

	public RZTRESTServiceResponseCode[]responseCodes();

	public boolean visible() default true;
}
