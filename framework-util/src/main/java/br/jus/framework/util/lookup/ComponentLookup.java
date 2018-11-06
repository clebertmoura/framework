package br.jus.framework.util.lookup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;

import br.jus.framework.util.ejb.LookupUtils;

/**
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
public class ComponentLookup implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String COMPONENT_LOOKUP_CLASS = "componentLookup.class";
	public static final String SERVER_APP_NAME = "serverAppName";
	public static final String SERVER_MODULE_NAME = "serverModuleName";
	
	public static final String[] JNDI_NAME_PATTERNS = new String[] {
		"java:module/%distinctName%!%interfaceName%",
		"java:app/%moduleName%/%distinctName%!%interfaceName%",
		"java:global/%appName%/%moduleName%/%distinctName%!%interfaceName%"
	};

	/**
	 * The app name is the application name of the deployed EJBs. This is typically the ear name
	 * without the .ear suffix. However, the application name could be overridden in the application.xml of the
	 * EJB deployment on the server.
	 * Since we haven't deployed the application as a .ear, the app name for us will be an empty string
	 */
	protected String appName;
	
	/**
	 * This is the module name of the deployed EJBs on the server. This is typically the jar name of the
	 * EJB deployment, without the .jar suffix, but can be overridden via the ejb-jar.xml
	 * In this example, we have deployed the EJBs in a jboss-as-ejb-remote-app.jar, so the module name is
	 * jboss-as-ejb-remote-app
	 */
	protected String moduleName;

	public ComponentLookup() {
	}

	/**
	 * @param appName
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @param moduleName
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * Retorna o nome JNDI do componente.
	 * 
	 * @param jndiNamePattern
	 * @param clazz
	 * @param distinctName
	 * @param isStateful
	 * @return
	 */
	public String getJNDI(String jndiNamePattern, Class<?> clazz, String distinctName, boolean isStateful) {
		String jndiName = StringUtils.replace(jndiNamePattern, "%appName%", appName);
		jndiName = StringUtils.replace(jndiName, "%moduleName%", moduleName);
		jndiName = StringUtils.replace(jndiName, "%distinctName%", distinctName);
		jndiName = StringUtils.replace(jndiName, "%interfaceName%", clazz.getName());
		if (isStateful) {
			jndiName = jndiName + "?stateful";
		}
		return jndiName;
	}

	/**
	 * @param jndiNamePattern
	 * @param clazz
	 * @return
	 */
	public String getJNDI(String jndiNamePattern, Class<?> clazz) {
		return getJNDI(jndiNamePattern, clazz, "", false);
	}
	
	/**
	 * @param jndiNamePattern
	 * @param clazz
	 * @param isStateful
	 * @return
	 */
	public String getJNDI(String jndiNamePattern, Class<?> clazz, boolean isStateful) {
		return getJNDI(jndiNamePattern, clazz, "", isStateful);
	}
	
	/**
	 * 
	 * @param Class implementation of EJB
	 * @return first interface
	 */
	protected Class<?> getInterfaceName(Class<?> clazz) {
		return clazz.getInterfaces()[0];
	}
	
	/**
	 * Faz o lookup do componente utilizando
	 * 
	 * @param componentInterface
	 * @param distinctName
	 * @param isStateful
	 * @param context 
	 * 	Caso seja null, cria um novo {@link InitialContext}.
	 * @return
	 * @throws NamingException
	 */
	public <T> T lookup(Class<T> componentInterface, String distinctName, boolean isStateful, InitialContext context) throws NamingException {
		boolean close = false;
		if (context == null) {
			context = LookupUtils.getInitialContext(null);
			close = true;
		}
		T result = null;
		try {
			List<String> distinctNames = new ArrayList<String>();
			if (distinctName != null) {
				distinctNames.add(distinctName);
			} else {
				distinctNames.add(componentInterface.getSimpleName());
				distinctNames.add(componentInterface.getSimpleName() + "Impl");
				distinctNames.add(componentInterface.getSimpleName() + "Bean");
			}
			forComponentNames:
			for (String componentName : distinctNames) {
				for (String jndiPattern : JNDI_NAME_PATTERNS) {
					T component = null;
					try {
						component = (T) LookupUtils.getComponent(componentInterface, getJNDI(jndiPattern, componentInterface, componentName, isStateful), context);
					} catch(NamingException e) {
						continue;
					}
					if (component != null && componentInterface.isAssignableFrom(component.getClass())) {
						result = component;
						break forComponentNames;
					}
				}
			}
		} finally {
			if (close) {
				try {
					context.close();
				} catch (Exception e) {}
			}
		}
		if (result == null)
			throw new NamingException(String.format("No component found %s/%s", componentInterface.getName(), distinctName != null ? distinctName : ""));
		return result;
	}

	/**
	 * Faz o lookup do componente utilizando seu distinctName
	 * 
	 * @param componentInterface
	 * @param distinctName
	 * @param isStateful
	 * @return
	 * @throws NamingException
	 */
	public <T> T lookup(Class<T> componentInterface, String distinctName, boolean isStateful) throws NamingException {
		return lookup(componentInterface, distinctName, isStateful, null);
	}
	
	/**
	 * Faz o lookup do componente
	 * 
	 * @param componentInterface
	 * @param isStateful
	 * @param context
	 * 	{@link InitialContext}
	 * @return
	 * @throws NamingException
	 */
	public <T> T lookup(Class<T> componentInterface, boolean isStateful, InitialContext context) throws NamingException {
		return lookup(componentInterface, null, isStateful, context);
	}
	
	/**
	 * Faz o lookup do componente
	 * 
	 * @param componentClazz
	 * @param isStateful
	 * @return
	 * @throws NamingException
	 */
	public <T> T lookup(Class<T> componentClazz, boolean isStateful) throws NamingException {
		return lookup(componentClazz, isStateful, null);
	}
	
}