package rcp.assets.editors;

import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import rcp.assets.controllers.AtencionesController;
import rcp.assets.model.Atencion;
import rcp.assets.model.Responsable;
import rcp.assets.services.ComboData;
import rcp.assets.services.ComboDataManager;
import rcp.assets.services.FechaUtil;
import rcp.assets.services.IBaseKeywords;

import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;


public class AtencionesEditor extends AbstractEditor {

	public static final String ID = "rcp.assets.editors.AtencionesEditor"; //$NON-NLS-1$
	private String idSession = ID + FechaUtil.getMilisegundos();
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	private Responsable responsable = null;
	
	private Atencion registro = null;
	private AtencionesController controller;
	private Text txtCodigoResponsable;
	private Text txtNombreResponsable;
	private Text txtCodigo;
	private Text txtNombre;
	private Combo comboEstado;
	private ComboData cdEstado;


	public AtencionesEditor() {
		controller = new AtencionesController(idSession);
		cdEstado = ComboDataManager.getInstance().getComboData(IBaseKeywords.TipoKeyword.STATUS.getDescripcion());
	}

	/**
	 * Create contents of the editor part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		ScrolledForm scrldfrmMain = formToolkit.createScrolledForm(parent);
		formToolkit.paintBordersFor(scrldfrmMain);
		scrldfrmMain.setText("Detalles de atenci\u00F3n");
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.verticalSpacing = 20;
		gridLayout.marginHeight = 10;
		gridLayout.marginWidth = 10;
		scrldfrmMain.getBody().setLayout(gridLayout);

		Section sctnResponsable = formToolkit.createSection(scrldfrmMain.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnResponsable.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		formToolkit.paintBordersFor(sctnResponsable);
		sctnResponsable.setText("Responsable");
		sctnResponsable.setExpanded(true);

		Composite compositeResponsable = formToolkit.createComposite(sctnResponsable, SWT.NONE);
		formToolkit.paintBordersFor(compositeResponsable);
		sctnResponsable.setClient(compositeResponsable);
		GridLayout gl_compositeResponsable = new GridLayout(2, false);
		compositeResponsable.setLayout(gl_compositeResponsable);

		Label lblCodigoResponsable = formToolkit.createLabel(compositeResponsable, "C\u00F3digo:", SWT.NONE);
		lblCodigoResponsable.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtCodigoResponsable = formToolkit.createText(compositeResponsable, "New Text", SWT.NONE);
		txtCodigoResponsable.setEnabled(false);
		txtCodigoResponsable.setText("");
		GridData gd_txtCodigoResponsable = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtCodigoResponsable.widthHint = 35;
		txtCodigoResponsable.setLayoutData(gd_txtCodigoResponsable);

		Label lblNombreResponsable = formToolkit.createLabel(compositeResponsable, "Nombre:", SWT.NONE);
		lblNombreResponsable.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtNombreResponsable = formToolkit.createText(compositeResponsable, "New Text", SWT.NONE);
		txtNombreResponsable.setEnabled(false);
		txtNombreResponsable.setText("");
		GridData gd_txtNombreResponsable = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtNombreResponsable.widthHint = 250;
		txtNombreResponsable.setLayoutData(gd_txtNombreResponsable);

		Section sctnGenerales = formToolkit.createSection(scrldfrmMain.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnGenerales.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.paintBordersFor(sctnGenerales);
		sctnGenerales.setText("Informaci\u00F3n general");
		sctnGenerales.setExpanded(true);

		Composite compositeGenerales = formToolkit.createComposite(sctnGenerales, SWT.NONE);
		formToolkit.paintBordersFor(compositeGenerales);
		sctnGenerales.setClient(compositeGenerales);
		GridLayout gl_compositeGenerales = new GridLayout(2, false);
		compositeGenerales.setLayout(gl_compositeGenerales);

		Label lblCodigo = formToolkit.createLabel(compositeGenerales, "C\u00F3digo:", SWT.NONE);
		lblCodigo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtCodigo = formToolkit.createText(compositeGenerales, "New Text", SWT.NONE);
		txtCodigo.setEnabled(false);
		txtCodigo.setText("");
		GridData gd_txtCodigo = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtCodigo.widthHint = 35;
		txtCodigo.setLayoutData(gd_txtCodigo);

		Label lblNombre = formToolkit.createLabel(compositeGenerales, "Nombre:", SWT.NONE);
		lblNombre.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtNombre = formToolkit.createText(compositeGenerales, "New Text", SWT.NONE);
		txtNombre.setText("");
		GridData gd_txtNombre = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtNombre.widthHint = 200;
		txtNombre.setLayoutData(gd_txtNombre);
		
		Label lblStatus = formToolkit.createLabel(compositeGenerales, "Estado:", SWT.NONE);
		lblStatus.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		comboEstado = new Combo(compositeGenerales, SWT.READ_ONLY);
		comboEstado.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(comboEstado);
		formToolkit.paintBordersFor(comboEstado);
		
		// adición de listeners para modificaciones de los campos
		txtCodigo.addModifyListener(createModifyListener());
		txtNombre.addModifyListener(createModifyListener());
		comboEstado.addModifyListener(createModifyListener());
		comboEstado.setItems(cdEstado.getTexto());

		// acciones necesarias luego de crear los controles
		llenarControles();
		addFilledFlag();
		setFocoInicial(txtNombre);
	}


	@Override
	protected void llenarControles() {
		if (getEditorInput().getId() == null) {
			LOGGER.debug("Creando nueva atención...");
			registro = new Atencion();
			if (getEditorInput() instanceof ResponseEditorInput) {
				responsable = (Responsable) ((ResponseEditorInput) getEditorInput()).getParent();
				txtCodigoResponsable.setText(valor2Txt(responsable.getNoCliente()));
				txtNombreResponsable.setText(responsable.getNombreCompleto());
				registro.setResponsable(responsable);
			}
			comboEstado.setText(IBaseKeywords.ESTADO[0]);
			this.setPartName("Nueva atención");
		} else {
			registro = controller.getRegistroById(getEditorInput().getId());
			txtCodigoResponsable.setText(valor2Txt(registro.getResponsable().getNoCliente()));
			txtNombreResponsable.setText(registro.getResponsable().getNombreCompleto());
			txtCodigo.setText(valor2Txt(registro.getNoAtencion()));
			txtNombre.setText(registro.getDescripcion());
			comboEstado.setText(cdEstado.getTextoByKey(registro.getEstado()));
			this.setPartName(registro.getTituloDocumento());
		}
	}


	@Override
	public void doSave(IProgressMonitor monitor) {

		//Integer pCodigo = txt2Integer(txtCodigo.getText());
		Integer pCodigo = 0;
		if (txtCodigo.getText().equals("")) {
			pCodigo = controller.getSiguienteNoAtencion(responsable.getIdResponsable());
		} else {
			pCodigo = txt2Integer(txtCodigo.getText());
		}
		String pNombre = txtNombre.getText().trim();
		String pEstado = cdEstado.getKeyByIndex(comboEstado.getSelectionIndex());

		//registro.setResponsable(responsable);      // movido al llenarControles
		registro.setNoCia("01");
		registro.setNoAtencion(pCodigo);
		registro.setDescripcion(pNombre);
		registro.setEstado(pEstado);
		registro.setFechaCreado(new Date());
		registro.setActualizado(true);

		controller.doSave(registro);
		txtCodigo.setText(valor2Txt(pCodigo));
		
/*
		if (getEditorInput().getId() == null) {
			txtCodigo.setText(registro.getIdAtencion().toString());
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
