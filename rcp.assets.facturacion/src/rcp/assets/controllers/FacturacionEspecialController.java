package rcp.assets.controllers;

import rcp.assets.dao.FacturacionEspecialDAO;
import rcp.assets.model.FacturacionEspecial;

public class FacturacionEspecialController extends AbstractController<FacturacionEspecial> {

	public FacturacionEspecialController() {
		super(new FacturacionEspecialDAO());
	}
	
	public FacturacionEspecialController(String editorId) {
		super(editorId, new FacturacionEspecialDAO());
	}
	
	

}
