package rcp.assets.dao;

import java.util.List;

import org.hibernate.Query;

import rcp.assets.model.Recargo;


public class RecargoDAO extends GenericDAOImpl<Recargo, Long> {

	public List<Recargo> getRecargos(String noCia, Integer noPeriodo) {
		String queryString = "select r from Recargo as r " +
				"inner join r.tipoRecargo as k " +
				"where r.noCia = :noCia and r.periodo = :noPeriodo and k.tipo = 'Recargo de anualidad' and " +
				"r.estado.estado != 'I' " +
				"order by k.posicion";
		Query query = getSession().createQuery(queryString);
		query.setParameter("noCia", noCia);
		query.setParameter("noPeriodo", noPeriodo);
		System.out.println("Query SQL: " + query);
		List<Recargo> resultado = executeQuery(query);
		return resultado;
	}

}
