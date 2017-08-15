/**
 * 
 */
package br.com.framework.codegen.annotations;

/**
 * Anotação os atributos de imagem.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public @interface Image {

	/**
	 * Indica se deve apresentar um preview da imagem.
	 * 
	 * @return
	 */
	boolean preview() default false;
	
	/**
	 * Indica se deve permitir o corte da imagem.
	 * 
	 * @return
	 */
	boolean cropped() default false;
	
}
