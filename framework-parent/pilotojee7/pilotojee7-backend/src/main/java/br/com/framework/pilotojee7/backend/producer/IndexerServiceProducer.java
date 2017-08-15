package br.com.framework.pilotojee7.backend.producer;


import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.model.qualifiers.Destroyed;
import br.com.framework.model.qualifiers.Initialized;
import br.com.framework.search.indexer.api.service.IndexerService;

/**
 * Responsável pela criação do {@link IndexerService}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class IndexerServiceProducer {
	
	private static Logger LOG = LoggerFactory.getLogger(IndexerServiceProducer.class);

    @Inject
    private IndexerService solrService;
    
    /**
     * Observa a inicialização do contexto da aplicação
     * 
     * @param ctx
     */
    public void onStartup(@Observes @Initialized ServletContext ctx) {
    	if (LOG.isDebugEnabled()) {
    		LOG.debug("Initialized web application at context path " + ctx.getContextPath());
    	}
    	solrService.startService();
    }
    
	/**
	 * Observa a destruição do contexto da aplicação.
	 * 
	 * @param ctx
	 */
	public void onDestroy(@Observes @Destroyed ServletContext ctx) {
		if (LOG.isDebugEnabled()) {
    		LOG.debug("Destroyed web application at context path " + ctx.getContextPath());
    	}
		solrService.stopService();
	}
    
}
