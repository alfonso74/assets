package rcp.assets.editors;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import rcp.assets.controllers.ExpedientesController;
import rcp.assets.controllers.FacturasController;
import rcp.assets.controllers.VerFacturasController;
import rcp.assets.model.Expediente;
import rcp.assets.model.Factura;
import rcp.assets.model.Periodo;
import rcp.assets.model.TipoFactura;
import rcp.assets.model.TipoProforma;
import rcp.assets.services.CalComboSettings;
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.nebula.widgets.calendarcombo.CalendarCombo;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.wb.swt.SWTResourceManager;


public class FacturarRetainersEditor extends AbstractEditor {

	public static final String ID = "rcp.assets.editors.FacturarRetainersEditor"; //$NON-NLS-1$
	private String idSession = ID + FechaUtil.getMilisegundos();
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private ExpedientesController controller;
	private FacturasController facturasController;

	private TableViewer viewer;
	private Table table;

	private Text txtNoExpediente;
	private CalendarCombo calendarComboFechaFact;
	private Combo comboMM;
	private Combo comboYY;
	private Combo comboIdioma;

	private ComboData cdIdioma;
	private ComboData cdMM;


	public FacturarRetainersEditor() {
		controller = new ExpedientesController();
		facturasController = new FacturasController();
		cdIdioma = ComboDataManager.getInstance().getComboData(IBaseKeywords.TipoKeyword.IDIOMA.getDescripcion());
		cdMM = ComboDataManager.getInstance().getComboData("Meses español");
	}

	/**
	 * Create contents of the editor part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		ScrolledForm scrldfrmMain = formToolkit.createScrolledForm(parent);
		formToolkit.paintBordersFor(scrldfrmMain);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginHeight = 10;
		gridLayout.marginWidth = 10;
		scrldfrmMain.getBody().setLayout(gridLayout);

		Section sctnBusquedaDeExpediente = formToolkit.createSection(scrldfrmMain.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnBusquedaDeExpediente.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.paintBordersFor(sctnBusquedaDeExpediente);
		sctnBusquedaDeExpediente.setText("B\u00FAsqueda de retainers para facturaci\u00F3n");
		sctnBusquedaDeExpediente.setExpanded(true);

		Composite compositeBuscar = formToolkit.createComposite(sctnBusquedaDeExpediente, SWT.NONE);
		formToolkit.paintBordersFor(compositeBuscar);
		sctnBusquedaDeExpediente.setClient(compositeBuscar);
		compositeBuscar.setLayout(new GridLayout(3, false));

		Label lblNumeroDeExpediente = formToolkit.createLabel(compositeBuscar, "N\u00FAmero de expediente:", SWT.NONE);
		lblNumeroDeExpediente.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtNoExpediente = formToolkit.createText(compositeBuscar, "New Text", SWT.NONE);
		txtNoExpediente.setText("");
		txtNoExpediente.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnBuscar = new Button(compositeBuscar, SWT.NONE);
		formToolkit.adapt(btnBuscar, true, true);
		btnBuscar.setText("Buscar");
		btnBuscar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (txtNoExpediente == null) {
					MessageDialog.openInformation(getSite().getShell(), "Búsqueda de expediente", "Debe suministrar un número de expediente para utilizar esta función.");
				} else {
					String noExp = txtNoExpediente.getText().trim();
					viewer.setInput(controller.buscarExpediente(noExp));
				}
			}
		});

		Label lblFechaFactura = formToolkit.createLabel(compositeBuscar, "Fecha de factura:", SWT.NONE);
		lblFechaFactura.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		Composite composite = formToolkit.createComposite(compositeBuscar, SWT.NONE);
		formToolkit.paintBordersFor(composite);
		GridLayout gl_composite = new GridLayout(6, false);
		gl_composite.marginWidth = 0;
		composite.setLayout(gl_composite);

		calendarComboFechaFact = new CalendarCombo(composite, SWT.NONE, new CalComboSettings(), null);
		formToolkit.adapt(calendarComboFechaFact);
		formToolkit.paintBordersFor(calendarComboFechaFact);

		Label lblPeriodo = formToolkit.createLabel(composite, "Periodo:", SWT.NONE);
		GridData gd_lblPeriodo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblPeriodo.horizontalIndent = 30;
		lblPeriodo.setLayoutData(gd_lblPeriodo);

		comboMM = new Combo(composite, SWT.READ_ONLY);
		comboMM.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboMM.setItems(cdMM.getTexto());
		formToolkit.adapt(comboMM);
		formToolkit.paintBordersFor(comboMM);

		comboYY = new Combo(composite, SWT.READ_ONLY);
		comboYY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboYY.setItems(new String[] {"" + Calendar.getInstance().get(Calendar.YEAR), "" + (Calendar.getInstance().get(Calendar.YEAR) + 1)});
		formToolkit.adapt(comboYY);
		formToolkit.paintBordersFor(comboYY);

		Label lblIdioma = formToolkit.createLabel(composite, "Idioma:", SWT.NONE);
		GridData gd_lblIdioma = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblIdioma.horizontalIndent = 30;
		lblIdioma.setLayoutData(gd_lblIdioma);

		comboIdioma = new Combo(composite, SWT.READ_ONLY);
		comboIdioma.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboIdioma.setItems(cdIdioma.getTexto());
		formToolkit.adapt(comboIdioma);
		formToolkit.paintBordersFor(comboIdioma);
		new Label(compositeBuscar, SWT.NONE);

		Composite compositeBotones = formToolkit.createComposite(scrldfrmMain.getBody(), SWT.NONE);
		compositeBotones.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.paintBordersFor(compositeBotones);
		GridLayout gl_compositeBotones = new GridLayout(3, false);
		gl_compositeBotones.marginHeight = 0;
		compositeBotones.setLayout(gl_compositeBotones);

		ImageHyperlink mghprlnkProforma = formToolkit.createImageHyperlink(compositeBotones, SWT.NONE);
		mghprlnkProforma.setForeground(SWTResourceManager.getColor(0, 0, 0));
		mghprlnkProforma.setUnderlined(false);
		mghprlnkProforma.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		mghprlnkProforma.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/pdf_16.png"));
		formToolkit.paintBordersFor(mghprlnkProforma);
		mghprlnkProforma.setText("Proforma");
		mghprlnkProforma.addHyperlinkListener(new IHyperlinkListener() {
			public void linkActivated(HyperlinkEvent e) {
				Expediente expediente = getElementoSeleccionado(viewer);
				if (expediente != null) {
					generarProformaRetainer(expediente);
				} else {
					MessageDialog.openInformation(getSite().getShell(), "Generar proforma (retainer)", "Debe seleccionar un expediente para ejecutar esta acción.");
				}
			}
			public void linkEntered(HyperlinkEvent e) {
			}
			public void linkExited(HyperlinkEvent e) {
			}
		});

		ImageHyperlink mghprlnkFactura = formToolkit.createImageHyperlink(compositeBotones, SWT.NONE);
		mghprlnkFactura.setForeground(SWTResourceManager.getColor(0, 0, 0));
		mghprlnkFactura.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/excel_16.png"));
		mghprlnkFactura.setUnderlined(false);
		formToolkit.paintBordersFor(mghprlnkFactura);
		mghprlnkFactura.setText("Factura");
		mghprlnkFactura.addHyperlinkListener(new IHyperlinkListener() {
			public void linkActivated(HyperlinkEvent e) {
				//MessageDialog.openError(getSite().getShell(), "Generar factura", "Todavia no!!");
				Expediente expediente = getElementoSeleccionado(viewer);
				if (expediente != null) {
					Integer idMes = Integer.valueOf(cdMM.getKeyByTexto(comboMM.getText()));
					Integer yyyy = Integer.valueOf(comboYY.getText());
					LOGGER.info("Verificando retainer:  Exp " + expediente.getNoExpediente() + ", mes: " + idMes + ", año: " + yyyy);
					if (!facturasController.existeFacturaRetainer(expediente, idMes, yyyy)) {
						Factura factura = generarFacturaRetainer(expediente);
						abrirFactura(new Browser(getSite().getShell(), SWT.None), factura);
					} else {
						Factura facturaExistente = facturasController.getFacturaRetainer(expediente, idMes, yyyy);
						boolean flagAbrirFactura = MessageDialog.openQuestion(getSite().getShell(), "Generar factura", "Ya existe una factura de retainer para el periodo indicado.  Para generar\n" +
								"una nueva factura debe anular el documento existente (número " + facturaExistente.getNoFactura() + ").\n" +
								" Desea abrir la factura existente?");
						LOGGER.info("ABRIR FACTURA: " + flagAbrirFactura); 
						if (flagAbrirFactura) {
							abrirFactura(new Browser(getSite().getShell(), SWT.NONE), facturaExistente);
						}
					}
				} else {
					MessageDialog.openInformation(getSite().getShell(), "Generar factura (retainer)", "Debe seleccionar un expediente para ejecutar esta acción.");
				}
			}
			public void linkEntered(HyperlinkEvent e) {
			}
			public void linkExited(HyperlinkEvent e) {
			}
		});

		ImageHyperlink mghprlnkActualizar = formToolkit.createImageHyperlink(compositeBotones, SWT.NONE);
		mghprlnkActualizar.setForeground(SWTResourceManager.getColor(0, 0, 0));
		mghprlnkActualizar.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/refresh_16.png"));
		mghprlnkActualizar.setUnderlined(false);
		formToolkit.paintBordersFor(mghprlnkActualizar);
		mghprlnkActualizar.setText("Actualizar");
		mghprlnkActualizar.addHyperlinkListener(new IHyperlinkListener() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				viewer.setInput(controller.buscarExpedientesConRetainers());
			}

			@Override
			public void linkEntered(HyperlinkEvent e) {		}

			@Override
			public void linkExited(HyperlinkEvent e) {		}					
		});


		viewer = new TableViewer(scrldfrmMain.getBody(), SWT.BORDER | SWT.FULL_SELECTION);
		table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		formToolkit.paintBordersFor(table);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnNoexp = tableViewerColumn.getColumn();
		tblclmnNoexp.setWidth(70);
		tblclmnNoexp.setText("NoExp");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnNombre = tableViewerColumn_1.getColumn();
		tblclmnNombre.setWidth(250);
		tblclmnNombre.setText("Nombre");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnSubarea = tableViewerColumn_2.getColumn();
		tblclmnSubarea.setText("SubArea");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnCreado = tableViewerColumn_3.getColumn();
		tblclmnCreado.setWidth(85);
		tblclmnCreado.setText("Otros (anual)");

		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnEstado = tableViewerColumn_4.getColumn();
		tblclmnEstado.setWidth(50);
		tblclmnEstado.setText("Estado");

		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnR = tableViewerColumn_5.getColumn();
		tblclmnR.setAlignment(SWT.RIGHT);
		tblclmnR.setWidth(60);
		tblclmnR.setText("R1");

		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnR_1 = tableViewerColumn_6.getColumn();
		tblclmnR_1.setAlignment(SWT.RIGHT);
		tblclmnR_1.setWidth(60);
		tblclmnR_1.setText("R2");

		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnR_2 = tableViewerColumn_7.getColumn();
		tblclmnR_2.setAlignment(SWT.RIGHT);
		tblclmnR_2.setWidth(60);
		tblclmnR_2.setText("R3");

		TableViewerColumn tableViewerColumn_8 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnR_3 = tableViewerColumn_8.getColumn();
		tblclmnR_3.setAlignment(SWT.RIGHT);
		tblclmnR_3.setWidth(60);
		tblclmnR_3.setText("Otros ret");

		TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnDescripcion = tableViewerColumn_9.getColumn();
		tblclmnDescripcion.setWidth(150);
		tblclmnDescripcion.setText("Descripci\u00F3n (otros retainers)");


		llenarControles();

	}


	private void generarProformaRetainer(Expediente expediente) {
		//Float impuesto = casosController.calcularImpuestos(caso);
		ProformasEditorInput input = new ProformasEditorInput(TipoProforma.RETAINER.getTitulo());
		input.setTipoProforma(TipoProforma.RETAINER.getNombre());

		Integer idExpediente = Integer.valueOf(expediente.getIdExpediente().toString());
		Float impuesto = 0F;
		impuesto = controller.calcularImpuestoRetainers(expediente, 0.07F);
		String idioma = "1";
		if (comboIdioma.getSelectionIndex() != -1) {
			LOGGER.info("Idioma x formulario: " + comboIdioma.getText());
			idioma = cdIdioma.getKeyByIndex(comboIdioma.getSelectionIndex());
		} else {
			idioma = expediente.getCargoExpediente().getResponsable().getIdioma();
			LOGGER.info("Idioma x responsable: " + idioma);
			//idioma = "1";
		}
		Integer idMes = Integer.valueOf(cdMM.getKeyByTexto(comboMM.getText()));
		Integer yyyy = Integer.valueOf(comboYY.getText());
		LOGGER.info("M: " + idMes + ",   Y: " + yyyy);

		input.configurarParametrosRetainer(idExpediente, impuesto, idioma, idMes, yyyy);

		try {
			getSite().getPage().openEditor(input, ProformasEditor.ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}

	}


	/**
	 * Genera una nueva factura en base al expediente y la info de retainer suministrado.  Se debe agregar el idioma, 
	 * el número de factura, y la fecha de la factura.
	 * @param caso Caso con la información necesaria para generar la factura
	 * @return 
	 */
	private Factura generarFacturaRetainer(Expediente expediente) {
		String idiomaFactura = "1";  // idioma español
		if (comboIdioma.getSelectionIndex() != -1) {
			LOGGER.info("Idioma seleccionado x formulario: " + comboIdioma.getText());
			idiomaFactura = cdIdioma.getKeyByIndex(comboIdioma.getSelectionIndex());
		} else {
			// se prepara la factura con el idioma indicado en los datos del responsable
			idiomaFactura = expediente.getCargoExpediente().getResponsable().getIdioma();
			LOGGER.info("Idioma seleccionado x responsable: " + idiomaFactura);
		}
		LOGGER.info("No de factura: " + facturasController.getSiguienteNoFactura());
		String numeroFactura = facturasController.getSiguienteNoFactura().toString();
		LOGGER.info("Fecha de factura: " + calendarComboFechaFact.getDate().getTime());
		Date fechaFactura = calendarComboFechaFact.getDate().getTime();
		Periodo periodo = new Periodo(Integer.parseInt(cdMM.getKeyByIndex(comboMM.getSelectionIndex())), Integer.parseInt(comboYY.getText()));
		Factura factura = facturasController.generarFacturaRetainer(expediente, periodo, fechaFactura, numeroFactura, idiomaFactura);
		facturasController.doSave(factura);
		System.out.println("EXPEDIENTE: " + factura.getNombreExpediente());
		return factura;

	}


	private void abrirFactura(Browser browser, Factura factura) {
		VerFacturasController gfc = new VerFacturasController();
		gfc.agregarParametro("idFactura", Integer.valueOf(factura.getIdFactura().toString()));
		gfc.agregarParametro("idioma", factura.getIdioma());
		gfc.generarFacturaExcel(browser, TipoFactura.RETAINER);
	}


	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// Do the Save operation
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
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public void dispose() {
		formToolkit.dispose();
		super.dispose();
	}

	@Override
	protected void llenarControles() {
		inicializarViewer();
		calendarComboFechaFact.setDate(new Date());
		comboMM.select(Calendar.getInstance().get(Calendar.MONTH));
		comboYY.setText("" + Calendar.getInstance().get(Calendar.YEAR));
	}


	protected void inicializarViewer() {
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(controller.buscarExpedientesConRetainers());
		//viewer.setInput(controller.getListado());
		// registramos el tableViewer como un selection provider
		// esto es requerido para obtener el elemento seleccionado en las acciones
		getSite().setSelectionProvider(viewer);
		// habilitamos el listener para doble clic
		//this.hookDoubleClickListener(viewer, DireccionesEditor.ID);
	}


	class ViewContentProvider implements IStructuredContentProvider {
		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public Object[] getElements(Object parent) {
			@SuppressWarnings("unchecked")
			Collection<Expediente> n = (Collection<Expediente>) parent; 
			Object[] resultados = n.toArray(new Expediente[n.size()]);
			return resultados;
		}
	}


	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			String resultado = "";
			Expediente k = (Expediente) element;
			switch (columnIndex) {
			case 0:
				resultado = k.getNoExpediente();
				break;
			case 1:
				resultado = k.getNombre();
				break;
			case 2:
				resultado = k.getDspSubArea();
				break;
			case 3:
				//resultado = FechaUtil.toString(k.getFechaCreado());
				resultado = valor2Txt(k.getCargoExpediente().getCargosAnuales().getOtros(), "#,##0.00");
				break;
			case 4:
				resultado = k.getEstado();
				//resultado = k.getDspEstado();
				//resultado = valor2Txt(k.getCargoExpediente().getRetainer().getTotalRetainers());
				break;
			case 5:
				resultado = valor2Txt(k.getCargoExpediente().getRetainer().getRetainer1(), "#,##0.00");
				break;
			case 6:
				resultado = valor2Txt(k.getCargoExpediente().getRetainer().getRetainer2(), "#,##0.00");
				break;
			case 7:
				resultado = valor2Txt(k.getCargoExpediente().getRetainer().getRetainer3(), "#,##0.00");
				break;
			case 8:
				resultado = valor2Txt(k.getCargoExpediente().getRetainer().getRetainerOtros(), "#,##0.00");
				break;
			case 9:
				resultado = k.getCargoExpediente().getRetainer().getRetainerOtrosDesc();
				break;
			}
			return resultado;
		}
	}


	/**
	 * Obtiene los elementos seleccionados en un viewer
	 * @param viewer
	 * @return lista de elementos seleccionados o null si no hay nada seleccionado
	 */
	public <T> List<T> getElementosSeleccionados(StructuredViewer viewer) {
		List<T> seleccion = null;
		if (!viewer.getSelection().isEmpty()) {
			seleccion = (List<T>) ((IStructuredSelection) viewer.getSelection()).toList();
		}
		return seleccion;
	}

	/**
	 * Obtiene el primer elemento seleccionado en un viewer
	 * @param viewer
	 * @return elemento seleccionado en el viewer o null si no hay nada seleccionado
	 */
	public <T> T getElementoSeleccionado(StructuredViewer viewer) {
		T seleccion = null;
		if (!viewer.getSelection().isEmpty()) {
			seleccion = (T) ((IStructuredSelection) viewer.getSelection()).getFirstElement();
		}
		return seleccion;
	}
}
