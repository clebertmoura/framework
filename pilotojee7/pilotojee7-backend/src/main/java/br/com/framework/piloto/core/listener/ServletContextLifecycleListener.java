package br.com.framework.piloto.core.listener;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import br.com.framework.model.qualifiers.Destroyed;
import br.com.framework.model.qualifiers.Initialized;

/**
 * Classe de listener que monitora o start/stop do {@link ServletContext} da aplicação. 
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@WebListener
public class ServletContextLifecycleListener implements ServletContextListener {

	@Inject
	@Any
	private Event<ServletContext> servletContextEvent;

	@SuppressWarnings("serial")
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		servletContextEvent.select(
			new AnnotationLiteral<Initialized>(){}).fire(sce.getServletContext());
	}

	@SuppressWarnings("serial")
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		servletContextEvent.select(
			new AnnotationLiteral<Destroyed>(){}).fire(sce.getServletContext());
	}
}