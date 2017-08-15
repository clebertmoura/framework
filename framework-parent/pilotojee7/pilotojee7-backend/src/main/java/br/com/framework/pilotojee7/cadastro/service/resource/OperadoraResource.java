package br.com.framework.pilotojee7.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.cadastro.domain.Operadora;
import br.com.framework.service.impl.BaseEntityAuditedResourceImpl;


/**
 * Resource da entidade {@link Operadora}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class OperadoraResource extends BaseEntityAuditedResourceImpl<Long, Operadora> {

	private static final long serialVersionUID = -1L;
	
	private String nome;
	
	public OperadoraResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public OperadoraResource(Operadora entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public OperadoraResource(Operadora entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(Operadora entity) {
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

