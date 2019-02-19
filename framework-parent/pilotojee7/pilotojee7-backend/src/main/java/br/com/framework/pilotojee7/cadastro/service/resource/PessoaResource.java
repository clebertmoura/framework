package br.com.framework.pilotojee7.cadastro.service.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.cadastro.domain.Pessoa;
import br.com.framework.pilotojee7.cadastro.domain.PessoaContato;
import br.com.framework.service.impl.BaseEntityResourceImpl;

/**
 * Resource da entidade {@link Pessoa}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class PessoaResource<P extends Pessoa> extends BaseEntityResourceImpl<Long, P> {

	private static final long serialVersionUID = -1L;
	
	private String nomePessoa;
	private EnderecoResource endereco;
	private List<PessoaContatoResource> contatos;
	
	public PessoaResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public PessoaResource(P entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public PessoaResource(P entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(P entity) {
		setNomePessoa(entity.getNomePessoa());
		setEndereco(new EnderecoResource(entity.getEndereco()));
		Iterator<PessoaContato> itContatos = entity.getContatos().iterator();
		while (itContatos.hasNext()) {
			PessoaContato element = itContatos.next();
			this.getContatos().add(new PessoaContatoResource(element));
		}
	}

	public String getNomePessoa() {
		return this.nomePessoa;
	}
	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}
	public EnderecoResource getEndereco() {
		return this.endereco;
	}
	public void setEndereco(EnderecoResource endereco) {
		this.endereco = endereco;
	}
	public List<PessoaContatoResource> getContatos() {
		if (contatos == null) {
			contatos = new ArrayList<PessoaContatoResource>();
		}
		return this.contatos;
	}
	public void setContatos(List<PessoaContatoResource> contatos) {
		this.contatos = contatos;
	}

}

