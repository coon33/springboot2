package org.coonchen.fk.annotation;


import java.lang.annotation.*;


@Repeatable(RequestMappingAssist.class)
public @interface ValidatorAnnotation {
	String name();
	String explain();
	String rule();
}

