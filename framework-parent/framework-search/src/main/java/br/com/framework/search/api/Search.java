package br.com.framework.search.api;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NonUniqueResultException;

import br.com.framework.search.exception.SearchException;
import br.com.framework.search.impl.Operator;
import br.com.framework.search.impl.Ordering;
import br.com.framework.search.impl.Restriction;

/**
 * Interface base dos componentes de consulta.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <DocId> Tipo do chave de identificação do documento.
 * @param <Doc> Tipo do documento.
 */
public interface Search<DocId extends Serializable, Doc extends Serializable> extends Serializable {
	
	/**
	 * Pesquisa a entidade pelo sua chave primária.
	 * 
	 * @param id
	 * @return
	 * @throws SearchException
	 */
	public SearchUniqueResult<Doc> findById(DocId id) throws SearchException;

	/**
	 * Pesquisa os registros.
	 * 
	 * @param orderings
	 * 		Ordenações
	 * @return
	 * @throws SearchException
	 */
	public SearchResult<Doc> findAll(Ordering... orderings)
			throws SearchException;

	/**
	 * Pesquisa os registros, com suporte a paginação.
	 * 
	 * @param first
	 * @param max
	 * @param orderings
	 * @return
	 * @throws SearchException
	 */
	public SearchResult<Doc> findAll(int first, int max, Ordering... orderings) throws SearchException;

	/**
	 * Retorna a quantidade de registros da entidade.
	 * 
	 * @return
	 * @throws SearchException
	 */
	public SearchUniqueResult<Long> getCountFindAll() throws SearchException;

	/**
	 * Pesquisa entidades com filtro pelo field informado e ordenação
	 * 
	 * @param field
	 * @param operator
	 * @param value
	 * @param orderings
	 * @return
	 * @throws SearchException
	 */
	public SearchResult<Doc> findByField(String field, Operator operator, Object value, Ordering... orderings) throws SearchException;

	/**
	 * Pesquisa entidades com filtro pelo field informado com ordenação e paginação.
	 * 
	 * @param field
	 * @param operator
	 * @param value
	 * @param first
	 * @param max
	 * @param orderings
	 * @return
	 * @throws SearchException
	 */
	public SearchResult<Doc> findByField(String field, Operator operator, Object value, int first, int max, Ordering... orderings)
			throws SearchException;
	
	/**
	 * Retorna a quantidade de registros encontrados com o filtro informado.
	 * 
	 * @param field
	 * @param operator
	 * @param value
	 * @return
	 * @throws SearchException
	 */
	public SearchUniqueResult<Long> getCountFindByField(String field, Operator operator, Object value) throws SearchException;
	
	/**
	 * Pesquisa um registro por field único.
	 * 
	 * @param field
	 * @param operator
	 * @param value
	 * @return
	 * @throws SearchException
	 */
	public SearchUniqueResult<Doc> findUniqueByField(String field, Operator operator, Object value)
			throws SearchException;
	
	/**
	 * Pesquisa registros de acordo com a restrição informada, com suporte a orenação.
	 *  
	 * @param restriction
	 * @param orderings
	 * @return
	 * @throws SearchException
	 */
	public SearchResult<Doc> findByRestriction(Restriction restriction, Ordering... orderings)
			throws SearchException;
	
	/**
	 * Pesquisa registros de acordo com a restrição informada, com suporte a orenação e paginação.
	 *  
	 * @param restriction
	 * @param first
	 * @param max
	 * @param orderings
	 * @return
	 * @throws SearchException
	 */
	public SearchResult<Doc> findByRestriction(Restriction restriction, int first, int max, Ordering... orderings)
			throws SearchException;
	
	/**
	 * Retorna a quantidade de registros encontrados de acordo com a restrição informada.
	 * 
	 * @param restrictions
	 * @return
	 * @throws SearchException
	 */
	public SearchUniqueResult<Long> getCountFindByRestriction(Restriction restriction) throws SearchException;
	
	/**
	 * Pesquisa um registro único de acordo com a restrição informada.
	 *  
	 * @param restriction
	 * @return
	 * @throws SearchException
	 * @throws NonUniqueResultException
	 */
	public SearchUniqueResult<Doc> findUniqueByRestriction(Restriction restriction) throws SearchException, NonUniqueResultException;
	
	/**
	 * Pesquisa um registro único de acordo com as restrições informadas.
	 *  
	 * @param restrictions
	 * @return
	 * @throws SearchException
	 * @throws NonUniqueResultException
	 */
	public SearchUniqueResult<Doc> findUniqueByRestrictions(List<Restriction> restrictions) throws SearchException, NonUniqueResultException;
	
	/**
	 * Pesquisa um registro único de acordo com as restrições informadas.
	 *  
	 * @param restrictions
	 * @param useOperatorOr - Habilita a utilização do operador OR nas {@link Restriction}.
	 * @return
	 * @throws SearchException
	 * @throws NonUniqueResultException
	 */
	public SearchUniqueResult<Doc> findUniqueByRestrictions(List<Restriction> restrictions, boolean useOperatorOr) throws SearchException, NonUniqueResultException;
	
	
	/**
	 * Pesquisa registros de acordo com as restrições informadas, com suporte a orenação.
	 *  
	 * @param restrictions
	 * @param orderings
	 * @return
	 * @throws SearchException
	 */
	public SearchResult<Doc> findByRestrictions(List<Restriction> restrictions, Ordering... orderings)
			throws SearchException;
	
	/**
	 * Pesquisa registros de acordo com as restrições informadas, com suporte a orenação.
	 *  
	 * @param restrictions
	 * @param useOperatorOr - Habilita a utilização do operador OR nas {@link Restriction}.
	 * @param orderings
	 * @return
	 * @throws SearchException
	 */
	public SearchResult<Doc> findByRestrictions(List<Restriction> restrictions, boolean useOperatorOr, Ordering... orderings)
			throws SearchException;
	
	/**
	 * Pesquisa registros de acordo com as restrições informadas, com suporte a orenação e paginação.
	 * 
	 * @param restrictions
	 * @param first
	 * @param max
	 * @param orderings
	 * @return
	 * @throws SearchException
	 */
	public SearchResult<Doc> findByRestrictions(List<Restriction> restrictions, int first, int max, Ordering... orderings)
			throws SearchException;
	
	/**
	 * Pesquisa registros de acordo com as restrições informadas, com suporte a orenação e paginação.
	 * 
	 * @param restrictions
	 * @param useOperatorOr - Habilita a utilização do operador OR nas {@link Restriction}.
	 * @param first
	 * @param max
	 * @param orderings
	 * @return
	 * @throws SearchException
	 */
	public SearchResult<Doc> findByRestrictions(List<Restriction> restrictions, boolean useOperatorOr, int first, int max, Ordering... orderings)
			throws SearchException;
	
	/**
	 * Retorna a quantidade de registros encontrados de acordo com as restrições informadas.
	 * 
	 * @param restrictions
	 * @return
	 * @throws SearchException
	 */
	public SearchUniqueResult<Long> getCountFindByRestrictions(List<Restriction> restrictions) throws SearchException;
	
	/**
	 * Retorna a quantidade de registros encontrados de acordo com as restrições informadas.
	 * 
	 * @param restrictions
	 * @param useOperatorOr - Habilita a utilização do operador OR nas {@link Restriction}.
	 * @return
	 * @throws SearchException
	 */
	public SearchUniqueResult<Long> getCountFindByRestrictions(List<Restriction> restrictions, boolean useOperatorOr) throws SearchException;

}
