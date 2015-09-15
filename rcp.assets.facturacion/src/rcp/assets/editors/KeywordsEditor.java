package rcp.assets.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import rcp.assets.controllers.KeywordsController;
import rcp.assets.model.Keyword;
import rcp.assets.services.ComboDataManager;
import rcp.assets.services.FechaUtil;
import rcp.assets.services.IBaseKeywords;

import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.wb.swt.SWTResourceManager;


public class KeywordsEditor extends AbstractEditor {

	public static final String ID = "rcp.assets.editors.KeywordsEditor";
	//public static final String ID = Class.class.getName();
	private String idSession = ID + FechaUtil.getMilisegundos();

	private Keyword registro = null;
	private KeywordsController controller;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text txtId;
	private Text txtCodigo;
	private Text txtDescripcion;
	private Combo comboTipo;
	private Text txtOrden;
	private Combo comboEstado;


	public KeywordsEditor() {
		controller = new KeywordsController(idSession);
	}


	@Override
	public void doSave(IProgressMonitor monitor) {
		
		if (!validarSave()) {
			monitor.setCanceled(true);
			return;
		}
		
		String pCodigo = txtCodigo.getText();
		String pDescripcion = txtDescripcion.getText();
		System.out.println("SEL: " + comboTipo.getSelectionIndex());
		String pTipo = "";
		if (comboTipo.getSelectionIndex() != -1) {
			pTipo = comboTipo.getItem(comboTipo.getSelectionIndex());
		} else {
			pTipo = comboTipo.getText();
		}
		Integer pOrden = txt2Integer(txtOrden.getText());
		Keyword.Estado pEstado = Keyword.Estado.fromDescripcion(comboEstado.getText());

		registro.setCodigo(pCodigo);
		registro.setDescripcion(pDescripcion);
		registro.setTipo(pTipo);
		registro.setPosicion(pOrden);
		registro.setEstado(pEstado.getCodigo());

		controller.doSave(registro);

		if (getEditorInput().getId() == null) {
			txtId.setText(registro.getIdKeyword().toString());
		}
		setPartName(registro.getTituloDocumento());
		//getEditorInput().setName(registro.getTituloDocumento());
		//actualizarVista(KeywordsView.ID);
		actualizarVistas();
		removeDirtyFlag();
	}
	
	
	private boolean validarSave() {
		String pCodigo = txtCodigo.getText().trim();
		String pDescripcion = txtDescripcion.getText().trim();
		String pOrden = txtOrden.getText();
		
		if (pCodigo.equals("")) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe introducir un código para el Keyword.");
			return false;
		} else if (pDescripcion.equals("")) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe introducir una descripción para el Keyword.");
			return false;
		} else if (comboTipo.getSelectionIndex() == -1) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe indicar el tipo del Keyword.");
			return false;
		} else if (txt2Integer(pOrden) == null) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe introducir la posición (orden) que corresponde al Keyword.");
			return false;
		} else if (comboEstado.getSelectionIndex() == -1) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe seleccionar un estado para el Keyword.");
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
			LOGGER.debug("Creando nuevo keyword...");
			registro = new Keyword();
			comboEstado.setText(IBaseKeywords.ESTADO[0]);
			this.setPartName("Nuevo keyword");
			//getEditorInput().setName("Nuevo keyword");
		} else {
			registro = controller.getRegistroById(getEditorInput().getId());
			LOGGER.debug("Editando keyword: " + registro.getTituloDocumento());
			txtId.setText(valor2Txt(registro.getIdKeyword()));
			txtCodigo.setText(registro.getCodigo());
			txtDescripcion.setText(registro.getDescripcion());
			comboTipo.select(comboTipo.indexOf(checkNull(registro.getTipo())));  // x ser un ComboBox editable
//			comboTipo.setText(checkNull(registro.getTipo()));  // si fuera ComboBox readonly
			txtOrden.setText(valor2Txt(registro.getPosicion()));
			Keyword.Estado estado = Keyword.Estado.fromCodigo(registro.getEstado());
			if (estado != null) {
				comboEstado.setText(estado.getDescripcion());
			}
			this.setPartName(registro.getTituloDocumento());
		}
	}


	@Override
	public void createPartControl(Composite parent) {

		ScrolledForm scrldfrmNewScrolledform = formToolkit.createScrolledForm(parent);
		formToolkit.paintBordersFor(scrldfrmNewScrolledform);
		scrldfrmNewScrolledform.setText("Detalles de keyword");
		scrldfrmNewScrolledform.getBody().setLayout(new GridLayout(4, false));

		Label lblId = formToolkit.createLabel(scrldfrmNewScrolledform.getBody(), "Id:", SWT.NONE);
		lblId.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtId = new Text(scrldfrmNewScrolledform.getBody(), SWT.BORDER);
		txtId.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		txtId.setEnabled(false);
		GridData gd_txtId = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtId.widthHint = 35;
		txtId.setLayoutData(gd_txtId);
		formToolkit.adapt(txtId, true, true);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);

		Label lblCodigo = formToolkit.createLabel(scrldfrmNewScrolledform.getBody(), "C\u00F3digo:", SWT.NONE);
		lblCodigo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtCodigo = new Text(scrldfrmNewScrolledform.getBody(), SWT.BORDER);
		GridData gd_txtCodigo = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtCodigo.widthHint = 75;
		txtCodigo.setLayoutData(gd_txtCodigo);
		formToolkit.adapt(txtCodigo, true, true);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);

		Label lblDescripcion = formToolkit.createLabel(scrldfrmNewScrolledform.getBody(), "Descripci\u00F3n:", SWT.NONE);
		lblDescripcion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtDescripcion = new Text(scrldfrmNewScrolledform.getBody(), SWT.BORDER);
		GridData gd_txtDescripcion = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtDescripcion.widthHint = 175;
		txtDescripcion.setLayoutData(gd_txtDescripcion);
		formToolkit.adapt(txtDescripcion, true, true);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);

		Label lblTipo = formToolkit.createLabel(scrldfrmNewScrolledform.getBody(), "Tipo:", SWT.NONE);
		lblTipo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		comboTipo = new Combo(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		comboTipo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(comboTipo);
		formToolkit.paintBordersFor(comboTipo);

		Label lblPosicion = formToolkit.createLabel(scrldfrmNewScrolledform.getBody(), "Posici\u00F3n:", SWT.NONE);
		lblPosicion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtOrden = formToolkit.createText(scrldfrmNewScrolledform.getBody(), "New Text", SWT.NONE);
		txtOrden.setText("1");
		GridData gd_txtOrden = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtOrden.widthHint = 35;
		txtOrden.setLayoutData(gd_txtOrden);

		Label lblEstado = formToolkit.createLabel(scrldfrmNewScrolledform.getBody(), "Estado:", SWT.NONE);
		lblEstado.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		comboEstado = new Combo(scrldfrmNewScrolledform.getBody(), SWT.READ_ONLY);
		comboEstado.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(comboEstado);
		formToolkit.paintBordersFor(comboEstado);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);

		txtCodigo.addModifyListener(createModifyListener());
		txtDescripcion.addModifyListener(createModifyListener());
		comboTipo.addModifyListener(createModifyListener());
		txtOrden.addModifyListener(createModifyListener());
		comboEstado.addModifyListener(createModifyListener());
		
		comboTipo.setItems(ComboDataManager.getInstance().getListaTipoKeywords());
		comboEstado.setItems(IBaseKeywords.ESTADO);

		llenarControles();
		addFilledFlag();
		setFocoInicial(txtCodigo);
	}


	@Override
	public void dispose() {
		controller.finalizarSesion();
		super.dispose();
	}

}
