package br.com.framework.pilotojee7.relatorio.service.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.codec.binary.Base64;

import br.com.framework.pilotojee7.core.enums.SimNao;
import br.com.framework.pilotojee7.relatorio.domain.Relatorio;
import br.com.framework.pilotojee7.relatorio.domain.RelatorioFiltro;
import br.com.framework.service.impl.BaseEntityAuditedResourceImpl;

/**
 * Resource da entidade {@link Relatorio}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class RelatorioResource extends BaseEntityAuditedResourceImpl<Long, Relatorio> {

	private static final long serialVersionUID = -1L;
	
	private String nome;
	private String descricao;
	private String permissao;
	private SimNao habilitado;
	private String jrxmlData;
	private List<RelatorioFiltroResource> filtros;
	private CategoriaResource categoria;
	
	public RelatorioResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public RelatorioResource(Relatorio entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public RelatorioResource(Relatorio entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(Relatorio entity) {
		super.loadFromEntity(entity);
		setNome(entity.getNome());
		setDescricao(entity.getDescricao());
		setPermissao(entity.getPermissao());
		setHabilitado(entity.getHabilitado());
		if (entity.getJrxmlData() != null) {
			setJrxmlData(Base64.encodeBase64String(entity.getJrxmlData()));
		}
		Iterator<RelatorioFiltro> itFiltros = entity.getFiltros().iterator();
		while (itFiltros.hasNext()) {
			RelatorioFiltro element = itFiltros.next();
			this.getFiltros().add(new RelatorioFiltroResource(element));
		}
		setCategoria(new CategoriaResource(entity.getCategoria()));
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
	public String getJrxmlData() {
		return this.jrxmlData;
	}
	public void setJrxmlData(String jrxmlData) {
		this.jrxmlData = jrxmlData;
	}
	public List<RelatorioFiltroResource> getFiltros() {
		if (filtros == null) {
			filtros = new ArrayList<RelatorioFiltroResource>();
		}
		return this.filtros;
	}
	public void setFiltros(List<RelatorioFiltroResource> filtros) {
		this.filtros = filtros;
	}
	public CategoriaResource getCategoria() {
		return this.categoria;
	}
	public void setCategoria(CategoriaResource categoria) {
		this.categoria = categoria;
	}

}

