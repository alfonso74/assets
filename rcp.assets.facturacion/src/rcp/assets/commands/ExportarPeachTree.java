package rcp.assets.commands;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import rcp.assets.controllers.PeachTreeController;
import rcp.assets.dialogs.ExportarPeachTreeDialogo;
import rcp.assets.model.Factura;


public class ExportarPeachTree extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Shell shell = HandlerUtil.getActiveShell(event);
		ISelection seleccion = HandlerUtil.getCurrentSelection(event);
		
		if (seleccion != null && seleccion instanceof IStructuredSelection) {
			// verificamos que esté seleccionado algo, cualquier cosa
			if (seleccion.isEmpty()) {
				MessageDialog.openInformation(shell, "Exportar a PeachTree", 
						"Debe seleccionar al menos una factura para ejecutar esta acción.");
			} else {
				HashMap<String, String> parametros = new HashMap<String, String>();
				ExportarPeachTreeDialogo d = new ExportarPeachTreeDialogo(shell, parametros);
				if (d.open() == 0) {
					System.out.println("Periodo: " + parametros.get("periodo"));
					System.out.println("Formato: " + parametros.get("formato"));
					
					System.out.println("No facturas: " + ((IStructuredSelection) seleccion).size());
					List<Factura> listaSeleccion = ((IStructuredSelection) seleccion).toList();
					//Factura factura = listaSeleccion.get(0);
					
					PeachTreeController ptc = new PeachTreeController();
					ptc.generarArchivoCSV(shell, listaSeleccion, new Integer(parametros.get("periodo")), parametros.get("formato"), "c:\\LegalFact-FacturasX.csv");
				} else {
					System.out.println("Accion cancelada");
				}
			}
		}
		
		return null;
	}



}
