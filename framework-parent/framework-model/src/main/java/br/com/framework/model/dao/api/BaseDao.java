package br.com.framework.model.dao.api;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.search.api.Search;
import br.com.framework.search.api.SearchResult;
import br.com.framework.search.api.SearchUniqueResult;
import br.com.framework.search.exception.SearchException;
import br.com.framework.search.impl.Ordering;
import br.com.framework.search.impl.Restriction;

/**
 * Interface base de um DAO (Data Access Object) das entidades de domínio.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK> Tipo da chave primária.
 * @param <E> Entidade de domínio.
 */
public interface BaseDao<PK extends Serializable, E extends BaseEntity<PK>>
		extends Search<PK, E> {
	
	/**
	 * Pesquisa a entidade pelo sua chave primária.
	 * 
	 * @param id
	 * @param entityGraphName - Nome do {@link EntityGraph} que será utilizado na query. Pode ser nulo.
	 * @return
	 * @throws SearchException
	 * @throws PersistenceException
	 */
	public SearchUniqueResult<E> findById(PK id, String entityGraphName) throws SearchException, PersistenceException;

	/**
	 * Pesquisa entidades com base na entidade exemplo informada, com ordenação e paginação.
	 * 
	 * @param e
	 * @param isLike
	 * @param isCaseSensitive
	 * @param primeiroResultado
	 * @param maxResultados
	 * @param ordenacoes
	 * @return
	 * @throws SearchException
	 * @throws PersistenceException
	 */
	public SearchResult<E> findByExample(E e, boolean isLike,
			boolean isCaseSensitive, int primeiroResultado, int maxResultados, Ordering... ordenacoes) throws SearchException, PersistenceException;
	
	/**
	 * Pesquisa entidades com base na entidade exemplo informada, com ordenação e paginação.
	 * 
	 * @param e
	 * @param isLike
	 * @param isCaseSensitive
	 * @param primeiroResultado
	 * @param maxResultados
	 * @param entityGraphName - Nome do {@link EntityGraph} que será utilizado na query. Pode ser nulo.
	 * @param ordenacoes
	 * @return
	 * @throws SearchException
	 * @throws PersistenceException
	 */
	public SearchResult<E> findByExample(E e, boolean isLike,
			boolean isCaseSensitive, int primeiroResultado, int maxResultados, String entityGraphName, Ordering... ordenacoes) throws SearchException, PersistenceException;

	/**
	 * Retorna a quantidade de registros com base na entidade exemplo informada.
	 * 
	 * @param e
	 * @param isLike
	 * @param isCaseSensitive
	 * @return
	 * @throws SearchException
	 * @throws PersistenceException
	 */
	public SearchUniqueResult<Long> getCountFindByExample(E e,
			boolean isLike, boolean isCaseSensitive) throws SearchException, PersistenceException;
	
	/**
	 * Pesquisa um registro único utilizando uma entidade exemplo.
	 * 
	 * @param e
	 * @param isLike
	 * @param isCaseSensitive
	 * @param entityGraphName - Nome do {@link EntityGraph} que será utilizado na query. Pode ser nulo.
	 * @return
	 * @throws SearchException
	 * @throws PersistenceException
	 * @throws NonUniqueResultException
	 */
	public SearchUniqueResult<E> findUniqueByExample(E e, boolean isLike,
			boolean isCaseSensitive, String entityGraphName) throws SearchException, PersistenceException, NonUniqueResultException;
	
	/**
	 * Pesquisa um registro único utilizando uma entidade exemplo.
	 * 
	 * @param e
	 * @param isLike
	 * @param isCaseSensitive
	 * @return
	 * @throws SearchException
	 * @throws PersistenceException
	 * @throws NonUniqueResultException
	 */
	public SearchUniqueResult<E> findUniqueByExample(E e, boolean isLike,
			boolean isCaseSensitive) throws SearchException, PersistenceException, NonUniqueResultException;

	/**
	 * Pesquisa um registro único utilizando uma entidade exemplo.
	 * 
	 * @param e
	 * @param entityGraphName - Nome do {@link EntityGraph} que será utilizado na query. Pode ser nulo.
	 * @return
	 * @throws SearchException
	 * @throws PersistenceException
	 * @throws NonUniqueResultException
	 */
	public SearchUniqueResult<E> findUniqueByExample(E e, String entityGraphName) throws SearchException, PersistenceException, NonUniqueResultException;
	
	/**
	 * Pesquisa um registro único utilizando uma entidade exemplo.
	 * 
	 * @param e
	 * @return
	 * @throws SearchException
	 * @throws PersistenceException
	 * @throws NonUniqueResultException
	 */
	public SearchUniqueResult<E> findUniqueByExample(E e) throws SearchException, PersistenceException, NonUniqueResultException;
	
	/**
	 * Desvincula o objeto do contexto atual.
	 * 
	 * @param entidade
	 * @return
	 */
	public void detach(E entidade);
	
	/**
	 * Pesquisa registros de acordo com as restrições informadas, com suporte a orenação e paginação.
	 * 
	 * @param restrictions
	 * @param first
	 * @param max
	 * @param entityGraphName 
	 * 	Nome do {@link EntityGraph} a ser utilizado na query. Pode ser nulo.
	 * @param orderings
	 * @return
	 * @throws SearchException
	 * @throws PersistenceException
	 */
	public SearchResult<E> findByRestrictions(
			List<Restriction> restrictions, int first, int max, String entityGraphName,
			Ordering... orderings) throws SearchException, PersistenceException;
	
	/**
	 * Pesquisa registros de acordo com as restrições informadas, com suporte a orenação e paginação.
	 * 
	 * @param restrictions
	 * @param useOperatorOr - Habilita a utilização do operador OR nas {@link Restriction}.
	 * @param first
	 * @param max
	 * @param entityGraphName
	 * 	Nome do {@link EntityGraph} a ser utilizado na query. Pode ser nulo.
	 * @param orderings
	 * @return
	 * @throws SearchException
	 * @throws PersistenceException
	 */
	public SearchResult<E> findByRestrictions(
			List<Restriction> restrictions, boolean useOperatorOr, int first, int max, String entityGraphName,
			Ordering... orderings) throws SearchException, PersistenceException;
	
	/**
	 * Pesquisa registros de acordo com a restrição informada, com suporte a orenação e paginação.
	 *  
	 * @param restriction
	 * @param first
	 * @param max
	 * @param entityGraphName
	 * 	Nome do {@link EntityGraph} a ser utilizado na query. Pode ser nulo.
	 * @param orderings
	 * @return
	 * @throws SearchException
	 */
	public SearchResult<E> findByRestriction(Restriction restriction, int first, int max, String entityGraphName, Ordering... orderings)
			throws SearchException;
	
	/**
	 * Pesquisa registros de acordo com a restrição informada, com suporte a orenação.
	 *  
	 * @param restriction
	 * @param entityGraphName
	 * 	Nome do {@link EntityGraph} a ser utilizado na query. Pode ser nulo.
	 * @param orderings
	 * @return
	 * @throws SearchException
	 */
	public SearchResult<E> findByRestriction(Restriction restriction, String entityGraphName, Ordering... orderings)
			throws SearchException;
	
	/**
	 * Pesquisa um registro único utilizando uma entidade exemplo.
	 * 
	 * @param restrictions
	 * @param entityGraphName
	 * @return
	 * @throws SearchException
	 * @throws NonUniqueResultException
	 */
	public SearchUniqueResult<E> findUniqueByRestrictions(
			List<Restriction> restrictions, String entityGraphName) throws SearchException,
			NonUniqueResultException;
	
	/**
	 * Pesquisa um registro único utilizando uma entidade exemplo.
	 * 
	 * @param restrictions
	 * @param useOperatorOr - Habilita a utilização do operador OR nas {@link Restriction}.
	 * @param entityGraphName
	 * 	Nome do {@link EntityGraph} a ser utilizado na query. Pode ser nulo.
	 * @return
	 * @throws SearchException
	 * @throws NonUniqueResultException
	 */
	public SearchUniqueResult<E> findUniqueByRestrictions(
			List<Restriction> restrictions, boolean useOperatorOr, String entityGraphName) throws SearchException,
			NonUniqueResultException;


}
