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

import br.com.framework.pilotojee7.core.domain.BaseEntityImpl;
import br.com.framework.search.indexer.annotations.DocumentId;
import br.com.framework.search.indexer.annotations.Indexed;

/**
 * @author clebertmoura
 *
 */
@Entity
@Table(name="PESSOA_CONTATO", schema = "DBARCHETYPE01")
@Audited
@Indexed
public class PessoaContato extends BaseEntityImpl<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Pessoa pessoa;
	private String email;
	private String ddd;
	private String numero;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PESSOA_CONTATO")
	@SequenceGenerator(name = "SEQ_PESSOA_CONTATO", schema = "DBARCHETYPE01", sequenceName = "SEQ_PESSOA_CONTATO", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_PESSOA_CONTATO")
	@DocumentId
	public Long getId() {
		return super.getId();
	}
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	/**
	 * @return the pessoa
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PESSOA", nullable = false)
	public Pessoa getPessoa() {
		return pessoa;
	}

	/**
	 * @param pessoa the pessoa to set
	 */
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	/**
	 * @return the email
	 */
	@Column(name = "EMAIL", nullable = true, length = 100)
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the ddd
	 */
	@Column(name = "DDD", nullable = true, length = 2)
	public String getDdd() {
		return ddd;
	}

	/**
	 * @param ddd the ddd to set
	 */
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	/**
	 * @return the numero
	 */
	@Column(name = "NUMERO", nullable = true, length = 12)
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	
}
