package br.com.framework.search.indexer.api;

import java.io.Serializable;

import br.com.framework.search.api.Search;

/**
 * Interface para pesquisa nos índices da entidade.
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK> Tipo da chave primária
 * @param <E> Classe da entidade que extenda {@link IndexedBaseEntity}
 */
public interface IndexedBaseEntitySearch<PK extends Serializable, E extends IndexedBaseEntity<PK>> extends Search<PK, E> {

}
