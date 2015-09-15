package rcp.assets.controllers;

import java.util.List;

import rcp.assets.dao.KeywordDAO;
import rcp.assets.model.Keyword;
import rcp.assets.services.ComboData;


public class KeywordsController extends AbstractController<Keyword> {

	public KeywordsController() {
		super(new KeywordDAO());
	}

	public KeywordsController(String editorId) {
		super(editorId, new KeywordDAO());
	}


	public ComboData getComboDataKeyword(String tipo) {
		ComboData data = new ComboData();
		List<Keyword> listado = this.getListado();
		for (Keyword keyword : listado) {
			if (keyword.getTipo().equals(tipo)) {
				data.agregarComboDataItem(keyword.toComboDataItem());
			}
		}
		return data;
	}

}
