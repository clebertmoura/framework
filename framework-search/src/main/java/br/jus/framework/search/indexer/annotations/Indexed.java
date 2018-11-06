package br.jus.framework.search.indexer.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Anotação para definição do uma entidade indexada.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface Indexed {
	public static final String DEFAULT = "id";
	String compositeIdField() default DEFAULT;
}
