package br.jus.framework.search.indexer.api;

import java.io.Serializable;

/**
 * Interface base para os documentos indexados.
 *  
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 * 
 */
public interface IndexedDocument<DocId extends Serializable> extends Serializable {

	/**
	 * @return
	 */
	public abstract String getUuid();

	/**
	 * @param uuid
	 */
	public abstract void setUuid(String uuid);

}