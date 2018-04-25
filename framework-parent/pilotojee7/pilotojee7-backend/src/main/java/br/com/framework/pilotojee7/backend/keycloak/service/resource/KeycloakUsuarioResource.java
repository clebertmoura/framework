package br.com.framework.pilotojee7.backend.keycloak.service.resource;

import java.util.*;
import java.time.*;

import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.framework.service.impl.BaseEntityResourceImpl;
import br.com.framework.service.impl.BaseEntityAuditedResourceImpl;
import br.com.framework.pilotojee7.backend.keycloak.domain.KeycloakUsuario;

import br.com.framework.pilotojee7.backend.keycloak.domain.KeycloakGrupo;
import br.com.framework.pilotojee7.backend.keycloak.service.resource.KeycloakGrupoResource;
import br.com.framework.pilotojee7.cadastro.domain.Imagem;
import br.com.framework.pilotojee7.cadastro.service.resource.ImagemResource;

/**
 * Resource da entidade {@link KeycloakUsuario}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class KeycloakUsuarioResource extends BaseEntityResourceImpl<Long, KeycloakUsuario> {

	private static final long serialVersionUID = -1L;
	
	private String primeiroNome;
	private String ultimoNome;
	private String email;
	private String login;
	private String idKeycloak;
	private List<KeycloakGrupoResource> grupos;
	private ImagemResource imagem;
	
	public KeycloakUsuarioResource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public KeycloakUsuarioResource(KeycloakUsuario entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public KeycloakUsuarioResource(KeycloakUsuario entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(KeycloakUsuario entity) {
		super.loadFromEntity(entity);
		setPrimeiroNome(entity.getPrimeiroNome());
		setUltimoNome(entity.getUltimoNome());
		setEmail(entity.getEmail());
		setLogin(entity.getLogin());
		setIdKeycloak(entity.getIdKeycloak());
		Iterator<KeycloakGrupo> itGrupos = entity.getGrupos().iterator();
		while (itGrupos.hasNext()) {
			KeycloakGrupo element = itGrupos.next();
			this.getGrupos().add(new KeycloakGrupoResource(element));
		}
		setImagem(new ImagemResource(entity.getImagem()));
	}

	public String getPrimeiroNome() {
		return this.primeiroNome;
	}
	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}
	public String getUltimoNome() {
		return this.ultimoNome;
	}
	public void setUltimoNome(String ultimoNome) {
		this.ultimoNome = ultimoNome;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLogin() {
		return this.login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getIdKeycloak() {
		return this.idKeycloak;
	}
	public void setIdKeycloak(String idKeycloak) {
		this.idKeycloak = idKeycloak;
	}
	public List<KeycloakGrupoResource> getGrupos() {
		if (grupos == null) {
			grupos = new ArrayList<KeycloakGrupoResource>();
		}
		return this.grupos;
	}
	public void setGrupos(List<KeycloakGrupoResource> grupos) {
		this.grupos = grupos;
	}
	public ImagemResource getImagem() {
		return this.imagem;
	}
	public void setImagem(ImagemResource imagem) {
		this.imagem = imagem;
	}

}

