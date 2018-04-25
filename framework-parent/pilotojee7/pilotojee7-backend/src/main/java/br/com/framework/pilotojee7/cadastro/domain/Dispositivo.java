package br.com.framework.pilotojee7.cadastro.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import br.com.framework.pilotojee7.backend.keycloak.domain.KeycloakUsuario;
import br.com.framework.pilotojee7.core.domain.BaseEntityAuditedImpl;

@Entity
@Table(name = "DISPOSITIVO", schema = "CADASTRO")
public class Dispositivo extends BaseEntityAuditedImpl<Long> {
	
	private static final long serialVersionUID = 1L;

	private String cordova;
	private String modelo;
	private String platform;
	private String uuid;
	private String versao;
	private String manufacturer;
	private String serial;
	private Boolean isVirtual;
	
	private String ddd;
	private String numero;
	
	private KeycloakUsuario keycloakUsuario;
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DISPOSITIVO")
	@SequenceGenerator(name = "SEQ_DISPOSITIVO", schema = "CADASTRO", sequenceName = "SEQ_DISPOSITIVO", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_DISPOSITIVO")
	public Long getId() {
		return super.getId();
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


	@Column(name = "VERSAO", nullable = true, length = 20)
	public String getVersao() {
		return versao;
	}
	
	public void setVersao(String versao) {
		this.versao = versao;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 30, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "CORDOVA", nullable = false, length = 30)
	public String getCordova() {
		return cordova;
	}

	public void setCordova(String cordova) {
		this.cordova = cordova;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 255, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "MODELO", nullable = false, length = 255)
	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 255, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "PLATFORM", nullable = false, length = 255)
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 255, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "UUID", nullable = false, length = 255)
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 255, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "MANUFACTURER", nullable = false, length = 255)
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Column(name = "IS_VIRTUAL", nullable = false)
	public Boolean getIsVirtual() {
		return isVirtual;
	}

	public void setIsVirtual(Boolean isVirtual) {
		this.isVirtual = isVirtual;
	}

	@Size(min = 3, max = 3, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "DDD", nullable = true, length = 3)
	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	@Size(min = 8, max = 20, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "NUMERO", nullable = true, length = 20)
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_KEYCLOAK_USR", nullable = false)
	public KeycloakUsuario getKeycloakUsuario() {
		return keycloakUsuario;
	}

	public void setKeycloakUsuario(KeycloakUsuario keycloakUsuario) {
		this.keycloakUsuario = keycloakUsuario;
	}
	
	/**
	 * @return
	 */
	@Transient
	@XmlTransient
	public String getAlias() {
		return Dispositivo.getDispositivoAlias(this, null);
	}
	
	/**
	 * @param keycloakUsuario
	 * @return
	 */
	public String getAlias(KeycloakUsuario keycloakUsuario) {
		return Dispositivo.getDispositivoAlias(this, keycloakUsuario);
	}
	
	/**
	 * Utilitário para geração do alias no formato abaixo:
	 * 
	 * [username]_[platform]_[uuid]
	 * 
	 * @param dispositivo
	 * @param keycloakUsuario
	 * @return
	 */
	public static String getDispositivoAlias(Dispositivo dispositivo, KeycloakUsuario keycloakUsuario) {
		if (keycloakUsuario == null) {
			keycloakUsuario = dispositivo.getKeycloakUsuario();
		}
		return String.format("%s_%s_%s", keycloakUsuario.getLogin(), dispositivo.getPlatform(), dispositivo.getUuid());
	}

}