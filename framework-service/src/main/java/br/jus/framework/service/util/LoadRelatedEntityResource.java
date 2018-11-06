/**
 * 
 */
package br.jus.framework.service.util;

import java.io.Serializable;

import br.jus.framework.domain.api.BaseEntity;
import br.jus.framework.service.api.BaseEntityResource;

/**
 * Classe abstrata utilizada para carregar uma entidade reacionada.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
public abstract class LoadRelatedEntityResource<PE extends BaseEntity<? extends Serializable>, E extends BaseEntity<? extends Serializable>, R extends BaseEntityResource<? extends Serializable, E>> {
	
	private PE parentEntity;

	/**
	 * @param parentEntity - referência da entidade mãe.
	 */
	public LoadRelatedEntityResource(PE parentEntity) {
		super();
		this.parentEntity = parentEntity;
	}

	/**
	 * Deve ser implementado na classe concreta responsável por carregar o resource da entidade mãe.
	 * 
	 * @param relatedEntity
	 * @param relatedResource
	 * @return
	 */
	public abstract E loadRelatedEntityResource(E relatedEntity, R relatedResource);

	/**
	 * @return
	 */
	public PE getParentEntity() {
		return parentEntity;
	}
	
}
