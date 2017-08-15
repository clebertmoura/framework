package br.com.framework.pilotojee7.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.cadastro.domain.Cliente;
import br.com.framework.pilotojee7.cadastro.domain.PessoaFisica;
import br.com.framework.pilotojee7.cadastro.domain.PessoaJuridica;
import br.com.framework.service.impl.BaseEntityAuditedResourceImpl;

/**
 * Resource da entidade {@link Cliente}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class ClienteResource extends BaseEntityAuditedResourceImpl<Long, Cliente> {

	private static final long serialVersionUID = -1L;
	
	private PessoaFisicaResource pessoaFisica;
	private PessoaJuridicaResource pessoaJuridica;
	
	public ClienteResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public ClienteResource(Cliente entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public ClienteResource(Cliente entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(Cliente entity) {
		super.loadFromEntity(entity);
		if (entity.getPessoa() != null) {
			if (entity.getPessoa().isPessoaFisica()) {
				setPessoaFisica(new PessoaFisicaResource((PessoaFisica)entity.getPessoa()));
			} else {
				setPessoaJuridica(new PessoaJuridicaResource((PessoaJuridica)entity.getPessoa()));
			}
		}
	}

	public PessoaFisicaResource getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisicaResource pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public PessoaJuridicaResource getPessoaJuridica() {
		return pessoaJuridica;
	}

	public void setPessoaJuridica(PessoaJuridicaResource pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}


}

