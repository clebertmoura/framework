package br.com.framework.pilotojee7.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.cadastro.domain.Pais;
import br.com.framework.service.impl.BaseEntityResourceImpl;

/**
 * Resource da entidade {@link Pais}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class PaisResource extends BaseEntityResourceImpl<Long, Pais> {

	private static final long serialVersionUID = -1L;
	
	private String nome;
	private String codigoOnuAlpha;
	private String codigoOnu;
	private ImagemResource imagem;
	
	public PaisResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public PaisResource(Pais entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public PaisResource(Pais entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(Pais entity) {
		setNome(entity.getNome());
		setCodigoOnuAlpha(entity.getCodigoOnuAlpha());
		setCodigoOnu(entity.getCodigoOnu());
		setImagem(new ImagemResource(entity.getImagem()));
	}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCodigoOnuAlpha() {
		return this.codigoOnuAlpha;
	}
	public void setCodigoOnuAlpha(String codigoOnuAlpha) {
		this.codigoOnuAlpha = codigoOnuAlpha;
	}
	public String getCodigoOnu() {
		return this.codigoOnu;
	}
	public void setCodigoOnu(String codigoOnu) {
		this.codigoOnu = codigoOnu;
	}
	public ImagemResource getImagem() {
		return this.imagem;
	}
	public void setImagem(ImagemResource imagem) {
		this.imagem = imagem;
	}

}

