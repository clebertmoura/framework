package br.com.framework.pilotojee7.cadastro.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import br.com.framework.pilotojee7.core.domain.BaseEntityImpl;

@Entity
@Table(name = "LOGRADOURO", schema = "DBARCHETYPE01")
@Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
public class Logradouro extends BaseEntityImpl<Long>{
	
	private static final long serialVersionUID = 1L;
	private String cep;
	private String nome;
	private Bairro bairro;
	private TipoLogradouro tipoLogradouro;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LOGRADOURO")
	@SequenceGenerator(name = "SEQ_LOGRADOURO", schema = "DBARCHETYPE01", sequenceName = "SEQ_LOGRADOURO", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_LOGRADOURO")
	public Long getId() {
		return super.getId();
	}
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BAIRRO", nullable = true)
	public Bairro getBairro() {
		return bairro;
	}
	
	public void setBairro(Bairro bairro){
		this.bairro = bairro;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_LOGRADOURO", nullable = true)
	public TipoLogradouro getTipoLogradouro() {
		return tipoLogradouro;
	}

	public void setTipoLogradouro(TipoLogradouro tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}

	@Column(name = "CEP", nullable = false)
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Column(name = "NOME", nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
