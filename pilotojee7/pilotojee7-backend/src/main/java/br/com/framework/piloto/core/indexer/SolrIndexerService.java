/**
 * 
 */
package br.com.framework.piloto.core.indexer;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

import br.com.framework.search.indexer.api.service.IndexerService;
import br.com.framework.piloto.core.util.Constants;
import br.com.framework.util.Config;

/**
 * Implementação Solr do {@link IndexerService}.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@ApplicationScoped
public class SolrIndexerService implements IndexerService {
	
	private static final int CHECK_INTERVAL = 30000;
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(SolrIndexerService.class.getName());
	
	private SolrServer solrServer;
	private boolean isActive;
	private Date ultimoCheckStatus = new Date();
	private Config modelConfig;
	
	private boolean running;

	/**
	 * 
	 */
	private SolrIndexerService() {
		modelConfig = new Config(Thread.currentThread().getContextClassLoader(), Constants.CONFIG_FILENAME);
	}
	
	protected void init() {
		if (modelConfig.getPropriedade(Constants.INDEXER_ACTIVE) != null) {
			boolean indexerActive = Boolean.valueOf(modelConfig.getPropriedade(Constants.INDEXER_ACTIVE));
			if (indexerActive) {
				String serverAddress = modelConfig.getPropriedade(Constants.INDEXER_SERVER_ADDRESS);
				String collectionName = modelConfig.getPropriedade(Constants.INDEXER_COLLECTION_NAME);
				solrServer = new HttpSolrServer(String.format("%s/%s", serverAddress, collectionName));
				startService();
			}
		}
	}
	
	/**
	 * @return the running
	 */
	protected boolean isRunning() {
		return running;
	}

	/**
	 * 
	 */
	protected void checkServerStatus() {
		boolean isServerActive = false;
		if (solrServer != null) {
			LOG.fine("Checando o status do servico de indexaxao Solr.");
			try {
				ultimoCheckStatus = new Date();
				solrServer.ping();
				LOG.fine("Status do servico de indexaxao Solr: OK.");
				isServerActive = true;
			} catch (SolrServerException e) {
				LOG.log(Level.SEVERE, "Error ao realizar ping no servidor Solr. " + e.getMessage());
			} catch (Exception e) {
				LOG.log(Level.SEVERE, "Error inesperado ao conectar no servidor Solr.", e);
			}
		}
		this.isActive = isServerActive;
	}
	
	private void createThreadCheck() {
		LOG.fine("Criando thread de verificacao do servico de indexaxao Solr.");
		Thread thread = new Thread("CheckSolrServerStatus") {
			@Override
			public void run() {
				LOG.fine("Iniciando thread de verificacao do servico de indexaxao Solr.");
				synchronized (this) {
					running = true;
					while (running) {
						try {
							LOG.log(Level.FINE, String.format("Aguardando intervalo (%s millis) para verificar status do servidor Solr.", CHECK_INTERVAL));
							this.wait(CHECK_INTERVAL);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if (running) 
							checkServerStatus();
					}
				}
				LOG.info("Finalizando thread de verificacao do servico de indexacao Solr.");
			}
		};
		thread.start();
	}

	/**
	 * @return the solrService
	 */
	public SolrServer getSolrServer() {
		return solrServer;
	}

	/**
	 * @return the isActive
	 */
	public boolean isServiceActive() {
		synchronized (this) {
			if ((System.currentTimeMillis() - ultimoCheckStatus.getTime()) > CHECK_INTERVAL) {
				checkServerStatus();
			}
		}
		return isActive;
	}
	
	public void startService() {
		if (!running) {
			LOG.fine("Iniciando o servico de indexaxao Solr.");
			if (modelConfig.getPropriedade(Constants.INDEXER_ACTIVE) != null) {
				boolean indexerActive = Boolean.valueOf(modelConfig.getPropriedade(Constants.INDEXER_ACTIVE));
				if (indexerActive) {
					String serverAddress = modelConfig.getPropriedade(Constants.INDEXER_SERVER_ADDRESS);
					String collectionName = modelConfig.getPropriedade(Constants.INDEXER_COLLECTION_NAME);
					solrServer = new HttpSolrServer(String.format("%s/%s", serverAddress, collectionName));
					
					checkServerStatus();
					createThreadCheck();
				}
			}
		} else {
			LOG.warning("O servico de indexaxao Solr ja esta rodando.");
		}
	}
	
	@Override
	public void stopService() {
		LOG.fine("Parando o servico de indexaxao Solr.");
		synchronized (this) {
			this.running = false;
			this.notifyAll();
		}
	}
	
}
