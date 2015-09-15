package rcp.assets.editors;

import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import rcp.assets.controllers.DireccionesController;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.widgets.Combo;


public class DireccionesEditor extends AbstractEditor {

	public static final String ID = "rcp.assets.editors.DireccionesEditor"; //$NON-NLS-1$
	private String idSession = ID + FechaUtil.getMilisegundos();
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	
	private Responsable responsable = null;
	private Direccion registro = null;
	
	private DireccionesController controller;
	
	private Text txtCodigoResponsable;
	private Text txtCia;
	private Text txtNombreAlterno;
	private Text txtDireccion1;
	private Text txtDireccion2;
	private Text txtDireccion3;
	private Text txtDireccion4;
	private Text txtDireccion5;
	private Combo comboEstado;
	private ComboData cdEstado;
	
	private Section sctnResponsable;
	private Section sctnGenerales;
	private Composite compositeGenerales;
	private Text txtCodigo;
	private Text txtNombreResponsable;

	
	public DireccionesEditor() {
		controller = new DireccionesController(idSession);
		cdEstado = ComboDataManager.getInstance().getComboData(IBaseKeywords.TipoKeyword.STATUS.getDescripcion());
	}

	/**
	 * Create contents of the editor part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		
		ScrolledForm scrldfrmMain = formToolkit.createScrolledForm(parent);
		scrldfrmMain.setText("Datos de direcci\u00F3n alterna");
		formToolkit.paintBordersFor(scrldfrmMain);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.verticalSpacing = 20;
		gridLayout.marginHeight = 10;
		gridLayout.marginWidth = 10;
		scrldfrmMain.getBody().setLayout(gridLayout);
		
		sctnResponsable = formToolkit.createSection(scrldfrmMain.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnResponsable.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.paintBordersFor(sctnResponsable);
		sctnResponsable.setText("Responsable");
		sctnResponsable.setExpanded(true);
		
		Composite compositeResponsable = formToolkit.createComposite(sctnResponsable, SWT.NONE);
		formToolkit.paintBordersFor(compositeResponsable);
		sctnResponsable.setClient(compositeResponsable);
		GridLayout gl_compositeResponsable = new GridLayout(2, false);
		compositeResponsable.setLayout(gl_compositeResponsable);
		
		Label lblCodigoResponsable = formToolkit.createLabel(compositeResponsable, "C\u00F3digo:", SWT.NONE);
		lblCodigoResponsable.setSize(37, 13);
		
		txtCodigoResponsable = formToolkit.createText(compositeResponsable, "New Text", SWT.NONE);
		txtCodigoResponsable.setEnabled(false);
		GridData gd_txtCodigoResponsable = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtCodigoResponsable.widthHint = 35;
		txtCodigoResponsable.setLayoutData(gd_txtCodigoResponsable);
		txtCodigoResponsable.setSize(62, 19);
		txtCodigoResponsable.setText("");
		
		Label lblNombre = formToolkit.createLabel(compositeResponsable, "Nombre:", SWT.NONE);
		lblNombre.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtNombreResponsable = formToolkit.createText(compositeResponsable, "New Text", SWT.NONE);
		txtNombreResponsable.setEnabled(false);
		txtNombreResponsable.setText("");
		GridData gd_txtNombreResponsable = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtNombreResponsable.widthHint = 250;
		txtNombreResponsable.setLayoutData(gd_txtNombreResponsable);
		
		sctnGenerales = formToolkit.createSection(scrldfrmMain.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnGenerales.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.paintBordersFor(sctnGenerales);
		sctnGenerales.setText("Informaci\u00F3n general");
		
		compositeGenerales = formToolkit.createComposite(sctnGenerales, SWT.NONE);
		formToolkit.paintBordersFor(compositeGenerales);
		sctnGenerales.setClient(compositeGenerales);
		compositeGenerales.setLayout(new GridLayout(5, false));
		
		Label lblCodigo = formToolkit.createLabel(compositeGenerales, "C\u00F3digo:", SWT.NONE);
		lblCodigo.setEnabled(false);
		lblCodigo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtCodigo = formToolkit.createText(compositeGenerales, "New Text", SWT.NONE);
		txtCodigo.setEnabled(false);
		txtCodigo.setText("");
		GridData gd_txtCodigo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtCodigo.widthHint = 35;
		txtCodigo.setLayoutData(gd_txtCodigo);
		new Label(compositeGenerales, SWT.NONE);
		
		Label lblCia = formToolkit.createLabel(compositeGenerales, "Cia:", SWT.NONE);
		GridData gd_lblCia = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblCia.horizontalIndent = 30;
		lblCia.setLayoutData(gd_lblCia);
		lblCia.setSize(19, 13);
		
		txtCia = formToolkit.createText(compositeGenerales, "New Text", SWT.NONE);
		GridData gd_txtCia = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtCia.widthHint = 35;
		txtCia.setLayoutData(gd_txtCia);
		txtCia.setSize(47, 19);
		txtCia.setText("");
		
		Label lblNombreAlterno = formToolkit.createLabel(compositeGenerales, "Nombre alterno:", SWT.NONE);
		lblNombreAlterno.setSize(78, 13);
		
		txtNombreAlterno = formToolkit.createText(compositeGenerales, "New Text", SWT.NONE);
		GridData gd_txtNombreAlterno = new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1);
		gd_txtNombreAlterno.widthHint = 250;
		txtNombreAlterno.setLayoutData(gd_txtNombreAlterno);
		txtNombreAlterno.setSize(346, 19);
		txtNombreAlterno.setText("");
		
		Label lblDireccion = formToolkit.createLabel(compositeGenerales, "Direcci\u00F3n:", SWT.NONE);
		lblDireccion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDireccion.setSize(47, 13);
		
		txtDireccion1 = formToolkit.createText(compositeGenerales, "New Text", SWT.NONE);
		GridData gd_txtDireccion1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1);
		gd_txtDireccion1.widthHint = 200;
		txtDireccion1.setLayoutData(gd_txtDireccion1);
		txtDireccion1.setSize(346, 19);
		txtDireccion1.setText("");
		new Label(compositeGenerales, SWT.NONE);
		
		txtDireccion2 = formToolkit.createText(compositeGenerales, "New Text", SWT.NONE);
		GridData gd_txtDireccion2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1);
		gd_txtDireccion2.widthHint = 200;
		txtDireccion2.setLayoutData(gd_txtDireccion2);
		txtDireccion2.setSize(346, 19);
		txtDireccion2.setText("");
		new Label(compositeGenerales, SWT.NONE);
		
		txtDireccion3 = formToolkit.createText(compositeGenerales, "New Text", SWT.NONE);
		GridData gd_txtDireccion3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1);
		gd_txtDireccion3.widthHint = 200;
		txtDireccion3.setLayoutData(gd_txtDireccion3);
		txtDireccion3.setSize(346, 19);
		txtDireccion3.setText("");
		new Label(compositeGenerales, SWT.NONE);
		
		txtDireccion4 = formToolkit.createText(compositeGenerales, "New Text", SWT.NONE);
		GridData gd_txtDireccion4 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1);
		gd_txtDireccion4.widthHint = 200;
		txtDireccion4.setLayoutData(gd_txtDireccion4);
		txtDireccion4.setSize(346, 19);
		txtDireccion4.setText("");
		new Label(compositeGenerales, SWT.NONE);
		
		txtDireccion5 = formToolkit.createText(compositeGenerales, "New Text", SWT.NONE);
		GridData gd_txtDireccion5 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1);
		gd_txtDireccion5.widthHint = 200;
		txtDireccion5.setLayoutData(gd_txtDireccion5);
		txtDireccion5.setSize(346, 19);
		txtDireccion5.setText("");
		
		Label lblEstado = formToolkit.createLabel(compositeGenerales, "Estado:", SWT.NONE);
		lblEstado.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		comboEstado = new Combo(compositeGenerales, SWT.READ_ONLY);
		formToolkit.adapt(comboEstado);
		formToolkit.paintBordersFor(comboEstado);
		new Label(compositeGenerales, SWT.NONE);
		new Label(compositeGenerales, SWT.NONE);
		new Label(compositeGenerales, SWT.NONE);
		
		// adición de listeners para modificaciones de los campos
		txtCodigo.addModifyListener(createModifyListener());
		txtCia.addModifyListener(createModifyListener());
		txtNombreAlterno.addModifyListener(createModifyListener());
		txtDireccion1.addModifyListener(createModifyListener());
		txtDireccion2.addModifyListener(createModifyListener());
		txtDireccion3.addModifyListener(createModifyListener());
		txtDireccion4.addModifyListener(createModifyListener());
		txtDireccion5.addModifyListener(createModifyListener());
		comboEstado.addModifyListener(createModifyListener());
		comboEstado.setItems(cdEstado.getTexto());
		
		// acciones necesarias luego de crear los controles
		llenarControles();
		addFilledFlag();
		setFocoInicial(txtNombreAlterno);
	}

	
	@Override
	protected void llenarControles() {
		if (getEditorInput().getId() == null) {
			LOGGER.debug("Creando nueva dirección...");
			registro = new Direccion();
			if (getEditorInput() instanceof ResponseEditorInput) {
				responsable = (Responsable) ((ResponseEditorInput) getEditorInput()).getParent();
				txtCodigoResponsable.setText(valor2Txt(responsable.getNoCliente()));
				txtNombreResponsable.setText(responsable.getNombreCompleto());
				txtNombreAlterno.setText(responsable.getNombre());
				registro.setResponsable(responsable);
			}
			txtCia.setText(Compania.PMA.getCodigo());
			comboEstado.setText(IBaseKeywords.ESTADO[0]);
			this.setPartName("Nueva dirección");
		} else {
			registro = controller.getRegistroById(getEditorInput().getId());
			txtCodigoResponsable.setText(valor2Txt(registro.getResponsable().getNoCliente()));
			txtNombreResponsable.setText(registro.getResponsable().getNombreCompleto());
			txtCodigo.setText(valor2Txt(registro.getNoDireccion()));
			txtCia.setText(registro.getNoCia());
			txtNombreAlterno.setText(registro.getNombreAlterno());
			txtDireccion1.setText(checkNull(registro.getDireccion1()));
			txtDireccion2.setText(checkNull(registro.getDireccion2()));
			txtDireccion3.setText(checkNull(registro.getDireccion3()));
			txtDireccion4.setText(checkNull(registro.getDireccion4()));
			txtDireccion5.setText(checkNull(registro.getDireccion5()));
			comboEstado.setText(cdEstado.getTextoByKey(registro.getEstado()));
			this.setPartName(registro.getTituloDocumento());
		}
		sctnGenerales.setExpanded(true);
	}
	
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		
		//Integer pCodigo = txt2Integer(txtCodigo.getText());
		Integer pCodigo = 0;
		if (txtCodigo.getText().equals("")) {
			pCodigo = controller.getSiguienteNoDireccion(responsable.getIdResponsable());
		} else {
			pCodigo = txt2Integer(txtCodigo.getText());
		}
		String pNoCia = txtCia.getText();
		String pNombreAlterno = txtNombreAlterno.getText().trim();
		String pDireccion1 = txtDireccion1.getText().trim();
		String pDireccion2 = txtDireccion2.getText().trim();
		String pDireccion3 = txtDireccion3.getText().trim();
		String pDireccion4 = txtDireccion4.getText().trim();
		String pDireccion5 = txtDireccion5.getText().trim();
		String pEstado = cdEstado.getKeyByIndex(comboEstado.getSelectionIndex());
		
		//registro.setResponsable(responsable);   // movido al llenarControles
		registro.setNoDireccion(pCodigo);
		registro.setNoCia(pNoCia);
		registro.setNombreAlterno(pNombreAlterno);
		registro.setDireccion1(pDireccion1);
		registro.setDireccion2(pDireccion2);
		registro.setDireccion3(pDireccion3);
		registro.setDireccion4(pDireccion4);
		registro.setDireccion5(pDireccion5);
		registro.setEstado(pEstado);
		registro.setFechaCreado(new Date());
		registro.setActualizado(true);
		
		controller.doSave(registro);
		txtCodigo.setText(valor2Txt(pCodigo));
		
/*
		if (getEditorInput().getId() == null) {
			txtCodigo.setText(registro.getIdDireccion().toString());
		}
		*/
		setPartName(registro.getTituloDocumento());
		//getEditorInput().setName(registro.getTituloDocumento());
		//actualizarVista(KeywordsView.ID);
		actualizarVistas();
		removeDirtyFlag();
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
