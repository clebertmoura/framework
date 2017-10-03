package br.com.framework.pilotojee7.relatorio.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.relatorio.domain.Categoria;
import br.com.framework.service.impl.BaseEntityResourceImpl;


/**
 * Resource da entidade {@link Categoria}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class CategoriaResource extends BaseEntityResourceImpl<Long, Categoria> {

	private static final long serialVersionUID = -1L;
	
	private String nome;
	private String permissao;
	
	
	public CategoriaResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public CategoriaResource(Categoria entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public CategoriaResource(Categoria entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(Categoria entity) {
		super.loadFromEntity(entity);
		setNome(entity.getNome());
		setPermissao(entity.getPermissao());
	}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getPermissao() {
		return permissao;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}

}

