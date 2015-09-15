package rcp.assets.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import rcp.assets.model.Caso;
import rcp.assets.model.Compania;
import rcp.assets.model.DetalleCargo;


public class CasoDAO extends GenericDAOImpl<Caso, Long> {
	
	private static final String CODIGO_NO = "N";
	private static final String CODIGO_SI = "S";
	

	public Float calcularImpuesto(Caso caso, Float porcImpuesto) {
		// solo los tipo_cargos con impuesto
		Float impuesto = new Float(0);
		if (caso.getNoCia().equals(Compania.PMA.getCodigo())) {
			getSession().beginTransaction();
			String hql = "from DetalleCargo det " +
					"inner join fetch det.cargo cargo " +
					"inner join fetch cargo.caso caso " + 
					"left join fetch det.tipoCargo " +
					"left join fetch caso.responsable resp " +
					"where caso.idCaso = :idCaso";
			Query query = getSession().createQuery(hql);
			query.setParameter("idCaso", caso.getIdCaso());
			List<DetalleCargo> resultados = query.list();
			getSession().getTransaction().commit();
			System.out.println("Resultado: " + resultados);
			if (caso.getResponsable() != null & caso.getResponsable().getExcentoImpuesto().equals(CODIGO_NO)) {
				System.out.println("Cliente excento: " + caso.getResponsable().getExcentoImpuesto());
				DetalleCargo detalle = null;
				for (int n = 0; n < resultados.size(); n++) {
					detalle = resultados.get(n);
					if (detalle.getTipoCargo().getPagaImpuesto().equals(CODIGO_SI)) {
						impuesto += detalle.getMontoConImpuesto(porcImpuesto / 100);   // o 0.07F x default
					}
				}
				System.out.println("Total de impuesto: " + impuesto);
			} else {
				System.out.println("Cliente excento o null: " + caso.getResponsable());
			}
		} else {
			System.out.println("Compañía excenta: " + caso.getNoCia());
		}
		return impuesto;
	}


	/**
	 * Retorna un iterador con el tipo de cargo y monto, para cada tipo 
	 * de cargo incluido en un caso.
	 * @param caso
	 * @return Iterador con TipoCargo y Float
	 */
	public Iterator<Object> getDetalleCargos(Caso caso) {
		String hql = "select tc, grupo, sum(det.monto) " +
				"from DetalleCargo det " +
				"left join det.tipoCargo tc " +
				"left join det.grupo grupo " +
				"inner join det.cargo cargo " +
				"inner join cargo.caso caso " + 
				"where caso.idCaso = " + caso.getIdCaso().toString() + " " + 
				"group by ifnull(grupo.grupo, '99'), ifnull(grupo.descripcion, tc.descripcion) " +
				"having sum(det.monto) > 0 " +
				"order by ifnull(grupo.grupo, tc.prioridad), ifnull(grupo.descripcion, tc.descripcion)" ;
		//group by ifnull(grupocargo2_.grupo, '99'), ifnull(grupocargo2_.descripcion, tipocargo1_.descripcion)
		//order by ifnull(grupocargo2_.grupo, tipocargo1_.PRIORIDAD), ifnull(grupocargo2_.descripcion, tipocargo1_.descripcion)
		Iterator<Object> resultado = executeHQL(hql).iterator();
		return resultado;
	}


	/*
	public Iterator<Object> getDetalleCargos(Caso caso) {
		String hql = "select tc, grupo, sum(det.monto) " +
				"from DetalleCargo det " +
				"left join det.tipoCargo tc " +
				"left join det.grupo grupo " +
				"inner join det.cargo cargo " +
				"inner join cargo.caso caso " + 
				"where caso.idCaso = " + caso.getIdCaso().toString() + " " + 
				"group by tc, grupo " +
				"order by tc.prioridad, tc.descripcion" ;
		Iterator<Object> resultado = executeHQL(hql).iterator();
		return resultado;
	}
	 */

}
