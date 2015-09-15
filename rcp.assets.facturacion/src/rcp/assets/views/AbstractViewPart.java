package rcp.assets.views;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import rcp.assets.editors.GenericEditorInput;
import rcp.assets.model.IEditableDocument;

/**
 * Implementa un setFocus() y un hookDoubleClickListener() por default
 *
 */
public abstract class AbstractViewPart extends ViewPart implements IRefreshView {

	
	/**<p>
	 * En este método se asigna un ContentProvider, LabelProvider, y se inicializa el input() del viewer.  También:
	 * <ul>
	 * <li>Registramos el tableViewer como un selection provider (para obtener el elemento seleccionado en las acciones)<br>
	 * - Ejemplo:  <code>getSite().setSelectionProvider(viewer)</code></li>
	 * <li>Habilitamos un listener para doble clic<br>
	 * - Ejemplo:  <code>this.hookDoubleClickListener(viewer, id del editor)</code></li>
	 * </ul>
	 * </p>
	 */
	protected abstract void inicializarViewer();
	
	
	@Override
	public void setFocus() {
	}
	
	
	/**
	 * Agrega a un viewer la capacidad de abrir un registro al dar un doble clic.
	 * @param viewer Viewer al que se agrega el listener
	 * @param editorId ID del editor que será utilizado para editar el registro
	 */
	protected void hookDoubleClickListener(final StructuredViewer viewer, final String editorId) {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				System.out.println("Double click  :)");
				//Object seleccion = ((StructuredSelection) viewer.getSelection()).getFirstElement();
				IEditableDocument seleccion = (IEditableDocument) ((StructuredSelection) viewer.getSelection()).getFirstElement();
				try {
					GenericEditorInput input = new GenericEditorInput(seleccion.getId());
					getSite().getPage().openEditor(input, editorId);
					
					//getSite().getPage().openEditor(new ResponsablesFormEditorInput("PUFF!"), editorId);
					
				} catch(PartInitException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
