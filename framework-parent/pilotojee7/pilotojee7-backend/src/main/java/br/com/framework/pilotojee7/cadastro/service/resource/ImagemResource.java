package br.com.framework.pilotojee7.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.codec.binary.Base64;

import br.com.framework.pilotojee7.cadastro.domain.Imagem;
import br.com.framework.service.impl.BaseEntityAuditedResourceImpl;

/**
 * Resource da entidade {@link Imagem}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class ImagemResource extends BaseEntityAuditedResourceImpl<Long, Imagem> {

	private static final long serialVersionUID = -6491435032710958369L;

	private String nome;
	private String contentType;
	private String fileExtension;
	private int length;
	private String data;

	private boolean loadData = true;

	public ImagemResource() {
		super();
	}

	public ImagemResource(Imagem entity, boolean onlyId) {
		this(entity, onlyId, false);
	}
	
	public ImagemResource(Imagem entity, boolean onlyId, boolean loadData) {
		super(entity, onlyId);
		this.loadData = loadData;
		loadExtraFromEntity(entity);
	}

	public ImagemResource(Imagem entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(Imagem entity) {
		super.loadFromEntity(entity);
		setNome(entity.getNome());
		setContentType(entity.getContentType());
		setFileExtension(entity.getFileExtension());
		setLength(entity.getLength());
	}

	@Override
	public void loadExtraFromEntity(Imagem entity) {
		super.loadExtraFromEntity(entity);
		if (loadData) {
			setData(Base64.encodeBase64String(entity.getData()));
		}
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
