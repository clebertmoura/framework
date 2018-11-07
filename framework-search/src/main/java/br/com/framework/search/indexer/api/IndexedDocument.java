package br.com.framework.search.indexer.api;

import java.io.Serializable;

/**
 * Interface base para os documentos indexados.
 *  
 * @author Cleber Moura <cleber.t.moura@gmail.com>
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