package br.com.framework.pilotojee7.cadastro.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

import br.com.caelum.stella.bean.validation.CPF;
import br.com.framework.pilotojee7.cadastro.enums.Genero;
import br.com.framework.search.indexer.annotations.Field;
import br.com.framework.search.indexer.annotations.Indexed;


/**
 * @author clebertmoura
 *
 */
@Entity
@DiscriminatorValue("F")
@Indexed
@Audited
public class PessoaFisica extends Pessoa {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String cpf;
	private LocalDateTime dataNascimento;
	private String rg;
	private String nomeMae;
	private String nomePai;
	private Genero genero;
	
	@NotNull(message = "{campo.obrigatorio}")
	@CPF(formatted = false, ignoreRepeated = true, message = "{campo.cpf.invalido}")
	@Column(name = "CPF", nullable = true, length = 11)
	@Field
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@Column(name = "DATA_NASCIMENTO", nullable = true)
	@Field
	public LocalDateTime getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDateTime dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	@Column(name = "RG", nullable = true, length = 30)
	@Field
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	
	@Column(name = "NOME_MAE", nullable = true, length = 255)
	@Field
	public String getNomeMae() {
		return nomeMae;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	
	@Column(name = "NOME_PAI", nullable = true, length = 255)
	@Field
	public String getNomePai() {
		return nomePai;
	}
	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}
	
	@Column(name = "GENERO", nullable = true, length = 1)
	@Enumerated(EnumType.STRING)
	public Genero getGenero() {
		return genero;
	}
	public void setGenero(Genero genero) {
		this.genero = genero;
	}

}
