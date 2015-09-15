package rcp.assets.forms;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import rcp.assets.controllers.ResponsablesController;
import rcp.assets.editors.GenericEditorInput;
import rcp.assets.model.Responsable;
import rcp.assets.services.ComboData;
import rcp.assets.services.ComboDataManager;
import rcp.assets.services.FechaUtil;
import rcp.assets.services.FormUtils;
import rcp.assets.services.IBaseKeywords;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.widgets.Combo;


public class ResponsablesFormPage extends AbstractFormPage {

	private String idSession = ResponsablesFormEditor.ID + FechaUtil.getMilisegundos();

	private Responsable registro = null;
	private ResponsablesController controller;
	private Text txtCia;
	private Text txtCodigo;
	private Text txtNombre;
	private Text txtAlias;
	private Text txtNombreComercial;
	private Text txtCreado;
	private Text txtContacto;
	
	private Text txtTelefono1;
	private Text txtTelefono2;
	private Text txtEmail1;
	private Text txtEmail2;
	private Text txtFax1;
	private Text txtFax2;
	
	private Combo comboImpuesto;
	private Combo comboIdioma;
	private Combo comboEstado;
	
	private ComboData cdImpuesto;
	private ComboData cdIdioma;
	private ComboData cdEstado;
	
	private FormUtils formUtils;

	/**
	 * Create the form page.
	 * @param id
	 * @param title
	 */
	public ResponsablesFormPage(String id, String title) {
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
	public ResponsablesFormPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
		controller = new ResponsablesController(idSession);
		cdImpuesto = ComboDataManager.getInstance().getComboData(IBaseKeywords.TipoKeyword.CONDICIONAL.getDescripcion());
		cdIdioma = ComboDataManager.getInstance().getComboData(IBaseKeywords.TipoKeyword.IDIOMA.getDescripcion());
		cdEstado = ComboDataManager.getInstance().getComboData(IBaseKeywords.TipoKeyword.STATUS.getDescripcion());
		formUtils = new FormUtils();
	}

	/**
	 * Create contents of the form.
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		form.setText("Responsable");
		Composite body = form.getBody();
		toolkit.decorateFormHeading(form.getForm());
		toolkit.paintBordersFor(body);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.verticalSpacing = 20;
		gridLayout.marginHeight = 10;
		gridLayout.marginWidth = 10;
		managedForm.getForm().getBody().setLayout(gridLayout);

		Section sctnInformacinGeneral = managedForm.getToolkit().createSection(managedForm.getForm().getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnInformacinGeneral.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		managedForm.getToolkit().paintBordersFor(sctnInformacinGeneral);
		sctnInformacinGeneral.setText("Informaci\u00F3n general");
		sctnInformacinGeneral.setExpanded(true);

		Composite compositeGeneral = managedForm.getToolkit().createComposite(sctnInformacinGeneral, SWT.NONE);
		managedForm.getToolkit().paintBordersFor(compositeGeneral);
		sctnInformacinGeneral.setClient(compositeGeneral);
		GridLayout gl_compositeGeneral = new GridLayout(2, false);
		gl_compositeGeneral.marginHeight = 0;
		gl_compositeGeneral.marginWidth = 0;
		compositeGeneral.setLayout(gl_compositeGeneral);

		Label lblCodigo = managedForm.getToolkit().createLabel(compositeGeneral, "C\u00F3digo:", SWT.NONE);
		lblCodigo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		Composite composite_1 = managedForm.getToolkit().createComposite(compositeGeneral, SWT.NONE);
		managedForm.getToolkit().paintBordersFor(composite_1);
		GridLayout gl_composite_1 = new GridLayout(6, false);
		gl_composite_1.marginHeight = 0;
		gl_composite_1.marginWidth = 0;
		composite_1.setLayout(gl_composite_1);

		txtCodigo = managedForm.getToolkit().createText(composite_1, "New Text", SWT.NONE);
		txtCodigo.setEnabled(false);
		GridData gd_txtCodigo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtCodigo.widthHint = 35;
		txtCodigo.setLayoutData(gd_txtCodigo);
		txtCodigo.setText("");

		Label lblCia = managedForm.getToolkit().createLabel(composite_1, "Compa\u00F1\u00EDa:", SWT.NONE);
		GridData gd_lblCia = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblCia.horizontalIndent = 40;
		lblCia.setLayoutData(gd_lblCia);

		txtCia = managedForm.getToolkit().createText(composite_1, "New Text", SWT.NONE);
		txtCia.setEnabled(false);
		GridData gd_txtCia = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtCia.widthHint = 35;
		txtCia.setLayoutData(gd_txtCia);
		txtCia.setText("");
		
				Label lblCreado = managedForm.getToolkit().createLabel(composite_1, "Creado:", SWT.NONE);
				GridData gd_lblCreado = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_lblCreado.horizontalIndent = 40;
				lblCreado.setLayoutData(gd_lblCreado);
		
				txtCreado = managedForm.getToolkit().createText(composite_1, "New Text", SWT.NONE);
				txtCreado.setEnabled(false);
				txtCreado.setText("");
		new Label(composite_1, SWT.NONE);

		Label lblNombre = managedForm.getToolkit().createLabel(compositeGeneral, "Nombre:", SWT.NONE);
		lblNombre.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtNombre = managedForm.getToolkit().createText(compositeGeneral, "New Text", SWT.NONE);
		txtNombre.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtNombre.setText("");

		Label lblAlias = managedForm.getToolkit().createLabel(compositeGeneral, "Alias:", SWT.NONE);
		lblAlias.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtAlias = managedForm.getToolkit().createText(compositeGeneral, "New Text", SWT.NONE);
		txtAlias.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		txtAlias.setText("");

		Label lblNombreComercial = managedForm.getToolkit().createLabel(compositeGeneral, "Nombre Comercial:", SWT.NONE);

		txtNombreComercial = managedForm.getToolkit().createText(compositeGeneral, "New Text", SWT.NONE);
		txtNombreComercial.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		txtNombreComercial.setText("");

		Label lblImpuesto = managedForm.getToolkit().createLabel(compositeGeneral, "Excento impuesto:", SWT.NONE);
		
		Composite composite = new Composite(compositeGeneral, SWT.NONE);
		managedForm.getToolkit().adapt(composite);
		managedForm.getToolkit().paintBordersFor(composite);
		GridLayout gl_composite = new GridLayout(5, false);
		gl_composite.marginHeight = 0;
		gl_composite.marginWidth = 0;
		composite.setLayout(gl_composite);
		
		comboImpuesto = new Combo(composite, SWT.READ_ONLY);
		comboImpuesto.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		managedForm.getToolkit().adapt(comboImpuesto);
		managedForm.getToolkit().paintBordersFor(comboImpuesto);
		
		Label lblIdioma = managedForm.getToolkit().createLabel(composite, "Idioma:", SWT.NONE);
		GridData gd_lblIdioma = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblIdioma.horizontalIndent = 40;
		lblIdioma.setLayoutData(gd_lblIdioma);
		
		comboIdioma = new Combo(composite, SWT.READ_ONLY);
		comboIdioma.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		managedForm.getToolkit().adapt(comboIdioma);
		managedForm.getToolkit().paintBordersFor(comboIdioma);
		
		Label lblEstado = managedForm.getToolkit().createLabel(composite, "Estado:", SWT.NONE);
		GridData gd_lblEstado = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblEstado.horizontalIndent = 30;
		lblEstado.setLayoutData(gd_lblEstado);
		
		comboEstado = new Combo(composite, SWT.READ_ONLY);
		comboEstado.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		managedForm.getToolkit().adapt(comboEstado);
		managedForm.getToolkit().paintBordersFor(comboEstado);

		Section sctnDetallesDeContactos = managedForm.getToolkit().createSection(managedForm.getForm().getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnDetallesDeContactos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		managedForm.getToolkit().paintBordersFor(sctnDetallesDeContactos);
		sctnDetallesDeContactos.setText("Detalles de contactos");
		sctnDetallesDeContactos.setExpanded(true);

		Composite compositeContactos = managedForm.getToolkit().createComposite(sctnDetallesDeContactos, SWT.NONE);
		managedForm.getToolkit().paintBordersFor(compositeContactos);
		sctnDetallesDeContactos.setClient(compositeContactos);
		compositeContactos.setLayout(new GridLayout(2, false));

		Label lblContacto = managedForm.getToolkit().createLabel(compositeContactos, "Contacto:", SWT.NONE);
		lblContacto.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtContacto = managedForm.getToolkit().createText(compositeContactos, "New Text", SWT.NONE);
		txtContacto.setText("");
		txtContacto.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblTelefono = managedForm.getToolkit().createLabel(compositeContactos, "Tel\u00E9fono(s):", SWT.NONE);
		lblTelefono.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		Composite compositeTelefonos = managedForm.getToolkit().createComposite(compositeContactos, SWT.NONE);
		managedForm.getToolkit().paintBordersFor(compositeTelefonos);

		txtTelefono1 = managedForm.getToolkit().createText(compositeTelefonos, "New Text", SWT.NONE);
		txtTelefono1.setBounds(0, 0, 100, 19);
		txtTelefono1.setText("");

		txtTelefono2 = managedForm.getToolkit().createText(compositeTelefonos, "New Text", SWT.NONE);
		txtTelefono2.setText("");
		txtTelefono2.setBounds(110, 0, 100, 19);
		
		Label lblFax = managedForm.getToolkit().createLabel(compositeContactos, "Fax(es):", SWT.NONE);
		lblFax.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		Composite compositeFaxes = managedForm.getToolkit().createComposite(compositeContactos, SWT.NONE);
		managedForm.getToolkit().paintBordersFor(compositeFaxes);
		compositeFaxes.setLayout(null);

		txtFax1 = managedForm.getToolkit().createText(compositeFaxes, "New Text", SWT.NONE);
		txtFax1.setBounds(0, 0, 100, 19);
		txtFax1.setText("");

		txtFax2 = managedForm.getToolkit().createText(compositeFaxes, "New Text", SWT.NONE);
		txtFax2.setText("");
		txtFax2.setBounds(110, 0, 100, 19);

		Label lblEmail = managedForm.getToolkit().createLabel(compositeContactos, "Correo electr\u00F3nico:", SWT.NONE);
		lblEmail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtEmail1 = managedForm.getToolkit().createText(compositeContactos, "New Text", SWT.NONE);
		txtEmail1.setText("");
		txtEmail1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(compositeContactos, SWT.NONE);

		txtEmail2 = managedForm.getToolkit().createText(compositeContactos, "New Text", SWT.NONE);
		txtEmail2.setText("");
		txtEmail2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		txtNombre.addModifyListener(createModifyListener());
		txtAlias.addModifyListener(createModifyListener());
		txtNombreComercial.addModifyListener(createModifyListener());
		txtContacto.addModifyListener(createModifyListener());
		txtTelefono1.addModifyListener(createModifyListener());
		txtTelefono2.addModifyListener(createModifyListener());
		txtFax1.addModifyListener(createModifyListener());
		txtFax2.addModifyListener(createModifyListener());
		
		llenarControles();
		addFilledFlag();
	}
	

	protected void llenarControles() {
		comboImpuesto.setItems(cdImpuesto.getTexto());
		comboImpuesto.addModifyListener(createModifyListener());
		comboIdioma.setItems(cdIdioma.getTexto());
		comboIdioma.addModifyListener(createModifyListener());
		comboEstado.setItems(cdEstado.getTexto());
		comboEstado.addModifyListener(createModifyListener());
		
		if (getEditorInput().getId() == null) {
			//LOGGER.debug("Creando nuevo responsable...");
			registro = new Responsable();
			txtCia.setText("01");
			comboImpuesto.setText("No");
			comboEstado.select(0);
			this.setPartName("Nuevo responsable");
		} else {
			registro = controller.getRegistroById(getEditorInput().getId());
			txtCodigo.setText(formUtils.valor2Txt(registro.getNoCliente()));
			txtCia.setText(registro.getNoCia());
			txtCreado.setText(FechaUtil.toString(registro.getFechaCreado()));
			txtNombre.setText(registro.getNombre());
			txtAlias.setText(registro.getAlias());
			txtNombreComercial.setText(registro.getNombreComercial());
			
			// contactos
			txtContacto.setText(registro.getContacto());
			txtTelefono1.setText(registro.getTelefono1());
			txtTelefono2.setText(registro.getTelefono2());
			txtFax1.setText(registro.getFax1());
			txtFax2.setText(registro.getFax2());
			txtEmail1.setText(formUtils.checkNull(registro.getEmail1()));
			txtEmail2.setText(formUtils.checkNull(registro.getEmail2()));
			
			// combos
			comboImpuesto.setText(cdImpuesto.getTextoByKey(registro.getExcentoImpuesto()));
			comboIdioma.setText(cdIdioma.getTextoByKey(registro.getIdioma()));
			comboEstado.setText(cdEstado.getTextoByKey(registro.getEstado()));
			
			this.setPartName(registro.getTituloDocumento());
			getManagedForm().getForm().setText(registro.getNombre());
		}
	}


	@Override
	public GenericEditorInput getEditorInput() {
		return (GenericEditorInput) super.getEditorInput();
	}


	@Override
	public void doSave(IProgressMonitor monitor) {
		System.out.println("Llamando doSAVE!!");
		String pCia = txtCia.getText();
		String pNombre = txtNombre.getText();
		String pAlias = txtAlias.getText();
		String pNombreComercial = txtNombreComercial.getText();
		String pContacto = txtContacto.getText();
		String pTelefono1 = txtTelefono1.getText();
		String pTelefono2 = txtTelefono2.getText();
		String pFax1 = txtFax1.getText();
		String pFax2 = txtFax2.getText();
		
		registro.setNoCia(pCia);
		registro.setNombre(pNombre);
		registro.setAlias(pAlias);
		registro.setNombreComercial(pNombreComercial);
		registro.setContacto(pContacto);
		registro.setTelefono1(pTelefono1);
		registro.setTelefono2(pTelefono2);
		registro.setFax1(pFax1);
		registro.setFax2(pFax2);
		
		controller.doSave(registro);
		if (getEditorInput().getId() == null) {
			txtCodigo.setText(registro.getId().toString());
		}
		setPartName(registro.getTituloDocumento());
		//getEditorInput().setName(registro.getTituloDocumento());
		//actualizarVista(KeywordsView.ID);
		actualizarVistas();
		removeDirtyFlag();
	}


	@Override
	public void dispose() {
		controller.finalizarSesion();
		super.dispose();
	}
}
