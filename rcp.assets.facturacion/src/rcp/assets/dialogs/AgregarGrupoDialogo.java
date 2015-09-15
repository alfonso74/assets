package rcp.assets.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
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

import rcp.assets.model.GrupoCargo;


public class AgregarGrupoDialogo extends AbstractAEPTitleAreaDialog {
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private GrupoCargo grupo = null;
	private Text txtGrupo;
	private Text txtDescripcion;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public AgregarGrupoDialogo(Shell parentShell, GrupoCargo grupo) {
		super(parentShell);
		this.grupo = grupo;
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
		
		Label lblCodigoDeGrupo = formToolkit.createLabel(frmNewForm.getBody(), "Prioridad de grupo:", SWT.NONE);
		lblCodigoDeGrupo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtGrupo = formToolkit.createText(frmNewForm.getBody(), "New Text", SWT.NONE);
		txtGrupo.setText("");
		GridData gd_txtGrupo = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtGrupo.widthHint = 50;
		txtGrupo.setLayoutData(gd_txtGrupo);
		
		Label lblDescripcion = formToolkit.createLabel(frmNewForm.getBody(), "Descripci\u00F3n:", SWT.NONE);
		lblDescripcion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtDescripcion = formToolkit.createText(frmNewForm.getBody(), "New Text", SWT.NONE);
		txtDescripcion.setText("");
		txtDescripcion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

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
		setTitle("Crear agrupaci\u00F3n de cargos");
		setMessage("Por favor, introduzca los detalles de la nueva agrupaci\u00F3n.");
		if (grupo.getGrupo() != 0) {
			txtGrupo.setText(grupo.getGrupo() + "");
			txtDescripcion.setText(grupo.getDescripcion());
		}
	}
	
	public boolean validarSave() {
		return true;
	}
	
	public boolean close() {
		if (getReturnCode() == IDialogConstants.OK_ID) {
			try {
				if (validarSave()) {
					guardarGrupo();
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
	
	public void guardarGrupo() {
		int pGrupo = txt2Integer(txtGrupo.getText());
		String pDescripcion = txtDescripcion.getText();
		grupo.setGrupo(pGrupo);
		grupo.setDescripcion(pDescripcion);
	}
}
