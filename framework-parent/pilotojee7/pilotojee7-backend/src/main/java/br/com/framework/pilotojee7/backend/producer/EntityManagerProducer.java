package br.com.framework.pilotojee7.backend.producer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.model.qualifiers.DataRepository;

/**
 * Responsável pela criação das instâncias do {@link EntityManager}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class EntityManagerProducer {
	
	private static Logger LOG = LoggerFactory.getLogger(EntityManagerProducer.class);

    // Use PersistenceUnit in a container environment
    @PersistenceUnit(unitName = "primary")
    @Produces @DataRepository
    private EntityManagerFactory entityManagerFactory;
    
    /**
     * Produces an entity manager from the entity manager favtory.
     *
     * @param entityManagerFactory
     * @return
     */
    @Produces
    @RequestScoped
    @DataRepository
    public EntityManager produceEntityManager(@DataRepository EntityManagerFactory entityManagerFactory) {
        EntityManager em = entityManagerFactory.createEntityManager();
        if (LOG.isDebugEnabled()) {
        	LOG.debug(String.format("Criando entityManager %s", em));
        }
        return em;
    }

    /**
     * Closes entity manager.
     *
     * @param em
     */
    public void close(@Disposes @DataRepository EntityManager em) {
    	if (LOG.isDebugEnabled()) {
    		LOG.debug(String.format("Fechando EntityManager %s", em));
        }
        em.close();
    }
    
}
