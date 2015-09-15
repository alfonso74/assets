package rcp.assets.forms;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

import rcp.assets.model.Direccion;

public class DireccionesDetailsPage implements IDetailsPage {

	private IManagedForm managedForm;
	private Direccion registro;
	private Text txtNombreAlterno;
	private Text txtDireccion1;
	private Text txtDireccion2;
	private Text txtDireccion3;
	private Text txtDireccion4;
	private Text txtDireccion5;

	/**
	 * Create the details page.
	 */
	public DireccionesDetailsPage() {
		// Create the details page
	}

	/**
	 * Initialize the details page.
	 * @param form
	 */
	public void initialize(IManagedForm form) {
		managedForm = form;
	}

	/**
	 * Create contents of the details page.
	 * @param parent
	 */
	public void createContents(Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		FillLayout fl_parent = new FillLayout();
		parent.setLayout(fl_parent);
		//		
		Section sctnDireccinAlterna = toolkit.createSection(parent,
				ExpandableComposite.EXPANDED | ExpandableComposite.TITLE_BAR);
		sctnDireccinAlterna.setText("Direcci\u00F3n alterna");
		sctnDireccinAlterna.marginWidth = 10;
		sctnDireccinAlterna.marginHeight = 10;
		//
		Composite composite = toolkit.createComposite(sctnDireccinAlterna, SWT.NONE);
		toolkit.paintBordersFor(composite);
		sctnDireccinAlterna.setClient(composite);
		GridLayout gl_composite = new GridLayout(1, false);
		gl_composite.marginWidth = 0;
		composite.setLayout(gl_composite);

		Label lblNombreAlterno = toolkit.createLabel(composite, "Nombre alterno", SWT.NONE);

		txtNombreAlterno = toolkit.createText(composite, "New Text", SWT.NONE);
		txtNombreAlterno.setText("");
		txtNombreAlterno.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblDireccin = toolkit.createLabel(composite, "Direcci\u00F3n", SWT.NONE);

		txtDireccion1 = toolkit.createText(composite, "New Text", SWT.NONE);
		txtDireccion1.setText("");
		txtDireccion1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		txtDireccion2 = toolkit.createText(composite, "New Text", SWT.NONE);
		txtDireccion2.setText("");
		txtDireccion2.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));

		txtDireccion3 = toolkit.createText(composite, "New Text", SWT.NONE);
		txtDireccion3.setText("");
		txtDireccion3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		txtDireccion4 = toolkit.createText(composite, "New Text", SWT.NONE);
		txtDireccion4.setText("");
		txtDireccion4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		txtDireccion5 = toolkit.createText(composite, "New Text", SWT.NONE);
		txtDireccion5.setText("");
		txtDireccion5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	public void dispose() {
		// Dispose
	}

	public void setFocus() {
		//TODO quitar el println() luego
		System.out.println("SetFocus() de detalles de dirección del responsable");
	}

	private void update() {
		//TODO quitar el println() luego
		System.out.println("Actualizando campos de dirección...");
		if (registro != null) {
			txtNombreAlterno.setText(registro.getNombreAlterno());
			txtDireccion1.setText(checkNull(registro.getDireccion1()));
			txtDireccion2.setText(checkNull(registro.getDireccion2()));
			txtDireccion3.setText(checkNull(registro.getDireccion3()));
			txtDireccion4.setText(checkNull(registro.getDireccion4()));
			txtDireccion5.setText(checkNull(registro.getDireccion5()));
		}
	}

	public boolean setFormInput(Object input) {
		return false;
	}

	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		System.out.println("Tipo selección: " + structuredSelection.getFirstElement());
		if (structuredSelection.size() == 1) {
			if (structuredSelection.getFirstElement() instanceof Direccion) {
				registro = (Direccion) structuredSelection.getFirstElement();
			}
		} else {
			registro = null;
		}
		update();
	}

	public void commit(boolean onSave) {
		// Commit
	}

	public boolean isDirty() {
		return false;
	}

	public boolean isStale() {
		return false;
	}

	public void refresh() {
		update();
	}


	protected Integer checkNull(Integer valorCampo) {
		return valorCampo == null ? 0 : valorCampo;
	}

	protected Float checkNull(Float valorCampo) {
		return valorCampo == null ? 0 : valorCampo;
	}

	protected String checkNull(String valorCampo) {
		return valorCampo == null ? "" : valorCampo;
	}

	protected boolean checkNull(Boolean valorCampo) {
		return valorCampo == null ? false : valorCampo.booleanValue();
	}

}
