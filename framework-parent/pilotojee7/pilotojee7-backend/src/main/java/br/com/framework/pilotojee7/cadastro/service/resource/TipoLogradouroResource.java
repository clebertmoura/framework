package br.com.framework.pilotojee7.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.cadastro.domain.TipoLogradouro;
import br.com.framework.service.impl.BaseEntityResourceImpl;


/**
 * Resource da entidade {@link TipoLogradouro}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class TipoLogradouroResource extends BaseEntityResourceImpl<Long, TipoLogradouro> {

	private static final long serialVersionUID = -1L;
	
	private String descricao;
	
	public TipoLogradouroResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public TipoLogradouroResource(TipoLogradouro entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public TipoLogradouroResource(TipoLogradouro entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(TipoLogradouro entity) {
		setDescricao(entity.getDescricao());
	}

	public String getDescricao() {
		return this.descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}

