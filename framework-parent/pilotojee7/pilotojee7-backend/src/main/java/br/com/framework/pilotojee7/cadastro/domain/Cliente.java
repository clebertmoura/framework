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
import br.com.framework.search.indexer.annotations.Indexed;

/**
 * Representa o {@link Cliente}.
 * 
 * @author Cleber Moura <cleber.moura@tecnofrota.com>
 *
 */
@Entity
@Table(name="CLIENTE", schema = "DBARCHETYPE01")
@Indexed
@Audited
public class Cliente extends BaseEntityAuditedImpl<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Pessoa pessoa;
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CLIENTE")
	@SequenceGenerator(name = "SEQ_CLIENTE", schema = "DBARCHETYPE01", sequenceName = "SEQ_CLIENTE", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_CLIENTE")
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
	@JoinColumn(name = "ID_PESSOA", nullable = true)
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	

}
