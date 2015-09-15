package rcp.assets.controllers;

import java.util.List;

import rcp.assets.dao.GrupoCargoDAO;
import rcp.assets.model.GrupoCargo;


public class GrupoCargosController extends AbstractController<GrupoCargo> {

	public GrupoCargosController() {
		super(new GrupoCargoDAO());
	}

	public GrupoCargosController(String editorId) {
		super(editorId, new GrupoCargoDAO());
	}
	
	public List<GrupoCargo> getGruposByCaso(String noCia, Long noCaso) {
		GrupoCargoDAO dao = (GrupoCargoDAO) getDAO();
		List<GrupoCargo> resultado = dao.getGruposByCaso(noCia, noCaso);
		return resultado;
	}
	
	
	public boolean hasCargosAsociados(GrupoCargo grupo) {
		GrupoCargoDAO dao = (GrupoCargoDAO) getDAO();
		return dao.hasDetalleCargos(grupo);
	}

}
