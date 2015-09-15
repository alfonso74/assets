package rcp.assets.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.handlers.HandlerUtil;

import rcp.assets.editors.FacturarCargos;


public class AgregarGrupoHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		FacturarCargos editor = (FacturarCargos) HandlerUtil.getActiveEditor(event);
		editor.ejecutarAgregarGrupo();
		return null;
	}

}
