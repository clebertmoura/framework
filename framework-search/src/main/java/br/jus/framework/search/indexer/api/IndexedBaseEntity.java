package br.jus.framework.search.indexer.api;

import java.io.Serializable;

import br.jus.framework.domain.api.BaseEntity;

/**
 * Interface base para as entidades do modelo que são documentos indexados.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 * @param <PK> Tipo da chave primária.
 */
public interface IndexedBaseEntity<PK extends Serializable> extends BaseEntity<PK>, IndexedDocument<PK> {

}