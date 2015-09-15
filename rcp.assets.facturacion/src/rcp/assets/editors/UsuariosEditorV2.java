package rcp.assets.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import rcp.assets.controllers.KeywordsController;
import rcp.assets.controllers.UsuariosController;
import rcp.assets.model.Usuario;
import rcp.assets.services.AutenticacionUtil;
import rcp.assets.services.ComboData;
import rcp.assets.services.FechaUtil;
import rcp.assets.services.IBaseKeywords;

import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Form;


public class UsuariosEditorV2 extends AbstractEditor {
	
	//public static final String ID = UsuariosEditor.class.toString();
	public static final String ID = "rcp.assets.editors.UsuariosEditor";
	private String idSession = ID + FechaUtil.getMilisegundos();
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	
	private Usuario registro = null;
	private UsuariosController controller;
	private KeywordsController keywordsController;
	private Text txtCodigo;
	private Text txtApellido;
	private Text txtNombre;
	private Text txtUsername;
	private Text txtPassword;
	private Combo comboEstado;
	private ComboData cdEstado;
	

	public UsuariosEditorV2() {
		controller = new UsuariosController(idSession);
		keywordsController = new KeywordsController();
		cdEstado = keywordsController.getComboDataKeyword(IBaseKeywords.TipoKeyword.STATUS.getDescripcion());
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		
		String pNombre = txtNombre.getText();
		String pApellido = txtApellido.getText();
		String pUsername = txtUsername.getText();
		String pPassword = AutenticacionUtil.encodePasswordMD5(txtPassword.getText());
		String pEstado = cdEstado.getKeyByIndex(comboEstado.getSelectionIndex()); 

		registro.setNombre(pNombre);
		registro.setApellido(pApellido);
		registro.setUserName(pUsername);
		registro.setPassword(pPassword);
		registro.setEstado(pEstado);
		
		controller.doSave(registro);
		
		if (getEditorInput().getId() == null) {
			txtCodigo.setText(registro.getIdUsuario().toString());
		}
		setPartName(registro.getTituloDocumento());
		//getEditorInput().setName(registro.getTituloDocumento());
		//actualizarVista(KeywordsView.ID);
		actualizarVistas();
		removeDirtyFlag();

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
	protected void llenarControles() {
		
		if (getEditorInput().getId() == null) {
			LOGGER.debug("Creando nuevo usuario...");
			registro = new Usuario();
			this.setPartName("Nuevo usuario");
			//getEditorInput().setName("Nuevo usuario");
		} else {
			registro = controller.getRegistroById(getEditorInput().getId());
			txtCodigo.setText(valor2Txt(registro.getIdUsuario()));
			txtNombre.setText(registro.getNombre());
			txtApellido.setText(registro.getApellido());
			txtUsername.setText(registro.getUserName());
			txtPassword.setEnabled(false);
			comboEstado.setText(cdEstado.getTextoByKey(registro.getEstado()));
			//comboEstado.setText(registro.getDspEstado());
			/*
			registro = (Usuario) getEditorInput().getElemento();
			// el .refresh() es para evitar error de hibernate por tener un collection aquí y otro en la vista
			// desde donde abrimos el registro
			usuariosController.getSession().refresh(registro, LockOptions.NONE);
			LOGGER.debug("Editando estantería: " + registro.getTituloDocumento());
			txtID.setText(valor2Txt(registro.getIdUsuario()));
			txtNombre.setText(registro.getNombre());
			txtApellido.setText(registro.getApellido());
			txtUsername.setText(registro.getUserName());
			txtPassword.setEnabled(false);
			comboEstado.setText(registro.getDspEstado());
			*/
		}

	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		ScrolledForm scrldfrmNewScrolledform = formToolkit.createScrolledForm(parent);
		scrldfrmNewScrolledform.setAlwaysShowScrollBars(true);
		scrldfrmNewScrolledform.setBackground(SWTResourceManager.getColor(255, 255, 255));
		formToolkit.paintBordersFor(scrldfrmNewScrolledform);
		scrldfrmNewScrolledform.setText("Detalles de usuario");
		scrldfrmNewScrolledform.getBody().setLayout(new GridLayout(5, false));
		
		Label lblCodigo = formToolkit.createLabel(scrldfrmNewScrolledform.getBody(), "C\u00F3digo:", SWT.NONE);
		lblCodigo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtCodigo = formToolkit.createText(scrldfrmNewScrolledform.getBody(), "New Text", SWT.NONE);
		txtCodigo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		txtCodigo.setEnabled(false);
		txtCodigo.setText("");
		GridData gd_txtCodigo = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtCodigo.widthHint = 35;
		txtCodigo.setLayoutData(gd_txtCodigo);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		
		Label lblNombre = formToolkit.createLabel(scrldfrmNewScrolledform.getBody(), "Nombre:", SWT.NONE);
		lblNombre.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtNombre = formToolkit.createText(scrldfrmNewScrolledform.getBody(), "New Text", SWT.NONE);
		txtNombre.setText("");
		GridData gd_txtNombre = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtNombre.widthHint = 120;
		txtNombre.setLayoutData(gd_txtNombre);
		txtNombre.addModifyListener(createModifyListener());
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		
		Label lblApellido = formToolkit.createLabel(scrldfrmNewScrolledform.getBody(), "Apellido:", SWT.NONE);
		lblApellido.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtApellido = formToolkit.createText(scrldfrmNewScrolledform.getBody(), "New Text", SWT.NONE);
		txtApellido.setText("");
		GridData gd_txtApellido = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtApellido.widthHint = 120;
		txtApellido.setLayoutData(gd_txtApellido);
		txtApellido.addModifyListener(createModifyListener());
		
		Label lblUsername = formToolkit.createLabel(scrldfrmNewScrolledform.getBody(), "Username:", SWT.NONE);
		lblUsername.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtUsername = formToolkit.createText(scrldfrmNewScrolledform.getBody(), "New Text", SWT.NONE);
		txtUsername.setText("");
		GridData gd_txtUsername = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtUsername.widthHint = 120;
		txtUsername.setLayoutData(gd_txtUsername);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		
		Label lblPassword = formToolkit.createLabel(scrldfrmNewScrolledform.getBody(), "Password", SWT.NONE);
		lblPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtPassword = formToolkit.createText(scrldfrmNewScrolledform.getBody(), "New Text", SWT.NONE);
		txtPassword.setText("");
		GridData gd_txtPassword = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtPassword.widthHint = 120;
		txtPassword.setLayoutData(gd_txtPassword);
		
		Label lblEstado = formToolkit.createLabel(scrldfrmNewScrolledform.getBody(), "Estado:", SWT.NONE);
		lblEstado.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		comboEstado = new Combo(scrldfrmNewScrolledform.getBody(), SWT.READ_ONLY);
		comboEstado.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		comboEstado.addModifyListener(createModifyListener());
		formToolkit.adapt(comboEstado);
		formToolkit.paintBordersFor(comboEstado);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		
		comboEstado.setItems(cdEstado.getTexto());
		
		llenarControles();
		addFilledFlag();
		setFocoInicial(txtNombre);
	}

	@Override
	public void dispose() {
		controller.finalizarSesion();
		super.dispose();
	}
		
}
