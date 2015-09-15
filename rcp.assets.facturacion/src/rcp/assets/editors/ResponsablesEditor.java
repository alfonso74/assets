package rcp.assets.editors;

import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import rcp.assets.controllers.ResponsablesController;
import rcp.assets.model.Compania;
import rcp.assets.model.Direccion;
import rcp.assets.model.Responsable;
import rcp.assets.services.ComboData;
import rcp.assets.services.ComboDataManager;
import rcp.assets.services.FechaUtil;
import rcp.assets.services.IBaseKeywords;

import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;


public class ResponsablesEditor extends AbstractEditor {

	public static final String ID = "rcp.assets.editors.ResponsablesEditor"; //$NON-NLS-1$
	private String idSession = ID + FechaUtil.getMilisegundos();

	private Responsable registro;
	private ResponsablesController controller;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	private Text txtNombre;
	private Text txtAlias;
	private Text txtNombreComercial;
	private Text txtCodigo;
	private Text txtCia;
	private Text txtCreado;
	private Text txtContacto;
	private Text txtTelefono1;
	private Text txtTelefono2;
	private Text txtFax1;
	private Text txtFax2;
	private Text txtEmail1;
	private Text txtEmail2;

	private Combo comboImpuesto;
	private Combo comboIdioma;
	private Combo comboEstado;

	private ComboData cdImpuesto;
	private ComboData cdIdioma;
	private ComboData cdEstado;
	private Text txtDireccion1;
	private Text txtDireccion2;
	private Text txtDireccion3;
	private Text txtDireccion4;
	private Text txtDireccion5;

	private Section sctnDetalleContactos;
	private Section sctnDireccionPrincipal;


	public ResponsablesEditor() {
		controller = new ResponsablesController(idSession);
		cdImpuesto = ComboDataManager.getInstance().getComboData(IBaseKeywords.TipoKeyword.CONDICIONAL.getDescripcion());
		cdIdioma = ComboDataManager.getInstance().getComboData(IBaseKeywords.TipoKeyword.IDIOMA.getDescripcion());
		cdEstado = ComboDataManager.getInstance().getComboData(IBaseKeywords.TipoKeyword.STATUS.getDescripcion());
	}

	/**
	 * Create contents of the editor part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		ScrolledForm scrldfrmResponsable = formToolkit.createScrolledForm(parent);
		formToolkit.paintBordersFor(scrldfrmResponsable);
		scrldfrmResponsable.setText("Detalle de Responsable");
		scrldfrmResponsable.getBody().setLayout(new GridLayout(1, false));

		Section sctnInformacionGeneral = formToolkit.createSection(scrldfrmResponsable.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnInformacionGeneral.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.paintBordersFor(sctnInformacionGeneral);
		sctnInformacionGeneral.setText("Informacion general");
		sctnInformacionGeneral.setExpanded(true);

		Composite compositeGeneral = formToolkit.createComposite(sctnInformacionGeneral, SWT.NONE);
		formToolkit.paintBordersFor(compositeGeneral);
		sctnInformacionGeneral.setClient(compositeGeneral);
		GridLayout gl_compositeGeneral = new GridLayout(2, false);
		compositeGeneral.setLayout(gl_compositeGeneral);

		Label lblCodigo = formToolkit.createLabel(compositeGeneral, "C\u00F3digo:", SWT.NONE);
		lblCodigo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		Composite composite_2 = formToolkit.createComposite(compositeGeneral, SWT.NONE);
		formToolkit.paintBordersFor(composite_2);
		GridLayout gl_composite_2 = new GridLayout(5, false);
		gl_composite_2.marginHeight = 0;
		gl_composite_2.marginWidth = 0;
		composite_2.setLayout(gl_composite_2);

		txtCodigo = formToolkit.createText(composite_2, "New Text", SWT.NONE);
		txtCodigo.setEnabled(false);
		txtCodigo.setText("");
		GridData gd_txtCodigo = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtCodigo.widthHint = 35;
		txtCodigo.setLayoutData(gd_txtCodigo);

		Label lblCompania = formToolkit.createLabel(composite_2, "Compa\u00F1ia:", SWT.NONE);
		GridData gd_lblCompania = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblCompania.horizontalIndent = 40;
		lblCompania.setLayoutData(gd_lblCompania);

		txtCia = formToolkit.createText(composite_2, "New Text", SWT.NONE);
		txtCia.setEnabled(false);
		txtCia.setText("");
		GridData gd_txtCia = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtCia.widthHint = 35;
		txtCia.setLayoutData(gd_txtCia);

		Label lblCreado = formToolkit.createLabel(composite_2, "Creado:", SWT.NONE);
		GridData gd_lblCreado = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblCreado.horizontalIndent = 40;
		lblCreado.setLayoutData(gd_lblCreado);

		txtCreado = formToolkit.createText(composite_2, "New Text", SWT.NONE);
		txtCreado.setEnabled(false);
		txtCreado.setText("");
		GridData gd_txtCreado = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtCreado.widthHint = 100;
		txtCreado.setLayoutData(gd_txtCreado);

		Label lblNombre = formToolkit.createLabel(compositeGeneral, "Nombre:", SWT.NONE);
		lblNombre.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtNombre = formToolkit.createText(compositeGeneral, "New Text", SWT.NONE);
		txtNombre.setText("");
		txtNombre.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtNombre.addModifyListener(createModifyListener());

		Label lblAlias = formToolkit.createLabel(compositeGeneral, "Alias:", SWT.NONE);
		lblAlias.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtAlias = formToolkit.createText(compositeGeneral, "New Text", SWT.NONE);
		txtAlias.setText("");
		txtAlias.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtAlias.addModifyListener(createModifyListener());

		Label lblNombreComercial = formToolkit.createLabel(compositeGeneral, "Nombre comercial:", SWT.NONE);
		lblNombreComercial.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtNombreComercial = formToolkit.createText(compositeGeneral, "New Text", SWT.NONE);
		txtNombreComercial.setText("");
		txtNombreComercial.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtNombreComercial.addModifyListener(createModifyListener());

		Label lblImpuesto = formToolkit.createLabel(compositeGeneral, "Excento impuesto:", SWT.NONE);
		lblImpuesto.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		Composite composite = formToolkit.createComposite(compositeGeneral, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		formToolkit.paintBordersFor(composite);
		GridLayout gl_composite = new GridLayout(5, false);
		gl_composite.marginHeight = 0;
		gl_composite.marginWidth = 0;
		composite.setLayout(gl_composite);

		comboImpuesto = new Combo(composite, SWT.READ_ONLY);
		comboImpuesto.setBounds(0, 0, 93, 21);
		comboImpuesto.addModifyListener(createModifyListener());
		formToolkit.adapt(comboImpuesto);
		formToolkit.paintBordersFor(comboImpuesto);

		Label lblIdioma = formToolkit.createLabel(composite, "Idioma:", SWT.NONE);
		GridData gd_lblIdioma = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblIdioma.horizontalIndent = 40;
		lblIdioma.setLayoutData(gd_lblIdioma);

		comboIdioma = new Combo(composite, SWT.READ_ONLY);
		comboIdioma.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboIdioma.addModifyListener(createModifyListener());
		formToolkit.adapt(comboIdioma);
		formToolkit.paintBordersFor(comboIdioma);

		Label lblEstado = formToolkit.createLabel(composite, "Estado:", SWT.NONE);
		GridData gd_lblEstado = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblEstado.horizontalIndent = 30;
		lblEstado.setLayoutData(gd_lblEstado);

		comboEstado = new Combo(composite, SWT.READ_ONLY);
		comboEstado.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboEstado.addModifyListener(createModifyListener());
		formToolkit.adapt(comboEstado);
		formToolkit.paintBordersFor(comboEstado);
		new Label(scrldfrmResponsable.getBody(), SWT.NONE);

		sctnDetalleContactos = formToolkit.createSection(scrldfrmResponsable.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnDetalleContactos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.paintBordersFor(sctnDetalleContactos);
		sctnDetalleContactos.setText("Detalle de contactos");

		Composite compositeContactos = formToolkit.createComposite(sctnDetalleContactos, SWT.NONE);
		formToolkit.paintBordersFor(compositeContactos);
		sctnDetalleContactos.setClient(compositeContactos);
		compositeContactos.setLayout(new GridLayout(2, false));

		Label lblContacto = formToolkit.createLabel(compositeContactos, "Contacto:", SWT.NONE);
		lblContacto.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtContacto = formToolkit.createText(compositeContactos, "New Text", SWT.NONE);
		txtContacto.setText("");
		txtContacto.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtContacto.addModifyListener(createModifyListener());

		Label lblTelefono = formToolkit.createLabel(compositeContactos, "Tel\u00E9fono(s):", SWT.NONE);
		lblTelefono.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		Composite compositeTelefonos = formToolkit.createComposite(compositeContactos, SWT.NONE);
		formToolkit.paintBordersFor(compositeTelefonos);

		txtTelefono1 = formToolkit.createText(compositeTelefonos, "New Text", SWT.NONE);
		txtTelefono1.setText("");
		txtTelefono1.setBounds(0, 0, 110, 19);
		txtTelefono1.addModifyListener(createModifyListener());

		txtTelefono2 = formToolkit.createText(compositeTelefonos, "New Text", SWT.NONE);
		txtTelefono2.setText("");
		txtTelefono2.setBounds(125, 0, 110, 19);
		txtTelefono2.addModifyListener(createModifyListener());

		Label lblFax = formToolkit.createLabel(compositeContactos, "Fax(es):", SWT.NONE);
		lblFax.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		Composite compositeFaxes = formToolkit.createComposite(compositeContactos, SWT.NONE);
		formToolkit.paintBordersFor(compositeFaxes);

		txtFax1 = formToolkit.createText(compositeFaxes, "New Text", SWT.NONE);
		txtFax1.setText("");
		txtFax1.setBounds(0, 0, 110, 19);
		txtFax1.addModifyListener(createModifyListener());

		txtFax2 = formToolkit.createText(compositeFaxes, "New Text", SWT.NONE);
		txtFax2.setText("");
		txtFax2.setBounds(125, 0, 110, 19);
		txtFax2.addModifyListener(createModifyListener());

		Label lblEmail = formToolkit.createLabel(compositeContactos, "Correo electr\u00F3nico:", SWT.NONE);
		lblEmail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtEmail1 = formToolkit.createText(compositeContactos, "New Text", SWT.NONE);
		txtEmail1.setText("");
		txtEmail1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtEmail1.addModifyListener(createModifyListener());
		new Label(compositeContactos, SWT.NONE);

		txtEmail2 = formToolkit.createText(compositeContactos, "New Text", SWT.NONE);
		txtEmail2.setText("");
		txtEmail2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtEmail2.addModifyListener(createModifyListener());


		new Label(scrldfrmResponsable.getBody(), SWT.NONE);

		sctnDireccionPrincipal = formToolkit.createSection(scrldfrmResponsable.getBody(), Section.TWISTIE | Section.TITLE_BAR);		
		sctnDireccionPrincipal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.paintBordersFor(sctnDireccionPrincipal);
		sctnDireccionPrincipal.setText("Direcci\u00F3n principal");

		Composite compositeDireccion = formToolkit.createComposite(sctnDireccionPrincipal, SWT.NONE);
		formToolkit.paintBordersFor(compositeDireccion);
		sctnDireccionPrincipal.setClient(compositeDireccion);
		compositeDireccion.setLayout(new GridLayout(2, false));

		Label lblDireccion = formToolkit.createLabel(compositeDireccion, "Direcci\u00F3n:", SWT.NONE);
		lblDireccion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtDireccion1 = formToolkit.createText(compositeDireccion, "New Text", SWT.NONE);
		txtDireccion1.setText("");
		txtDireccion1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(compositeDireccion, SWT.NONE);

		txtDireccion2 = formToolkit.createText(compositeDireccion, "New Text", SWT.NONE);
		txtDireccion2.setText("");
		txtDireccion2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(compositeDireccion, SWT.NONE);

		txtDireccion3 = formToolkit.createText(compositeDireccion, "New Text", SWT.NONE);
		txtDireccion3.setText("");
		txtDireccion3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(compositeDireccion, SWT.NONE);

		txtDireccion4 = formToolkit.createText(compositeDireccion, "New Text", SWT.NONE);
		txtDireccion4.setText("");
		txtDireccion4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(compositeDireccion, SWT.NONE);

		txtDireccion5 = formToolkit.createText(compositeDireccion, "New Text", SWT.NONE);
		txtDireccion5.setText("");
		txtDireccion5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));


		// acciones necesarias luego de crear los controles
		llenarControles();
		addFilledFlag();
		setFocoInicial(txtNombre);
	}


	@Override
	protected void llenarControles() {
		comboImpuesto.setItems(cdImpuesto.getTexto());
		comboIdioma.setItems(cdIdioma.getTexto());
		comboEstado.setItems(cdEstado.getTexto());
		
		if (getEditorInput().getId() == null) {
			LOGGER.debug("Creando nuevo responsable...");
			registro = new Responsable();
			txtCia.setText(Compania.PMA.getCodigo());
			comboImpuesto.setText(IBaseKeywords.CONDICIONAL[1]);
			comboEstado.setText(IBaseKeywords.ESTADO[0]);
			this.setPartName("Nuevo responsable");
		} else {
			registro = controller.getRegistroById(getEditorInput().getId());
			txtCodigo.setText(valor2Txt(registro.getNoCliente()));
			txtCia.setText(registro.getNoCia());
			txtCreado.setText(FechaUtil.toString(registro.getFechaCreado()));
			txtNombre.setText(registro.getNombre());
			txtAlias.setText(registro.getAlias());
			txtNombreComercial.setText(registro.getNombreComercial());
			txtContacto.setText(registro.getContacto());
			txtTelefono1.setText(registro.getTelefono1());
			txtTelefono2.setText(registro.getTelefono2());
			txtFax1.setText(registro.getFax1());
			txtFax2.setText(registro.getFax2());
			txtEmail1.setText(checkNull(registro.getEmail1()));
			txtEmail2.setText(checkNull(registro.getEmail2()));
			comboImpuesto.setText(cdImpuesto.getTextoByKey(registro.getExcentoImpuesto()));
			comboIdioma.setText(cdIdioma.getTextoByKey(registro.getIdioma()));
			comboEstado.setText(cdEstado.getTextoByKey(registro.getEstado()));
			this.setPartName(registro.getTituloDocumento());
		}
		if (registro.getNoCliente() != null) {
			sctnDetalleContactos.setExpanded(true);
			sctnDireccionPrincipal.setEnabled(false);
			sctnDireccionPrincipal.setVisible(false);
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {

		if (!validarSave()) {
			monitor.setCanceled(true);
			return;
		}

		String pCia = txtCia.getText();
		String pNombre = txtNombre.getText().trim();
		String pAlias = txtAlias.getText().trim();
		String pNombreComercial = txtNombreComercial.getText().trim();
		String pImpuesto = cdImpuesto.getKeyByIndex(comboImpuesto.getSelectionIndex());
		String pIdioma = cdIdioma.getKeyByIndex(comboIdioma.getSelectionIndex());
		String pEstado = cdEstado.getKeyByIndex(comboEstado.getSelectionIndex());

		String pContacto = txtContacto.getText().trim();
		String pTelefono1 = txtTelefono1.getText().trim();
		String pTelefono2 = txtTelefono2.getText().trim();
		String pFax1 = txtFax1.getText().trim();
		String pFax2 = txtFax2.getText().trim();
		String pEmail1 = txtEmail1.getText().trim();
		String pEmail2 = txtEmail2.getText().trim();

		registro.setNoCia(pCia);
		registro.setNombre(pNombre);
		registro.setAlias(pAlias);
		registro.setNombreComercial(pNombreComercial);
		registro.setExcentoImpuesto(pImpuesto);
		registro.setIdioma(pIdioma);
		registro.setEstado(pEstado);
		registro.setContacto(pContacto);
		registro.setTelefono1(pTelefono1);
		registro.setTelefono2(pTelefono2);
		registro.setFax1(pFax1);
		registro.setFax2(pFax2);
		registro.setEmail1(pEmail1);
		registro.setEmail2(pEmail2);
		registro.setFechaCreado(new Date());
		registro.setActualizado(true);


		// Guardamos una nueva dirección si el cliente es nuevo (código de cliente no asignado)
		if (registro.getNoCliente() == null) {
			Direccion regDir = new Direccion();
			Integer pCodigo = 0;      // la dirección principal siempre es código "0"
			String pNoCia = txtCia.getText();
			String pNombreAlterno = txtNombre.getText().trim();
			String pDireccion1 = txtDireccion1.getText().trim();
			String pDireccion2 = txtDireccion2.getText().trim();
			String pDireccion3 = txtDireccion3.getText().trim();
			String pDireccion4 = txtDireccion4.getText().trim();
			String pDireccion5 = txtDireccion5.getText().trim();

			regDir.setNoDireccion(pCodigo);
			regDir.setNoCia(pNoCia);
			regDir.setNombreAlterno(pNombreAlterno);
			regDir.setDireccion1(pDireccion1);
			regDir.setDireccion2(pDireccion2);
			regDir.setDireccion3(pDireccion3);
			regDir.setDireccion4(pDireccion4);
			regDir.setDireccion5(pDireccion5);
			regDir.setEstado(pEstado);
			regDir.setFechaCreado(new Date());
			regDir.setActualizado(true);
			regDir.setResponsable(registro);
			registro.getListaDirecciones().add(regDir);
		}

		controller.doSave(registro);

		if (txtCodigo.getText().equals("")) {
			txtCodigo.setText(registro.getId().toString());
			txtCreado.setText(FechaUtil.toString(registro.getFechaCreado()));
			registro.setNoCliente(Integer.valueOf(registro.getIdResponsable().intValue()));
			controller.doSave(registro);
		}
		setPartName(registro.getTituloDocumento());
		//getEditorInput().setName(registro.getTituloDocumento());
		//actualizarVista(KeywordsView.ID);
		actualizarVistas();
		removeDirtyFlag();
	}


	private boolean validarSave() {
		String pDireccion01 = txtDireccion1.getText().trim();
		String pDireccion02 = txtDireccion2.getText().trim();
		String pDireccion03 = txtDireccion3.getText().trim();
		String pDireccion04 = txtDireccion4.getText().trim();
		String pDireccion05 = txtDireccion5.getText().trim();
		
		//Solo validamos las direcciones si el cliente es nuevo (no tiene codigo de cliente asignado)
		if (registro.getNoCliente() == null) {
			if (pDireccion01.equals("") & pDireccion02.equals("") & pDireccion03.equals("") & pDireccion04.equals("") & pDireccion05.equals("")) {
				MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
						"Debe suministrar la dirección del cliente.");
				return false;
			}
			if (pDireccion01.length() > 45) {
				MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
						"El campo de dirección no puede superar los 45 caracteres (" + pDireccion01.length() + ").");
				return false;
			}
			if (pDireccion02.length() > 45) {
				MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
						"El campo de dirección no puede superar los 45 caracteres (" + pDireccion02.length() + ").");
				return false;
			}
			if (pDireccion03.length() > 45) {
				MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
						"El campo de dirección no puede superar los 45 caracteres (" + pDireccion03.length() + ").");
				return false;
			}
			if (pDireccion04.length() > 45) {
				MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
						"El campo de dirección no puede superar los 45 caracteres (" + pDireccion04.length() + ").");
				return false;
			}
			if (pDireccion05.length() > 45) {
				MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
						"El campo de dirección no puede superar los 45 caracteres (" + pDireccion05.length() + ").");
				return false;
			}
			// validamos si el nombre del responsable ya lo tiene algún registo y le avisamos al usuario
			if (controller.existeNombreResponsable(txtNombre.getText())) {
				LOGGER.info("Existe un responsable con el mismo nombre: " + txtNombre.getText());
				boolean continuarSave = false;
				continuarSave = MessageDialog.openConfirm(getSite().getShell(), "Reponsable similar encontrado", "Se encontró un responsable con el mismo " +
						"nombre.  Desea continuar\ncon la creación de este responsable?");
				if (!continuarSave) return false;
			} else {
				LOGGER.info("No se encontraron responsables con el nombre: " + txtNombre.getText());
			}
		}
		if (comboIdioma.getSelectionIndex() == -1) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe seleccionar el idioma utilizado para la facturación del cliente.");
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
