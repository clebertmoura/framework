package br.com.framework.pilotojee7.relatorio.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.framework.pilotojee7.core.domain.BaseEntityImpl;
import br.com.framework.pilotojee7.core.enums.SimNao;
import br.com.framework.pilotojee7.relatorio.enums.TipoFiltro;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Entity
@Table(name = "FILTRO", schema = "RELATORIO")
public class Filtro extends BaseEntityImpl<Long>{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String descricao;
	private String dados;
	private SimNao flagSql;
	private TipoFiltro tipoFiltro;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FILTRO")
	@SequenceGenerator(name = "SEQ_FILTRO", schema = "RELATORIO", sequenceName = "SEQ_FILTRO", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_FILTRO")
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

	@Column(name = "DADOS", nullable = true)
	@Lob
	@Basic(fetch = FetchType.EAGER)
	public String getDados() {
		return dados;
	}

	public void setDados(String dados) {
		this.dados = dados;
	}

	@Column(name = "TIPO_FILTRO", nullable = false, length = 30)
	@Enumerated(EnumType.STRING)
	public TipoFiltro getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(TipoFiltro tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	@Column(name = "FLAG_SQL", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	public SimNao getFlagSql() {
		return flagSql;
	}

	public void setFlagSql(SimNao flagSql) {
		this.flagSql = flagSql;
	}

	
}
