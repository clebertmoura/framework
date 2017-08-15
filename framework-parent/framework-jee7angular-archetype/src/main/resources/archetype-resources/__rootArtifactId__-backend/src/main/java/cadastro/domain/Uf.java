#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cadastro.domain;

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
import javax.persistence.UniqueConstraint;

import ${package}.core.domain.BaseEntityImpl;

/**
 * @author clebertmoura
 *
 */
@Entity
@Table(name = "UF", schema = "DBARCHETYPE01", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"NOME", "ID_PAIS"}, name = "UK_UF_NOME_NO_PAIS"),
		@UniqueConstraint(columnNames = {"SIGLA", "ID_PAIS"}, name = "UK_UF_SIGLA_NO_PAIS")}
)
public class Uf extends BaseEntityImpl<Long>{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String sigla;
	private String codigoIbge;
	private Pais pais;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_UF")
	@SequenceGenerator(name = "SEQ_UF", schema = "DBARCHETYPE01", sequenceName = "DBARCHETYPE01.SEQ_UF", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_UF")
	public Long getId() {
		return super.getId();
	}
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@Column(name = "NOME", nullable = false, length = 255)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "SIGLA", nullable = false, length = 255)
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	@Column(name = "CODIGO_IBGE", nullable = true, length = 2)
	public String getCodigoIbge() {
		return codigoIbge;
	}

	public void setCodigoIbge(String codigoIbge) {
		this.codigoIbge = codigoIbge;
	}

	/**
	 * @return the pais
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PAIS", nullable = false)
	public Pais getPais() {
		return pais;
	}

	/**
	 * @param pais the pais to set
	 */
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	
}
