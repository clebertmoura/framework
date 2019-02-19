/**
 * 
 */
package br.com.framework.codegen.annotations;

/**
 * Anotação para marcação das entidades que devem ser gerados os artefatos.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public @interface Gen {

	/**
	 * Indica se deve ser gerado o DAO da entidade. 
	 * @return
	 */
	boolean genDao() default true;
	/**
	 * Indica se deve ser gerado o Manager da entidade. 
	 * @return
	 */
	boolean genManager() default true;
	
}
