package rcp.assets.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import rcp.assets.views.IRefreshView;

public class ActualizarVista extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getViewReferences();
		String viewId = HandlerUtil.getActivePartId(event);
		try {
			IRefreshView view = (IRefreshView) HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().showView(viewId);
			view.refrescar();
		} catch (PartInitException e) {
			// mensaje de error al abrir la vista
			e.printStackTrace();
		}
		
		return null;
	}

}
