package rcp.assets.dao;

import java.util.List;

import rcp.assets.model.Factura;

public class FacturaDAO extends GenericDAOImpl<Factura, Long> {

	public Integer getSiguienteNoFactura() {
		Integer siguiente = 23001;
		String hql = "select max(a.noFactura) from Factura a ";
		List<String> resultados = executeHQL(hql);
		// si encontramos registros, y si el resultado no es un null, entonces incrementamos el índice
		// de las direcciones
		if (!resultados.isEmpty() & resultados.get(0) != null) {
			Integer max = Integer.valueOf(resultados.get(0));
			siguiente = max + 1;
		}
		return siguiente;
	}
	
	
	public boolean existeFactura(Long idCaso) {
		String hql = "from Factura a " +
				"where a.caso.idCaso = " + idCaso.toString() + " and a.estado = 'ACT'";
		List<Factura> resultados = executeHQL(hql);
		// si encontramos registros, y si el resultado no es un null, entonces tenemos una factura
		if (!resultados.isEmpty() && resultados.get(0) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public boolean existeFacturaRetainer(String noExp, Integer mes, Integer yy) {
		String hql = "from Factura a " +
				"where a.noExpediente = '" + noExp + "' and a.periodoMM = " + mes.toString() + " " +
				"and a.periodoYY = " + yy.toString() + " and a.estado = 'ACT'";
		List<Factura> resultados = executeHQL(hql);
		if (!resultados.isEmpty() && resultados.get(0) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public Factura getFacturaByCaso(Long idCaso) {
		String hql = "from Factura fact " +
				"inner join fetch fact.caso caso " +
				"where caso.idCaso = " + idCaso.toString();
		List<Factura> resultados = executeHQL(hql);
		return resultados.get(0);
	}
	
	
	public Factura getFacturaRetainer(String noExp, Integer mes, Integer yy) {
		String hql = "from Factura a " +
				"where a.noExpediente = '" + noExp + "' and a.periodoMM = " + mes.toString() + " " +
				"and a.periodoYY = " + yy.toString();
		List<Factura> resultados = executeHQL(hql);
		return resultados.get(0);
	}
	
	public List<Factura> getFacturasPendientesPeachtree() {
		String hql = "from Factura fact " +
				"where fact.estado = 'ACT' " +
				"and (fact.noPeriodoTx is null or fact.noPeriodoTx = '' " +
				"or fact.noFisico is null or fact.noFisico = '')";
		List<Factura> resultados = executeHQL(hql);
		return resultados;
	}
	
	public List<Factura> getFacturasEnviadasPeachtree() {
		String hql = "from Factura fact " +
				"where fact.estado = 'ACT' " +
				"and (fact.noPeriodoTx is not null and fact.noPeriodoTx != '') " +
				"and (fact.noFisico is not null and fact.noFisico != '')";
		List<Factura> resultados = executeHQL(hql);
		return resultados;
	}

}
