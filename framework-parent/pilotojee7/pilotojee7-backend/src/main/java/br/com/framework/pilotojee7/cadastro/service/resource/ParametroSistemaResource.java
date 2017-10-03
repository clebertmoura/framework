package br.com.framework.pilotojee7.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.cadastro.domain.ParametroSistema;
import br.com.framework.service.impl.BaseEntityResourceImpl;

/**
 * Resource da entidade {@link ParametroSistema}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class ParametroSistemaResource extends BaseEntityResourceImpl<Long, ParametroSistema> {

	private static final long serialVersionUID = -1L;

	private String chave;
	private String valor;

	public ParametroSistemaResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public ParametroSistemaResource(ParametroSistema entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public ParametroSistemaResource(ParametroSistema entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(ParametroSistema entity) {
		setChave(entity.getChave());
		setValor(entity.getValor());
	}

	public String getChave() {
		return this.chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
