package rcp.assets.dao;

import java.util.List;

import org.hibernate.Query;

import rcp.assets.model.DetalleCargo;


public class DetalleCargoDAO extends GenericDAOImpl<DetalleCargo, Long> {

	/*
	@SuppressWarnings("unchecked")
	public List<DetalleCargo> buscarDetallesPorCaso(String noCia, String noCaso) {
		getSession().beginTransaction();
		String hql = "from DetalleCargo det " +
				"inner join fetch det.cargo cargo " +
				"inner join fetch cargo.caso caso " + 
				"left join fetch det.grupo grupo" +
				"left join fetch det.tipoCargo " + 
				"where caso.noCaso = :noCaso and caso.noCia = :noCia";
		Query query = getSession().createQuery(hql);
		query.setParameter("noCaso", Long.parseLong(noCaso));
		query.setParameter("noCia", noCia);
		List<DetalleCargo> resultados = query.list();
//		System.out.println("Cantidad: " + resultados.size());
//		System.out.println("Detalles: " + resultados);
		getSession().getTransaction().commit();
		return resultados;
	}
*/
	
	@SuppressWarnings("unchecked")
	public List<DetalleCargo> buscarDetallesPorCaso(String noCia, String noCaso) {
		getSession().beginTransaction();
		String hql = "from DetalleCargo det " +
				"inner join fetch det.cargo cargo " +
				"inner join fetch cargo.caso caso " + 
				"left join fetch det.grupo grupo" +
				"left join fetch det.tipoCargo tipoCargo " + 
				"where caso.noCaso = :noCaso and caso.noCia = :noCia " +
				"order by tipoCargo.prioridad, tipoCargo.descripcion";
		Query query = getSession().createQuery(hql);
		query.setParameter("noCaso", Long.parseLong(noCaso));
		query.setParameter("noCia", noCia);
		List<DetalleCargo> resultados = query.list();
//		System.out.println("Cantidad: " + resultados.size());
//		System.out.println("Detalles: " + resultados);
		getSession().getTransaction().commit();
		return resultados;
	}
	
	
}
