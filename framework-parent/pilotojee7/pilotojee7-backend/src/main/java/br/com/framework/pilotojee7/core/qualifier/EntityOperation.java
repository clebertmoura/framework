package br.com.framework.pilotojee7.core.qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * Qualificador para eventos de Insert, Update, Delete ocorridos nas entidades.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER, ElementType.FIELD })
public @interface EntityOperation {
	
	OperationType operation();
	
}