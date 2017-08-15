package br.com.framework.pilotojee7.cadastro.service.resource;

import java.time.LocalDate;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.framework.pilotojee7.cadastro.domain.Simcard;
import br.com.framework.pilotojee7.cadastro.enums.UsoSimcard;
import br.com.framework.pilotojee7.core.enums.SimNao;
import br.com.framework.service.impl.BaseEntityAuditedResourceImpl;

/**
 * Resource da entidade {@link Simcard}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class SimcardResource extends BaseEntityAuditedResourceImpl<Long, Simcard> {

	private static final long serialVersionUID = -1L;
	
	private String ddd;
	private String numero;
	private String imei;
	private LocalDate dataCompra;
	private LocalDate dataNotaFiscal;
	private String notaFiscal;
	private Date dataCancelamento;
	private SimNao habilitado;
	private UsoSimcard usoSimcard;
	private OperadoraResource operadora;
	private EmpresaResource empresa;
	
	public SimcardResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public SimcardResource(Simcard entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public SimcardResource(Simcard entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(Simcard entity) {
		super.loadFromEntity(entity);
		setDdd(entity.getDdd());
		setNumero(entity.getNumero());
		setImei(entity.getImei());
		setDataCompra(entity.getDataCompra());
		setDataNotaFiscal(entity.getDataNotaFiscal());
		setNotaFiscal(entity.getNotaFiscal());
		setDataCancelamento(entity.getDataCancelamento());
		setHabilitado(entity.getHabilitado());
		setUsoSimcard(entity.getUsoSimcard());
		setOperadora(new OperadoraResource(entity.getOperadora()));
		setEmpresa(new EmpresaResource(entity.getEmpresa()));
	}

	public String getDdd() {
		return this.ddd;
	}
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	public String getNumero() {
		return this.numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getImei() {
		return this.imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	@JsonFormat(pattern = "dd-MM-yyyy")
	public LocalDate getDataCompra() {
		return this.dataCompra;
	}
	public void setDataCompra(LocalDate dataCompra) {
		this.dataCompra = dataCompra;
	}
	@JsonFormat(pattern = "dd-MM-yyyy")
	public LocalDate getDataNotaFiscal() {
		return this.dataNotaFiscal;
	}
	public void setDataNotaFiscal(LocalDate dataNotaFiscal) {
		this.dataNotaFiscal = dataNotaFiscal;
	}
	public String getNotaFiscal() {
		return this.notaFiscal;
	}
	public void setNotaFiscal(String notaFiscal) {
		this.notaFiscal = notaFiscal;
	}
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm.Z")
	public Date getDataCancelamento() {
		return this.dataCancelamento;
	}
	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}
	public SimNao getHabilitado() {
		return this.habilitado;
	}
	public void setHabilitado(SimNao habilitado) {
		this.habilitado = habilitado;
	}
	public UsoSimcard getUsoSimcard() {
		return this.usoSimcard;
	}
	public void setUsoSimcard(UsoSimcard usoSimcard) {
		this.usoSimcard = usoSimcard;
	}
	public OperadoraResource getOperadora() {
		return this.operadora;
	}
	public void setOperadora(OperadoraResource operadora) {
		this.operadora = operadora;
	}
	public EmpresaResource getEmpresa() {
		return this.empresa;
	}
	public void setEmpresa(EmpresaResource empresa) {
		this.empresa = empresa;
	}

}

