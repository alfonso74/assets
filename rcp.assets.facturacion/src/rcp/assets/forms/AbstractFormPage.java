package rcp.assets.forms;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;

import rcp.assets.editors.GenericEditorInput;
import rcp.assets.services.FormUtils;
import rcp.assets.views.IRefreshView;

public abstract class AbstractFormPage extends FormPage {

	private static Logger LOGGER = null;
	private FormUtils formUtils;
	private boolean dirty = false;
	private boolean filled = false;
	private FormEditor editor = null;


	/**
	 * Create the form page.
	 * @param id
	 * @param title
	 */
	public AbstractFormPage(String id, String title) {
		super(id, title);
		LOGGER = Logger.getLogger(getClass());
		formUtils = new FormUtils();
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
	public AbstractFormPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
		LOGGER = Logger.getLogger(getClass());
		formUtils = new FormUtils();
		this.editor = editor;
	}

	/**
	 * Create contents of the form.
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		form.setText("Empty FormPage");
		Composite body = form.getBody();
		toolkit.decorateFormHeading(form.getForm());
		toolkit.paintBordersFor(body);
	}


	/**
	 * Modificado para obligar implementación
	 */
	@Override
	public abstract void doSave(IProgressMonitor monitor);


	
	// ******************************************* métodos agregados *********************************************

	@Override
	public boolean isDirty() {
		return dirty;
	}
	
	public void removeDirtyFlag() {
		dirty = false;
		firePropertyChange(PROP_DIRTY);
	}

	public void addDirtyFlag() {
		System.out.println("Llamando a addDirtyFlag()");
		dirty = true;
		firePropertyChange(PROP_DIRTY);
		this.getManagedForm().dirtyStateChanged();
	}

	public void removeFilledFlag() {
		filled = false;
	}

	public void addFilledFlag() {
		filled = true;
	}
	
	
	protected abstract void llenarControles();
	
	
	@Override
	public GenericEditorInput getEditorInput() {
		return (GenericEditorInput) super.getEditorInput();
	}
	
	
	protected ModifyListener createModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (filled) {
					System.out.println("DIRTYX1: " + getManagedForm().isDirty() + ", " + getEditor().isDirty());
					addDirtyFlag();
					System.out.println("DIRTYX2: " + getManagedForm().isDirty() + ", " + getEditor().isDirty());
				}
			}
		};
	}
	
	
	protected void actualizarVistas() {
		IViewReference[] viewRef = getSite().getPage().getViewReferences();
		for (int n = 0; n < viewRef.length; n++) {
			ViewPart vista = (ViewPart) viewRef[n].getView(true);
			if (vista instanceof IRefreshView) {
				((IRefreshView) vista).refrescar();
			}
		}
	}
	
}

