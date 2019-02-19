package br.com.framework.pilotojee7.cadastro.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

import br.com.caelum.stella.bean.validation.CNPJ;
import br.com.framework.search.indexer.annotations.Field;
import br.com.framework.search.indexer.annotations.Indexed;

/**
 * @author clebertmoura
 *
 */
@Entity
@DiscriminatorValue("J")
@Indexed
@Audited
public class PessoaJuridica extends Pessoa {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nomeFantasia;
	private String cnpj;
	
	@NotNull(message = "{campo.obrigatorio}")
	@CNPJ(formatted = false, message = "{campo.cnpj.invalido}")
	@Column(name = "CNPJ", nullable = true, length = 14)
	@Field
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 255, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "NOME_FANTASIA", nullable = true, length = 255)
	@Field
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	
}
