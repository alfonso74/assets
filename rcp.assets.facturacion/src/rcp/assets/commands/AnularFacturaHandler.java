package rcp.assets.commands;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.ViewPart;

import rcp.assets.controllers.FacturasController;
import rcp.assets.model.Factura;
import rcp.assets.views.IRefreshView;


public class AnularFacturaHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Shell shell = HandlerUtil.getActiveShell(event);
		ISelection seleccion = HandlerUtil.getCurrentSelection(event);

		System.out.println("SELECCION: " + seleccion);

		if (seleccion != null && seleccion instanceof IStructuredSelection) {
			// verificamos que esté seleccionado algo, cualquier cosa
			if (seleccion.isEmpty()) {
				MessageDialog.openInformation(shell, "Anular factura", 
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
					boolean flag = MessageDialog.openConfirm(shell, "Anular factura", "Desea anular la factura " + factura.getNoFactura() + " (" +
							factura.getNombreExpediente() + ")?.");
					if (flag) {
						factura.setEstado("INA");
						FacturasController controller = new FacturasController();
						controller.doSave(factura);
						actualizarVistas(event);
					}
				}
			}
		}

		return null;
	}


	private void actualizarVistas(ExecutionEvent event) {
		IViewReference[] viewRef = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getViewReferences();
		for (int n = 0; n < viewRef.length; n++) {
			ViewPart vista = (ViewPart) viewRef[n].getView(true);
			if (vista instanceof IRefreshView) {
				((IRefreshView) vista).refrescar();
			}
		}
	}

}
