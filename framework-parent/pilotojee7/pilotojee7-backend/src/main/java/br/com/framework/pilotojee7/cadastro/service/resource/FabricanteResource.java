package br.com.framework.pilotojee7.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.cadastro.domain.Fabricante;
import br.com.framework.service.impl.BaseEntityAuditedResourceImpl;

/**
 * Resource da entidade {@link Fabricante}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class FabricanteResource extends BaseEntityAuditedResourceImpl<Long, Fabricante> {

	private static final long serialVersionUID = -1L;
	
	private PessoaJuridicaResource pessoaJuridica;
	
	public FabricanteResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public FabricanteResource(Fabricante entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public FabricanteResource(Fabricante entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(Fabricante entity) {
		super.loadFromEntity(entity);
		setPessoaJuridica(new PessoaJuridicaResource(entity.getPessoaJuridica()));
	}

	public PessoaJuridicaResource getPessoaJuridica() {
		return this.pessoaJuridica;
	}
	public void setPessoaJuridica(PessoaJuridicaResource pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}

}

