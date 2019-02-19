package br.com.framework.pilotojee7.cadastro.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import br.com.framework.pilotojee7.core.domain.BaseEntityImpl;

@Entity
@Table(name = "TIPO_LOGRADOURO", schema = "CADASTRO")
@Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
public class TipoLogradouro extends BaseEntityImpl<Long> {

	private static final long serialVersionUID = 1L;
	
	private String descricao;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPO_LOGRADOURO")
	@SequenceGenerator(name = "SEQ_TIPO_LOGRADOURO", schema = "CADASTRO", sequenceName = "SEQ_TIPO_LOGRADOURO", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_TIPO_LOGRADOURO")
	public Long getId() {
		return super.getId();
	}
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@Column(name = "descricao", nullable = false, length = 255)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	

}
