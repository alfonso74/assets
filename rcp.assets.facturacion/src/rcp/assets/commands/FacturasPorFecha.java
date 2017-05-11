package rcp.assets.commands;
import java.util.HashMap;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import rcp.assets.controllers.VerFacturasController;
import rcp.assets.dialogs.ExportarPeachTreeDialogo;
import rcp.assets.dialogs.FacturasPorFechaDialogo;


public class FacturasPorFecha extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Shell shell = HandlerUtil.getActiveShell(event);
		
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		FacturasPorFechaDialogo d = new FacturasPorFechaDialogo(shell, parametros);
		if (d.open() == 0) {
			System.out.println("Fecha inicial: " + parametros.get("fechaIni"));
			System.out.println("Fecha final: " + parametros.get("fechaFin"));
			
			VerFacturasController controller = new VerFacturasController();
			controller.agregarParametro("tipoFactura", parametros.get("tipoFactura"));
			controller.agregarParametro("fechaIni", parametros.get("fechaIni"));
			controller.agregarParametro("fechaFin", parametros.get("fechaFin"));
			controller.generarDocumentoExcel(new Browser(shell, SWT.None), "/reports/listadoFacturas.rptdesign");
			
		} else {
			System.out.println("Accion cancelada");
		}
		
		return null;
	}



}
