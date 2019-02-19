package br.com.framework.domain.api;

import java.io.Serializable;

/**
 * Interface base para as entidades de domínio que tenham um atributo nome.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK> Tipo da chave primária.
 */
public interface NamedBaseEntity<PK extends Serializable> extends BaseEntity<PK> {
	
	/**
	 * @return
	 */
	public abstract String getNome();
	/**
	 * @param nome
	 */
	public abstract void setNome(String nome);

}