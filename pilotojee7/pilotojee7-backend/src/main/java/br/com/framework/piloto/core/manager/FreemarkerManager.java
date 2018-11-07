/**
 * 
 */
package br.com.framework.piloto.core.manager;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>.t.moura
 *
 */
@Singleton
public class FreemarkerManager implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8638241699559098987L;

	private Configuration configuration;

	/**
	 * 
	 */
	public FreemarkerManager() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	protected void postConstruct() {
		configuration = new Configuration(Configuration.VERSION_2_3_24);
		configuration.setClassForTemplateLoading(FreemarkerManager.class, "/fmtl");
		configuration.setDefaultEncoding("UTF-8");
		// Sets how errors will appear. During web page *development*
		// TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		//configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		configuration.setLogTemplateExceptions(false);
	}

	public Configuration getConfiguration() {
		return configuration;
	}
	
}
