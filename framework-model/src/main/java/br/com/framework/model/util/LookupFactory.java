package br.com.framework.model.util;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.util.Config;
import br.com.framework.util.lookup.ComponentLookup;

/**
 * Fabrica responsável por construir o {@link ComponentLookup} de acordo com o servidor de aplicação.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Named
@ApplicationScoped
public class LookupFactory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(LookupFactory.class);
	private static LookupFactory INSTANCE;
	
	private ComponentLookup componentLookup;
	private Config config;
	
	public LookupFactory() {
		config = new Config(Thread.currentThread().getContextClassLoader(), Constantes.CONFIG_FILENAME);
		buildComponentLookup();
	}
	
	/**
	 * Inicializa o {@link ComponentLookup}
	 */
	private void buildComponentLookup() {
		String componentLookupClass, appName, moduleName;
		try {
			componentLookupClass = config.getPropriedade(ComponentLookup.COMPONENT_LOOKUP_CLASS);
			appName = config.getPropriedade(ComponentLookup.SERVER_APP_NAME);
			moduleName = config.getPropriedade(ComponentLookup.SERVER_MODULE_NAME);

			if ((componentLookupClass == null || componentLookupClass.isEmpty())
					|| (appName == null || appName.isEmpty())
					|| (moduleName == null || moduleName.isEmpty())) {
				LOGGER.warn("Faltando propriedade(s) no arquivo de configuraçãoo");
			} else {
				componentLookup = (ComponentLookup) Class.forName(componentLookupClass).newInstance();
				componentLookup.setAppName(appName);
				componentLookup.setModuleName(moduleName);
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao inicializar ComponentLookup.", e);
		}
	}
	
	public static LookupFactory get() {
		if (INSTANCE == null) {
			INSTANCE = new LookupFactory();
		}
		return INSTANCE;
	}

	/**
	 * Retorna o {@link ComponentLookup}
	 * @return
	 */
	public ComponentLookup getComponentLookup() {
		return componentLookup;
	}
	
}