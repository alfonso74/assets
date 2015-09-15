package rcp.assets.forms;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.swt.layout.GridLayout;

import rcp.assets.controllers.ResponsablesController;
import rcp.assets.editors.GenericEditorInput;
import rcp.assets.model.Responsable;
import rcp.assets.services.FechaUtil;

public class DireccionesFormPage extends FormPage {
	
	private String idSession = ResponsablesFormEditor.ID + FechaUtil.getMilisegundos();
	private DireccionesMasterPage block;
	private GenericEditorInput input;
	
	private Responsable registro = null;
	private ResponsablesController controller;

	/**
	 * Create the form page.
	 * @param id
	 * @param title
	 */
	public DireccionesFormPage(String id, String title) {
		super(id, title);
	}

	/**
	 * Create the form page.
	 * @param editor
	 * @param id
	 * @param title
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter id "Some id"
	 * @wbp.eval.method.parameter title "Some title"
	 */
	public DireccionesFormPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}
	
	@Override
	public GenericEditorInput getEditorInput() {
		return (GenericEditorInput) super.getEditorInput();
	}

	/**
	 * Create contents of the form.
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		form.setText("Responsable - Direcciones alternas");
		Composite body = form.getBody();
		toolkit.decorateFormHeading(form.getForm());
		toolkit.paintBordersFor(body);
		GridLayout gridLayout = new GridLayout(1, false);
		managedForm.getForm().getBody().setLayout(gridLayout);
		
		input = getEditorInput();
		System.out.println("AAAA0: " + input);
		controller = new ResponsablesController(idSession);
		System.out.println("AAAA1: " + controller);
		registro = controller.getRegistroById(input.getId());
		System.out.println("AAAA2: " + registro);
		managedForm.getForm().setText(registro.getNombre());
		//block = new DireccionesMasterPage(this, registro);
		block = new DireccionesMasterPage(registro);
		block.createContent(managedForm);
	}
}
