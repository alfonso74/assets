package rcp.assets.controllers;

import java.util.List;

import rcp.assets.dao.RecargoDAO;
import rcp.assets.model.Recargo;


public class RecargosController extends AbstractController<Recargo> {
	
	/**
	 * Constructor utilizado en las vistas o en otros casos particulares.  Utiliza la sesión
	 * general de Hibernate
	 */
	public RecargosController() {
		super(new RecargoDAO());
	}

	
	/**
	 * Constructor utilizado en los editores, para guardar una sesión por editor
	 * @param editorId
	 */
	public RecargosController(String editorId) {
		super(editorId, new RecargoDAO());
	}
	
	
	public List<Recargo> getRecargos(String noCia, Integer noPeriodo) {
		RecargoDAO dao = (RecargoDAO) getDAO();
		return dao.getRecargos(noCia, noPeriodo);
	}
	
}
