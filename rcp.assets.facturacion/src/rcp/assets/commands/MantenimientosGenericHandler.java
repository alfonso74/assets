package rcp.assets.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import rcp.assets.editors.AtencionesEditor;
import rcp.assets.editors.NotificacionesEditor;
import rcp.assets.editors.DireccionesEditor;
import rcp.assets.editors.GenericEditorInput;
import rcp.assets.editors.KeywordsEditor;
import rcp.assets.editors.RecargosEditor;
import rcp.assets.editors.ResponsablesEditor;
import rcp.assets.editors.ResponseEditorInput;
import rcp.assets.editors.TipoCargosEditor;
import rcp.assets.editors.UsuariosEditor;
import rcp.assets.model.Responsable;


public class MantenimientosGenericHandler extends AbstractHandler implements
IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
		GenericEditorInput input = new GenericEditorInput();
		String parametro = event.getParameter("rcp.assets.commands.parametroMantenimiento");
		System.out.println("Parámetro: " + parametro);

		String editorId = "";
		Shell shell = HandlerUtil.getActiveSite(event).getShell();

		if (parametro.equals("usuario")) {
			editorId = UsuariosEditor.ID;
		} else if (parametro.equals("keyword")) {
			editorId = KeywordsEditor.ID;
		} else if (parametro.equals("responsable")) {
			editorId = ResponsablesEditor.ID;
		} else if (parametro.equals("direccion")) {
			if (verificarSeleccion(event)) {
				editorId = DireccionesEditor.ID;
				ResponseEditorInput response = new ResponseEditorInput();
				response.setParent(((IStructuredSelection) HandlerUtil.getCurrentSelection(event)).getFirstElement());
				input = response;
			} else {
				MessageDialog.openInformation(shell, "Crear dirección", "Debe seleccionar un responsable para crear la dirección.");
			}
		} else if (parametro.equals("atencion")) {
			if (verificarSeleccion(event)) {
				editorId = AtencionesEditor.ID;
				ResponseEditorInput response = new ResponseEditorInput();
				response.setParent(((IStructuredSelection) HandlerUtil.getCurrentSelection(event)).getFirstElement());
				input = response;
			} else {
				MessageDialog.openInformation(shell, "Crear atención", "Debe seleccionar un responsable para crear la atención.");
			}
		} else if (parametro.equals("tipo de cargo")) {
			editorId = TipoCargosEditor.ID;
		} else if (parametro.equals("notificacion")) {
			editorId = NotificacionesEditor.ID;
		} else if (parametro.equals("recargo")) {
			editorId = RecargosEditor.ID;
		} else {
			System.out.println("Parámetro de mantenimiento desconocido: " + parametro);
		}

		if (!editorId.equals("")) {
			try {
				page.openEditor(input, editorId);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}

		return null;
	}



	private boolean verificarSeleccion(ExecutionEvent event) {
		Object seleccion = null;
		if (HandlerUtil.getCurrentSelection(event) != null) {
			seleccion = ((IStructuredSelection) HandlerUtil.getCurrentSelection(event)).getFirstElement();
		}
		System.out.println("SELECCIONADO: " + seleccion);
		return seleccion instanceof Responsable ? true : false; 
	}

}
