package br.com.framework.pilotojee7.cadastro.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.framework.pilotojee7.core.domain.BaseEntityAuditedImpl;
import br.com.framework.pilotojee7.core.enums.SimNao;

@Entity
@Table(name = "CELULAR", schema = "CADASTRO")
public class Celular extends BaseEntityAuditedImpl<Long> {
	
	private static final long serialVersionUID = 1L;

	private SimNao trackingAtivo;
	private String imei;
	private String serial;
	private Filial filial;
	private String versao;
	private Simcard simcard;
	private ModeloCelular modelo;

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CELULAR")
	@SequenceGenerator(name = "SEQ_CELULAR", schema = "CADASTRO", sequenceName = "SEQ_CELULAR", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_CELULAR")
	public Long getId() {
		return super.getId();
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Column(name = "TRACKING_ATIVO", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	public SimNao getTrackingAtivo() {
		return trackingAtivo;
	}

	public void setTrackingAtivo(SimNao trackingAtivo) {
		this.trackingAtivo = trackingAtivo;
	}

	/**
	 * @return the imei
	 */
	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 255, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "IMEI", length = 255, nullable = false)
	public String getImei() {
		return imei;
	}

	/**
	 * @param imei
	 *            the imei to set
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}

	/**
	 * @return the numeroSerie
	 */
	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 255, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "SERIAL", nullable = false, length = 255)
	public String getSerial() {
		return serial;
	}

	/**
	 * @param numeroSerie
	 *            the numeroSerie to set
	 */
	public void setSerial(String numeroSerie) {
		this.serial = numeroSerie;
	}

	/**
	 * @return the filial
	 */
	@NotNull(message = "{campo.obrigatorio}")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FILIAL", nullable = false)
	public Filial getFilial() {
		return filial;
	}

	/**
	 * @param filial
	 *            the filial to set
	 */
	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SIMCARD", nullable = true)
	public Simcard getSimcard() {
		return simcard;
	}

	public void setSimcard(Simcard simcard) {
		this.simcard = simcard;
	}

	@Column(name = "VERSAO", nullable = true, length = 20)
	public String getVersao() {
		return versao;
	}
	
	public void setVersao(String versao) {
		this.versao = versao;
	}
	
	@NotNull(message = "{campo.obrigatorio}")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MODELO_CEL", nullable = false)
	public ModeloCelular getModelo() {
		return modelo;
	}

	public void setModelo(ModeloCelular modelo) {
		this.modelo = modelo;
	}

	@Transient
	public String getNumeroCelular() {
		String numeroCelular = null;
		try {
			if (getSimcard() != null) {
				numeroCelular = getSimcard().getDdd() + getSimcard().getNumero().replaceAll("[-]", "");
			}
		} catch(Exception e) {}
		return numeroCelular;
	}

}