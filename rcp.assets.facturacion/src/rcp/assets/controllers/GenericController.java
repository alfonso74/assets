package rcp.assets.controllers;

import java.util.List;

import rcp.assets.dao.GenericDAO;


public class GenericController<X> extends AbstractController<X> {

	public GenericController() {
		super(new GenericDAO<X>());
	}


	public GenericController(String editorId) {
		super(editorId, new GenericDAO<X>());
		// TODO Auto-generated constructor stub
	}


	public List<X> getListado() {
		return getDAO().findAll();
	}


	public X getRegistroById(Long id) {
		return getDAO().findById(id, true);
	}
	
}
