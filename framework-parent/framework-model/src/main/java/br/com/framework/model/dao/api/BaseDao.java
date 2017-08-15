package br.com.framework.model.dao.api;

import java.io.Serializable;

import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.search.api.Search;
import br.com.framework.search.api.SearchResult;
import br.com.framework.search.api.SearchUniqueResult;
import br.com.framework.search.exception.SearchException;
import br.com.framework.search.impl.Ordering;

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

}
