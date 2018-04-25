package br.com.framework.pilotojee7.relatorio.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.framework.pilotojee7.core.domain.BaseEntityAuditedImpl;
import br.com.framework.pilotojee7.core.enums.SimNao;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Entity
@Table(name = "RELATORIO", schema = "RELATORIO")
public class Relatorio extends BaseEntityAuditedImpl<Long>{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String descricao;
	private String permissao;
	private SimNao habilitado;
	private byte[] jrxmlData;
	
	private Categoria categoria;
	private List<RelatorioFiltro> filtros;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RELATORIO")
	@SequenceGenerator(name = "SEQ_RELATORIO", schema = "RELATORIO", sequenceName = "SEQ_RELATORIO", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_RELATORIO")
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

	@Column(name = "DESCRICAO", nullable = true, length = 255)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "PERMISSAO", nullable = true, length = 255)
	public String getPermissao() {
		return permissao;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}

	@Column(name = "HABILITADO", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	public SimNao getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(SimNao habilitado) {
		this.habilitado = habilitado;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "JRXML_DATA", nullable = false)
	public byte[] getJrxmlData() {
		return jrxmlData;
	}

	public void setJrxmlData(byte[] jrxmlData) {
		this.jrxmlData = jrxmlData;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "relatorio", orphanRemoval = true)
	@OrderBy("ordem ASC")
	public List<RelatorioFiltro> getFiltros() {
		if (filtros == null) {
			filtros = new ArrayList<>();
		}
		return filtros;
	}

	public void setFiltros(List<RelatorioFiltro> filtros) {
		this.filtros = filtros;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CATEGORIA", nullable = false)
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
}
