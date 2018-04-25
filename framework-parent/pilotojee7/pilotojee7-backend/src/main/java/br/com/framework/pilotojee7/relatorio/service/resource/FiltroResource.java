package br.com.framework.pilotojee7.relatorio.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.core.enums.SimNao;
import br.com.framework.pilotojee7.relatorio.domain.Filtro;
import br.com.framework.pilotojee7.relatorio.enums.TipoFiltro;
import br.com.framework.service.impl.BaseEntityResourceImpl;

/**
 * Resource da entidade {@link Filtro}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class FiltroResource extends BaseEntityResourceImpl<Long, Filtro> {

	private static final long serialVersionUID = -1L;
	
	private String nome;
	private String descricao;
	private String dados;
	private SimNao flagSql;
	private TipoFiltro tipoFiltro;
	
	public FiltroResource() {
		super();
	}

	/**
	 * @param entity
	 */
	public FiltroResource(Filtro entity) {
		super(entity);
		loadExtraFromEntity(entity);
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public FiltroResource(Filtro entity, boolean onlyId) {
		super(entity, onlyId);
	}

	@Override
	public void loadFromEntity(Filtro entity) {
		super.loadFromEntity(entity);
		setNome(entity.getNome());
		setDescricao(entity.getDescricao());
		setDados(entity.getDados());
		setTipoFiltro(entity.getTipoFiltro());
		setFlagSql(entity.getFlagSql());
	}
	
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return this.descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDados() {
		return this.dados;
	}
	public void setDados(String dados) {
		this.dados = dados;
	}
	public TipoFiltro getTipoFiltro() {
		return this.tipoFiltro;
	}
	public void setTipoFiltro(TipoFiltro tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}
	public SimNao getFlagSql() {
		return flagSql;
	}
	public void setFlagSql(SimNao flagSql) {
		this.flagSql = flagSql;
	}

}

