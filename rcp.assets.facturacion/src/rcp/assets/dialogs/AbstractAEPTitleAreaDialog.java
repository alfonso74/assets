package rcp.assets.dialogs;


import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import rcp.assets.services.FormUtils;
import rcp.assets.services.ICalendarUtils;
import rcp.assets.services.IFormUtils;

public abstract class AbstractAEPTitleAreaDialog extends TitleAreaDialog implements
		IFormUtils, ICalendarUtils {
	private FormUtils formUtils;
	
	public AbstractAEPTitleAreaDialog(Shell parentShell) {
		super(parentShell);
		formUtils = new FormUtils();
	}
	
	//protected abstract Control createContents(Composite parent);

	//protected Control createDialogArea(Composite parent) {};
	
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	
	/**
	 * Se supone que siempre llenamos alg�n campo o no? sirve para estandarizar el
	 * nombre del procedimiento, y si no se usa, pues a implementarlo en blanco!!.
	 * <br>
	 * Aqu� tambi�n se puede cambiar el t�tulo y mensaje del dialog con setTitle()
	 * y setMessage().
	 */
	protected abstract void llenarCampos();
	
	protected void buttonPressed(int buttonId) {
		setReturnCode(buttonId);
		close();
	}
	
	protected void mensajeError(Shell shell, Exception e) {
		MessageDialog.openError(shell, "Error en la aplicaci�n", "Error: " + e.toString() + "\n\nStack trace: " + e.getStackTrace()[0]);
	}
	
	protected void mensajeError(Shell shell, String titulo, Exception e) {
		MessageDialog.openError(shell, "Error en la aplicaci�n: " + titulo, "Error: " + e.toString() + "\n\nStack trace: " + e.getStackTrace()[0]);
	}
	
	public Double txt2Currency(String valorCampo) {
		return formUtils.txt2Currency(valorCampo);
	}
	
	public Integer txt2Integer(String valorCampo) {
		return formUtils.txt2Integer(valorCampo);
	}
	
	public Float txt2Float(String valorCampo) {
		return formUtils.txt2Float(valorCampo);
	}
	
	public Long txt2Long(String valorCampo) {
		return formUtils.txt2Long(valorCampo);
	}

	public String valor2Txt(Object valorCampo) {
		return formUtils.valor2Txt(valorCampo);
	}

	public String valor2Txt(Object valorCampo, String formato) {
		return formUtils.valor2Txt(valorCampo, formato);
	}
	
	public boolean valor2Bool(Boolean valorCampo) {
		return formUtils.valor2Bool(valorCampo);
	}

	public SelectionAdapter crearCalendario(Shell shell, Text txtCampo) {
		return formUtils.crearCalendario(shell, txtCampo);
	}

	public SelectionAdapter crearCalendario(Shell shell, Text txtCampo,
			Text txtFechaDefault) {
		return formUtils.crearCalendario(shell, txtCampo, txtFechaDefault);
	}

	public KeyAdapter crearKeyAdapter(Text txtCampo) {
		return formUtils.crearKeyAdapter(txtCampo);
	}
}
