package rcp.assets.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rcp.assets.dao.FacturaDAO;
import rcp.assets.model.Atencion;
import rcp.assets.model.CargoExpediente;
import rcp.assets.model.Caso;
import rcp.assets.model.Compania;
import rcp.assets.model.Direccion;
import rcp.assets.model.Expediente;
import rcp.assets.model.Factura;
import rcp.assets.model.FacturaAnualidad;
import rcp.assets.model.FacturaRetainer;
import rcp.assets.model.Idioma;
import rcp.assets.model.Keyword;
import rcp.assets.model.LineaRecargoTxt;
import rcp.assets.model.Periodo;
import rcp.assets.model.Recargo;
import rcp.assets.model.Responsable;


public class FacturasController extends AbstractController<Factura> {

	public FacturasController() {
		super(new FacturaDAO());
	}

	public FacturasController(String editorId) {
		super(editorId, new FacturaDAO());
	}

	public Integer getSiguienteNoFactura() {
		FacturaDAO dao = (FacturaDAO) getDAO();
		return dao.getSiguienteNoFactura();
	}

	public boolean existeFactura(Caso caso) {
		FacturaDAO dao = (FacturaDAO) getDAO();
		return dao.existeFactura(caso.getIdCaso());
	}
	
	public boolean existeFacturaRetainer(Expediente expediente, Integer mes, Integer yy) {
		FacturaDAO dao = (FacturaDAO) getDAO();
		return dao.existeFacturaRetainer(expediente.getNoExpediente(), mes, yy);
	}

	public Factura getFacturaByCaso(Caso caso) {
		FacturaDAO dao = (FacturaDAO) getDAO();
		return dao.getFacturaByCaso(caso.getId());
	}
	
	public Factura getFacturaRetainer(Expediente expediente, Integer mes, Integer yy) {
		FacturaDAO dao = (FacturaDAO) getDAO();
		return dao.getFacturaRetainer(expediente.getNoExpediente(), mes, yy);
	}

	public List<Factura> getFacturasActivas() {
		List<Factura> resultado = getDAO().findByField("estado", Factura.Estado.ACT.toString());
		return resultado;
	}
	
	public List<Factura> getFacturasPendientesPeachtree() {
		FacturaDAO dao = (FacturaDAO) getDAO();
		List<Factura> resultado = dao.getFacturasPendientesPeachtree();
		return resultado;
	}
	
	public List<Factura> getFacturasEnviadasPeachtree() {
		FacturaDAO dao = (FacturaDAO) getDAO();
		List<Factura> resultado = dao.getFacturasEnviadasPeachtree();
		return resultado;
	}

	public List<Factura> getFacturasInactivas() {
		List<Factura> resultado = getDAO().findByField("estado", Factura.Estado.INA.toString());
		return resultado;
	}


	public FacturaAnualidad generarFacturaAnualidad(Expediente expediente, Date fecha, String numero, Idioma idioma, String concepto,
			Integer periodoYY, Integer noPeriodo, Keyword keywordNotificacion) {
		CargoExpediente anualidad = expediente.getCargoExpediente();
		Responsable responsable = anualidad.getResponsable();
		Direccion direccion = anualidad.getDireccion();
		Atencion atencion = anualidad.getAtencion();

		FacturaAnualidad registro = new FacturaAnualidad();
		registro.setCaso(null);
		registro.setPeriodoMM(0);
		registro.setPeriodoYY(periodoYY);
		registro.setNoFactura(numero);
		registro.setNoExpediente(expediente.getNoExpediente());
		registro.setNoCliente(responsable.getNoCliente());
		registro.setRefSecuencia(Long.parseLong(numero));
		registro.setNombreExpediente(expediente.getNombre());
		registro.setConcepto(concepto);
		registro.setNombreCliente(responsable.getNombre());
		registro.setDireccion1(direccion.getDireccion1());
		registro.setDireccion2(direccion.getDireccion2());
		registro.setDireccion3(direccion.getDireccion3());
		registro.setDireccion4(direccion.getDireccion4());
		registro.setDireccion5(direccion.getDireccion5());
		if (atencion != null)
			registro.setAtencion(atencion.getDescripcion());
		registro.setFechaFactura(fecha);
		registro.setTotalDetalle(0F);
		registro.setImpuesto(0F);
		registro.setTotal(0F);
		registro.setSaldo(0F);
		registro.setFechaCreado(new Date());
		//TODO cambiar manejo de impuestos, extraer un flag de la compania o parametrizarlos
		Float porcImpuesto = 0F;
		boolean excentoImpuesto = false;
		if (expediente.getNoCia().equals(Compania.BVI.getCodigo())) {
			porcImpuesto = 0F;
			excentoImpuesto = true;
		} else {
			porcImpuesto = 7F;
			excentoImpuesto = responsable.getExcentoImpuesto().equals("S") ? true : false;
		}
		registro.setImpuestoPorcentaje(porcImpuesto);
		registro.setExcentoImpuesto(excentoImpuesto);
		registro.setIdioma(idioma.getCodigo());
		registro.setEstado(Factura.Estado.ACT.toString());

		registro.setFechaModificado(null);

		//agregamos la información de los retainers
		//registro.setFactura(registro);
		registro.setAgenteResidente(anualidad.getCargosAnuales().getAgenteResidente());
		registro.setDirectores(anualidad.getCargosAnuales().getDirectores());
		registro.setCustodiaLibros(anualidad.getCargosAnuales().getCustodiaLibros());
		registro.setRetransmision(anualidad.getCargosAnuales().getRetransmision());
		registro.setServFiduciarios(anualidad.getCargosAnuales().getServFiduciarios());
		registro.setServFirma(anualidad.getCargosAnuales().getServFirmas());
		registro.setTasa(anualidad.getCargosAnuales().getTasa());
		registro.setOtros(anualidad.getCargosAnuales().getOtros());
		registro.setOtrosDetalle(anualidad.getCargosAnuales().getOtrosDetalle());
		
		registro.recalcularTotales();
		
		String notificacion = getNotificacion(keywordNotificacion, expediente.getNoCia(), noPeriodo, idioma);
		registro.setNotificacion(notificacion);
		
		agregarLineasRecargoTxt(registro, expediente.getNoCia(), noPeriodo, idioma);
		
		return registro;
	}
	
	
	private String getNotificacion(Keyword tipoNotificacion, String noCia, Integer noPeriodo, Idioma idioma) {
		NotificacionesController notificacionesController = new NotificacionesController();
		return notificacionesController.getNotificacion(tipoNotificacion, noCia, noPeriodo, idioma);
	}
	
	
	private void agregarLineasRecargoTxt(FacturaAnualidad registro, String noCia, Integer noPeriodo, Idioma idioma) {
		List<Recargo> recargosActivos = getRecargosTxt(noCia, noPeriodo);
		LineaRecargoTxt lineaRecargoTxt;
		for (int n = 0; n < recargosActivos.size(); n++) {
			lineaRecargoTxt = recargosActivos.get(n).toLineaRecargoTxt(idioma);
			lineaRecargoTxt.setSecuencia(n);
			lineaRecargoTxt.setMonto(lineaRecargoTxt.getMonto() + registro.getTotal());
			registro.addLineaRecargoTxt(lineaRecargoTxt);
		}
	}
	
	
	private List<Recargo> getRecargosTxt(String noCia, Integer noPeriodo) {
		RecargosController recargosController = new RecargosController();
		List<Recargo> listaRecargos = recargosController.getRecargos(noCia, noPeriodo);
		List<Recargo> listaActivos = new ArrayList<Recargo>();
		if (listaRecargos != null) {
			for (Recargo recargo : listaRecargos) {
				Keyword estado = recargo.getEstado();
				if (estado.getCodigo().equalsIgnoreCase("A")) {
					listaActivos.add(recargo);
				}
			}
		}
		return listaActivos;
	}


	public FacturaRetainer generarFacturaRetainer(Expediente expediente, Periodo periodo, Date fecha, String numero, String idioma) {
		//CasoDAO dao = (CasoDAO) getDAO();

		//		Expediente expediente = caso.getExpediente();
		CargoExpediente anualidad = expediente.getCargoExpediente();
		Responsable responsable = anualidad.getResponsable();
		Direccion direccion = anualidad.getDireccion();
		Atencion atencion = anualidad.getAtencion();

		FacturaRetainer registro = new FacturaRetainer();
		registro.setCaso(null);
		registro.setPeriodoMM(periodo.getMM());
		registro.setPeriodoYY(periodo.getYY());
		registro.setNoFactura(numero);
		registro.setNoExpediente(expediente.getNoExpediente());
		registro.setNoCliente(responsable.getNoCliente());
		registro.setRefSecuencia(Long.parseLong(numero));
		registro.setNombreExpediente(expediente.getNombre());
		registro.setConcepto("Servicios correspondientes al mes de: " + periodo.getPeriodoMMMMYYYY(idioma));
		registro.setNombreCliente(responsable.getNombre());
		registro.setDireccion1(direccion.getDireccion1());
		registro.setDireccion2(direccion.getDireccion2());
		registro.setDireccion3(direccion.getDireccion3());
		registro.setDireccion4(direccion.getDireccion4());
		registro.setDireccion5(direccion.getDireccion5());
		if (atencion != null)
			registro.setAtencion(atencion.getDescripcion());
		registro.setFechaFactura(fecha);
		registro.setTotalDetalle(0F);
		registro.setImpuesto(0F);
		registro.setTotal(0F);
		registro.setSaldo(0F);
		registro.setFechaCreado(new Date());
		//TODO cambiar manejo de impuestos, extraer un flag de la compania o parametrizarlos
		Float porcImpuesto = 0F;
		boolean excentoImpuesto = false;
		if (expediente.getNoCia().equals(Compania.BVI.getCodigo())) {
			porcImpuesto = 0F;
			excentoImpuesto = true;
		} else {
			porcImpuesto = 7F;
			excentoImpuesto = responsable.getExcentoImpuesto().equals("S") ? true : false;
		}
		registro.setImpuestoPorcentaje(porcImpuesto);
		registro.setExcentoImpuesto(excentoImpuesto);
		registro.setIdioma(idioma);
		registro.setEstado(Factura.Estado.ACT.toString());

		registro.setFechaModificado(null);

		//agregamos la información de los retainers
		registro.setFactura(registro);
		registro.setRetainer1(anualidad.getRetainer().getRetainer1());
		registro.setRetainer2(anualidad.getRetainer().getRetainer2());
		registro.setRetainer3(anualidad.getRetainer().getRetainer3());
		registro.setOtroRetainer(anualidad.getRetainer().getRetainerOtros());
		registro.setOtroRetainerCom(anualidad.getRetainer().getRetainerOtrosDesc());

		// en el controller no realizamos cálculos, llamamos a la factura para que actualice totales, impuestos, etc.
		registro.recalcularTotales();

		return registro;
	}

}
