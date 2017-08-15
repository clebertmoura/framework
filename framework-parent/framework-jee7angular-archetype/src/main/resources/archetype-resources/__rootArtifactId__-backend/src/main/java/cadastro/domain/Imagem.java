#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cadastro.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import ${package}.core.domain.BaseEntityAuditedImpl;

@Entity
@Table(name="IMAGEM", schema="DBARCHETYPE01")
@AttributeOverrides({
	@AttributeOverride(name = "createDate", column = @Column(name = "PROC_DH_CRIACAO", nullable = false)),
	@AttributeOverride(name = "lastModifiedDate", column = @Column(name = "IMAGEM_DH_MODIFICACAO", nullable = false)),
	@AttributeOverride(name = "status", column = @Column(name = "IMAGEM_FL_HABILITADO", nullable = false)),
	@AttributeOverride(name = "userLogin", column = @Column(name = "IMAGEM_NM_LOGIN", length = 255, nullable = true))
})
public class Imagem extends BaseEntityAuditedImpl<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7376796482735791898L;
	
	private String nome;
	private String contentType;
	private String fileExtension;
	private byte[] data;
	private int length;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IMAGEM")
	@SequenceGenerator(name = "SEQ_IMAGEM", schema = "DBARCHETYPE01", sequenceName = "SEQ_IMAGEM", allocationSize = 1, initialValue = 1)
	@Column(name = "IMAGEM_ID")
	public Long getId() {
		return super.getId();
	}
	
	@Column(name = "IMAGEM_NM_NOME", length = 255, nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "IMAGEM_NM_CONTENTTYPE", nullable = false, length = 30)
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Column(name = "IMAGEM_NM_FILEEXT", nullable = false, length = 10)
	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	@Column(name="IMAGEM_BL_DATA", nullable=false)
	@Lob @Basic(fetch = FetchType.LAZY)
	@XmlTransient
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Column(name = "IMAGEM_NU_LENGTH", nullable = false)
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
