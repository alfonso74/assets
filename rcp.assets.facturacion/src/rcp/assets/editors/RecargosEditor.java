package rcp.assets.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import rcp.assets.controllers.RecargosController;
import rcp.assets.model.Keyword;
import rcp.assets.model.Recargo;
import rcp.assets.services.ComboData;
import rcp.assets.services.ComboDataManager;
import rcp.assets.services.FechaUtil;
import rcp.assets.services.IBaseKeywords;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;


public class RecargosEditor extends AbstractEditor {

	public static final String ID = "rcp.assets.editors.RecargosEditor"; //$NON-NLS-1$
	private String idSession = ID + FechaUtil.getMilisegundos();
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	private Recargo registro = null;
	private RecargosController controller;

	private Text txtCodigo;
	private Text txtMonto;
	private Text txtTextoFactura;

	private Combo comboCia;
	private Combo comboPeriodo;
	private Combo comboTipoRecargo;
	private Combo comboEstado;

	private ComboData cdPeriodo;
	private ComboData cdTipoRecargo;
	private ComboData cdEstado;
	private Text txtTextoIngles;


	public RecargosEditor() {
		controller = new RecargosController(idSession);
		cdPeriodo = ComboDataManager.getInstance().getComboData("Periodo anualidad");
		cdTipoRecargo = ComboDataManager.getInstance().getComboData("Recargo de anualidad");
		cdEstado = ComboDataManager.getInstance().getComboData(IBaseKeywords.TipoKeyword.STATUS.getDescripcion());
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		GenericEditorInput genericInput = (GenericEditorInput) input;
		setSite(site);
		setInput(genericInput);
	}

	/**
	 * Create contents of the editor part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		ScrolledForm scrldfrmDetallesDelRecargo = formToolkit.createScrolledForm(parent);
		formToolkit.paintBordersFor(scrldfrmDetallesDelRecargo);
		scrldfrmDetallesDelRecargo.setText("Detalles del recargo de anualidad");
		scrldfrmDetallesDelRecargo.getBody().setLayout(new GridLayout(5, false));

		Label lblCodigo = formToolkit.createLabel(scrldfrmDetallesDelRecargo.getBody(), "C\u00F3digo:", SWT.NONE);
		lblCodigo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtCodigo = formToolkit.createText(scrldfrmDetallesDelRecargo.getBody(), "New Text", SWT.NONE);
		txtCodigo.setEnabled(false);
		txtCodigo.setText("");
		GridData gd_txtCodigo = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtCodigo.widthHint = 35;
		txtCodigo.setLayoutData(gd_txtCodigo);
		new Label(scrldfrmDetallesDelRecargo.getBody(), SWT.NONE);
		new Label(scrldfrmDetallesDelRecargo.getBody(), SWT.NONE);
		new Label(scrldfrmDetallesDelRecargo.getBody(), SWT.NONE);

		Label lblCompania = formToolkit.createLabel(scrldfrmDetallesDelRecargo.getBody(), "Compa\u00F1\u00EDa:", SWT.NONE);
		lblCompania.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		comboCia = new Combo(scrldfrmDetallesDelRecargo.getBody(), SWT.READ_ONLY);
		comboCia.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(comboCia);
		formToolkit.paintBordersFor(comboCia);
		new Label(scrldfrmDetallesDelRecargo.getBody(), SWT.NONE);

		Label lblPeriodo = formToolkit.createLabel(scrldfrmDetallesDelRecargo.getBody(), "Periodo:", SWT.NONE);
		lblPeriodo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		comboPeriodo = new Combo(scrldfrmDetallesDelRecargo.getBody(), SWT.READ_ONLY);
		comboPeriodo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(comboPeriodo);
		formToolkit.paintBordersFor(comboPeriodo);

		Label lblTipo = new Label(scrldfrmDetallesDelRecargo.getBody(), SWT.NONE);
		lblTipo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblTipo, true, true);
		lblTipo.setText("Tipo:");

		comboTipoRecargo = new Combo(scrldfrmDetallesDelRecargo.getBody(), SWT.READ_ONLY);
		comboTipoRecargo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		comboTipoRecargo.addModifyListener(createModifyListener());
		formToolkit.adapt(comboTipoRecargo);
		formToolkit.paintBordersFor(comboTipoRecargo);
		new Label(scrldfrmDetallesDelRecargo.getBody(), SWT.NONE);
		new Label(scrldfrmDetallesDelRecargo.getBody(), SWT.NONE);
		new Label(scrldfrmDetallesDelRecargo.getBody(), SWT.NONE);

		Label lblMonto = formToolkit.createLabel(scrldfrmDetallesDelRecargo.getBody(), "Monto:", SWT.NONE);
		lblMonto.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtMonto = formToolkit.createText(scrldfrmDetallesDelRecargo.getBody(), "New Text", SWT.NONE);
		txtMonto.setText("");
		GridData gd_txtMonto = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtMonto.widthHint = 45;
		txtMonto.setLayoutData(gd_txtMonto);
		txtMonto.addModifyListener(createModifyListener());
		new Label(scrldfrmDetallesDelRecargo.getBody(), SWT.NONE);
		new Label(scrldfrmDetallesDelRecargo.getBody(), SWT.NONE);
		new Label(scrldfrmDetallesDelRecargo.getBody(), SWT.NONE);

		Label lblTextoFactura = formToolkit.createLabel(scrldfrmDetallesDelRecargo.getBody(), "Texto factura:", SWT.NONE);
		lblTextoFactura.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtTextoFactura = formToolkit.createText(scrldfrmDetallesDelRecargo.getBody(), "New Text", SWT.NONE);
		txtTextoFactura.setText("");
		txtTextoFactura.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		txtTextoFactura.addModifyListener(createModifyListener());

		Label lblTextoIngles = formToolkit.createLabel(scrldfrmDetallesDelRecargo.getBody(), "Texto (ingl\u00E9s):", SWT.NONE);
		lblTextoIngles.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtTextoIngles = formToolkit.createText(scrldfrmDetallesDelRecargo.getBody(), "New Text", SWT.NONE);
		txtTextoIngles.setText("");
		txtTextoIngles.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		txtTextoIngles.addModifyListener(createModifyListener());

		Label lblEstado = formToolkit.createLabel(scrldfrmDetallesDelRecargo.getBody(), "Estado:", SWT.NONE);
		lblEstado.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		comboEstado = new Combo(scrldfrmDetallesDelRecargo.getBody(), SWT.READ_ONLY);
		comboEstado.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		comboEstado.addModifyListener(createModifyListener());
		formToolkit.adapt(comboEstado);
		formToolkit.paintBordersFor(comboEstado);
		new Label(scrldfrmDetallesDelRecargo.getBody(), SWT.NONE);
		new Label(scrldfrmDetallesDelRecargo.getBody(), SWT.NONE);

		llenarControles();
		addFilledFlag();
		setFocoInicial(lblCompania);
		new Label(scrldfrmDetallesDelRecargo.getBody(), SWT.NONE);
	}


	@Override
	protected void llenarControles() {
		comboCia.setItems(IBaseKeywords.COMPANIAS);
		comboPeriodo.setItems(cdPeriodo.getTexto());
		comboTipoRecargo.setItems(cdTipoRecargo.getTexto());
		comboEstado.setItems(cdEstado.getTexto());

		if (getEditorInput().getId() == null) {
			LOGGER.debug("Creando nuevo recargo...");
			registro = new Recargo();
			this.setPartName("Nuevo recargo");
		} else {
			registro = controller.getRegistroById(getEditorInput().getId());
			txtCodigo.setText(valor2Txt(registro.getId()));
			txtMonto.setText(valor2Txt(registro.getMonto(), "#,###.00"));
			txtTextoFactura.setText(checkNull(registro.getTextoFactura()));
			txtTextoIngles.setText(checkNull(registro.getTextoIngles()));
			comboCia.setText(registro.getNoCia());
			comboPeriodo.select(registro.getPeriodo());
			comboPeriodo.setText(cdPeriodo.getTextoByKey(registro.getPeriodo().toString()));
			comboTipoRecargo.setText(registro.getTipoRecargo().getDescripcion());
			comboEstado.setText(registro.getEstado() == null ? "" : registro.getEstado().getDescripcion());
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {

		if (!validarSave()) {
			monitor.setCanceled(true);
			return;
		}

		String pNoCia = comboCia.getItem(comboCia.getSelectionIndex());
		String pPeriodo = cdPeriodo.getKeyByIndex(comboPeriodo.getSelectionIndex());
		Keyword pTipoRecargo = (Keyword) cdTipoRecargo.getObjectByIndex(comboTipoRecargo.getSelectionIndex());
		String pMonto = txtMonto.getText().trim();
		String pTextoFactura = txtTextoFactura.getText();
		String pTextoIngles = txtTextoIngles.getText();
		Keyword pEstado = (Keyword) cdEstado.getObjectByIndex(comboEstado.getSelectionIndex());

		registro.setNoCia(pNoCia);
		registro.setPeriodo(txt2Integer(pPeriodo));
		registro.setTipoRecargo(pTipoRecargo);
		registro.setMonto(txt2Float(pMonto));
		registro.setTextoFactura(pTextoFactura);
		registro.setTextoIngles(pTextoIngles);
		registro.setEstado(pEstado);

		controller.doSave(registro);

		if (getEditorInput().getId() == null) {
			txtCodigo.setText(registro.getIdRecargo().toString());
		}
		setPartName(registro.getTituloDocumento());
		actualizarVistas();
		removeDirtyFlag();
	}


	private boolean validarSave() {
		String pMonto = txtMonto.getText();
		String pTextoFactura = txtTextoFactura.getText().trim();

		if (comboCia.getSelectionIndex() == -1) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe seleccionar la compañía para la cual se define el recargo.");
			return false;
		} else if (comboPeriodo.getSelectionIndex() == -1) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe indicar el período al que pertenece el recargo.");
			return false;
		} else if (comboTipoRecargo.getSelectionIndex() == -1) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe indicar el tipo del recargo.");
			return false;
		} else if (txt2Float(pMonto) == null) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe introducir el monto del recargo.");
			return false;
		} else if (pTextoFactura.equals("")) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe introducir el texto que saldrá al generar una factura con este recargo.");
			return false;
		} else if (comboEstado.getSelectionIndex() == -1) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"Debe indicar un estado para el recargo.");
			return false;
		} else if (pTextoFactura.length() > 200) {
			MessageDialog.openInformation(getSite().getShell(), "Validación de campos",
					"El campo de 'Texto factura' no puede ser mayor a 200 caracteres (tamaño actual: " + pTextoFactura.length() + ").");
			return false;
		}
		return true;
	}


	@Override
	public void dispose() {
		controller.finalizarSesion();
		super.dispose();
	}


}
