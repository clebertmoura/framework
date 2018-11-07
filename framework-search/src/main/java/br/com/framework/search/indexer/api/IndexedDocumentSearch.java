/**
 * 
 */
package br.com.framework.search.indexer.api;

import java.io.Serializable;

import br.com.framework.search.api.Search;
import br.com.framework.search.api.SearchUniqueResult;
import br.com.framework.search.exception.SearchException;

/**
 * Interface padrão para componentes de consulta de documentos indexados.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <DocId>
 * @param <Doc>
 */
public interface IndexedDocumentSearch<DocId extends Serializable, Doc extends IndexedDocument<DocId>> extends Search<DocId, Doc> {

	/**
	 * Search a entidade pelo sua UUID.
	 * 
	 * @param uuid
	 * @return
	 * @throws SearchException
	 */
	public SearchUniqueResult<Doc> findByUUID(String uuid) throws SearchException;
	
}
