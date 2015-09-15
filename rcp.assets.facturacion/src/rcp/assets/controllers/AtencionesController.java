package rcp.assets.controllers;

import rcp.assets.dao.AtencionDAO;
import rcp.assets.model.Atencion;


public class AtencionesController extends AbstractController<Atencion> {

	/**
	 * Constructor utilizado en las vistas o en otros casos particulares.  Utiliza la sesión
	 * general de Hibernate
	 */
	public AtencionesController() {
		super(new AtencionDAO());
	}

	/**
	 * Constructor utilizado en los editores, para guardar una sesión por editor
	 * @param editorId
	 */
	public AtencionesController(String editorId) {
		super(editorId, new AtencionDAO());
	}
	
	
	public Integer getSiguienteNoAtencion(Long idResponsable) {
		AtencionDAO dao = (AtencionDAO) getDAO();
		return dao.getSiguienteNoAtencion(idResponsable);
	}
	
}
