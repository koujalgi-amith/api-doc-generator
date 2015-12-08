package com.amithkoujalgi.apidoc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target( value = ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
public @interface RZTRESTServiceResponseCode {

	public String httpCode();

	public String description();

	public String statusCode();
}
