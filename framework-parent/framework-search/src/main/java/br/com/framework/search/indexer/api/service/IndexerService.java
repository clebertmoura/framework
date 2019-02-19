/**
 * 
 */
package br.com.framework.search.indexer.api.service;

import java.io.Serializable;

import org.apache.solr.client.solrj.SolrServer;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
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
