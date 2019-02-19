package br.com.framework.pilotojee7.cadastro.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.framework.domain.enums.Status;
import br.com.framework.pilotojee7.backend.keycloak.domain.KeycloakUsuario;
import br.com.framework.pilotojee7.cadastro.domain.Dispositivo;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Dispositivo.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class DispositivoDao extends AppBaseDaoImpl<Long, Dispositivo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public DispositivoDao() {
		super(Dispositivo.class);
	}
	
	public Dispositivo findByUsuarioPlatformAndUUID(KeycloakUsuario keycloakUsuario, String platform, String uuid) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT disp FROM Dispositivo disp ");
		sb.append("JOIN disp.keycloakUsuario kUsr ");
		sb.append("WHERE disp.status = :ativo AND kUsr = :keycloakUsuario ");
		sb.append("AND disp.platform = :platform AND disp.uuid = :uuid ");
		
		TypedQuery<Dispositivo> query  = getEntityManager().createQuery(sb.toString(), Dispositivo.class);
		query.setParameter("ativo", Status.ACTIVE);
		query.setParameter("keycloakUsuario", keycloakUsuario);
		query.setParameter("platform", platform);
		query.setParameter("uuid", uuid);
		
		Dispositivo dispositivo = null;
		try {
			dispositivo = query.getSingleResult();
		} catch (NoResultException e) {
		}
		
		return dispositivo;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Dispositivo> findByUsuario(KeycloakUsuario keycloakUsuario) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT disp FROM Dispositivo disp ");
		sb.append("JOIN disp.keycloakUsuario kUsr ");
		sb.append("WHERE disp.status = :ativo AND kUsr = :keycloakUsuario ");
		sb.append("ORDER BY disp.createDate DESC ");
		
		TypedQuery<Dispositivo> query  = getEntityManager().createQuery(sb.toString(), Dispositivo.class);
		query.setParameter("ativo", Status.ACTIVE);
		query.setParameter("keycloakUsuario", keycloakUsuario);
		
		return query.getResultList();
	}
	
	
}
