package br.com.framework.pilotojee7.relatorio.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.core.enums.SimNao;
import br.com.framework.pilotojee7.relatorio.domain.RelatorioFiltro;
import br.com.framework.service.impl.BaseEntityResourceImpl;

/**
 * Resource da entidade {@link RelatorioFiltro}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class RelatorioFiltroResource extends BaseEntityResourceImpl<Long, RelatorioFiltro> {

	private static final long serialVersionUID = -1L;
	
	private String rotulo;
	private Integer ordem;
	private String permissao;
	private SimNao habilitado;
	private String parametro;
	private RelatorioResource relatorio;
	private FiltroResource filtro;
	private Integer dependenteDe;
	
	public RelatorioFiltroResource() {
		super();
	}

	/**
	 * @param entity
	 */
	public RelatorioFiltroResource(RelatorioFiltro entity) {
		super(entity);
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public RelatorioFiltroResource(RelatorioFiltro entity, boolean onlyId) {
		super(entity, onlyId);
	}
	
	@Override
	public void loadFromEntity(RelatorioFiltro entity) {
		super.loadFromEntity(entity);
		setRotulo(entity.getRotulo());
		setOrdem(entity.getOrdem());
		setPermissao(entity.getPermissao());
		setHabilitado(entity.getHabilitado());
		setParametro(entity.getParametro());
		setDependenteDe(entity.getDependenteDe());
		setRelatorio(new RelatorioResource(entity.getRelatorio(), true));
		setFiltro(new FiltroResource(entity.getFiltro(), true));
	}

	public String getRotulo() {
		return this.rotulo;
	}
	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}
	public Integer getOrdem() {
		return this.ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	public String getPermissao() {
		return this.permissao;
	}
	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}
	public SimNao getHabilitado() {
		return this.habilitado;
	}
	public void setHabilitado(SimNao habilitado) {
		this.habilitado = habilitado;
	}
	public String getParametro() {
		return this.parametro;
	}
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}
	public RelatorioResource getRelatorio() {
		return this.relatorio;
	}
	public void setRelatorio(RelatorioResource relatorio) {
		this.relatorio = relatorio;
	}
	public FiltroResource getFiltro() {
		return this.filtro;
	}
	public void setFiltro(FiltroResource filtro) {
		this.filtro = filtro;
	}
	public Integer getDependenteDe() {
		return dependenteDe;
	}
	public void setDependenteDe(Integer dependenteDe) {
		this.dependenteDe = dependenteDe;
	}

}

