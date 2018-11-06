package br.jus.framework.search.indexer.api;

import java.io.Serializable;

import br.jus.framework.search.api.Search;

/**
 * Interface para pesquisa nos índices da entidade.
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 * @param <PK> Tipo da chave primária
 * @param <E> Classe da entidade que extenda {@link IndexedBaseEntity}
 */
public interface IndexedBaseEntitySearch<PK extends Serializable, E extends IndexedBaseEntity<PK>> extends Search<PK, E> {

}
