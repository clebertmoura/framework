package br.com.framework.pilotojee7.cadastro.service.resource;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.cadastro.domain.Celular;
import br.com.framework.pilotojee7.core.enums.SimNao;
import br.com.framework.service.impl.BaseEntityAuditedResourceImpl;

/**
 * Resource da entidade {@link Celular}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class CelularResource extends BaseEntityAuditedResourceImpl<Long, Celular> {

	private static final long serialVersionUID = -1L;
	
	private SimNao trackingAtivo;
	private String imei;
	private String serial;
	private String versao;
	private FilialResource filial;
	private SimcardResource simcard;
	private ModeloCelularResource modelo;
	
	public CelularResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public CelularResource(Celular entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public CelularResource(Celular entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(Celular entity) {
		super.loadFromEntity(entity);
		setTrackingAtivo(entity.getTrackingAtivo());
		setImei(entity.getImei());
		setSerial(entity.getSerial());
		setVersao(entity.getVersao());
		setFilial(new FilialResource(entity.getFilial()));
		setSimcard(new SimcardResource(entity.getSimcard()));
		setModelo(new ModeloCelularResource(entity.getModelo()));
	}

	public SimNao getTrackingAtivo() {
		return this.trackingAtivo;
	}
	public void setTrackingAtivo(SimNao trackingAtivo) {
		this.trackingAtivo = trackingAtivo;
	}
	public String getImei() {
		return this.imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getSerial() {
		return this.serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getVersao() {
		return this.versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	public FilialResource getFilial() {
		return this.filial;
	}
	public void setFilial(FilialResource filial) {
		this.filial = filial;
	}
	public SimcardResource getSimcard() {
		return this.simcard;
	}
	public void setSimcard(SimcardResource simcard) {
		this.simcard = simcard;
	}
	public ModeloCelularResource getModelo() {
		return this.modelo;
	}
	public void setModelo(ModeloCelularResource modelo) {
		this.modelo = modelo;
	}

}

