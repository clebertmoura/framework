package br.com.framework.pilotojee7.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.cadastro.domain.Filial;
import br.com.framework.service.impl.BaseEntityAuditedResourceImpl;

/**
 * Resource da entidade {@link Filial}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class FilialResource extends BaseEntityAuditedResourceImpl<Long, Filial> {

	private static final long serialVersionUID = -1L;
	
	private PessoaJuridicaResource pessoaJuridica;
	private EmpresaResource empresa;
	
	public FilialResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public FilialResource(Filial entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public FilialResource(Filial entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(Filial entity) {
		super.loadFromEntity(entity);
		setPessoaJuridica(new PessoaJuridicaResource(entity.getPessoaJuridica()));
		setEmpresa(new EmpresaResource(entity.getEmpresa()));
	}

	public PessoaJuridicaResource getPessoaJuridica() {
		return this.pessoaJuridica;
	}
	public void setPessoaJuridica(PessoaJuridicaResource pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}
	public EmpresaResource getEmpresa() {
		return this.empresa;
	}
	public void setEmpresa(EmpresaResource empresa) {
		this.empresa = empresa;
	}

}

