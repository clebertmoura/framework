package br.com.framework.pilotojee7.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.cadastro.domain.ModeloCelular;
import br.com.framework.service.impl.BaseEntityResourceImpl;

/**
 * Resource da entidade {@link ModeloCelular}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class ModeloCelularResource extends BaseEntityResourceImpl<Long, ModeloCelular> {

	private static final long serialVersionUID = -1L;
	
	private String nome;
	private FabricanteResource fabricante;
	
	public ModeloCelularResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public ModeloCelularResource(ModeloCelular entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public ModeloCelularResource(ModeloCelular entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(ModeloCelular entity) {
		setNome(entity.getNome());
		setFabricante(new FabricanteResource(entity.getFabricante()));
	}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public FabricanteResource getFabricante() {
		return this.fabricante;
	}
	public void setFabricante(FabricanteResource fabricante) {
		this.fabricante = fabricante;
	}

}

