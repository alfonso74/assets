package rcp.assets.dao;

import java.util.List;


import rcp.assets.model.GrupoCargo;

public class GrupoCargoDAO extends GenericDAOImpl<GrupoCargo, Long> {

	/**
	 * Retorna un listado de los grupos que están asociados a un caso.
	 * @param noCia
	 * @param noCaso
	 * @return
	 */
	public List<GrupoCargo> getGruposByCaso(String noCia, Long noCaso) {
		String queryHQL = "from GrupoCargo grupo " +
				"inner join fetch grupo.caso caso " +
				"where caso.noCaso = " + noCaso + " and caso.noCia = " + noCia;
		System.out.println("QueryHQL: " + queryHQL);
		List<GrupoCargo> resultado = executeHQL(queryHQL);
		System.out.println("Resultado: " + resultado);
		System.out.println("Empty: " + resultado.isEmpty());
		return resultado;
	}
	
	/**
	 * Permite saber si un grupo tiene algún detalle de cargo asociado al mismo.
	 * @param grupo
	 * @return
	 */
	public boolean hasDetalleCargos(GrupoCargo grupo) {
		String queryHQL = "select grupo " +
				"from GrupoCargo grupo, DetalleCargo detCargo " +
				"where detCargo.grupo = grupo and grupo.idGrupo = " + grupo.getId();
		System.out.println("QueryHQL: " + queryHQL);
		List<GrupoCargo> resultado = executeHQL(queryHQL);
		System.out.println("Resultado: " + resultado);
		System.out.println("Empty: " + resultado.isEmpty());
		return !resultado.isEmpty();
	}
}
