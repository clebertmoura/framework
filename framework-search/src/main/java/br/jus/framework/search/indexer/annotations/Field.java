package br.jus.framework.search.indexer.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Anotação para definição de campo indexado em uma entidade.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
@Target({ FIELD, METHOD })
@Retention(RUNTIME)
public @interface Field {
	public static final String DEFAULT = "#default";
	String value() default DEFAULT;
	boolean skip() default false;
}
