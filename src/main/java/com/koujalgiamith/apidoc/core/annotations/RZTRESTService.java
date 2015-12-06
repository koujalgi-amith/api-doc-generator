package com.koujalgiamith.apidoc.core.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RZTRESTService {

	public String version() default "1.0";

	public String path();

	public String serviceDescription() default "";

	public String serviceName() default "";

	public RZTRESTServiceHeader[]headers() default {};

	public boolean deprecated() default false;

	public boolean visible() default true;

}
