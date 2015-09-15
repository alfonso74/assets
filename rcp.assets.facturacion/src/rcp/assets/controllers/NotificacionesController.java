package rcp.assets.controllers;

import rcp.assets.dao.NotificacionDAO;
import rcp.assets.model.Idioma;
import rcp.assets.model.Keyword;
import rcp.assets.model.Notificacion;


public class NotificacionesController extends AbstractController<Notificacion> {

	/**
	 * Constructor utilizado en las vistas o en otros casos particulares.  Utiliza la sesión
	 * general de Hibernate
	 */
	public NotificacionesController() {
		super(new NotificacionDAO());
	}


	/**
	 * Constructor utilizado en los editores, para guardar una sesión por editor
	 * @param editorId
	 */
	public NotificacionesController(String editorId) {
		super(editorId, new NotificacionDAO());
	}


	public boolean existeNotificacion(String noCia, Integer noPeriodo, Keyword tipoNotificacion) {
		NotificacionDAO dao = (NotificacionDAO) getDAO();
		return dao.existeNotificacion(noCia, noPeriodo, tipoNotificacion);
	}


	public String getNotificacion(Keyword tipoNotificacion, String noCia, Integer noPeriodo, Idioma idioma) {
		NotificacionDAO dao = (NotificacionDAO) getDAO();
		Notificacion notificacion = dao.getNotificacion(noCia, noPeriodo, tipoNotificacion);
		if (notificacion != null) {
			if (idioma.equals(Idioma.ESP)) {
				return notificacion.getDescripcion();
			} else {
				return notificacion.getDescripcionIngles();
			}
		}
		return "";
	}

}
