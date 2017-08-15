#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import ${package}.cadastro.domain.Bairro;
import br.com.framework.service.impl.BaseEntityResourceImpl;

/**
 * Resource da entidade {@link Bairro}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class BairroResource extends BaseEntityResourceImpl<Long, Bairro> {

	private static final long serialVersionUID = -1L;
	
	private String nome;
	private CidadeResource cidade;
	
	public BairroResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public BairroResource(Bairro entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public BairroResource(Bairro entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(Bairro entity) {
		setNome(entity.getNome());
		setCidade(new CidadeResource(entity.getCidade()));
	}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public CidadeResource getCidade() {
		return this.cidade;
	}
	public void setCidade(CidadeResource cidade) {
		this.cidade = cidade;
	}

}

