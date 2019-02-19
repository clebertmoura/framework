package br.com.framework.pilotojee7.cadastro.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.framework.pilotojee7.backend.keycloak.domain.KeycloakGrupo;
import br.com.framework.pilotojee7.core.domain.BaseEntityAuditedImpl;
import br.com.framework.pilotojee7.core.enums.SimNao;

/**
 * @author Raniel
 *
 */
@Entity
@Table(name = "NOTICIA", schema = "CADASTRO")
public class Noticia extends BaseEntityAuditedImpl<Long> {

	private static final long serialVersionUID = 1L;

	private String titulo;
	private String descricao;
	private String conteudo;
	private List<Imagem> imagens;
	private LocalDateTime dataInicial;
	private LocalDateTime dataFinal;
	private List<CategoriaNoticia> categorias;
	private List<KeycloakGrupo> grupos;
	private SimNao destaque;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NOTICIA")
	@SequenceGenerator(name = "SEQ_NOTICIA", schema = "CADASTRO", sequenceName = "SEQ_NOTICIA", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_NOTICIA")
	public Long getId() {
		return super.getId();
	}

	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 255, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "TITULO", nullable = false, length = 255)
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "CONTEUDO", nullable = false)
	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Size(min = 1, max = 255, message = "{campo.texto.minMaxInvalido}")
	@Column(name = "DESCRICAO", nullable = false, length = 255)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Column(name = "DATA_INICIAL", nullable = false)
	public LocalDateTime getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(LocalDateTime dataInicial) {
		this.dataInicial = dataInicial;
	}

	@NotNull(message = "{campo.obrigatorio}")
	@Column(name = "DATA_FINAL", nullable = false)
	public LocalDateTime getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDateTime dataFinal) {
		this.dataFinal = dataFinal;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "NOTICIA_IMAGEM", schema = "CADASTRO", joinColumns = @JoinColumn(name = "ID_NOTICIA", referencedColumnName = "ID_NOTICIA"), inverseJoinColumns = @JoinColumn(name = "ID_IMAGEM", referencedColumnName = "ID_IMAGEM"))
	public List<Imagem> getImagens() {
		if (imagens == null) {
			imagens = new ArrayList<Imagem>();
		}
		return imagens;
	}

	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "NOTICIA_GRUPO", schema = "CADASTRO", 
		joinColumns = @JoinColumn(name = "ID_NOTICIA", referencedColumnName = "ID_NOTICIA"), 
		inverseJoinColumns = @JoinColumn(name = "ID_KEYCLOAK_GRP", referencedColumnName = "ID_KEYCLOAK_GRP")
	)
	public List<KeycloakGrupo> getGrupos() {
		if (grupos == null) {
			grupos = new ArrayList<>();
		}
		return grupos;
	}

	public void setGrupos(List<KeycloakGrupo> grupos) {
		this.grupos = grupos;
	}

	@Column(name = "DESTAQUE", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	public SimNao getDestaque() {
		return destaque;
	}

	public void setDestaque(SimNao destaque) {
		this.destaque = destaque;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "NOTICIA_CATEGORIAS", schema = "CADASTRO",
			joinColumns = @JoinColumn(name = "ID_NOTICIA", referencedColumnName = "ID_NOTICIA"),
			inverseJoinColumns = @JoinColumn(name = "ID_CATEGORIA_NOTICIA", referencedColumnName = "ID_CATEGORIA_NOTICIA")
	)
	public List<CategoriaNoticia> getCategorias() {
		if (categorias == null) {
			categorias = new ArrayList<CategoriaNoticia>();
		}
		return categorias;
	}

	public void setCategorias(List<CategoriaNoticia> categorias) {
		this.categorias = categorias;
	}
}
