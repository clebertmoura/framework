/**
 * 
 */
package br.jus.framework.util.ejb;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Classe utilitária para efetuar lookup de componentes no servidor.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 * 
 */
public class LookupUtils {

	/**
	 * 
	 */
	private LookupUtils() {
	}

	/**
	 * Cria o {@link InitialContext} para lookup de componentes.
	 * 
	 * @param p
	 * @return
	 * @throws NamingException
	 */
	public static InitialContext getInitialContext(Properties p)
			throws NamingException {
		return new InitialContext(p);
	}


	/**
	 * Faz o lookup do componente informado.
	 * 
	 * @param componentClazz
	 * @param componentName
	 * @param context
	 * @return
	 * @throws NamingException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getComponent(Class<T> componentClazz,
			String componentName, InitialContext context)
			throws NamingException {
		boolean close = false;
		if (context == null) {
			context = getInitialContext(null);
			close = true;
		}
		Object object = null;
		try {
			object = context.lookup(componentName);
		} finally {
			if (close) {
				try {
					context.close();
				} catch (Exception e) {}
			}
		}
		return (T) object;
	}
	
	/**
	 * Faz o lookup do componente informado.
	 * 
	 * @param componentClazz
	 * 	Classe ou interface que implementada pelo componente
	 * @param componentName
	 * 	Nome JNDI do componente.
	 * @return
	 * @throws NamingException
	 */
	public static <T> T getComponent(Class<T> componentClazz,
			String componentName)
			throws NamingException {
		return getComponent(componentClazz, componentName, null);
	}

}
