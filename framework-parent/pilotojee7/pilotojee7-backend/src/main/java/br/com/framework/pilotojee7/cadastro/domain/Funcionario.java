package br.com.framework.pilotojee7.cadastro.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.framework.pilotojee7.core.domain.BaseEntityAuditedImpl;
import br.com.framework.search.indexer.annotations.DocumentId;
import br.com.framework.search.indexer.annotations.Field;
import br.com.framework.search.indexer.annotations.Indexed;

/**
 * Representa uma abstração de Funcionário
 * 
 * @author clebertmoura
 *
 */
@Entity
@Table(name = "FUNCIONARIO", schema = "CADASTRO")
@Indexed
public class Funcionario extends BaseEntityAuditedImpl<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Filial filial;
	private PessoaFisica pessoaFisica;
	private String matricula;
	private TipoFuncionario tipoFuncionario;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FUNCIONARIO")
	@SequenceGenerator(name = "SEQ_FUNCIONARIO", schema = "CADASTRO", sequenceName = "SEQ_FUNCIONARIO", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_FUNCIONARIO")
	@DocumentId
	public Long getId() {
		return super.getId();
	}
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}
	
	@NotNull(message = "{campo.obrigatorio}")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_TIPO_FUNC")
	public TipoFuncionario getTipoFuncionario() {
		return tipoFuncionario;
	}

	public void setTipoFuncionario(TipoFuncionario tipoFuncionario) {
		this.tipoFuncionario = tipoFuncionario;
	}
	
	@NotNull(message = "{campo.obrigatorio}")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FILIAL", nullable = false)
	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	/**
	 * @return the pessoaFisica
	 */
	@NotNull(message = "{campo.obrigatorio}")
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ID_PESSOA_FISICA", nullable = false)
	@Field
	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	/**
	 * @param pessoaFisica the pessoaFisica to set
	 */
	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 30, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "MATRICULA", nullable = true, length = 30)
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

}
