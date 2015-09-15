package rcp.assets.dao;

import java.util.List;

import org.hibernate.Query;

import rcp.assets.model.Keyword;
import rcp.assets.model.Notificacion;

public class NotificacionDAO extends GenericDAOImpl<Notificacion, Long> {
	
	public boolean existeNotificacion(String noCia, Integer noPeriodo, Keyword tipoNotificacion) {
//		String hql = "from Notificacion n " +
//				"where n.noCia = :noCia and n.periodo = :noPeriodo and n.tipo = :tipoNotificacion";
//		Query query = getSession().createQuery(hql);
//		query.setParameter("noCia", noCia);
//		query.setParameter("noPeriodo", noPeriodo);
//		query.setParameter("tipoNotificacion", tipoNotificacion);
//		List<Notificacion> resultados = executeQuery(query);
//		System.out.println("RRR: " + resultados);
//		if (resultados != null && !resultados.isEmpty()) {
//			return true;
//		}
		Notificacion resultado = getNotificacion(noCia, noPeriodo, tipoNotificacion);
		if (resultado != null) {
			return true;
		}
		return false;
	}
	
	
	public Notificacion getNotificacion(String noCia, Integer noPeriodo, Keyword tipoNotificacion) {
		String hql = "from Notificacion n " +
				"where n.noCia = :noCia and n.periodo = :noPeriodo and n.tipo = :tipoNotificacion";
		Query query = getSession().createQuery(hql);
		query.setParameter("noCia", noCia);
		query.setParameter("noPeriodo", noPeriodo);
		query.setParameter("tipoNotificacion", tipoNotificacion);
		List<Notificacion> resultados = executeQuery(query);
		System.out.println("RRR: " + resultados);
		if (resultados != null && !resultados.isEmpty()) {
			return resultados.get(0);
		}
		return null;
	}

}
