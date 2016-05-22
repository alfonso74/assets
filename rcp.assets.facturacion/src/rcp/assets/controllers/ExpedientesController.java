package rcp.assets.controllers;

import java.util.List;

import rcp.assets.dao.ExpedienteDAO;
import rcp.assets.model.Expediente;
import rcp.assets.services.PeriodoAnualidad;

public class ExpedientesController extends AbstractController<Expediente> {

	public ExpedientesController() {
		super(new ExpedienteDAO());
	}

	public ExpedientesController(String editorId) {
		super(editorId, new ExpedienteDAO());
	}

	public List<Expediente> buscarExpedientesConRetainers() {
		ExpedienteDAO dao = (ExpedienteDAO) getDAO();
		return dao.buscarExpedientesConRetainers();
	}

	public List<Expediente> buscarExpedientesConAnualidad() {
		ExpedienteDAO dao = (ExpedienteDAO) getDAO();
		return dao.buscarExpedientesConAnualidad();
	}

	public Expediente buscarExpediente(String noExpediente) {
		List<Expediente> resultado = getDAO().findByField("noExpediente", noExpediente);
		if (resultado != null && !resultado.isEmpty()) {
			return resultado.get(0);
		} else {
			return null;
		}
	}

	public List<Expediente> buscarExpedientes(PeriodoAnualidad periodo, String noCia, boolean conTasa) {
		ExpedienteDAO dao = (ExpedienteDAO) getDAO();
		List<Expediente> listado = dao.buscarExpedientesPorPeriodo(periodo, noCia, conTasa);
		return listado;
	}


	public Float calcularImpuestoRetainers(Expediente expediente, Float porcImpuesto) {
		Float impuesto = 0F;
		if (!expediente.getNoCia().equals("02")) {
			if (expediente.getCargoExpediente().getResponsable().getExcentoImpuesto() != null &&
					expediente.getCargoExpediente().getResponsable().getExcentoImpuesto().equals("N")) {
				impuesto = expediente.getCargoExpediente().getRetainer().getTotalRetainerConImpuesto(porcImpuesto);
			} else {
				System.out.println("Cliente exento o null: " + expediente.getCargoExpediente().getResponsable());
			}
		} else {
			System.out.println("Compañía exenta: " + expediente.getNoCia());
		}
		return impuesto;
	}


	public Float calcularImpuestoCargosAnuales(Expediente expediente, Float porcImpuesto) {
		Float impuesto = 0F;
		if (!expediente.getNoCia().equals("02")) {
			if (expediente.getCargoExpediente().getResponsable().getExcentoImpuesto() != null &&
					expediente.getCargoExpediente().getResponsable().getExcentoImpuesto().equals("N")) {
				impuesto = expediente.getCargoExpediente().getCargosAnuales().getTotalImpuestos(porcImpuesto);
			} else {
				System.out.println("Cliente excento o null: " + expediente.getCargoExpediente().getResponsable());
			}
		} else {
			System.out.println("Compañía excenta: " + expediente.getNoCia());
		}
		return impuesto;
	}

}
