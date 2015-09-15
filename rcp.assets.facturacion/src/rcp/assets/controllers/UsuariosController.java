package rcp.assets.controllers;

import rcp.assets.dao.UsuarioDAO;
import rcp.assets.model.Usuario;


public class UsuariosController extends AbstractController<Usuario> {

	/**
	 * Constructor utilizado en los editores, para guardar una sesi�n por editor
	 * @param editorId
	 */
	public UsuariosController(String editorId) {
		super(editorId, new UsuarioDAO());
	}
	
	
	/**
	 * Constructor utilizado en las vistas o en otros casos particulares.  Utiliza la sesi�n
	 * general de Hibernate
	 */
	public UsuariosController() {
		super(new UsuarioDAO());
	}
	
}
