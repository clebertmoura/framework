package br.com.framework.pilotojee7.cadastro.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.com.framework.domain.enums.Status;
import br.com.framework.pilotojee7.cadastro.domain.CategoriaNoticia;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 * DAO da entidade CategoriaNoticia.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class CategoriaNoticiaDao extends AppBaseDaoImpl<Long, CategoriaNoticia> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public CategoriaNoticiaDao() {
		super(CategoriaNoticia.class);
	}

	public List<CategoriaNoticia> getInseridas(LocalDateTime dataUltSinc, LocalDateTime dataIntervalo,
			ArrayList<String> gruposRestrictions) throws PersistenceException {
		StringBuffer sBuf = new StringBuffer();
		sBuf.append("SELECT distinct(categoriaNoticia) FROM CategoriaNoticia categoriaNoticia ");
		sBuf.append("WHERE categoriaNoticia.status = :statusAtivo ");

		if (dataUltSinc != null) {
			sBuf.append("AND categoriaNoticia.createDate >= :dataUltSinc ");
		}

		sBuf.append("ORDER BY categoriaNoticia.nome ASC ");

		TypedQuery<CategoriaNoticia> query = getEntityManager().createQuery(sBuf.toString(), CategoriaNoticia.class);

		query.setParameter("statusAtivo", Status.ACTIVE);

		if (dataUltSinc != null) {
			query.setParameter("dataUltSinc", dataUltSinc);
		}

		return query.getResultList();
	}

	public List<CategoriaNoticia> getAtualizadas(LocalDateTime dataUltSinc, LocalDateTime dataIntervalo,
			List<Long> idsNoticias, ArrayList<String> gruposRestrictions) throws PersistenceException {
		StringBuffer sBuf = new StringBuffer();
		sBuf.append("SELECT distinct(categoriaNoticia) FROM CategoriaNoticia categoriaNoticia ");
		sBuf.append("WHERE categoriaNoticia.status = :statusAtivo ");
		if (idsNoticias.size() > 0) {
			sBuf.append("AND categoriaNoticia.id NOT IN (:idsNoticias) ");
		}
		if (dataUltSinc != null) {
			sBuf.append("AND categoriaNoticia.lastModifiedDate >= :dataUltSinc ");
		}

		sBuf.append("ORDER BY categoriaNoticia.nome ASC ");
		TypedQuery<CategoriaNoticia> query = getEntityManager().createQuery(sBuf.toString(), CategoriaNoticia.class);

		query.setParameter("statusAtivo", Status.ACTIVE);

		if (dataUltSinc != null) {
			query.setParameter("dataUltSinc", dataUltSinc);
		}

		if (idsNoticias.size() > 0) {
			query.setParameter("idsNoticias", idsNoticias);
		}

		return query.getResultList();
	}

	public List<CategoriaNoticia> getDeletadas(LocalDateTime dataUltSinc, LocalDateTime dataIntervalo,
			ArrayList<String> gruposRestrictions) throws PersistenceException {
		StringBuffer sBuf = new StringBuffer();
		sBuf.append("SELECT distinct(categoriaNoticia) FROM CategoriaNoticia categoriaNoticia ");
		sBuf.append("WHERE categoriaNoticia.status = :statusAtivo ");
		if (dataUltSinc != null) {
			sBuf.append("AND categoriaNoticia.lastModifiedDate >= :dataUltSinc ");
		}

		sBuf.append("ORDER BY categoriaNoticia.nome ASC ");

		TypedQuery<CategoriaNoticia> query = getEntityManager().createQuery(sBuf.toString(), CategoriaNoticia.class);
		query.setParameter("statusAtivo", Status.INACTIVE);
		if (dataUltSinc != null) {
			query.setParameter("dataUltSinc", dataUltSinc);
		}

		return query.getResultList();
	}

}
