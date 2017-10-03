package br.com.framework.pilotojee7.cadastro.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

import br.com.framework.pilotojee7.core.domain.BaseEntityAuditedImpl;
import br.com.framework.search.indexer.annotations.DocumentId;
import br.com.framework.search.indexer.annotations.Field;
import br.com.framework.search.indexer.annotations.Indexed;

/**
 * Representa o {@link Fabricante}.
 * 
 * @author Cleber Moura <cleber.moura@tecnofrota.com>
 *
 */
@Entity
@Table(name="FABRICANTE", schema = "CADASTRO")
@Indexed
@Audited
public class Fabricante extends BaseEntityAuditedImpl<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PessoaJuridica pessoaJuridica;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FABRICANTE")
	@SequenceGenerator(name = "SEQ_FABRICANTE", schema = "CADASTRO", sequenceName = "SEQ_FABRICANTE", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_FABRICANTE")
	@DocumentId
	public Long getId() {
		return super.getId();
	}
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@NotNull(message = "{campo.obrigatorio}")
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ID_PESSOA_JURIDICA", nullable = false)
	@Field
	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}

	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}

}
