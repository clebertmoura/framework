package br.com.framework.pilotojee7.cadastro.domain;

import java.time.LocalDate;
import java.util.Date;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import br.com.framework.pilotojee7.cadastro.enums.UsoSimcard;
import br.com.framework.pilotojee7.core.domain.BaseEntityAuditedImpl;
import br.com.framework.pilotojee7.core.enums.SimNao;

/**
 * Representa uma Operadora de telefonia.
 * 
 * @author Cleber Moura <cleber.moura@tecnofrota.com>
 */
@Entity
@Table(name = "SIMCARD", schema = "DBARCHETYPE01")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Simcard extends BaseEntityAuditedImpl<Long>{

	private static final long serialVersionUID = 1L;
	
	private Operadora operadora;
	private String ddd;
	private String numero;
	private String imei;
	
	private LocalDate dataCompra;
	private LocalDate dataNotaFiscal;
	private String notaFiscal;
	private Date dataCancelamento;
	private SimNao habilitado;
	private Empresa empresa;
	private UsoSimcard usoSimcard;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SIMCARD")
	@SequenceGenerator(name = "SEQ_SIMCARD", schema = "DBARCHETYPE01", sequenceName = "SEQ_SIMCARD", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_SIMCARD")
	public Long getId() {
		return super.getId();
	}
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@NotNull(message = "{campo.obrigatorio}")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_OPERADORA", nullable = false)
	public Operadora getOperadora() {
		return operadora;
	}

	public void setOperadora(Operadora operadora) {
		this.operadora = operadora;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 2, max = 3, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "DDD", nullable = true, length = 3)
	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 7, max = 12, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "NUMERO", nullable = false, length = 12)
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 255, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "IMEI", nullable = false, length = 255)
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Column(name = "DATA_COMPRA", nullable = true)
	public LocalDate getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(LocalDate dataCompra) {
		this.dataCompra = dataCompra;
	}

	@Column(name = "DATA_NOTAFISCAL", nullable = true)
	public LocalDate getDataNotaFiscal() {
		return dataNotaFiscal;
	}

	public void setDataNotaFiscal(LocalDate dataNotaFiscal) {
		this.dataNotaFiscal = dataNotaFiscal;
	}

	@Column(name = "NOTA_FISCAL", nullable = true, length = 15)
	public String getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(String notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CANCELAMENTO", nullable = true)
	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Enumerated(EnumType.STRING)
	@Column(name = "HABILITADO", nullable = false, length = 1)
	public SimNao getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(SimNao habilitado) {
		this.habilitado = habilitado;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMPRESA", nullable = false)
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Enumerated(EnumType.STRING)
	@Column(name = "USO_SIMCARD", nullable = false, length = 5)
	public UsoSimcard getUsoSimcard() {
		return usoSimcard;
	}

	public void setUsoSimcard(UsoSimcard usoSimcard) {
		this.usoSimcard = usoSimcard;
	}

}
