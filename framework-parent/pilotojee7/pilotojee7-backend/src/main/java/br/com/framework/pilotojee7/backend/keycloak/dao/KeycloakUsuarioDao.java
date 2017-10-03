package br.com.framework.pilotojee7.backend.keycloak.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import br.com.framework.pilotojee7.backend.keycloak.domain.KeycloakUsuario;
import br.com.framework.pilotojee7.cadastro.domain.Noticia;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;
import br.com.framework.search.exception.SearchException;

/**
 *  DAO da entidade KeycloakUsuario.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class KeycloakUsuarioDao extends AppBaseDaoImpl<Long, KeycloakUsuario>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public KeycloakUsuarioDao() {
		super(KeycloakUsuario.class);
	}
	

	protected Query createQueryFindByGruposDaNoticia(boolean isQueryCount, Long idNoticia) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		if (isQueryCount) {
			sb.append("COUNT(DISTINCT kUsr) ");
		} else {
			sb.append("DISTINCT kUsr ");
		}
		sb.append("FROM KeycloakUsuario kUsr ");
		sb.append("INNER JOIN kUsr.grupos kGrp ");
		sb.append("WHERE EXISTS ( ");
		sb.append("	SELECT n FROM Noticia n ");
		sb.append("	INNER JOIN n.grupos grp ");
		sb.append("	WHERE n.id = :idNoticia AND grp = kGrp ");
		sb.append(") ");
		if (!isQueryCount) {
			sb.append("ORDER BY kUsr.login ASC ");
		}
		Query query = getEntityManager().createQuery(sb.toString());
		query.setParameter("idNoticia", idNoticia);
		
		return query;
	}
	
	/**
	 * Retorna os {@link KeycloakUsuario} com permissão na {@link Noticia} informada.
	 * 
	 * @param idNoticia
	 * @param first
	 * @param max
	 * @return
	 * @throws SearchException
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<KeycloakUsuario> findByGruposDaNoticia(Long idNoticia, int first, int max) throws SearchException {
		Query query = createQueryFindByGruposDaNoticia(false, idNoticia);
		if (first > -1 && max > 0) {
			query.setFirstResult(first);
			query.setMaxResults(max);
		}
		return query.getResultList();
	}
	
	/**
	 * Retorna a quantidade de {@link KeycloakUsuario} com permissão na {@link Noticia} informada.
	 * 
	 * @param idNoticia
	 * @return
	 * @throws SearchException
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long getCountByGruposDaNoticia(Long idNoticia) throws SearchException {
		Query query = createQueryFindByGruposDaNoticia(true, idNoticia);
		return (Long) query.getSingleResult();
	}
	
}
