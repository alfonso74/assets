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
import org.eclipse.swt.widgets.Combo;

import rcp.assets.model.Atencion;
import rcp.assets.services.ComboData;
import rcp.assets.services.ComboDataManager;
import rcp.assets.services.IBaseKeywords;

public class AtencionesDetailsPage implements IDetailsPage {

	private IManagedForm managedForm;
	private Atencion registro;
	private Text txtCodigo;
	private Text txtDescripcion;
	private Combo comboEstado;
	
	private ComboData cdEstado;

	/**
	 * Create the details page.
	 */
	public AtencionesDetailsPage() {
		cdEstado = ComboDataManager.getInstance().getComboData(IBaseKeywords.TipoKeyword.STATUS.getDescripcion());
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
		parent.setLayout(new FillLayout());
		//		
		Section sctnAtencion = toolkit.createSection(parent,
				ExpandableComposite.EXPANDED | ExpandableComposite.TITLE_BAR);
		sctnAtencion.setText("Atenci\u00F3n");
		sctnAtencion.marginWidth = 10;
		sctnAtencion.marginHeight = 10;
		//
		Composite composite = toolkit.createComposite(sctnAtencion, SWT.NONE);
		toolkit.paintBordersFor(composite);
		sctnAtencion.setClient(composite);
		GridLayout gl_composite = new GridLayout(1, false);
		gl_composite.marginWidth = 0;
		composite.setLayout(gl_composite);
		
		Label lblCdigo = toolkit.createLabel(composite, "C\u00F3digo", SWT.NONE);
		
		txtCodigo = toolkit.createText(composite, "New Text", SWT.NONE);
		txtCodigo.setText("");
		txtCodigo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblDescripcion = toolkit.createLabel(composite, "Descripci\u00F3n", SWT.NONE);
		
		txtDescripcion = toolkit.createText(composite, "New Text", SWT.NONE);
		txtDescripcion.setText("");
		txtDescripcion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblEstado = toolkit.createLabel(composite, "Estado", SWT.NONE);
		
		comboEstado = new Combo(composite, SWT.READ_ONLY);
		comboEstado.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboEstado.setItems(cdEstado.getTexto());
		
		toolkit.adapt(comboEstado);
		toolkit.paintBordersFor(comboEstado);
	}

	public void dispose() {
		// Dispose
	}

	public void setFocus() {
		// Set focus
	}

	private void update() {
		txtCodigo.setText(registro.getNoAtencion().toString());
		txtDescripcion.setText(registro.getDescripcion());
		comboEstado.setText(registro.getDspEstado());
	}

	public boolean setFormInput(Object input) {
		return false;
	}

	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		System.out.println("Tipo selección: " + structuredSelection.getFirstElement());
		if (structuredSelection.size() == 1) {
			if (structuredSelection.getFirstElement() instanceof Atencion) {
				registro = (Atencion) structuredSelection.getFirstElement();
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

}
