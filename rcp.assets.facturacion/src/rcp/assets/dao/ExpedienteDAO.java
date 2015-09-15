package rcp.assets.dao;

import java.util.List;

import rcp.assets.model.Expediente;
import rcp.assets.services.PeriodoAnualidad;


public class ExpedienteDAO extends GenericDAOImpl<Expediente, Long> {

	public List<Expediente> buscarExpedientesConRetainers() {
		/*
		getSession().beginTransaction();
		Criteria crit = getSession().createCriteria(Expediente.class);
		crit.add(Restrictions.eq("noCia", "01"))
			.createCriteria("cargoExpediente")
				.add(Restrictions.gt("retainer.totalRetainers", 0F));
		List<Expediente> resultados = crit.list();
		getSession().getTransaction().commit();
		*/

		/*
		String queryHQL = "from Expediente e " +
				"where e.cargoExpediente.retainer.totalRetainers > 0";
		List<Expediente> resultados = executeHQL(queryHQL);
		*/
		
		String queryHQL = "from Expediente e " +
				"inner join fetch e.cargoExpediente ce " +
				"inner join fetch ce.responsable r " +
				"inner join fetch ce.direccion d " +
				"left outer join fetch ce.atencion a " +
				"where ce.retainer.totalRetainers > 0 " +
				"order by e.noExpediente";
		List<Expediente> resultados = executeHQL(queryHQL);
		
		//crit02 = Restrictions.eq("noCia", "02");
		//List<Expediente> resultados = findByCriteria(crit01);
		return resultados;
	}
	
	
	public List<Expediente> buscarExpedientesActivosConRetainers() {

		String queryHQL = "from Expediente e " +
				"inner join fetch e.cargoExpediente ce " +
				"inner join fetch ce.responsable r " +
				"inner join fetch ce.direccion d " +
				"left outer join fetch ce.atencion a " +
				"where ce.retainer.totalRetainers > 0 and e.estado = 'ACT' " +
				"order by e.noExpediente";
		List<Expediente> resultados = executeHQL(queryHQL);
		
		return resultados;
	}
	
	
	public List<Expediente> buscarExpedientesConAnualidad() {
		String queryHQL = "from Expediente e " +
				"inner join fetch e.cargoExpediente ce " +
				"inner join fetch ce.responsable r " +
				"inner join fetch ce.direccion d " +
				"left outer join fetch ce.atencion a " +
				"where ce.cargosAnuales.totalCargosAnuales > 0 and e.estado = 'ACT' " +
				"order by e.noExpediente";
		List<Expediente> resultados = executeHQL(queryHQL);
		return resultados;
	}
	
	//public List<Expediente> buscarExpedientesPorPeriodo(PeriodoAnualidad periodo, String noCia, boolean tasa) {
	
	public List<Expediente> buscarExpedientesPorPeriodo(PeriodoAnualidad periodo, String noCia, boolean conTasa) {
		Integer diaDesde = periodo.getInscripcionDesdeDD();
		Integer mesDesde = periodo.getInscripcionDesdeMM() + 1;  // en la base de datos, los meses tienen base 1, en java base 0
		Integer diaHasta = periodo.getInscripcionHastaDD();
		Integer mesHasta = periodo.getInscripcionHastaMM() + 1;  // en la base de datos, los meses tienen base 1, en java base 0
		String condicionTasa = conTasa ? "and ce.cargosAnuales.tasa > 0 " : "and ce.cargosAnuales.tasa = 0 ";
		String queryHQL = "from Expediente e " +
				"inner join fetch e.cargoExpediente ce " +
				"inner join fetch ce.responsable r " +
				"inner join fetch ce.direccion d " +
				"left outer join fetch ce.atencion a " +
				"where ce.cargosAnuales.totalCargosAnuales > 0 and e.estado = 'ACT' " +
				"and e.noCia = '" + noCia + "' " + condicionTasa +
				"and ((day(fecha_inscripcion) >= " + diaDesde + " and month(fecha_inscripcion) = " + mesDesde + ") or month(fecha_inscripcion) > " + mesDesde + ") " +
				"and ((day(fecha_inscripcion) <= " + diaHasta + " and month(fecha_inscripcion) = " + mesHasta + ") or month(fecha_inscripcion) < " + mesHasta + ") " + 
				"order by e.noExpediente";
		List<Expediente> resultados = executeHQL(queryHQL);
		return resultados;
	}
	
}
