package br.com.framework.pilotojee7.backend.keycloak.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.framework.pilotojee7.cadastro.domain.Imagem;
import br.com.framework.pilotojee7.core.domain.BaseEntityImpl;

@Entity
@Table(name = "KC_USUARIO", schema = "CADASTRO")
public class KeycloakUsuario extends BaseEntityImpl<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String primeiroNome;
	private String ultimoNome;
	private String email;
	private String login;
	private String idKeycloak;
	
	private List<KeycloakGrupo> grupos;
	private Imagem imagem;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_KC_USUARIO")
	@SequenceGenerator(name = "SEQ_KC_USUARIO", schema = "CADASTRO", sequenceName = "SEQ_KC_USUARIO", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_KC_USUARIO")
	public Long getId() {
		return super.getId();
	}

	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@Column(name = "PRIMEIRO_NOME", length = 255, nullable = false)
	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public void setPrimeiroNome(String nome) {
		this.primeiroNome = nome;
	}

	@Column(name = "ULTIMO_NOME", length = 255, nullable = true)
	public String getUltimoNome() {
		return ultimoNome;
	}

	public void setUltimoNome(String lastName) {
		this.ultimoNome = lastName;
	}

	@Column(name = "EMAIL", length = 255, nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "LOGIN", length = 255, nullable = false, unique = true)
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "ID_KEYCLOAK", length = 255, nullable = true)
	public String getIdKeycloak() {
		return idKeycloak;
	}

	public void setIdKeycloak(String idKeycloak) {
		this.idKeycloak = idKeycloak;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "KC_USUARIO_GRUPO", schema = "CADASTRO", 
		joinColumns = @JoinColumn(name = "ID_KC_USUARIO", referencedColumnName = "ID_KC_USUARIO"), 
		inverseJoinColumns = @JoinColumn(name = "ID_KC_GRUPO", referencedColumnName = "ID_KC_GRUPO")
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

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_IMAGEM", nullable = true)
	public Imagem getImagem() {
		return imagem;
	}

	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}

}
