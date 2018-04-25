package br.com.framework.pilotojee7.cadastro.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import br.com.framework.pilotojee7.core.domain.BaseEntityImpl;

/**
 * @author clebertmoura
 *
 */
@Entity
@Table(name = "ENDERECO", schema = "CADASTRO")
@Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
public class Endereco extends BaseEntityImpl<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LocalDateTime dataHoraCadastro;
	private Bairro bairro;
	private Cidade cidade;
	private Uf uf;
	private Pais pais;
	private String logradouro;
	private String numero;
	private String complemento;
	private String cep;
	private String pontoReferencia;
	private Double lat;
	private Double lng;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ENDERECO")
	@SequenceGenerator(name = "SEQ_ENDERECO", schema = "CADASTRO", sequenceName = "SEQ_ENDERECO", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_ENDERECO")
	public Long getId() {
		return super.getId();
	}
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@NotNull(message = "{campo.obrigatorio}")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CIDADE", nullable = false)
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Column(name = "DH_CADASTRO", nullable = false)
	public LocalDateTime getDataHoraCadastro() {
		return dataHoraCadastro;
	}

	public void setDataHoraCadastro(LocalDateTime dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}

	@Column(name = "LATITUDE", nullable = true)
	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	@Column(name = "LONGITUDE", nullable = true)
	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_BAIRRO", nullable = true)
	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	/**
	 * @return the uf
	 */
	@NotNull(message = "{campo.obrigatorio}")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_Uf", nullable = true)
	public Uf getUf() {
		return uf;
	}

	/**
	 * @param uf the uf to set
	 */
	public void setUf(Uf uf) {
		this.uf = uf;
	}

	/**
	 * @return the pais
	 */
	// @NotNull(message = "{campo.obrigatorio}") 
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_PAIS", nullable = true)
	public Pais getPais() {
		return pais;
	}

	/**
	 * @param pais the pais to set
	 */
	public void setPais(Pais pais) {
		this.pais = pais;
	}

	@NotNull(message = "{campo.logradouro.obrigatorio}")
	@Column(name = "LOGRADOURO", length = 255, nullable = false)
	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	@Column(name = "NUMERO", length = 10, nullable = true)
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Column(name = "COMPLEMENTO", length = 50, nullable = true)
	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@Column(name = "CEP", length = 8, nullable = true)
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Column(name = "PONTO_REFERENCIA", length = 100, nullable = true)
	public String getPontoReferencia() {
		return pontoReferencia;
	}

	public void setPontoReferencia(String pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}

	@PrePersist
	public void prePersist() {
		if (getDataHoraCadastro() == null) {
			setDataHoraCadastro(LocalDateTime.now());
		}
	}
}
