package br.com.framework.model.qualifiers;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Properties;

import javax.inject.Qualifier;

/**
 * Anotação para injeção do {@link Properties} de confguração da aplicação.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, METHOD, FIELD, PARAMETER})
public @interface AppConfig {}