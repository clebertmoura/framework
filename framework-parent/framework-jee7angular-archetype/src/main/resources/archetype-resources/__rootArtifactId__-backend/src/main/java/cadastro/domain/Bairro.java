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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ${package}.core.domain.BaseEntityImpl;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Entity
@Table(name = "BAIRRO", schema = "DBARCHETYPE01", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"NOME", "ID_CIDADE"}, name = "UK_BAIRRO_NOME_NA_CIDADE")
})
public class Bairro extends BaseEntityImpl<Long>{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private Cidade cidade;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BAIRRO")
	@SequenceGenerator(name = "SEQ_BAIRRO", schema = "DBARCHETYPE01", sequenceName = "SEQ_BAIRRO", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_BAIRRO")
	public Long getId() {
		return super.getId();
	}
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 255, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "NOME", nullable = false, length = 255)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CIDADE", nullable = false)
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

}
