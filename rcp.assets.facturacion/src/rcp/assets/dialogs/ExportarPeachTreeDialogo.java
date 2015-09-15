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


public class ExportarPeachTreeDialogo extends AbstractAEPTitleAreaDialog {
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Shell shell;
	
	private Map<String, String> parametros;
	private Text txtPeriodo;
	private Combo comboFormatoFecha;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public ExportarPeachTreeDialogo(Shell parentShell, Map<String, String> parametros) {
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
		
		Label lblCodigoDeGrupo = formToolkit.createLabel(frmNewForm.getBody(), "Periodo del PeachTree:", SWT.NONE);
		lblCodigoDeGrupo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtPeriodo = formToolkit.createText(frmNewForm.getBody(), "New Text", SWT.NONE);
		txtPeriodo.setText("");
		GridData gd_txtPeriodo = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtPeriodo.widthHint = 50;
		txtPeriodo.setLayoutData(gd_txtPeriodo);
		
		Label lblDescripcion = formToolkit.createLabel(frmNewForm.getBody(), "Formato de fecha:", SWT.NONE);
		lblDescripcion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		comboFormatoFecha = new Combo(frmNewForm.getBody(), SWT.NONE);
		comboFormatoFecha.setItems(new String[] {"MM/dd/yyyy", "dd/MM/yyyy"});
		comboFormatoFecha.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(comboFormatoFecha);
		formToolkit.paintBordersFor(comboFormatoFecha);

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
		return new Point(400, 250);
	}

	@Override
	protected void llenarCampos() {
		setTitle("Exportar al PeachTree");
		setMessage("Introduzca el periodo del PeachTree y el formato de fecha.");
		comboFormatoFecha.select(0);
	}
	
	public boolean validarSave() {
		if (txtPeriodo.getText().equals("")) {
			MessageDialog.openError(shell, "Validación de campos", "Debe suministrar el periodo utilizado para importar al PeachTree");
			return false;
		}
		return true;
	}
	
	public boolean close() {
		if (getReturnCode() == IDialogConstants.OK_ID) {
			try {
				if (validarSave()) {
					parametros.put("periodo", txtPeriodo.getText().trim());
					parametros.put("formato", comboFormatoFecha.getText());
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
