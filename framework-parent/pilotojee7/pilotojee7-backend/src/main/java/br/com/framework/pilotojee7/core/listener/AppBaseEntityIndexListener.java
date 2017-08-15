/**
 * 
 */
package br.com.framework.pilotojee7.core.listener;

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import br.com.framework.search.indexer.api.service.IndexerService;
import br.com.framework.search.indexer.listener.IndexedBaseEntityListener;


/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
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
		IndexerService object = null;
		if (!beans.isEmpty()) {
			Bean<IndexerService> bean = (Bean<IndexerService>) beans.iterator().next();
	        CreationalContext<IndexerService> ctx = beanManager.createCreationalContext(bean);
	        object = (IndexerService) beanManager.getReference(bean, IndexerService.class, ctx);
		}
		return object;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.model.indexer.listener.BaseEntityIndexListener#onPostPersist(java.lang.Object)
	 */
	@Override
	@PostPersist
	public void onPostPersist(Object object) {
		super.onPostPersist(object);
	}

	/* (non-Javadoc)
	 * @see br.com.framework.model.indexer.listener.BaseEntityIndexListener#onPostUpdate(java.lang.Object)
	 */
	@Override
	@PostUpdate
	public void onPostUpdate(Object object) {
		super.onPostUpdate(object);
	}

	/* (non-Javadoc)
	 * @see br.com.framework.model.indexer.listener.BaseEntityIndexListener#onPostRemove(java.lang.Object)
	 */
	@Override
	@PostRemove
	public void onPostRemove(Object object) {
		super.onPostRemove(object);
	}

}
