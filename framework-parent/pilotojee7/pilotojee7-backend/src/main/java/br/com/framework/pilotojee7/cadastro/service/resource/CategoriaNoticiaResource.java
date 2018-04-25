package br.com.framework.pilotojee7.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.cadastro.domain.CategoriaNoticia;
import br.com.framework.service.impl.BaseEntityAuditedResourceImpl;


/**
 * Resource da entidade {@link CategoriaNoticia}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class CategoriaNoticiaResource extends BaseEntityAuditedResourceImpl<Long, CategoriaNoticia> {

	private static final long serialVersionUID = -1L;
	
	private String nome;
	
	public CategoriaNoticiaResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public CategoriaNoticiaResource(CategoriaNoticia entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public CategoriaNoticiaResource(CategoriaNoticia entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(CategoriaNoticia entity) {
		super.loadFromEntity(entity);
		setNome(entity.getNome());
	}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

}

