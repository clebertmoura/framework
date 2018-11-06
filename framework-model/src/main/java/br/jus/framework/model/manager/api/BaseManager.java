package br.jus.framework.model.manager.api;

import java.io.Serializable;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import br.jus.framework.domain.api.BaseEntity;
import br.jus.framework.domain.api.BaseEntityAudited;
import br.jus.framework.model.exception.ModelException;
import br.jus.framework.search.api.Search;

/**
 * Interface base para as classes de negocio do sistema.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>

 * @param <PK> Tipo da chave primária. 
 * @param <E> Tipo da entidade de domínio.
 * @param <B> Interface de pesquisa
 */
public interface BaseManager<PK extends Serializable, E extends BaseEntity<PK>, B extends Search<PK, E>> extends Serializable {
	
	/**
	 * Insere uma nova entidade.
	 * 
	 * @param entidade
	 * @return
	 * @throws PersistenceException
	 * @throws ConstraintViolationException
	 * @throws ModelException
	 */
	public abstract E insert(E entidade) throws PersistenceException, ConstraintViolationException, ModelException;

	/**
	 * Altera uma entidade existente.
	 * 
	 * @param entidade
	 * @return
	 * @throws PersistenceException
	 * @throws ConstraintViolationException
	 * @throws ModelException
	 */
	public abstract E update(E entidade) throws PersistenceException, ConstraintViolationException, ModelException;

	/**
	 * Remove uma entidade existente. Caso a entidade pertença a hierarquia de {@link BaseEntityAudited}, o registro será apenas inativado.
	 * 
	 * @param entidade
	 * @return
	 * @throws PersistenceException
	 * @throws ConstraintViolationException
	 * @throws ModelException
	 */
	public abstract E remove(E entidade) throws PersistenceException, ConstraintViolationException, ModelException;
	
	/**
	 * Remove uma entidade existente pelo ID. Caso a entidade pertença a hierarquia de {@link BaseEntityAudited}, o registro será apenas inativado.
	 * 
	 * @param id
	 * @return
	 * @throws PersistenceException
	 * @throws ConstraintViolationException
	 * @throws ModelException
	 */
	public abstract E remove(PK id) throws PersistenceException, ConstraintViolationException, ModelException;
	
	/**
	 * Remove uma entidade existente de forma definitiva.
	 * 
	 * @param entity
	 * @return
	 * @throws PersistenceException
	 * @throws ConstraintViolationException
	 * @throws ModelException
	 */
	public E removeDefinitely(E entity) throws PersistenceException, ConstraintViolationException, ModelException;
	
	/**
	 * Remove uma entidade existente pelo ID de forma definitiva.
	 * 
	 * @param id
	 * @return
	 * @throws PersistenceException
	 * @throws ConstraintViolationException
	 * @throws ModelException
	 */
	public abstract E removeDefinitely(PK id) throws PersistenceException, ConstraintViolationException, ModelException;

	/**
	 * Faz um refresh na entidade.
	 * 
	 * @param entidade
	 * @return
	 * @throws PersistenceException
	 */
	public abstract E refresh(E entidade) throws PersistenceException;

	/**
	 * Desvincula a entidade do contexto de persistencia.
	 * 
	 * @param entidade
	 * @throws PersistenceException
	 */
	public abstract void detach(E entidade) throws PersistenceException;
/*
	*//**
	 * Consulta todas as revisões de uma entidade anotada com @Audited
	 * @param entidade
	 * @return
	 *//*
	public List<Object> findRevisionLogsById(E entidade) throws ModelException;*/
	
}