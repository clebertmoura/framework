/**
 * 
 */
package br.com.framework.search.indexer.impl;

import java.io.Serializable;
import java.util.List;

import br.com.framework.search.api.SearchUniqueResult;
import br.com.framework.search.exception.SearchException;
import br.com.framework.search.impl.Operator;
import br.com.framework.search.impl.Restriction;
import br.com.framework.search.indexer.api.IndexedBaseEntity;
import br.com.framework.search.indexer.api.IndexedBaseEntitySearch;
import br.com.framework.search.indexer.listener.IndexedBaseEntityListener;
import br.com.framework.search.util.SearchUtil;

/**
 * Classe abstrata que implementa a interface {@link IndexedBaseEntitySearch} para componentes de consulta indexada a entidades.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK> Classe do Tipo da chave primária da entidade.
 * @param <E> Classe do Tipo da entidade.
 */
public abstract class IndexedBaseEntitySearchImpl<PK extends Serializable, E extends IndexedBaseEntity<PK>>
	extends IndexedDocumentSearchImpl<PK, E> implements IndexedBaseEntitySearch<PK, E> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String FIELD_ID = "id";

	/**
	 * @param entidadeClass
	 */
	public IndexedBaseEntitySearchImpl(Class<E> entidadeClass) {
		super(entidadeClass);
	}

	/* (non-Javadoc)
	 * @see br.com.framework.search.search.indexer.impl.IndexedDocumentSearchableImpl#bypassField(java.lang.String)
	 */
	@Override
	protected boolean bypassField(String fieldName) {
		return super.bypassField(fieldName) || fieldName.equals(IndexedBaseEntityListener.FIELD_ENTITY_CLASS);
	}

	/* (non-Javadoc)
	 * @see br.com.framework.search.search.indexer.impl.IndexedDocumentSearchableImpl#isIdFieldLongType(java.lang.String)
	 */
	@Override
	protected boolean isIdFieldLongType(String fieldName) {
		return fieldName.equals(FIELD_ID);
	}

	/* (non-Javadoc)
	 * @see br.com.framework.search.search.indexer.impl.IndexedDocumentSearchableImpl#setDefaultRestrictons(java.util.List)
	 */
	@Override
	protected void setDefaultRestrictons(List<Restriction> restrictions) {
		restrictions.add(SearchUtil.instance().restriction(IndexedBaseEntityListener.FIELD_ENTITY_CLASS, Operator.EQ, getDocumentClass().getName()));
	}
	
	@Override
	public SearchUniqueResult<E> findById(PK id) throws SearchException {
		return findUniqueByField(FIELD_ID, Operator.EQ, id);
	}
	
}
