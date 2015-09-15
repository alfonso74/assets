package rcp.assets.controllers;

import java.util.List;

import rcp.assets.dao.TipoCargoDAO;
import rcp.assets.model.TipoCargo;


public class TipoCargosController extends AbstractController<TipoCargo> {

	/**
	 * Constructor utilizado en las vistas o en otros casos particulares.  Utiliza la sesión
	 * general de Hibernate
	 */
	public TipoCargosController() {
		super(new TipoCargoDAO());
	}

	/**
	 * Constructor utilizado en los editores, para guardar una sesión por editor
	 * @param editorId
	 */
	public TipoCargosController(String editorId) {
		super(editorId, new TipoCargoDAO());
	}
	
	
	public List<TipoCargo> getListadoOrdenado(String nombreCampo, boolean ascending) {
		return getDAO().findAllOrdered(nombreCampo, ascending);
	}
}
