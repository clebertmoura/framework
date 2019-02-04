package br.com.framework.model.manager.api;

import java.io.Serializable;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.domain.api.BaseEntityAudited;
import br.com.framework.model.exception.ModelException;
import br.com.framework.search.api.Search;

/**
 * Interface base para as classes de negocio do sistema.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>

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
	 * @throws ModelException
	 */
	public abstract E insert(E entidade) throws ModelException;

	/**
	 * Altera uma entidade existente.
	 * 
	 * @param entidade
	 * @return
	 * @throws PersistenceException
	 * @throws ModelException
	 */
	public abstract E update(E entidade) throws ModelException;

	/**
	 * Remove uma entidade existente. Caso a entidade pertenÃ§a a hierarquia de {@link BaseEntityAudited}, o registro serÃ¡ apenas inativado.
	 * 
	 * @param entidade
	 * @return
	 * @throws PersistenceException
	 * @throws ModelException
	 */
	public abstract E remove(E entidade) throws ModelException;
	
	/**
	 * Remove uma entidade existente pelo ID. Caso a entidade pertenÃ§a a hierarquia de {@link BaseEntityAudited}, o registro serÃ¡ apenas inativado.
	 * 
	 * @param id
	 * @return
	 * @throws PersistenceException
	 * @throws ModelException
	 */
	public abstract E remove(PK id) throws ModelException;
	
	/**
	 * Remove uma entidade existente de forma definitiva.
	 * 
	 * @param entity
	 * @return
	 * @throws PersistenceException
	 * @throws ModelException
	 */
	public E removeDefinitely(E entity) throws ModelException;
	
	/**
	 * Remove uma entidade existente pelo ID de forma definitiva.
	 * 
	 * @param id
	 * @return
	 * @throws PersistenceException
	 * @throws ModelException
	 */
	public abstract E removeDefinitely(PK id) throws ModelException;

	/**
	 * Faz um refresh na entidade.
	 * 
	 * @param entidade
	 * @return
	 * @throws PersistenceException
	 */
	public abstract E refresh(E entidade);

	/**
	 * Desvincula a entidade do contexto de persistencia.
	 * 
	 * @param entidade
	 * @throws PersistenceException
	 */
	public abstract void detach(E entidade);
/*
	*//**
	 * Consulta todas as revisões de uma entidade anotada com @Audited
	 * @param entidade
	 * @return
	 *//*
	public List<Object> findRevisionLogsById(E entidade) throws ModelException;*/
	
}