package rcp.assets.controllers;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import rcp.assets.dao.CasoDAO;
import rcp.assets.model.Atencion;
import rcp.assets.model.Caso;
import rcp.assets.model.FacturaCaso;
import rcp.assets.model.GrupoCargo;
import rcp.assets.model.LineaFactura;
import rcp.assets.model.Direccion;
import rcp.assets.model.Expediente;
import rcp.assets.model.Responsable;
import rcp.assets.model.TipoCargo;


public class CasosController extends AbstractController<Caso> {

	public CasosController() {
		super(new CasoDAO());
	}

	public CasosController(String editorId) {
		super(editorId, new CasoDAO());
	}

	public List<Caso> buscarCasosOrdenados() {
		List<Caso> listado = getDAO().findAllOrdered("noCaso", true);
		return listado;
	}

	public List<Caso> buscarCasosPorFecha(Date fecha) {
		List<Caso> listado = getDAO().findByField("fechaCreado", fecha);
		return listado;
	}

	public List<Caso> buscarCaso(Long noCaso) {
		List<Caso> listado = getDAO().findByField("noCaso", noCaso);
		return listado;
	}

	public List<Caso> buscarCasosPorFacturar() {
		List<Caso> listado = getDAO().findByField("estado", "PF");
		return listado;
	}

	public Float calcularImpuestos(Caso caso, Float porcImpuesto) {
		CasoDAO dao = (CasoDAO) getDAO();
		return dao.calcularImpuesto(caso, porcImpuesto);
	}


	public FacturaCaso generarFactura(Caso caso, Date fecha, String numero, String idioma, Float porcImpuestoDefault) {
		CasoDAO dao = (CasoDAO) getDAO();

		Expediente expediente = caso.getExpediente();
		Responsable responsable = caso.getResponsable();
		Direccion direccion = caso.getDireccion();
		Atencion atencion = caso.getAtencion();

		FacturaCaso registro = new FacturaCaso();
		registro.setCaso(caso);
		registro.setNoFactura(numero);
		registro.setNoExpediente(expediente.getNoExpediente());
		registro.setNoCliente(responsable.getNoCliente());
		registro.setRefSecuencia(caso.getNoCaso());
		registro.setNombreExpediente(expediente.getNombre());
		registro.setConcepto(caso.getNaturaleza());
		registro.setNombreCliente(responsable.getNombre());
		registro.setDireccion1(direccion.getDireccion1());
		registro.setDireccion2(direccion.getDireccion2());
		registro.setDireccion3(direccion.getDireccion3());
		registro.setDireccion4(direccion.getDireccion4());
		registro.setDireccion5(direccion.getDireccion5());
		if (atencion != null) {
			registro.setAtencion(atencion.getDescripcion());
		}
		registro.setFechaFactura(fecha);
		registro.setTotalDetalle(0F);
		registro.setImpuesto(0F);
		registro.setTotal(0F);
		registro.setSaldo(0F);
		registro.setFechaCreado(new Date());
		//TODO cambiar manejo de impuestos, extraer un flag de la compania o parametrizarlos
		Float porcImpuesto = 0F;
		boolean excentoImpuesto = false;
		if (caso.getNoCia().equals("02")) {
			porcImpuesto = 0F;
			excentoImpuesto = true;
		} else {
			porcImpuesto = porcImpuestoDefault;
			excentoImpuesto = responsable.getExcentoImpuesto().equals("S") ? true : false;
		}
		registro.setImpuestoPorcentaje(porcImpuesto);
		registro.setExcentoImpuesto(excentoImpuesto);
		registro.setIdioma(idioma);
		registro.setEstado("ACT");

		registro.setFechaModificado(null);

		// crear lineas de detalles (para cargos que no tienen grupo asignado)
		TipoCargo tipoCargo;
		GrupoCargo grupo;
		Float monto;
		int n = 1;
		Iterator<Object> resultados = dao.getDetalleCargos(caso);
		while (resultados.hasNext()) {
			Object[] row = (Object[]) resultados.next();
			System.out.println("Result row: " + row[0] + ", " + row[1] + ", " + row[2]);
			tipoCargo = (TipoCargo) row[0];
			grupo = (GrupoCargo) row[1];
			monto = ((Double) row[2]).floatValue();
			LineaFactura linea = new LineaFactura();
			linea.setFactura(registro);

			if (grupo != null) {
				linea.setTipoCargo(null);
				linea.setGrupo(grupo);
				linea.setDescripcion(grupo.getDescripcion());
			} else {
				linea.setTipoCargo(tipoCargo);
				linea.setGrupo(null);
				if (idioma.equals("1")) {
					linea.setDescripcion(tipoCargo.getDescripcion());
				} else {
					linea.setDescripcion(tipoCargo.getDescripcionIngles());
				}
			}
			linea.setMonto(monto);
			linea.setPagaImpuesto(tipoCargo.getPagaImpuesto());
			linea.setSecuencia(n++);
			registro.agregarLineaDetalle(linea);
			//System.out.println("Prioridad: " + tipoCargo.getPrioridad());
			//System.out.println("Total de lineas: " + registro.getListaDetalles().size());
		}

		// en el controller no realizamos cálculos, llamamos a la factura para que actualice totales, impuestos, etc.
		//registro.recalcularTotales();

		// recalcularTotales() falla en el calculo de impuesto de cargos agrupados, asi que utilizamos el caso
		registro.setImpuesto(calcularImpuestos(caso, registro.getImpuestoPorcentaje()));
		registro.calcularTotalDetalle();
		registro.setTotal(registro.getTotalDetalle() + registro.getImpuesto());

		return registro;
	}

}
