package rcp.assets.forms;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;


public class ResponsablesFormEditor extends FormEditor {

	public static final String ID = "rcp.assets.forms.ResponsablesFormEditor"; //$NON-NLS-1$
	

	public ResponsablesFormEditor() {
	}
/*
	@Override
	protected void createPages() {

	}
*/
	
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		System.out.println("Llamando doSAVEX!!");
		findPage("ResponsablesID").doSave(null);
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	protected void addPages() {
		try {
			addPage(new ResponsablesFormPage(this, "ResponsablesID", "Generales"));
			addPage(new DireccionesFormPage(this, "DireccionesID", "Direcciones alternas"));
			addPage(new AtencionesFormPage(this, "AtencionesID", "Atenciones"));
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

}
