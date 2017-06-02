package rcp.assets.services;

import org.apache.log4j.Logger;

import rcp.assets.controllers.KeywordsController;
import rcp.assets.model.Keyword;

/**
 * Inicializar los keywords basicos que son utilizando en varias partes del sistema, como
 * Activo / Inactivo, Si / No, etc
 * @author Owner
 *
 */
public class InicializarKeywords {
	
	private static final Logger LOGGER = Logger.getLogger(InicializarKeywords.class);
	private KeywordsController controller;
	
	
	public InicializarKeywords() {
		controller = new KeywordsController();
	}
	
	
	public void crearRegistros() {
		LOGGER.info("Verificando creación de keywords...");
		String kStatusGeneral = IBaseKeywords.TipoKeyword.STATUS.getDescripcion();
		String kOpcionGeneral = IBaseKeywords.TipoKeyword.CONDICIONAL.getDescripcion();
		String kIdioma = IBaseKeywords.TipoKeyword.IDIOMA.getDescripcion();
		String kStatusExpediente = IBaseKeywords.TipoKeyword.STATUS_EXP.getDescripcion();
		String kStatusCaso = IBaseKeywords.TipoKeyword.STATUS_CASO.getDescripcion();
		String kRecargoAnualidad = IBaseKeywords.TipoKeyword.RECARGO.getDescripcion();
		String kTipoNotificacion = IBaseKeywords.TipoKeyword.NOTIFICACION.getDescripcion();
		String kGrupoCargo = IBaseKeywords.TipoKeyword.GRUPO.getDescripcion();
		
		crearRegistro(new Keyword("A", "Activo", kStatusGeneral, 1, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("I", "Inactivo", kStatusGeneral, 2, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("S", "Si", kOpcionGeneral, 1, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("N", "No", kOpcionGeneral, 2, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("1", "Español", kIdioma, 1, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("2", "Inglés", kIdioma, 2, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("ACT", "Activo", kStatusExpediente, 1, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("DIS", "Disuelto", kStatusExpediente, 2, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("INA", "Inactivo", kStatusExpediente, 3, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("RES", "En reserva", kStatusExpediente, 4, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("STR", "Strike off", kStatusExpediente, 5, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("AB", "Abierto", kStatusCaso, 1, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("AN", "Anulado", kStatusCaso, 2, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("CE", "Cerrado", kStatusCaso, 3, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("FA", "Facturado", kStatusCaso, 4, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("PF", "Por facturar", kStatusCaso, 5, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("SC01", "Primer recargo", kRecargoAnualidad, 1, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("SC02", "Segundo recargo", kRecargoAnualidad, 2, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("RES01", "Primera restauración", kRecargoAnualidad, 3, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("RES02", "Segunda restauranción", kRecargoAnualidad, 4, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("AN01", "Anualidad", kTipoNotificacion, 1, Keyword.Estado.ACTIVO));
//		crearRegistro(new Keyword("AN01", "Anualidad (largo)", kTipoNotificacion, 1, Keyword.Estado.ACTIVO));
//		crearRegistro(new Keyword("AN02", "Anualidad (corta)", kTipoNotificacion, 2, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("LT", "Legal Track", kGrupoCargo, 1, Keyword.Estado.ACTIVO));
		crearRegistro(new Keyword("SAP", "SAP", kGrupoCargo, 2, Keyword.Estado.ACTIVO));
	}
	
	
	private void crearRegistro(Keyword registro) {
		ComboData cd = controller.getComboDataKeyword(registro.getTipo());
		if (cd.getTextoByKey(registro.getCodigo()) == null) {
			controller.doSave(registro);
			LOGGER.info("Keyword creado: " + registro);
		} else {
			LOGGER.info("Keyword omitido: " + registro);
		}
	}

}
