package br.jus.framework.domain.api;

import java.io.Serializable;

/**
 * Interface base para as entidades de domínio. 
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 * @param <PK> Tipo da chave primária.
 */
public interface BaseEntity<PK extends Serializable> extends Serializable {
	
	/**
	 * Método utilitário para retornar a className da entidade.
	 * 
	 * @return
	 */
	String entityClassName();
	
	/**
     * Método utilitário para saber se a chave primária está preenchida.
     *
     * @return true if the primary key is set, false otherwise
     */
    boolean isIdSet();

	/**
	 * Seta a chave primária.
	 * 
	 * @return
	 */
	public abstract PK getId();
	
	/**
	 * Retorna a chave primária.
	 * 
	 * @param id
	 */
	public abstract void setId(PK id);
	
	/**
	 * @param id
	 * @return
	 */
	public abstract BaseEntity<PK> id(PK id);
	
	/**
	 * Retorna a versão do registro. Campo utilizado para controle de lock otimista.
	 * 
	 * @return
	 */
	public abstract Long getVersion();
	/**
	 * Seta a versão do registro.
	 * 
	 * @param version
	 */
	public abstract void setVersion(Long version); 
	/**
	 * @param version
	 * @return
	 */
	public abstract BaseEntity<PK> version(Long version);

}