package rcp.assets.dao;

import java.util.List;

import org.hibernate.Query;

import rcp.assets.model.Keyword;

public class KeywordDAO extends GenericDAOImpl<Keyword, Long> {

	public List<Keyword> findAllForComboDataManager() {
		getSession().beginTransaction();
		String hql = "from Keyword k " +
				"order by k.tipo, k.posicion, k.descripcion";
		Query query = getSession().createQuery(hql);
		List<Keyword> resultados = query.list();
		getSession().getTransaction().commit();
		return resultados;
	}
	
}
