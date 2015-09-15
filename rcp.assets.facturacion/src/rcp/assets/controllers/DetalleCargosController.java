package rcp.assets.controllers;

import java.util.List;

import rcp.assets.dao.DetalleCargoDAO;
import rcp.assets.model.DetalleCargo;

public class DetalleCargosController extends AbstractController<DetalleCargo> {

	public DetalleCargosController() {
		super(new DetalleCargoDAO());
	}

	public DetalleCargosController(String editorId) {
		super(editorId, new DetalleCargoDAO());
	}
	
	public List<DetalleCargo> buscarDetalleCargosPorCaso(String noCia, String noCaso) {
		DetalleCargoDAO dao = (DetalleCargoDAO) getDAO();
		List<DetalleCargo> resultado = dao.buscarDetallesPorCaso(noCia, noCaso);
		return resultado;

	}

}
