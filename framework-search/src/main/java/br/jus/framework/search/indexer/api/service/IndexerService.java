/**
 * 
 */
package br.jus.framework.search.indexer.api.service;

import java.io.Serializable;

import org.apache.solr.client.solrj.SolrServer;

/**
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
public interface IndexerService extends Serializable {
	
	/**
	 * @return the indexerService
	 */
	public SolrServer getSolrServer();

	/**
	 * @return the isActive
	 */
	public boolean isServiceActive();
	
	/**
	 * 
	 */
	public void startService();
	
	/**
	 * 
	 */
	public void stopService();
	
}
