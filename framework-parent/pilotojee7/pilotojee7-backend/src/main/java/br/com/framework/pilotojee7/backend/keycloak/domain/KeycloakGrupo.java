package br.com.framework.pilotojee7.backend.keycloak.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.framework.pilotojee7.core.domain.BaseEntityImpl;

@Entity
@Table(name = "KC_GRUPO", schema = "CADASTRO")
public class KeycloakGrupo extends BaseEntityImpl<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nome;
	private String idKeycloak;
	
	private List<KeycloakUsuario> usuarios;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_KC_GRUPO")
	@SequenceGenerator(name = "SEQ_KC_GRUPO", schema = "CADASTRO", sequenceName = "SEQ_KC_GRUPO", allocationSize = 1, initialValue = 1)
	@Column(name = "ID_KC_GRUPO")
	public Long getId() {
		return super.getId();
	}

	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@Column(name = "ID_KEYCLOAK", length = 255, nullable = true)
	public String getIdKeycloak() {
		return idKeycloak;
	}

	public void setIdKeycloak(String idKeycloak) {
		this.idKeycloak = idKeycloak;
	}

	@Column(name = "NOME", length = 255, nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "grupos")
	public List<KeycloakUsuario> getUsuarios() {
		if (usuarios == null) {
			usuarios = new ArrayList<>();
		}
		return usuarios;
	}

	public void setUsuarios(List<KeycloakUsuario> usuarios) {
		this.usuarios = usuarios;
	}

}
