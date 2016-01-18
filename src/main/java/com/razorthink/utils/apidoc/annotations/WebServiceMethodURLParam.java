package com.razorthink.utils.apidoc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebServiceMethodURLParam {
	public String name();

	public String desc();

	public String[]possibleValues() default {};

	public int order() default 1;
}