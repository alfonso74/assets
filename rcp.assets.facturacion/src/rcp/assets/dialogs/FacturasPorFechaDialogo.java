package rcp.assets.dialogs;

import java.util.Map;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.nebula.widgets.calendarcombo.CalendarCombo;

import rcp.assets.services.CalComboSettings;
import org.eclipse.swt.widgets.List;
import java.util.Date;


public class FacturasPorFechaDialogo extends AbstractAEPTitleAreaDialog {
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Shell shell;
	
	private Map<String, Object> parametros;
	private CalendarCombo comboFechaIni;
	private CalendarCombo comboFechaFin;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public FacturasPorFechaDialogo(Shell parentShell, Map<String, Object> parametros) {
		super(parentShell);
		this.shell = parentShell;
		this.parametros = parametros;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Form frmNewForm = formToolkit.createForm(area);
		frmNewForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		formToolkit.paintBordersFor(frmNewForm);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 15;
		gridLayout.marginWidth = 10;
		frmNewForm.getBody().setLayout(gridLayout);
		
		Label lblDescripcion = formToolkit.createLabel(frmNewForm.getBody(), "Tipo de facturas:", SWT.NONE);
		lblDescripcion.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		
		List tipoFacturas = new List(frmNewForm.getBody(), SWT.BORDER | SWT.MULTI);
		tipoFacturas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		tipoFacturas.setItems(new String[] {"Anualidad", "Retainer", "Facturaci\u00F3n especial"});
		formToolkit.adapt(tipoFacturas, true, true);
		
		Label lblFechaInicial = new Label(frmNewForm.getBody(), SWT.NONE);
		lblFechaInicial.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblFechaInicial, true, true);
		lblFechaInicial.setText("Fecha inicial:");
		
		comboFechaIni = new CalendarCombo(frmNewForm.getBody(), SWT.NONE, new CalComboSettings(), null);
		comboFechaIni.setDate(new Date(978325200000L));
		formToolkit.adapt(comboFechaIni);
		formToolkit.paintBordersFor(comboFechaIni);
		
		Label lblFechaFinal = new Label(frmNewForm.getBody(), SWT.NONE);
		lblFechaFinal.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblFechaFinal, true, true);
		lblFechaFinal.setText("Fecha final:");
		
		comboFechaFin = new CalendarCombo(frmNewForm.getBody(), SWT.NONE, new CalComboSettings(), null);
		comboFechaFin.setDate(new Date(1343192400000L));
		formToolkit.adapt(comboFechaFin);
		formToolkit.paintBordersFor(comboFechaFin);

		llenarCampos();
		
		return area;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				"Cancelar", false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(400, 304);
	}

	@Override
	protected void llenarCampos() {
		setTitle("Reporte de Facturas (x Fecha)");
		setMessage("Introduzca el rango de fechas y seleccione los tipos de facturas a incluir en el reporte.");
	}
	
	public boolean validarSave() {
//		if (txtPeriodo.getText().equals("")) {
//			MessageDialog.openError(shell, "Validación de campos", "Debe suministrar el periodo utilizado para importar al PeachTree");
//			return false;
//		}
		return true;
	}
	
	public boolean close() {
		if (getReturnCode() == IDialogConstants.OK_ID) {
			try {
				if (validarSave()) {
//					parametros.put("periodo", txtPeriodo.getText().trim());
//					parametros.put("formato", comboFormatoFecha.getText());
					parametros.put("fechaIni", comboFechaIni.getDate().getTime());
					parametros.put("fechaFin", comboFechaFin.getDate().getTime());
					return super.close();
				} else {
					return false;
				}
			} catch (Exception e) {
				mensajeError(getShell(), e);
				return false;
			}
		};
		return super.close();
	}
	
}
