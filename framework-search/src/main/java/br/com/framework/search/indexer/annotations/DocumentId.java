package br.com.framework.search.indexer.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Anotação para definição do campo de identificação de um documento indexado.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Target({ FIELD, METHOD })
@Retention(RUNTIME)
public @interface DocumentId {
	public static final String DEFAULT = "#default";
	String value() default DEFAULT;
}
