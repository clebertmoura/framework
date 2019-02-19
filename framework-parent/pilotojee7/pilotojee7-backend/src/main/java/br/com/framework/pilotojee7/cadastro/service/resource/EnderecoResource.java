package br.com.framework.pilotojee7.cadastro.service.resource;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.framework.pilotojee7.cadastro.domain.Endereco;
import br.com.framework.service.impl.BaseEntityResourceImpl;

/**
 * Resource da entidade {@link Endereco}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class EnderecoResource extends BaseEntityResourceImpl<Long, Endereco> {

	private static final long serialVersionUID = -1L;
	
	private LocalDateTime dataHoraCadastro;
	private Double lat;
	private Double lng;
	private String logradouro;
	private String numero;
	private String complemento;
	private String cep;
	private String pontoReferencia;
	private CidadeResource cidade;
	private BairroResource bairro;
	private UfResource uf;
	private PaisResource pais;
	
	public EnderecoResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public EnderecoResource(Endereco entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public EnderecoResource(Endereco entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(Endereco entity) {
		setDataHoraCadastro(entity.getDataHoraCadastro());
		setLat(entity.getLat());
		setLng(entity.getLng());
		setLogradouro(entity.getLogradouro());
		setNumero(entity.getNumero());
		setComplemento(entity.getComplemento());
		setCep(entity.getCep());
		setPontoReferencia(entity.getPontoReferencia());
		setCidade(new CidadeResource(entity.getCidade()));
		setBairro(new BairroResource(entity.getBairro()));
		setUf(new UfResource(entity.getUf()));
		setPais(new PaisResource(entity.getPais()));
	}

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm.Z")
	public LocalDateTime getDataHoraCadastro() {
		return this.dataHoraCadastro;
	}
	public void setDataHoraCadastro(LocalDateTime dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}
	public Double getLat() {
		return this.lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return this.lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public String getLogradouro() {
		return this.logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getNumero() {
		return this.numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return this.complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getCep() {
		return this.cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getPontoReferencia() {
		return this.pontoReferencia;
	}
	public void setPontoReferencia(String pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}
	public CidadeResource getCidade() {
		return this.cidade;
	}
	public void setCidade(CidadeResource cidade) {
		this.cidade = cidade;
	}
	public BairroResource getBairro() {
		return this.bairro;
	}
	public void setBairro(BairroResource bairro) {
		this.bairro = bairro;
	}
	public UfResource getUf() {
		return this.uf;
	}
	public void setUf(UfResource uf) {
		this.uf = uf;
	}
	public PaisResource getPais() {
		return this.pais;
	}
	public void setPais(PaisResource pais) {
		this.pais = pais;
	}

}

