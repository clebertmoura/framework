package br.com.framework.pilotojee7.backend.keycloak.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.model.qualifiers.AppConfig;
import br.com.framework.pilotojee7.backend.keycloak.domain.KeycloakUsuario;
import br.com.framework.pilotojee7.backend.keycloak.exception.KeycloakException;
import br.com.framework.pilotojee7.core.util.Constants;

@ApplicationScoped
public class KeycloakAdminManager implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakAdminManager.class);

    @Inject
    @AppConfig
    private Properties appConfig;

    private String serverUrl, realm, userName, password, clientId, clientSecret = null;

    private Keycloak keycloak;

    public KeycloakAdminManager() {
        // TODO Auto-generated constructor stub
    }

    @PostConstruct
    private void postConstruct() {
        serverUrl = appConfig.getProperty(Constants.KEYCLOAK_AUTHSERVERURL);
        realm = appConfig.getProperty(Constants.KEYCLOAK_REALM);
        userName = appConfig.getProperty(Constants.KEYCLOAK_USERNAME);
        clientId = appConfig.getProperty(Constants.KEYCLOAK_CLIENTID);
        password = appConfig.getProperty(Constants.KEYCLOAK_PASSWORD);
        clientSecret = appConfig.getProperty(Constants.KEYCLOAK_CLIENTSECRET);

        keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .username(userName)
                .password(password)
                .clientId("admin-cli")
                .build();
    }

    /**
     * Retorna um {@link GroupRepresentation} pelo id.
     *
     * @param id
     * @return
     */
    public GroupRepresentation getGroup(String id) {
        return keycloak.realm(realm).groups().group(id).toRepresentation();
    }

    /**
     * Retorna uma lista de {@link GroupRepresentation}.
     *
     * @return
     */
    public List<GroupRepresentation> getGroups() {
        return keycloak.realms().realm(realm).groups().groups();
    }

    /**
     * Gera um token de autenticacao.
     *
     * @param username
     * @param password
     * @return
     */
    public String getToken(String username, String password) {
        String token = null;
        Keycloak instanceKeycloak;
        try {
            instanceKeycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .username(username)
                    .password(password)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .build();
            if (instanceKeycloak.tokenManager() != null) {
                token = instanceKeycloak.tokenManager().getAccessTokenString();
            }
        } catch (Exception e) {
            LOGGER.error("Falha ao autenticar usuario no keycloak.", e);
        }
        return token;
    }

    /**
     * Retorna o {@link UserRepresentation} pelo id.
     *
     * @param userId
     * @return
     */
    public UserRepresentation getUserById(String userId) {
        try {
            return keycloak.realm(realm).users().get(userId).toRepresentation();
        } catch (Exception e) {
        	LOGGER.error("Erro ao carregar usuario do keycloak.", e);
            return null;
        }
    }

    /**
     * Retorna o {@link UserRepresentation} pelo id.
     *
     * @param userId
     * @return
     */
    public List<GroupRepresentation> getUserGroupsByUserId(String userId) {
        try {
            return keycloak.realm(realm).users().get(userId).groups();
        } catch (Exception e) {
            LOGGER.error("Erro ao carregar os grupos do usuario do keycloak.", e);
            return null;
        }
    }
    
    /**
     * Cria um novo {@link UserRepresentation}.
     *
     * @param kcUsuario
     * @return
     * @throws KeycloakException
     */
    public Response createUser(KeycloakUsuario kcUsuario) throws KeycloakException {
    	UserRepresentation userRep = new UserRepresentation();
    	userRep.setEnabled(true);
    	userRep.setFirstName(kcUsuario.getPrimeiroNome());
    	userRep.setLastName(kcUsuario.getUltimoNome());
    	userRep.setUsername(kcUsuario.getLogin());
    	userRep.setEmail(kcUsuario.getEmail());
    	
    	// Define password credential
		CredentialRepresentation passwordCred = new CredentialRepresentation();
		passwordCred.setTemporary(true);
		passwordCred.setType(CredentialRepresentation.PASSWORD);
		passwordCred.setValue("temp123");
		
    	userRep.setCredentials(Arrays.asList(passwordCred));
		try {
			return keycloak.realm(realm).users().create(userRep);
		} catch (Exception e) {
			LOGGER.error("Erro ao criar usuario no keycloak.", e);
			throw new KeycloakException(e);
		}
    }

    /**
     * Retorna o {@link UserRepresentation} pelo id.
     *
     * @param userId
     * @return
     */
    public List<String> getRolesByUserId(String userId) {
        try {
            List<String> roles = new ArrayList<>();
            List<ClientRepresentation> clients = keycloak.realm(realm).clients().findAll();

            if (clients != null) {
                if (clients.size() > 0) {
                    for (ClientRepresentation client : clients) {
                        List<RoleRepresentation> rolesFromGroup = keycloak.realm(realm).users().get(userId).roles().clientLevel(client.getId()).listEffective();
                        if (rolesFromGroup != null) {
                            if (rolesFromGroup.size() > 0) {
                                for (RoleRepresentation roleFromGroup : rolesFromGroup) {
                                    roles.add(roleFromGroup.getName());
                                }
                            }
                        }
                    }
                }
            }

            return roles;
        } catch (Exception e) {
            LOGGER.error("Erro ao carregar as roles do usuario do keycloak.", e);
            return null;
        }
    }
}
