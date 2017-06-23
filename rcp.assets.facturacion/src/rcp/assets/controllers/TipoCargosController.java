package rcp.assets.controllers;

import java.util.List;

import rcp.assets.dao.TipoCargoDAO;
import rcp.assets.model.TipoCargo;


public class TipoCargosController extends AbstractController<TipoCargo> {

	/**
	 * Constructor utilizado en las vistas o en otros casos particulares.  Utiliza la sesi�n
	 * general de Hibernate
	 */
	public TipoCargosController() {
		super(new TipoCargoDAO());
	}

	/**
	 * Constructor utilizado en los editores, para guardar una sesi�n por editor
	 * @param editorId
	 */
	public TipoCargosController(String editorId) {
		super(editorId, new TipoCargoDAO());
	}
	
	
	public List<TipoCargo> getListadoOrdenado(String nombreCampo, boolean ascending) {
		return getDAO().findAllOrdered(nombreCampo, ascending);
	}
	
	/**
	 * Permite saber si un c�digo de tipo de cargo est� disponible para ser utilizado en un nuevo
	 * registro.
	 * @param codigoTipoCargo valor num�rico que corresponde al c�digo de tipo de cargo que se
	 * desea verificar.
	 * @return <code>true</code> si el c�digo est� disponible, o <code>false</code> en caso contrario.
	 * Tambi�n retorna <code>false</code> si el valor del par�metro codigoTipoCargo es <code>null</code>
	 * o si est� vac�o.
	 * 
	 */
	public boolean codigoDisponible(String codigoTipoCargo) {
		boolean disponible = false;
		if (codigoTipoCargo == null || codigoTipoCargo.isEmpty()) {
			return disponible;
		}
		List<TipoCargo> results = getDAO().findByField("noTipoCargo", Integer.parseInt(codigoTipoCargo));
		if (results == null || results.isEmpty()) {
			disponible = true;
		}
		return disponible;
	}
	
}
