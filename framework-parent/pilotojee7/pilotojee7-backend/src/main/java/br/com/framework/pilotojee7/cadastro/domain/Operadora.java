package br.com.framework.pilotojee7.cadastro.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import br.com.framework.pilotojee7.core.domain.BaseEntityAuditedImpl;

/**
 * Representa uma Operadora de telefonia.
 * 
 * @author Cleber Moura <cleber.moura@tecnofrota.com>
 */
@Entity
@Table(name = "OPERADORA", schema = "CADASTRO")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Operadora extends BaseEntityAuditedImpl<Long>{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OPERADORA")
	@SequenceGenerator(name = "SEQ_OPERADORA", schema = "CADASTRO", sequenceName = "SEQ_OPERADORA", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_OPERADORA")
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

}
