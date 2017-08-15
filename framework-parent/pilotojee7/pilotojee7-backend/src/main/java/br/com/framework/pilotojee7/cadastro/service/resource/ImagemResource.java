package br.com.framework.pilotojee7.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.cadastro.domain.Imagem;
import br.com.framework.service.impl.BaseEntityAuditedResourceImpl;

/**
 * Resource da entidade {@link Imagem}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class ImagemResource extends BaseEntityAuditedResourceImpl<Long, Imagem> {

	private static final long serialVersionUID = -1L;
	
	private String nome;
	private String contentType;
	private String fileExtension;
	private byte[] data;
	private int length;
	
	public ImagemResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public ImagemResource(Imagem entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public ImagemResource(Imagem entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(Imagem entity) {
		super.loadFromEntity(entity);
		setNome(entity.getNome());
		setContentType(entity.getContentType());
		setFileExtension(entity.getFileExtension());
		setData(entity.getData());
		setLength(entity.getLength());
	}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getContentType() {
		return this.contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getFileExtension() {
		return this.fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public byte[] getData() {
		return this.data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public int getLength() {
		return this.length;
	}
	public void setLength(int length) {
		this.length = length;
	}

}

