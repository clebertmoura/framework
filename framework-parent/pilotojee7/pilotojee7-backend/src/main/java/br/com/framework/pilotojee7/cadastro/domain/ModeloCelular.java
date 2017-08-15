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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

import br.com.framework.pilotojee7.core.domain.BaseEntityImpl;

/**
 * @author clebertmoura
 *
 */
@Entity
@Table(name = "MODELO_CELULAR", schema = "DBARCHETYPE01")
@Audited
public class ModeloCelular extends BaseEntityImpl<Long>{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private Fabricante fabricante;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MODELO_CEL")
	@SequenceGenerator(name = "SEQ_MODELO_CEL", schema = "DBARCHETYPE01", sequenceName = "SEQ_MODELO_CEL", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_MODELO_CEL")
	public Long getId() {
		return super.getId();
	}
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 255, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "NOME", nullable = false, length = 255)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FABRICANTE", nullable = false)
	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

}
