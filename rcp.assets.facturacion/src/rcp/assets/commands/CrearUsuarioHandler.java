package rcp.assets.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import rcp.assets.editors.GenericEditorInput;
import rcp.assets.editors.UsuariosEditor;

public class CrearUsuarioHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
		GenericEditorInput input = new GenericEditorInput();
		try {
			page.openEditor(input, UsuariosEditor.ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}

}
