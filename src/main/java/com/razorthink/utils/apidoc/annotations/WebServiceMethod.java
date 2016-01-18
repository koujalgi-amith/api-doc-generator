package com.razorthink.utils.apidoc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.constraints.Null;

@Documented
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebServiceMethod {

	public String version() default "1.0";

	public String path();

	public String description();

	public RESTMethodType type();

	public WebServiceHeader[]headers() default {};

	public WebServiceMethodURLParam[]urlParameters() default {};

	public String consumesMediaType() default "";

	public Class<?>requestClass() default Null.class;

	public Class<?>responseClass();

	public String producesMediaType() default "";

	public enum RESTMethodType {
		GET, PUT, POST, DELETE
	}

	public WebServiceResponseCode[]responseCodes();

	/**
	 * The service method can be made visible or invisible.
	 */
	public boolean visible() default true;

	/**
	 * The service method can have tags, which can be used for grouping.
	 */
	public String[]tags() default {};

}