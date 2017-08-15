package br.com.framework.pilotojee7.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.cadastro.domain.PessoaContato;
import br.com.framework.service.impl.BaseEntityResourceImpl;

/**
 * Resource da entidade {@link PessoaContato}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class PessoaContatoResource extends BaseEntityResourceImpl<Long, PessoaContato> {

	private static final long serialVersionUID = -1L;
	
	private String email;
	private String ddd;
	private String numero;
	
	public PessoaContatoResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public PessoaContatoResource(PessoaContato entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public PessoaContatoResource(PessoaContato entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(PessoaContato entity) {
		setEmail(entity.getEmail());
		setDdd(entity.getDdd());
		setNumero(entity.getNumero());
	}

	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDdd() {
		return this.ddd;
	}
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	public String getNumero() {
		return this.numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

}

