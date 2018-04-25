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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.framework.pilotojee7.core.domain.BaseEntityImpl;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Entity
@Table(name = "PAIS", schema = "CADASTRO", uniqueConstraints={
		@UniqueConstraint(columnNames = {"CODIGO_ONU_ALPHA"}, name = "UK_CODIGO_ONU_ALPHA"),
		@UniqueConstraint(columnNames = {"CODIGO_ONU"}, name = "UK_CODIGO_ONU")
})
public class Pais extends BaseEntityImpl<Long>{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String codigoOnuAlpha;
	private String codigoOnu;
	
	private Imagem imagem;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PAIS")
	@SequenceGenerator(name = "SEQ_PAIS", schema = "CADASTRO", sequenceName = "SEQ_PAIS", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_PAIS")
	public Long getId() {
		return super.getId();
	}
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 255, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "NOME", nullable = false, unique = true, length = 255)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "CODIGO_ONU_ALPHA", nullable = true, length = 3)
	public String getCodigoOnuAlpha() {
		return codigoOnuAlpha;
	}
	public void setCodigoOnuAlpha(String codigoOnuAlpha) {
		this.codigoOnuAlpha = codigoOnuAlpha;
	}

	@Column(name = "CODIGO_ONU", nullable = true, length = 3)
	public String getCodigoOnu() {
		return codigoOnu;
	}

	public void setCodigoOnu(String codigoOnu) {
		this.codigoOnu = codigoOnu;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_IMAGEM", nullable = true)
	public Imagem getImagem() {
		return imagem;
	}

	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}
	
}
