package br.com.framework.pilotojee7.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.cadastro.domain.TipoFuncionario;
import br.com.framework.service.impl.BaseEntityAuditedResourceImpl;

/**
 * Resource da entidade {@link TipoFuncionario}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class TipoFuncionarioResource extends BaseEntityAuditedResourceImpl<Long, TipoFuncionario> {

	private static final long serialVersionUID = -1L;
	
	private String descricao;
	private EmpresaResource empresa;
	
	public TipoFuncionarioResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public TipoFuncionarioResource(TipoFuncionario entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public TipoFuncionarioResource(TipoFuncionario entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(TipoFuncionario entity) {
		super.loadFromEntity(entity);
		setDescricao(entity.getDescricao());
		setEmpresa(new EmpresaResource(entity.getEmpresa()));
	}

	public String getDescricao() {
		return this.descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public EmpresaResource getEmpresa() {
		return this.empresa;
	}
	public void setEmpresa(EmpresaResource empresa) {
		this.empresa = empresa;
	}

}

