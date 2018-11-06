/**
 * 
 */
package br.jus.framework.piloto.core.listener;

import java.util.Set;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import br.jus.framework.search.indexer.api.service.IndexerService;
import br.jus.framework.search.indexer.listener.IndexedBaseEntityListener;


/**
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
public class AppBaseEntityIndexListener extends IndexedBaseEntityListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private BeanManager beanManager;
	

	/**
	 * 
	 */
	public AppBaseEntityIndexListener() {
	}

	@Override
	public IndexerService getIndexerService() {
		Set<Bean<?>> beans = beanManager.getBeans(IndexerService.class);
		return !beans.isEmpty() ? (IndexerService) beans.iterator().next() : null;
	}

	/* (non-Javadoc)
	 * @see br.jus.framework.model.indexer.listener.BaseEntityIndexListener#onPostPersist(java.lang.Object)
	 */
	@Override
	@PostPersist
	public void onPostPersist(Object object) {
		super.onPostPersist(object);
	}

	/* (non-Javadoc)
	 * @see br.jus.framework.model.indexer.listener.BaseEntityIndexListener#onPostUpdate(java.lang.Object)
	 */
	@Override
	@PostUpdate
	public void onPostUpdate(Object object) {
		super.onPostUpdate(object);
	}

	/* (non-Javadoc)
	 * @see br.jus.framework.model.indexer.listener.BaseEntityIndexListener#onPostRemove(java.lang.Object)
	 */
	@Override
	@PostRemove
	public void onPostRemove(Object object) {
		super.onPostRemove(object);
	}

}
