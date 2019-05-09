/**
 * 
 */
package br.com.framework.piloto.core.util;

import javax.enterprise.context.ApplicationScoped;

import br.com.framework.util.resource.BaseMessageSource;
import br.com.framework.util.resource.MessageSource;

/**
 * Implementação do {@link MessageSource}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@ApplicationScoped
public class AppMessageSource extends BaseMessageSource {

	/**
	 * 
	 */
	public AppMessageSource() {
		setBasenames(
			"Mensagens",
			"ValidationMessages",
			"Enums");
	}

}
