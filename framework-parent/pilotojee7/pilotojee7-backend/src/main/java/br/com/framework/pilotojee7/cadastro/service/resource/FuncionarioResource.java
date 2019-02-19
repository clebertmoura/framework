package br.com.framework.pilotojee7.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.cadastro.domain.Funcionario;
import br.com.framework.service.impl.BaseEntityAuditedResourceImpl;

/**
 * Resource da entidade {@link Funcionario}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class FuncionarioResource extends BaseEntityAuditedResourceImpl<Long, Funcionario> {

	private static final long serialVersionUID = -1L;
	
	private String matricula;
	private TipoFuncionarioResource tipoFuncionario;
	private FilialResource filial;
	private PessoaFisicaResource pessoaFisica;
	
	public FuncionarioResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public FuncionarioResource(Funcionario entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public FuncionarioResource(Funcionario entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(Funcionario entity) {
		super.loadFromEntity(entity);
		setMatricula(entity.getMatricula());
		setTipoFuncionario(new TipoFuncionarioResource(entity.getTipoFuncionario()));
		setFilial(new FilialResource(entity.getFilial()));
		setPessoaFisica(new PessoaFisicaResource(entity.getPessoaFisica()));
	}

	public String getMatricula() {
		return this.matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public TipoFuncionarioResource getTipoFuncionario() {
		return this.tipoFuncionario;
	}
	public void setTipoFuncionario(TipoFuncionarioResource tipoFuncionario) {
		this.tipoFuncionario = tipoFuncionario;
	}
	public FilialResource getFilial() {
		return this.filial;
	}
	public void setFilial(FilialResource filial) {
		this.filial = filial;
	}
	public PessoaFisicaResource getPessoaFisica() {
		return this.pessoaFisica;
	}
	public void setPessoaFisica(PessoaFisicaResource pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

}

