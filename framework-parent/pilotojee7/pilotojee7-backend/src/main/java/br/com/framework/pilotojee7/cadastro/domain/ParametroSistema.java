package br.com.framework.pilotojee7.cadastro.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.framework.pilotojee7.core.domain.BaseEntityImpl;

/**
 * @author Raniel
 *
 */
@Entity
@Table(name = "PARAMETRO_SISTEMA", schema = "CADASTRO", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "CHAVE" }, name = "UK_CHAVE") })
public class ParametroSistema extends BaseEntityImpl<Long> {

	private static final long serialVersionUID = 1L;

	private String chave;
	private String valor;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARAMETRO_SISTEMA")
	@SequenceGenerator(name = "SEQ_PARAMETRO_SISTEMA", schema = "CADASTRO", sequenceName = "SEQ_PARAMETRO_SISTEMA", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_PARAMETRO_SISTEMA")
	public Long getId() {
		return super.getId();
	}

	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 255, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "CHAVE", nullable = false, unique = true, length = 255)
	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}
	
	@NotNull(message = "{campo.obrigatorio}")
	@Column(name = "VALOR", nullable = false)
	@Lob
	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
