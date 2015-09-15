package rcp.assets.controllers;

import rcp.assets.dao.CargoDAO;
import rcp.assets.model.Cargo;

public class CargosController extends AbstractController<Cargo> {

	public CargosController() {
		super(new CargoDAO());
	}

	public CargosController(String editorId) {
		super(editorId, new CargoDAO());
	}
	
}
