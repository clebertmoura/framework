package br.com.framework.pilotojee7.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.cadastro.domain.PessoaJuridica;

/**
 * Resource da entidade {@link PessoaJuridica}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class PessoaJuridicaResource extends PessoaResource<PessoaJuridica> {

	private static final long serialVersionUID = -1L;
	
	private String cnpj;
	private String nomeFantasia;
	
	public PessoaJuridicaResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public PessoaJuridicaResource(PessoaJuridica entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public PessoaJuridicaResource(PessoaJuridica entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(PessoaJuridica entity) {
		super.loadFromEntity(entity);
		setCnpj(entity.getCnpj());
		setNomeFantasia(entity.getNomeFantasia());
	}

	public String getCnpj() {
		return this.cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getNomeFantasia() {
		return this.nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

}

