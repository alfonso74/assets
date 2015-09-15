package rcp.assets.dao;

import java.util.List;

import org.hibernate.Query;

import rcp.assets.model.Direccion;

public class DireccionDAO extends GenericDAOImpl<Direccion, Long> {
	
	public Integer getSiguienteNoDireccion(Long idResponsable) {
		Integer siguiente = 1;
		String hql = "select max(d.noDireccion) from Direccion d " +
				"where d.responsable.idResponsable = :idResponsable";
		Query query = getSession().createQuery(hql);
		query.setParameter("idResponsable", idResponsable);
		List<Integer> resultados = executeQuery(query);
		// si encontramos registros, y si el resultado no es un null, entonces incrementamos el índice
		// de las direcciones
		if (!resultados.isEmpty() & resultados.get(0) != null) {
			siguiente = resultados.get(0) + 1;
		}
		return siguiente;
	}

}
