package br.jus.framework.domain.api;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.jus.framework.domain.enums.Status;

/**
 * Interface base para as entidades de domínio auditáveis e com suporte a exclusão lógica. 
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 * @param <PK> Tipo da chave primária.
 */
public interface BaseEntityAudited<PK extends Serializable> extends BaseEntity<PK> {
	/**
	 * Retorna a data de criação do registro.
	 *  
	 * @return
	 */
	public abstract LocalDateTime getCreationDate();
	/**
	 * Seta a data de criação do registro.
	 * 
	 * @param createDate
	 */
	public abstract void setCreationDate(LocalDateTime createDate);
	
	/**
	 * @param createDate
	 * @return
	 */
	public abstract BaseEntityAudited<PK> creationDate(LocalDateTime createDate);
	
	/**
	 * Retorna a data de modificação do registro.
	 * @return
	 */
	public abstract LocalDateTime getLastModificationDate();
	/**
	 * Seta a data de modificação do registro.
	 * 
	 * @param lastModifiedDate
	 */
	public abstract void setLastModificationDate(LocalDateTime lastModifiedDate);
	
	/**
	 * @param lastModifiedDate
	 * @return
	 */
	public abstract BaseEntityAudited<PK> lastModificationDate(LocalDateTime lastModifiedDate);
	
	/**
	 * Retorna o username do usuário de criou o registro.
	 *  
	 * @return
	 */
	public abstract String getCreationAuthor();
	/**
	 * Seta o username do usuário de criou o registro.
	 * 
	 * @param creationAuthor
	 */
	public abstract void setCreationAuthor(String creationAuthor);
	
	/**
	 * @param creationAuthor
	 * @return
	 */
	public abstract BaseEntityAudited<PK> creationAuthor(String creationAuthor);
	
	/**
	 * Retorna o username do usuário de criou/modificou o registro.
	 *  
	 * @return
	 */
	public abstract String getLastModificationAuthor();
	/**
	 * Seta o username do usuário de criou/modificou o registro.
	 * 
	 * @param lastModificationAuthor
	 */
	public abstract void setLastModificationAuthor(String lastModificationAuthor);
	
	/**
	 * @param lastModificationAuthor
	 * @return
	 */
	public abstract BaseEntityAudited<PK> lastModificationAuthor(String lastModificationAuthor);
	
	/**
	 * Retorna o status do registro.
	 * 
	 * @return
	 */
	public abstract Status getStatus();
	/**
	 * Seta o status do registro.
	 * 
	 * @param status
	 */
	public abstract void setStatus(Status status);
	
	/**
	 * @param status
	 * @return
	 */
	public abstract BaseEntityAudited<PK> status(Status status);
	
	/**
	 * Retorna true se o regsitro estiver ativo.
	 *  
	 * @return
	 */
	public abstract boolean isActive();

}