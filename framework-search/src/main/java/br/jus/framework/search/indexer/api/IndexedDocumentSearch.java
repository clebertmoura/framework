/**
 * 
 */
package br.jus.framework.search.indexer.api;

import java.io.Serializable;

import br.jus.framework.search.api.Search;
import br.jus.framework.search.api.SearchUniqueResult;
import br.jus.framework.search.exception.SearchException;

/**
 * Interface padrão para componentes de consulta de documentos indexados.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
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
