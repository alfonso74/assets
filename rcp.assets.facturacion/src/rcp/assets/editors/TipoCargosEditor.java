package rcp.assets.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import rcp.assets.controllers.TipoCargosController;
import rcp.assets.model.TipoCargo;
import rcp.assets.services.ComboData;
import rcp.assets.services.ComboDataManager;
import rcp.assets.services.FechaUtil;
import rcp.assets.services.IBaseKeywords;

import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.wb.swt.ResourceManager;


public class TipoCargosEditor extends AbstractEditor {

	public static final String ID = "rcp.assets.editors.TipoCargosEditor"; //$NON-NLS-1$
	private String idSession = ID + FechaUtil.getMilisegundos();
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	
	private TipoCargo registro = null;
	private TipoCargosController controller;
	private Text txtCodigo;
	private Text txtNombre;
	private Text txtValor;
	private Combo comboGrupo;
	private Combo comboImpuesto;
	private Combo comboEstado;
	
	private ComboData cdGrupo;
	private ComboData cdOpcionYN;
	private ComboData cdEstado;
	
	private Text txtNivel1;
	private Text txtNivel2;
	private Text txtNivel3;
	private Text txtNivel4;
	private Text txtNivel5;
	private Text txtNombreIngles;
	private Label lblNombreIngles;
	private Label lblCodigo;
	private Composite composite_2;
	private Combo comboHonorario;
	private Combo comboIngreso;
	private Label lblIngreso;
	private Label lblHonorario;
	private Label lblPrioridad;
	private Text txtPrioridad;
	private Label lblGrupo;
	
	

	public TipoCargosEditor() {
		controller = new TipoCargosController(idSession);
		cdGrupo = ComboDataManager.getInstance().getComboData(IBaseKeywords.TipoKeyword.GRUPO.getDescripcion());
		cdOpcionYN = ComboDataManager.getInstance().getComboData(IBaseKeywords.TipoKeyword.CONDICIONAL.getDescripcion());
		cdEstado = ComboDataManager.getInstance().getComboData(IBaseKeywords.TipoKeyword.STATUS.getDescripcion());
	}

	/**
	 * Create contents of the editor part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		
		ScrolledForm scrldfrmMain = formToolkit.createScrolledForm(parent);
		scrldfrmMain.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/coins_16.png"));
		formToolkit.paintBordersFor(scrldfrmMain);
		scrldfrmMain.setText(" Detalle del tipo de cargo");
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 10;
		fillLayout.marginHeight = 10;
		fillLayout.marginWidth = 10;
		scrldfrmMain.getBody().setLayout(fillLayout);
		
		Section sctnNewSection = formToolkit.createSection(scrldfrmMain.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		formToolkit.paintBordersFor(sctnNewSection);
		sctnNewSection.setText("Informaci\u00F3n general");
		sctnNewSection.setExpanded(true);
		
		Composite composite = formToolkit.createComposite(sctnNewSection, SWT.NONE);
		formToolkit.paintBordersFor(composite);
		sctnNewSection.setClient(composite);
		composite.setLayout(new GridLayout(2, false));
		
		lblCodigo = formToolkit.createLabel(composite, "C\u00F3digo:", SWT.NONE);
		lblCodigo.setEnabled(false);
		lblCodigo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtCodigo = new Text(composite, SWT.BORDER);
		GridData gd_txtCodigo = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtCodigo.widthHint = 60;
		txtCodigo.setLayoutData(gd_txtCodigo);
		formToolkit.adapt(txtCodigo, true, true);
		
		lblGrupo = new Label(composite, SWT.NONE);
		lblGrupo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblGrupo, true, true);
		lblGrupo.setText("Grupo:");
		
		comboGrupo = new Combo(composite, SWT.READ_ONLY);
		comboGrupo.setItems(cdGrupo.getTexto());
		formToolkit.adapt(comboGrupo);
		formToolkit.paintBordersFor(comboGrupo);
		comboGrupo.addModifyListener(createModifyListener());
		
		Label lblNombre = formToolkit.createLabel(composite, "Nombre (esp):", SWT.NONE);
		lblNombre.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtNombre = formToolkit.createText(composite, "New Text", SWT.NONE);
		txtNombre.setText("");
		GridData gd_txtNombre = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtNombre.widthHint = 180;
		txtNombre.setLayoutData(gd_txtNombre);
		
		lblNombreIngles = formToolkit.createLabel(composite, "Nombre (ing):", SWT.NONE);
		lblNombreIngles.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtNombreIngles = formToolkit.createText(composite, "New Text", SWT.NONE);
		GridData gd_txtNombreIngles = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtNombreIngles.widthHint = 180;
		txtNombreIngles.setLayoutData(gd_txtNombreIngles);
		txtNombreIngles.setText("");
		
		Label lblValor = formToolkit.createLabel(composite, "Valor:", SWT.NONE);
		lblValor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtValor = formToolkit.createText(composite, "New Text", SWT.NONE);
		txtValor.setText("");
		GridData gd_txtValor = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtValor.widthHint = 80;
		txtValor.setLayoutData(gd_txtValor);
		
		composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		formToolkit.adapt(composite_2);
		formToolkit.paintBordersFor(composite_2);
		GridLayout gl_composite_2 = new GridLayout(6, false);
		gl_composite_2.marginHeight = 10;
		composite_2.setLayout(gl_composite_2);
		
		lblHonorario = formToolkit.createLabel(composite_2, "Honorario:", SWT.NONE);
		GridData gd_lblHonorario = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblHonorario.horizontalIndent = 13;
		lblHonorario.setLayoutData(gd_lblHonorario);
		
		comboHonorario = new Combo(composite_2, SWT.READ_ONLY);
		comboHonorario.setItems(cdOpcionYN.getTexto());
		formToolkit.adapt(comboHonorario);
		formToolkit.paintBordersFor(comboHonorario);
		comboHonorario.addModifyListener(createModifyListener());
		
		lblIngreso = formToolkit.createLabel(composite_2, "No Ingreso:", SWT.NONE);
		GridData gd_lblIngreso = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblIngreso.horizontalIndent = 40;
		lblIngreso.setLayoutData(gd_lblIngreso);
		
		comboIngreso = new Combo(composite_2, SWT.READ_ONLY);
		comboIngreso.setItems(cdOpcionYN.getTexto());
		formToolkit.adapt(comboIngreso);
		formToolkit.paintBordersFor(comboIngreso);
		comboIngreso.addModifyListener(createModifyListener());
		
		Label lblPagaItbms = formToolkit.createLabel(composite_2, "Paga ITBMS:", SWT.NONE);
		GridData gd_lblPagaItbms = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblPagaItbms.horizontalIndent = 40;
		lblPagaItbms.setLayoutData(gd_lblPagaItbms);
		
		comboImpuesto = new Combo(composite_2, SWT.READ_ONLY);
		comboImpuesto.setItems(cdOpcionYN.getTexto());
		formToolkit.adapt(comboImpuesto);
		formToolkit.paintBordersFor(comboImpuesto);
		comboImpuesto.addModifyListener(createModifyListener());
		
		lblPrioridad = formToolkit.createLabel(composite_2, "Prioridad:", SWT.NONE);
		lblPrioridad.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtPrioridad = formToolkit.createText(composite_2, "New Text", SWT.NONE);
		txtPrioridad.setText("");
		GridData gd_txtPrioridad = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtPrioridad.widthHint = 30;
		txtPrioridad.setLayoutData(gd_txtPrioridad);
		
		Label lblStatus = formToolkit.createLabel(composite_2, "Status:", SWT.NONE);
		lblStatus.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		comboEstado = new Combo(composite_2, SWT.READ_ONLY);
		comboEstado.setItems(cdEstado.getTexto());
		formToolkit.adapt(comboEstado);
		formToolkit.paintBordersFor(comboEstado);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		comboEstado.addModifyListener(createModifyListener());
		
		Section sctnNewSection_1 = formToolkit.createSection(scrldfrmMain.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		formToolkit.paintBordersFor(sctnNewSection_1);
		sctnNewSection_1.setText("Cuentas contables");
		sctnNewSection_1.setExpanded(true);
		
		Composite composite_1 = new Composite(sctnNewSection_1, SWT.NONE);
		formToolkit.adapt(composite_1);
		formToolkit.paintBordersFor(composite_1);
		sctnNewSection_1.setClient(composite_1);
		composite_1.setLayout(new GridLayout(6, false));
		
		Label lblDistribucin = formToolkit.createLabel(composite_1, "Distribuci\u00F3n:", SWT.NONE);
		GridData gd_lblDistribucin = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblDistribucin.horizontalIndent = 10;
		gd_lblDistribucin.widthHint = 70;
		lblDistribucin.setLayoutData(gd_lblDistribucin);
		
		txtNivel1 = formToolkit.createText(composite_1, "New Text", SWT.NONE);
		txtNivel1.setText("");
		GridData gd_txtNivel1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtNivel1.widthHint = 35;
		txtNivel1.setLayoutData(gd_txtNivel1);
		
		txtNivel2 = formToolkit.createText(composite_1, "New Text", SWT.NONE);
		txtNivel2.setText("");
		GridData gd_txtNivel2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtNivel2.widthHint = 35;
		txtNivel2.setLayoutData(gd_txtNivel2);
		
		txtNivel3 = formToolkit.createText(composite_1, "New Text", SWT.NONE);
		txtNivel3.setText("");
		GridData gd_txtNivel3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtNivel3.widthHint = 35;
		txtNivel3.setLayoutData(gd_txtNivel3);
		
		txtNivel4 = formToolkit.createText(composite_1, "New Text", SWT.NONE);
		txtNivel4.setText("");
		GridData gd_txtNivel4 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtNivel4.widthHint = 35;
		txtNivel4.setLayoutData(gd_txtNivel4);
		
		txtNivel5 = formToolkit.createText(composite_1, "New Text", SWT.NONE);
		txtNivel5.setText("");
		GridData gd_txtNivel5 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtNivel5.widthHint = 35;
		txtNivel5.setLayoutData(gd_txtNivel5);
		
		txtCodigo.addModifyListener(createModifyListener());
		txtNombre.addModifyListener(createModifyListener());
		txtNombreIngles.addModifyListener(createModifyListener());
		txtValor.addModifyListener(createModifyListener());
		txtPrioridad.addModifyListener(createModifyListener());
		txtNivel1.addModifyListener(createModifyListener());
		txtNivel2.addModifyListener(createModifyListener());
		txtNivel3.addModifyListener(createModifyListener());
		txtNivel4.addModifyListener(createModifyListener());
		txtNivel5.addModifyListener(createModifyListener());
		
		llenarControles();
		addFilledFlag();
		setFocoInicial(txtValor);
	}
	
	
	@Override
	protected void llenarControles() {
		if (getEditorInput().getId() == null) {
			LOGGER.debug("Creando nuevo usuario...");
			registro = new TipoCargo();
			lblCodigo.setEnabled(true);
			txtCodigo.setEnabled(true);
			comboGrupo.setText(cdGrupo.getTexto()[0]);
			txtValor.setText("0.00");
			txtPrioridad.setText("99");
			comboImpuesto.setText(IBaseKeywords.CONDICIONAL[0]);
			comboEstado.setText(IBaseKeywords.ESTADO[0]);
			this.setPartName("Nuevo tipo de cargo");
		}else {
			registro = controller.getRegistroById(getEditorInput().getId());
			txtCodigo.setText(registro.getNoTipoCargo());
			comboGrupo.setText(checkNull(cdGrupo.getTextoByKey(registro.getGrupo())));
			txtNombre.setText(registro.getDescripcion());
			txtNombreIngles.setText(checkNull(registro.getDescripcionIngles()));
			txtValor.setText(valor2Txt(registro.getValor(), "#,##0.00"));
			txtPrioridad.setText(valor2Txt(registro.getPrioridad()));
			comboHonorario.setText(checkNull(cdOpcionYN.getTextoByKey(registro.getHonorario())));
			comboIngreso.setText(checkNull(cdOpcionYN.getTextoByKey(registro.getIngreso())));
			comboImpuesto.setText(checkNull(cdOpcionYN.getTextoByKey(registro.getPagaImpuesto())));
			comboEstado.setText(checkNull(cdEstado.getTextoByKey(registro.getEstado())));
			txtNivel1.setText(registro.getNivel1());
			txtNivel2.setText(registro.getNivel2());
			txtNivel3.setText(registro.getNivel3());
			txtNivel4.setText(registro.getNivel4());
			txtNivel5.setText(registro.getNivel5());
			this.setPartName(registro.getTituloDocumento());
		}
	}


	@Override
	public void doSave(IProgressMonitor monitor) {
		
		if (!validarSave()) {
			monitor.setCanceled(true);
			return;
		}
		
		String pCodigo = txtCodigo.getText().trim();
		if (registro.getNoTipoCargo() == null ||
				!registro.getNoTipoCargo().equals(pCodigo)) {
			if (!codigoDisponible(pCodigo)) {
				monitor.setCanceled(true);
				return;
			}
		}
		
		String pGrupo = cdGrupo.getKeyByIndex(comboGrupo.getSelectionIndex());
		String pNombre = txtNombre.getText().trim();
		String pNombreIngles = txtNombreIngles.getText().trim();
		String pValor = txtValor.getText();
		Integer pPrioridad = txt2Integer(txtPrioridad.getText());
		String pHonorario = cdOpcionYN.getKeyByIndex(comboHonorario.getSelectionIndex());
		String pIngreso = cdOpcionYN.getKeyByIndex(comboIngreso.getSelectionIndex());
		String pImpuesto = cdOpcionYN.getKeyByIndex(comboImpuesto.getSelectionIndex()); 
		String pEstado = cdEstado.getKeyByIndex(comboEstado.getSelectionIndex());
		
		String pNivel1 = txtNivel1.getText().trim();
		String pNivel2 = txtNivel2.getText().trim();
		String pNivel3 = txtNivel3.getText().trim();
		String pNivel4 = txtNivel4.getText().trim();
		String pNivel5 = txtNivel5.getText().trim();
		
		registro.setNoTipoCargo(pCodigo);
		registro.setGrupo(pGrupo);
		registro.setDescripcion(pNombre);
		registro.setDescripcionIngles(pNombreIngles);
		registro.setValor(txt2Float(pValor));
		registro.setPrioridad(pPrioridad);
		registro.setHonorario(pHonorario);
		registro.setIngreso(pIngreso);
		registro.setPagaImpuesto(pImpuesto);
		registro.setEstado(pEstado);
		registro.setNivel1(pNivel1);
		registro.setNivel2(pNivel2);
		registro.setNivel3(pNivel3);
		registro.setNivel4(pNivel4);
		registro.setNivel5(pNivel5);
		
		controller.doSave(registro);
		/*
		if (getEditorInput().getId() == null) {
			txtCodigo.setText(registro.getIdTipoCargo().toString());
		}
		*/
		setPartName(registro.getTituloDocumento());
		//getEditorInput().setName(registro.getTituloDocumento());
		//actualizarVista(KeywordsView.ID);
		actualizarVistas();
		removeDirtyFlag();
	}

	
	private boolean validarSave() {
		
		String pCodigo = txtCodigo.getText().trim();
		String pNombre = txtNombre.getText().trim();
		String pNombreIngles = txtNombreIngles.getText().trim();
		String pValor = txtValor.getText();
		Integer pPrioridad = txt2Integer(txtPrioridad.getText());
		String pHonorario = cdOpcionYN.getKeyByIndex(comboHonorario.getSelectionIndex());
		String pIngreso = cdOpcionYN.getKeyByIndex(comboIngreso.getSelectionIndex());
		String pImpuesto = cdOpcionYN.getKeyByIndex(comboImpuesto.getSelectionIndex()); 
		String pEstado = cdEstado.getKeyByIndex(comboEstado.getSelectionIndex());
		
		String pNivel1 = txtNivel1.getText().trim();
		String pNivel2 = txtNivel2.getText().trim();
		String pNivel3 = txtNivel3.getText().trim();
		String pNivel4 = txtNivel4.getText().trim();
		String pNivel5 = txtNivel5.getText().trim();
		
		if (pCodigo.equals("")) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe introducir un código para el tipo de cargo.");
			return false;
		} else if (pCodigo.length() > 10) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"En código para el tipo de cargo no puede tener más de 10 caracteres.");
			return false;
		} else if (pNombre.equals("")) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe introducir un nombre para el tipo de cargo.");
			return false;
		} else if (pNombre.length() > 60) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"El campo de nombre no puede ser mayor a 60 caracteres (tamaño actual: " + pNombre.length() + ").");
			return false;
		} else if (pNombreIngles.length() > 60) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"El campo de nombre (en inglés) no puede ser mayor a 60 caracteres (tamaño actual: " + pNombreIngles.length() + ").");
			return false;
		} else if (txt2Float(pValor) == null) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe introducir un valor válido para el tipo de cargo.");
			return false;
		} else if (comboHonorario.getSelectionIndex() == -1) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe indicar si el tipo de cargo es un honorario.");
			return false;
		} else if (comboIngreso.getSelectionIndex() == -1) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe indicar si el tipo de cargo no es un ingreso.");
			return false;
		}
		
		return true;
	}
	
	private boolean codigoDisponible(String codigoTipoCargo) {
		boolean codigoDisponible = controller.codigoDisponible(codigoTipoCargo);
		if (!codigoDisponible) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"El código '" + codigoTipoCargo + "' ya está asignado a otro tipo de cargo.  Por favor seleccione\n" +
					"otro código.");
			return false;
		}
		return true;
	}
	
	
	@Override
	public void doSaveAs() {
		// Do the Save As operation
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		GenericEditorInput genericInput = (GenericEditorInput) input;
		setSite(site);
		setInput(genericInput);
		//registro = controller.getUsuarioById(genericInput.getId());
		//person = MyModel.getInstance().getPersonById(this.input.getId());
		//setPartName("Person ID: " + registro.getId());
	}


	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void dispose() {
		controller.finalizarSesion();
		super.dispose();
	}
}
