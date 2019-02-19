package br.com.framework.pilotojee7.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.cadastro.domain.Empresa;
import br.com.framework.service.impl.BaseEntityAuditedResourceImpl;

/**
 * Resource da entidade {@link Empresa}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class EmpresaResource extends BaseEntityAuditedResourceImpl<Long, Empresa> {

	private static final long serialVersionUID = -1L;
	
	private PessoaJuridicaResource pessoaJuridica;
	
	public EmpresaResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public EmpresaResource(Empresa entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public EmpresaResource(Empresa entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(Empresa entity) {
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

