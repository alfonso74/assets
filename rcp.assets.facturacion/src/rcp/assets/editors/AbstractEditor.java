package rcp.assets.editors;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.ViewPart;

import rcp.assets.services.FormUtils;
import rcp.assets.views.IRefreshView;


public abstract class AbstractEditor extends EditorPart /* implements ICalendarUtils, IFormUtils */ {
	
	protected static Logger LOGGER = null;
	private FormUtils formUtils;
	private boolean dirty = false;
	protected boolean filled = false;
	protected Control campoFocoInicial;
	

	/**
	 * Constructor default con LOGGER habilitado y funciones auxiliares para el manejo de campos y calendario.
	 * Se ejecuta automáticamente siempre que se instancia cualquier clase que extienda AbstractEditor (aunque 
	 * no se ponga super() en el constructor.)
	 */
	public AbstractEditor() {
		super();
		LOGGER = Logger.getLogger(getClass());
		formUtils = new FormUtils();
	}
	

// ************************************** métodos generados al crear la clase ******************************************

	/**
	 * Modificado para obligar implementación
	 */
	@Override
	public abstract void doSave(IProgressMonitor monitor);

	@Override
	public void doSaveAs() {
	}

	/**
	 * Modificado para obligar la implementación en la clase concreta.  Llamado antes del createPartControl<br>
		- setSite(site);<br>
		- setInput(input);<br>
		- setPartName(input.getName());
	 */
	@Override
	public abstract void init(IEditorSite site, IEditorInput input)
			throws PartInitException;
			
	/**
	 * Modificado para utilizar el flag "dirty"
	 */
	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/*
	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub

	}
	*/

	/**
	 * Modificado para poder pasar el foco inicial a un campo del editor.
	 * Se debe invocar el método setFocoInicial().
	 */
	@Override
	public void setFocus() {
		if (campoFocoInicial != null) {
			campoFocoInicial.setFocus();
		}
	}
	
	
// ******************************************* métodos agregados *********************************************
	
	public void removeDirtyFlag() {
		dirty = false;
		firePropertyChange(PROP_DIRTY);
	}
	
	public void addDirtyFlag() {
		dirty = true;
		firePropertyChange(PROP_DIRTY);
	}
	
	public void removeFilledFlag() {
		filled = false;
	}
	
	public void addFilledFlag() {
		filled = true;
	}
	
	
	/**
	 * 
	 *  El .refresh se incluye para que funcione código del tipo:</br>
	 *     p.getPaquete().getIdTransaccion() en un viewer, sino aparece error de proxy no inicializado</br>
	 *  El .merge no recuerdo  :P</br>
	 *	 controller.getSession().refresh(registro, LockOptions.NONE);</br>
	 *	 controller.getSession().merge(registro);
	 */
	protected abstract void llenarControles();

	protected void actualizarVistas() {
		IViewReference[] viewRef = getSite().getPage().getViewReferences();
		for (int n = 0; n < viewRef.length; n++) {
			ViewPart vista = (ViewPart) viewRef[n].getView(true);
			if (vista instanceof IRefreshView) {
				((IRefreshView) vista).refrescar();
			}
		}
	}
	
	protected void actualizarVista(String viewID) {
		IViewReference[] viewRef = getSite().getPage().getViewReferences();
		// ejecutamos el ciclo en busca de una vista con el viewID indicado
		for (int n = 0; n < viewRef.length; n++) {
			if (viewRef[n].getId().equals(viewID)) {
				// si encontramos el viewID, actualizamos el contenido
				ViewPart vista = (ViewPart) viewRef[n].getView(true);
				if (vista instanceof IRefreshView) {
					((IRefreshView) vista).refrescar();
				}
			}
		}
	}
	
	protected void setFocoInicial(Control campoFocoInicial) {
		this.campoFocoInicial = campoFocoInicial;
	}

	
	public GenericEditorInput getEditorInput() {
		return (GenericEditorInput) super.getEditorInput();
	}
	
	
	protected void mensajeError(Shell shell, Exception e) {
		String nombre = this.toString();
		int puntoFinal = nombre.indexOf(".") + 1;
		int arroba = nombre.indexOf("@");
		MessageDialog.openError(shell, "Error en " + nombre.substring(puntoFinal, arroba), "Error: " + e.toString() + "\n\nStack trace: " + e.getStackTrace()[0] + "\n" + e.getStackTrace()[1]);
		LOGGER.debug("Error en " + nombre.substring(puntoFinal, arroba));
		LOGGER.debug("Error: " + e.toString() + "\n\nStack trace: " + e.getStackTrace()[0] + "\n" + e.getStackTrace()[1]);
		//MessageDialog.openError(shell, "Error en la aplicación", "Error: " + e.toString() + "\n\nStack trace: " + e.getStackTrace()[0]);
	}

	protected ModifyListener createModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (filled) {
					addDirtyFlag();
				}
			}
		};
	}
	
	protected Long txt2Long(String valorCampo) {
		return formUtils.txt2Long(valorCampo);
	}
	
	protected Integer txt2Integer(String valorCampo) {
		return formUtils.txt2Integer(valorCampo);
	}
	
	protected Float txt2Float(String valorCampo) {
		return formUtils.txt2Float(valorCampo);
	}
	
	/**
	 * Transforma un valor numérico a su representacion de tipo String.
	 * @param valorCampo Objeto de tipo numérico
	 * @return El objeto original con el toString() aplicado, o un String vacío ("") en
	 * caso de recibir null.
	 */
	protected String valor2Txt(Object valorCampo) {
		return formUtils.valor2Txt(valorCampo);
	}
	
	/** 
	 * Presenta valor numérico en el formato suministrado.
	 * @param valorCampo Objeto de tipo numérico
	 * @param formato ejm: "0.00", "#,##0.00"
	 * @return Un String numérico en el formato indicado, o un String vacío ("") en caso
	 * de recibir null.
	 */
	protected String valor2Txt(Object valorCampo, String formato) {
		return formUtils.valor2Txt(valorCampo, formato);
	}
	
	protected SelectionAdapter crearCalendario(Text txtCampo) {
		return formUtils.crearCalendario(getSite().getShell(), txtCampo);
	}

	protected SelectionAdapter crearCalendario(Text txtCampo, Text txtFechaDefault) {
		return formUtils.crearCalendario(getSite().getShell(), txtCampo, txtFechaDefault);
	}

	protected KeyAdapter crearKeyAdapter(Text txtCampo) {
		return formUtils.crearKeyAdapter(txtCampo);
	}
	
	protected Integer checkNull(Integer valorCampo) {
		return formUtils.checkNull(valorCampo);
	}
	
	protected Float checkNull(Float valorCampo) {
		return formUtils.checkNull(valorCampo);
	}
	
	protected String checkNull(String valorCampo) {
		return formUtils.checkNull(valorCampo);
	}
	
	protected boolean checkNull(Boolean valorCampo) {
		return formUtils.checkNull(valorCampo);
	}

}
