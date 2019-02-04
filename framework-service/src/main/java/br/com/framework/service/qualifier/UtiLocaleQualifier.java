package br.com.framework.service.qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import br.com.framework.service.impl.LocaleType;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD , ElementType.TYPE, ElementType.METHOD })
public @interface UtiLocaleQualifier {
	
	LocaleType value();
}
