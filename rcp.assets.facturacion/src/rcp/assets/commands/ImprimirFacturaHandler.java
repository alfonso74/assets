package rcp.assets.commands;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import rcp.assets.controllers.VerFacturasController;
import rcp.assets.model.Factura;
import rcp.assets.model.TipoFactura;


public class ImprimirFacturaHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		Shell shell = HandlerUtil.getActiveShell(event);
		ISelection seleccion = HandlerUtil.getCurrentSelection(event);
		
		System.out.println("SELECCION: " + seleccion);

		if (seleccion != null && seleccion instanceof IStructuredSelection) {
			// verificamos que esté seleccionado algo, cualquier cosa
			if (seleccion.isEmpty()) {
				MessageDialog.openInformation(shell, "Imprimir factura", 
						"Debe seleccionar una factura para ejecutar esta acción.");
			} else {
				@SuppressWarnings("unchecked")
				List<Factura> listaSeleccion = ((IStructuredSelection) seleccion).toList();
				if (listaSeleccion.size() > 1) {
					// mensaje de solo seleccionar una factura a la vez
					MessageDialog.openInformation(shell, "Imprimir factura", 
							"Solamente puede seleccionar una factura para ejecutar esta acción.");
				} else {
					Factura factura = listaSeleccion.get(0);
					VerFacturasController controller = new VerFacturasController();
					controller.agregarParametro("idFactura", factura.getId().intValue());
					//controller.agregarParametro("impuesto", factura.getImpuesto());
					controller.agregarParametro("idioma", factura.getIdioma());
					controller.generarFacturaExcel(new Browser(shell, SWT.None), TipoFactura.fromCodigo(factura.getTipo()));
				}
			}
		}

		return null;
	}

}
