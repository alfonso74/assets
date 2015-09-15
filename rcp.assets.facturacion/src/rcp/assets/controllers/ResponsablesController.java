package rcp.assets.controllers;

import java.util.List;

import rcp.assets.model.Responsable;
import rcp.assets.dao.ResponsableDAO;


public class ResponsablesController extends AbstractController<Responsable> {

	/**
	 * Constructor utilizado en los editores, para guardar una sesión por editor
	 * @param editorId
	 */
	public ResponsablesController(String editorId) {
		super(editorId, new ResponsableDAO());
	}
	
	
	/**
	 * Constructor utilizado en las vistas o en otros casos particulares.  Utiliza la sesión
	 * general de Hibernate
	 */
	public ResponsablesController() {
		super(new ResponsableDAO());
	}
	
	
	public List<Responsable> getListadoOrdenado(String nombreCampo, boolean ascending) {
		return getDAO().findAllOrdered(nombreCampo, ascending);
	}
	
	public boolean existeNombreResponsable(String nombreResponsable) {
		List<Responsable> listado = getDAO().findByField("nombre", nombreResponsable);
		return !listado.isEmpty();
	}

}
