package br.com.framework.pilotojee7.cadastro.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.com.framework.domain.enums.Status;
import br.com.framework.pilotojee7.cadastro.domain.Noticia;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;
import br.com.framework.pilotojee7.core.enums.SimNao;

/**
 * DAO da entidade Noticia.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class NoticiaDao extends AppBaseDaoImpl<Long, Noticia> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public NoticiaDao() {
		super(Noticia.class);
	}

	public List<Noticia> getInseridas(LocalDateTime dataUltSinc, LocalDateTime dataIntervalo, 
			List<String> grupos, SimNao destaque, Integer first, Integer max, List<Long> idsCategorias, String titulo) throws PersistenceException {
		StringBuffer sBuf = new StringBuffer();
		sBuf.append("SELECT DISTINCT noti FROM Noticia noti ");
		sBuf.append("INNER JOIN noti.categorias cat ");
		sBuf.append("LEFT JOIN noti.grupos grp ");
		sBuf.append("WHERE cat.status = :statusAtivoCategoria ");
		sBuf.append("AND noti.status = :statusAtivo ");

		if (dataUltSinc != null) {
			sBuf.append("AND noti.createDate >= :dataUltSinc ");
		}
		sBuf.append("AND noti.dataInicial <= now() ");
		sBuf.append("AND noti.dataInicial >= :dataIntervalo ");
		sBuf.append("AND (noti.dataFinal IS NULL OR (noti.dataFinal >= now())) ");

		sBuf.append("AND (noti.grupos IS EMPTY OR grp.nome IN :grupos) ");

		if (idsCategorias != null && idsCategorias.size() > 0) {
			sBuf.append("AND cat.id IN (:idsCategorias) ");
		}
		
		if (destaque != null) {
			sBuf.append("AND noti.destaque = :destaque ");
		}

		if (titulo != null) {
			sBuf.append("AND noti.titulo LIKE :titulo ");
		}

		sBuf.append("ORDER BY noti.lastModifiedDate DESC ");

		TypedQuery<Noticia> query = getEntityManager().createQuery(sBuf.toString(), Noticia.class);

		query.setParameter("statusAtivo", Status.ACTIVE);
		query.setParameter("statusAtivoCategoria", Status.ACTIVE);
		query.setParameter("grupos", grupos);

		if (dataUltSinc != null) {
			query.setParameter("dataUltSinc", dataUltSinc);
		}

		query.setParameter("dataIntervalo", dataIntervalo);

		if (destaque != null) {
			query.setParameter("destaque", destaque);
		}

		if (titulo != null) {
			query.setParameter("titulo", "%" + titulo + "%");
		}

		if (idsCategorias != null && idsCategorias.size() > 0) {
			query.setParameter("idsCategorias", idsCategorias);
		}

		if (max != null || first != null) {
			query.setMaxResults(max);
			query.setFirstResult(first);
		}

		return query.getResultList();
	}

	public List<Noticia> getAtualizadas(LocalDateTime dataUltSinc, LocalDateTime dataIntervalo, List<Long> idsNoticias, List<String> grupos, Integer first, Integer max) throws PersistenceException {
		StringBuffer sBuf = new StringBuffer();
		sBuf.append("SELECT DISTINCT noti FROM Noticia noti ");
		sBuf.append("INNER JOIN noti.categorias cat ");
		sBuf.append("LEFT JOIN noti.grupos grp ");

		sBuf.append("WHERE cat.status = :statusAtivoCategoria ");
		sBuf.append("AND noti.status = :statusAtivo ");
		if (idsNoticias != null && idsNoticias.size() > 0) {
			sBuf.append("AND noti.id NOT IN (:noticiaIds) ");
		}
		if (dataUltSinc != null) {
			sBuf.append("AND noti.lastModifiedDate >= :dataUltSinc ");
		}
		sBuf.append("AND noti.dataInicial <= now() ");
		sBuf.append("AND noti.dataInicial >= :dataIntervalo ");
		sBuf.append("AND (noti.dataFinal >= now() OR noti.dataFinal = NULL OR (noti.dataFinal IS NULL)) ");

		sBuf.append("AND (noti.grupos IS EMPTY OR grp.nome IN :grupos) ");

		sBuf.append("ORDER BY noti.lastModifiedDate ASC ");
		TypedQuery<Noticia> query = getEntityManager().createQuery(sBuf.toString(), Noticia.class);

		query.setParameter("statusAtivo", Status.ACTIVE);
		query.setParameter("statusAtivoCategoria", Status.ACTIVE);
		query.setParameter("grupos", grupos);

		if (dataUltSinc != null) {
			query.setParameter("dataUltSinc", dataUltSinc);
		}

		if (idsNoticias != null && idsNoticias.size() > 0) {
			query.setParameter("noticiaIds", idsNoticias);
		}
		if (max != null || first != null) {
			query.setMaxResults(max);
			query.setFirstResult(first);
		}
		
		query.setParameter("dataIntervalo", dataIntervalo);

		return query.getResultList();
	}

	public List<Noticia> getDeletadas(LocalDateTime dataUltSinc, LocalDateTime dataIntervalo, List<String> grupos) throws PersistenceException {
		StringBuffer sBuf = new StringBuffer();
		sBuf.append("SELECT DISTINCT noti FROM Noticia noti ");
		sBuf.append("INNER JOIN noti.categorias cat ");
		sBuf.append("LEFT JOIN noti.grupos grp ");
		
		sBuf.append("WHERE noti.status = :statusAtivo ");
		if (dataUltSinc != null) {
			sBuf.append("AND noti.lastModifiedDate >= :dataUltSinc ");
		}
		sBuf.append("AND noti.dataInicial <= now() ");
		sBuf.append("AND noti.dataInicial >= :dataIntervalo ");
		sBuf.append("AND (noti.dataFinal  >= now() OR noti.dataFinal = NULL OR (noti.dataFinal IS NULL)) ");
		
		sBuf.append("AND (noti.grupos IS EMPTY OR grp.nome IN :grupos) ");
		
		sBuf.append("ORDER BY noti.lastModifiedDate ASC ");

		TypedQuery<Noticia> query = getEntityManager().createQuery(sBuf.toString(), Noticia.class);
		query.setParameter("statusAtivo", Status.INACTIVE);
		query.setParameter("grupos", grupos);
		
		if (dataUltSinc != null) {
			query.setParameter("dataUltSinc", dataUltSinc);
		}
		query.setParameter("dataIntervalo", dataIntervalo);

		return query.getResultList();
	}

	public List<Noticia> getExpiradas(LocalDateTime dataUltSinc, LocalDateTime dataIntervalo, List<String> grupos) throws PersistenceException {
		StringBuffer sBuf = new StringBuffer();
		sBuf.append("SELECT DISTINCT noti FROM Noticia noti ");
		sBuf.append("INNER JOIN noti.categorias cat ");
		sBuf.append("LEFT JOIN noti.grupos grp ");
		
		sBuf.append("WHERE cat.status = :statusAtivoCategoria ");
		sBuf.append("AND noti.status = :statusAtivo ");
		
		if (dataUltSinc != null) {
			sBuf.append("AND noti.lastModifiedDate >= :dataUltSinc ");
		}

		sBuf.append("AND noti.dataInicial <= now() ");
		sBuf.append("AND noti.dataInicial >= :dataIntervalo ");
		sBuf.append("AND (noti.dataFinal IS NOT NULL AND noti.dataFinal < now()) ");
		
		if (dataUltSinc != null) {
			sBuf.append("AND noti.dataInicial >= :dataUltSinc ");
		}

		sBuf.append("AND (noti.grupos IS EMPTY OR grp.nome IN :grupos) ");
		
		sBuf.append("ORDER BY noti.lastModifiedDate ASC ");
		
		TypedQuery<Noticia> query = getEntityManager().createQuery(sBuf.toString(), Noticia.class);
		query.setParameter("statusAtivo", Status.ACTIVE);
		query.setParameter("statusAtivoCategoria", Status.ACTIVE);
		query.setParameter("grupos", grupos);
		if (dataUltSinc != null) {
			query.setParameter("dataUltSinc", dataUltSinc);
		}
		query.setParameter("dataIntervalo", dataIntervalo);

		return query.getResultList();
	}

	
	
}
