#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import ${package}.cadastro.domain.Uf;
import br.com.framework.service.impl.BaseEntityResourceImpl;

/**
 * Resource da entidade {@link Uf}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class UfResource extends BaseEntityResourceImpl<Long, Uf> {

	private static final long serialVersionUID = -1L;
	
	private String nome;
	private String sigla;
	private String codigoIbge;
	private PaisResource pais;
	
	public UfResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public UfResource(Uf entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public UfResource(Uf entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(Uf entity) {
		setNome(entity.getNome());
		setSigla(entity.getSigla());
		setCodigoIbge(entity.getCodigoIbge());
		setPais(new PaisResource(entity.getPais()));
	}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSigla() {
		return this.sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getCodigoIbge() {
		return this.codigoIbge;
	}
	public void setCodigoIbge(String codigoIbge) {
		this.codigoIbge = codigoIbge;
	}
	public PaisResource getPais() {
		return this.pais;
	}
	public void setPais(PaisResource pais) {
		this.pais = pais;
	}

}

