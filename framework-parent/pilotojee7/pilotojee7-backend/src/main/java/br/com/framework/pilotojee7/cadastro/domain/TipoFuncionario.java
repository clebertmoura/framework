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

import br.com.framework.pilotojee7.core.domain.BaseEntityAuditedImpl;

@Entity
@Table(name = "TIPO_FUNCIONARIO", schema = "DBARCHETYPE01")
public class TipoFuncionario extends BaseEntityAuditedImpl<Long> {

	private static final long serialVersionUID = -1970388193748757292L;
	private String descricao;
	private Empresa empresa;

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPO_FUNC")
	@SequenceGenerator(name = "SEQ_TIPO_FUNC", schema = "DBARCHETYPE01", sequenceName = "SEQ_TIPO_FUNC", allocationSize = 1, initialValue = 0)
	@Column(name = "ID_TIPO_FUNC")
	public Long getId() {
		return super.getId();
	}
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 255, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "DESCRICAO", length = 255, nullable = false)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the empresa
	 */
	@NotNull(message = "{campo.obrigatorio}")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMPRESA", nullable = false)
	public Empresa getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa
	 *            the empresa to set
	 */
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}