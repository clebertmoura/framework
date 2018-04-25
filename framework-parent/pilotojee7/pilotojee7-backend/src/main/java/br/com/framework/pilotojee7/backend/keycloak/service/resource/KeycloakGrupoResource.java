package br.com.framework.pilotojee7.backend.keycloak.service.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.pilotojee7.backend.keycloak.domain.KeycloakGrupo;
import br.com.framework.pilotojee7.backend.keycloak.domain.KeycloakUsuario;
import br.com.framework.service.impl.BaseEntityResourceImpl;

/**
 * Resource da entidade {@link KeycloakGrupo}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class KeycloakGrupoResource extends BaseEntityResourceImpl<Long, KeycloakGrupo> {

	private static final long serialVersionUID = -1L;
	
	private String idKeycloak;
	private String nome;
	private List<KeycloakUsuarioResource> usuarios;
	
	public KeycloakGrupoResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public KeycloakGrupoResource(KeycloakGrupo entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public KeycloakGrupoResource(KeycloakGrupo entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(KeycloakGrupo entity) {
		super.loadFromEntity(entity);
		setIdKeycloak(entity.getIdKeycloak());
		setNome(entity.getNome());
		Iterator<KeycloakUsuario> itUsuarios = entity.getUsuarios().iterator();
		while (itUsuarios.hasNext()) {
			KeycloakUsuario element = itUsuarios.next();
			this.getUsuarios().add(new KeycloakUsuarioResource(element));
		}
	}

	public String getIdKeycloak() {
		return this.idKeycloak;
	}
	public void setIdKeycloak(String idKeycloak) {
		this.idKeycloak = idKeycloak;
	}
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<KeycloakUsuarioResource> getUsuarios() {
		if (usuarios == null) {
			usuarios = new ArrayList<KeycloakUsuarioResource>();
		}
		return this.usuarios;
	}
	public void setUsuarios(List<KeycloakUsuarioResource> usuarios) {
		this.usuarios = usuarios;
	}

}

