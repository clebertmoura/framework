package br.com.framework.pilotojee7.cadastro.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

import br.com.framework.pilotojee7.core.domain.IndexedBaseEntityImpl;
import br.com.framework.search.indexer.annotations.DocumentId;

/**
 * @author clebertmoura
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TIPO_PESSOA")
@Table(name="PESSOA", schema = "DBARCHETYPE01")
@Audited
public abstract class Pessoa extends IndexedBaseEntityImpl<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nomePessoa;
	private Endereco endereco;
	private List<PessoaContato> contatos; 

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PESSOA")
	@SequenceGenerator(name = "SEQ_PESSOA", schema = "DBARCHETYPE01", sequenceName = "SEQ_PESSOA", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_PESSOA")
	@DocumentId
	public Long getId() {
		return super.getId();
	}
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
	@JoinColumn(name = "ID_ENDERECO", nullable = true)
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the contatos
	 */
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "pessoa", orphanRemoval = true)
	public List<PessoaContato> getContatos() {
		if (contatos == null)
			contatos = new ArrayList<PessoaContato>();
		return contatos;
	}

	/**
	 * @param contatos the contatos to set
	 */
	public void setContatos(List<PessoaContato> contatos) {
		this.contatos = contatos;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 255, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "NOME_PESSOA", nullable = false, length = 255)
	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	@Transient
	public boolean isPessoaFisica() {
		return this instanceof PessoaFisica;
	}
	
	@Transient
	public boolean isPessoaJuridica() {
		return this instanceof PessoaJuridica;
	}
	
}
