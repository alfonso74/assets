package rcp.assets.controllers;

import rcp.assets.dao.DireccionDAO;
import rcp.assets.model.Direccion;

public class DireccionesController extends AbstractController<Direccion> {
	
	/**
	 * Constructor utilizado en los editores, para guardar una sesión por editor
	 * @param editorId
	 */
	public DireccionesController(String editorId) {
		super(editorId, new DireccionDAO());
	}
	
	
	/**
	 * Constructor utilizado en las vistas o en otros casos particulares.  Utiliza la sesión
	 * general de Hibernate
	 */
	public DireccionesController() {
		super(new DireccionDAO());
	}

	
	public Integer getSiguienteNoDireccion(Long idResponsable) {
		DireccionDAO dao = (DireccionDAO) getDAO();
		return dao.getSiguienteNoDireccion(idResponsable);
	}
}
