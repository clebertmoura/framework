#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import ${package}.cadastro.domain.Cidade;
import br.com.framework.service.impl.BaseEntityResourceImpl;

/**
 * Resource da entidade {@link Cidade}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class CidadeResource extends BaseEntityResourceImpl<Long, Cidade> {

	private static final long serialVersionUID = -1L;
	
	private String nome;
	private String codigoIbge;
	private UfResource uf;
	
	public CidadeResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public CidadeResource(Cidade entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public CidadeResource(Cidade entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(Cidade entity) {
		setNome(entity.getNome());
		setCodigoIbge(entity.getCodigoIbge());
		setUf(new UfResource(entity.getUf()));
	}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCodigoIbge() {
		return this.codigoIbge;
	}
	public void setCodigoIbge(String codigoIbge) {
		this.codigoIbge = codigoIbge;
	}
	public UfResource getUf() {
		return this.uf;
	}
	public void setUf(UfResource uf) {
		this.uf = uf;
	}

}

