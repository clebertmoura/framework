package br.com.framework.pilotojee7.cadastro.service.resource;

import java.time.LocalDateTime;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.framework.pilotojee7.cadastro.domain.PessoaContato;
import br.com.framework.pilotojee7.cadastro.domain.PessoaFisica;
import br.com.framework.pilotojee7.cadastro.enums.Genero;

/**
 * Resource da entidade {@link PessoaFisica}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class PessoaFisicaResource extends PessoaResource<PessoaFisica> {

	private static final long serialVersionUID = -1L;
	
	private String cpf;
	private LocalDateTime dataNascimento;
	private String rg;
	private String nomeMae;
	private String nomePai;
	private Genero genero;
	
	public PessoaFisicaResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public PessoaFisicaResource(PessoaFisica entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public PessoaFisicaResource(PessoaFisica entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(PessoaFisica entity) {
		super.loadFromEntity(entity);
		setNomePessoa(entity.getNomePessoa());
		setCpf(entity.getCpf());
		setDataNascimento(entity.getDataNascimento());
		setRg(entity.getRg());
		setNomeMae(entity.getNomeMae());
		setNomePai(entity.getNomePai());
		setGenero(entity.getGenero());
		setEndereco(new EnderecoResource(entity.getEndereco()));
		Iterator<PessoaContato> itContatos = entity.getContatos().iterator();
		while (itContatos.hasNext()) {
			PessoaContato element = itContatos.next();
			this.getContatos().add(new PessoaContatoResource(element));
		}
	}

	public String getCpf() {
		return this.cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm.Z")
	public LocalDateTime getDataNascimento() {
		return this.dataNascimento;
	}
	public void setDataNascimento(LocalDateTime dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getRg() {
		return this.rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getNomeMae() {
		return this.nomeMae;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	public String getNomePai() {
		return this.nomePai;
	}
	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}
	public Genero getGenero() {
		return this.genero;
	}
	public void setGenero(Genero genero) {
		this.genero = genero;
	}

}

