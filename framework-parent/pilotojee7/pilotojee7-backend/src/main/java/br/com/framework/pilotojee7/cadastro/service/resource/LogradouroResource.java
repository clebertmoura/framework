package br.com.framework.pilotojee7.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.cadastro.domain.Logradouro;
import br.com.framework.service.impl.BaseEntityResourceImpl;

/**
 * Resource da entidade {@link Logradouro}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class LogradouroResource extends BaseEntityResourceImpl<Long, Logradouro> {

	private static final long serialVersionUID = -1L;
	
	private String cep;
	private String nome;
	private BairroResource bairro;
	private TipoLogradouroResource tipoLogradouro;
	
	public LogradouroResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public LogradouroResource(Logradouro entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public LogradouroResource(Logradouro entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(Logradouro entity) {
		setCep(entity.getCep());
		setNome(entity.getNome());
		setBairro(new BairroResource(entity.getBairro()));
		setTipoLogradouro(new TipoLogradouroResource(entity.getTipoLogradouro()));
	}

	public String getCep() {
		return this.cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BairroResource getBairro() {
		return this.bairro;
	}
	public void setBairro(BairroResource bairro) {
		this.bairro = bairro;
	}
	public TipoLogradouroResource getTipoLogradouro() {
		return this.tipoLogradouro;
	}
	public void setTipoLogradouro(TipoLogradouroResource tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}

}

