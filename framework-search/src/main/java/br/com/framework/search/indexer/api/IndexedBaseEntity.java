package br.com.framework.search.indexer.api;

import java.io.Serializable;

import br.com.framework.domain.api.BaseEntity;

/**
 * Interface base para as entidades do modelo que são documentos indexados.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK> Tipo da chave primária.
 */
public interface IndexedBaseEntity<PK extends Serializable> extends BaseEntity<PK>, IndexedDocument<PK> {

}