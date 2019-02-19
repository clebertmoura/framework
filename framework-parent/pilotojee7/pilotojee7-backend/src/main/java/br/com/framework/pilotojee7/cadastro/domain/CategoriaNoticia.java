package br.com.framework.pilotojee7.cadastro.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.framework.pilotojee7.core.domain.BaseEntityAuditedImpl;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Entity
@Table(name = "CATEGORIA_NOTICIA", schema = "CADASTRO", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "NOME" }, name = "UK_NOME") })
public class CategoriaNoticia extends BaseEntityAuditedImpl<Long> {

	private static final long serialVersionUID = 1L;

	private String nome;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CATEGORIA_NOTICIA")
	@SequenceGenerator(name = "SEQ_CATEGORIA_NOTICIA", schema = "CADASTRO", sequenceName = "SEQ_CATEGORIA_NOTICIA", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_CATEGORIA_NOTICIA")
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

}
