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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.framework.pilotojee7.core.domain.BaseEntityImpl;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Entity
@Table(name = "CIDADE", schema = "CADASTRO", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"NOME", "ID_UF"}, name = "UK_CIDADE_NOME_NA_UF"),
		@UniqueConstraint(columnNames = {"CODIGO_IBGE"}, name = "UK_CIDADE_CODIGO_IBGE")}
)
public class Cidade extends BaseEntityImpl<Long>{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private Uf uf;
	private String codigoIbge;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CIDADE")
	@SequenceGenerator(name = "SEQ_CIDADE", schema = "CADASTRO", sequenceName = "SEQ_CIDADE", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_CIDADE")
	public Long getId() {
		return super.getId();
	}
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 255, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "NOME", nullable = false, length = 255)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_UF", nullable = false)
	public Uf getUf() {
		return uf;
	}

	public void setUf(Uf uf) {
		this.uf = uf;
	}

	@Column(name = "CODIGO_IBGE", nullable = true, length = 45)
	public String getCodigoIbge() {
		return codigoIbge;
	}

	public void setCodigoIbge(String codigoIbge) {
		this.codigoIbge = codigoIbge;
	}
	
}
