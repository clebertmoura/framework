package br.com.framework.pilotojee7.relatorio.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.framework.pilotojee7.core.domain.BaseEntityImpl;
import br.com.framework.pilotojee7.core.enums.SimNao;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Entity
@Table(name = "RELATORIO_FILTRO", schema = "RELATORIO", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"ID_RELATORIO", "ORDEM"}, name = "UKEY_REL_FILTRO_ORDEM")
})
public class RelatorioFiltro extends BaseEntityImpl<Long>{

	private static final long serialVersionUID = 1L;
	
	private Integer ordem;
	private String rotulo;
	private String parametro;
	private String permissao;
	private SimNao habilitado;
	private Relatorio relatorio;
	private Filtro filtro;
	private Integer dependenteDe;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REL_FILTRO")
	@SequenceGenerator(name = "SEQ_REL_FILTRO", schema = "RELATORIO", sequenceName = "SEQ_REL_FILTRO", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_REL_FILTRO")
	public Long getId() {
		return super.getId();
	}
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 50, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "ROTULO", nullable = false, length = 50)
	public String getRotulo() {
		return rotulo;
	}

	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Column(name = "ORDEM", nullable = false)
	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_RELATORIO", nullable = false)
	public Relatorio getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FILTRO", nullable = false)
	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
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

	@Column(name = "PARAMETRO", nullable = false, length = 50)
	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	@Column(name = "DEPENDENTE_DE", nullable = true)
	public Integer getDependenteDe() {
		return dependenteDe;
	}

	public void setDependenteDe(Integer dependenteDe) {
		this.dependenteDe = dependenteDe;
	}

}
