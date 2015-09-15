package rcp.assets.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;

import rcp.assets.controllers.NotificacionesController;
import rcp.assets.model.Notificacion;
import rcp.assets.model.Keyword;
import rcp.assets.services.ComboData;
import rcp.assets.services.ComboDataManager;
import rcp.assets.services.FechaUtil;
import rcp.assets.services.IBaseKeywords;

import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;


public class NotificacionesEditor extends AbstractEditor {

	public static final String ID = "rcp.assets.editors.NotificacionesEditor";
	//public static final String ID = Class.class.getName();
	private String idSession = ID + FechaUtil.getMilisegundos();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	
	private Notificacion registro = null;
	private NotificacionesController controller;
	
	private Text txtCodigo;
	private Combo comboCia;
	private Combo comboPeriodo;
	private Combo comboTipo;
	private Text txtContenido;
	private Text txtContent;
	
	private ComboData cdPeriodo;
	private ComboData cdTipoNotificacion;
	
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public NotificacionesEditor() {
		controller = new NotificacionesController(idSession);
		cdPeriodo = ComboDataManager.getInstance().getComboData("Periodo anualidad");
		cdTipoNotificacion = ComboDataManager.getInstance().getComboData(IBaseKeywords.TipoKeyword.NOTIFICACION.getDescripcion());
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		
		if (!validarSave()) {
			monitor.setCanceled(true);
			return;
		}
		
		String pNoCia = comboCia.getItem(comboCia.getSelectionIndex());
		String pPeriodo = cdPeriodo.getKeyByIndex(comboPeriodo.getSelectionIndex());
		Keyword pTipoNotificacion = (Keyword) cdTipoNotificacion.getObjectByIndex(comboTipo.getSelectionIndex());
		String pContenido = txtContenido.getText();
		String pContenidoIngles = txtContent.getText();
		
		registro.setNoCia(pNoCia);
		registro.setPeriodo(txt2Integer(pPeriodo));
		registro.setTipo(pTipoNotificacion);
		registro.setDescripcion(pContenido);
		registro.setDescripcionIngles(pContenidoIngles);
		
		controller.doSave(registro);
		
		if (getEditorInput().getId() == null) {
			txtCodigo.setText(registro.getIdNotificacion().toString());
		}
		setPartName(registro.getTituloDocumento());
		actualizarVistas();
		removeDirtyFlag();
	}

	
	private boolean validarSave() {
		String pContenido = txtContenido.getText().trim();
		String pContenidoIngles = txtContent.getText().trim();
		
		if (comboCia.getSelectionIndex() == -1) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe seleccionar la compañía para la cual se define la notificación.");
			return false;
		} else if (comboPeriodo.getSelectionIndex() == -1) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe indicar el período al que pertenece la notificación.");
			return false;
		} else if (comboTipo.getSelectionIndex() == -1) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe indicar el tipo de la notificación.");
			return false;
		} else if (pContenido.equals("")) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe introducir el detalle de la notificación en el campo 'Contenido (español)'.");
			return false;
		} else if (pContenidoIngles.equals("")) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe introducir el detalle de la notificación en el campo 'Contenido (inglés)'.");
			return false;
		}
		return true;
	}
	

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		GenericEditorInput genericInput = (GenericEditorInput) input;
		setSite(site);
		setInput(genericInput);
	}

	@Override
	protected void llenarControles() {
		if (getEditorInput().getId() == null) {
			LOGGER.debug("Creando nueva notificación...");
			registro = new Notificacion();
			this.setPartName("Nueva notificación");
		} else {
			registro = controller.getRegistroById(getEditorInput().getId());
			txtCodigo.setText(valor2Txt(registro.getId()));
			txtContenido.setText(checkNull(registro.getDescripcion()));
			txtContent.setText(checkNull(registro.getDescripcionIngles()));
			comboCia.setText(checkNull(registro.getNoCia()));
			//comboPeriodo.select(registro.getPeriodo());
			comboPeriodo.setText(checkNull(cdPeriodo.getTextoByKey(valor2Txt(registro.getPeriodo()))));
			comboTipo.setText(registro.getTipo().getDescripcion());
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		
		ScrolledForm scrldfrmNewScrolledform = toolkit.createScrolledForm(parent);
		toolkit.paintBordersFor(scrldfrmNewScrolledform);
		scrldfrmNewScrolledform.setText("Detalles de notificación");
		scrldfrmNewScrolledform.getBody().setLayout(new GridLayout(4, false));
		
		Label lblCodigo = toolkit.createLabel(scrldfrmNewScrolledform.getBody(), "C\u00F3digo:", SWT.NONE);
		lblCodigo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtCodigo = toolkit.createText(scrldfrmNewScrolledform.getBody(), "New Text", SWT.NONE);
		txtCodigo.setText("");
		txtCodigo.setEnabled(false);
		GridData gd_txtCodigo = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtCodigo.widthHint = 35;
		txtCodigo.setLayoutData(gd_txtCodigo);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		
		Label lblCompania = toolkit.createLabel(scrldfrmNewScrolledform.getBody(), "Compa\u00F1\u00EDa:", SWT.NONE);
		lblCompania.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		comboCia = new Combo(scrldfrmNewScrolledform.getBody(), SWT.READ_ONLY);
		comboCia.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(comboCia);
		toolkit.paintBordersFor(comboCia);
		
		Label lblPeriodo = toolkit.createLabel(scrldfrmNewScrolledform.getBody(), "Periodo:", SWT.NONE);
		lblPeriodo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		comboPeriodo = new Combo(scrldfrmNewScrolledform.getBody(), SWT.READ_ONLY);
		comboPeriodo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(comboPeriodo);
		toolkit.paintBordersFor(comboPeriodo);
		
		Label lblTipo = toolkit.createLabel(scrldfrmNewScrolledform.getBody(), "Tipo:", SWT.NONE);
		lblTipo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		comboTipo = new Combo(scrldfrmNewScrolledform.getBody(), SWT.READ_ONLY);
		comboTipo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(comboTipo);
		toolkit.paintBordersFor(comboTipo);
		comboTipo.addModifyListener(createModifyListener());
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		
		Label lblContenido = toolkit.createLabel(scrldfrmNewScrolledform.getBody(), "Contenido (espa\u00F1ol):", SWT.NONE);
		GridData gd_lblContenido = new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1);
		gd_lblContenido.verticalIndent = 3;
		lblContenido.setLayoutData(gd_lblContenido);
		
		txtContenido = toolkit.createText(scrldfrmNewScrolledform.getBody(), "New Text", SWT.WRAP);
		txtContenido.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 3, 1));
		txtContenido.setText("");
		txtContenido.addModifyListener(createModifyListener());
		
		Label lblContent = new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		lblContent.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		toolkit.adapt(lblContent, true, true);
		lblContent.setText("Contenido (ingl\u00E9s):");
		
		txtContent = toolkit.createText(scrldfrmNewScrolledform.getBody(), "New Text", SWT.WRAP);
		txtContent.setText("");
		txtContent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		txtContent.addModifyListener(createModifyListener());
		
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		
		comboCia.setItems(IBaseKeywords.COMPANIAS);
		comboPeriodo.setItems(cdPeriodo.getTexto());
		comboTipo.setItems(cdTipoNotificacion.getTexto());
		
		llenarControles();
		addFilledFlag();
		setFocoInicial(txtContenido);
	}
	
	@Override
	public void dispose() {
		controller.finalizarSesion();
		super.dispose();
	}

}
