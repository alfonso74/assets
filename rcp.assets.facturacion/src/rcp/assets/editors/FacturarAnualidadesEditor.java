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
import rcp.assets.controllers.NotificacionesController;
import rcp.assets.controllers.VerFacturasController;
import rcp.assets.model.Expediente;
import rcp.assets.model.Factura;
import rcp.assets.model.Idioma;
import rcp.assets.model.Keyword;
import rcp.assets.model.TipoFactura;
import rcp.assets.model.TipoProforma;
import rcp.assets.services.CalComboSettings;
import rcp.assets.services.ComboData;
import rcp.assets.services.ComboDataManager;
import rcp.assets.services.FechaUtil;
import rcp.assets.services.IBaseKeywords;
import rcp.assets.services.PeriodoAnualidad;

import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.nebula.widgets.calendarcombo.CalendarCombo;


public class FacturarAnualidadesEditor extends AbstractEditor {

	public static final String ID = "rcp.assets.editors.FacturarAnualidadesEditor"; //$NON-NLS-1$
	private String idSession = ID + FechaUtil.getMilisegundos();
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private ExpedientesController controller;
	private FacturasController facturasController;
	private NotificacionesController notificacionesController;

	private TableViewer viewer;
	private Table table;

	private Text txtNoExpediente;
	private Combo comboPeriodo;
	private Combo comboYY;
	private Combo comboCia;
	private Combo comboTasa;
	private CalendarCombo calendarComboFechaFact;
	private Combo comboIdioma;
	private Combo comboNotificacion;

	private ComboData cdIdioma;
	private ComboData cdPeriodo;
	private ComboData cdNotificacion;

	private Label lblRegistros;


	public FacturarAnualidadesEditor() {
		controller = new ExpedientesController(idSession);
		facturasController = new FacturasController(idSession);
		notificacionesController = new NotificacionesController(idSession);
		cdIdioma = ComboDataManager.getInstance().getComboData(IBaseKeywords.TipoKeyword.IDIOMA.getDescripcion());
		cdPeriodo = ComboDataManager.getInstance().getComboData("Periodo anualidad");
		cdNotificacion = ComboDataManager.getInstance().getComboData(IBaseKeywords.TipoKeyword.NOTIFICACION.getDescripcion());
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

		Section sctnAnualidades = formToolkit.createSection(scrldfrmMain.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnAnualidades.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.paintBordersFor(sctnAnualidades);
		sctnAnualidades.setText("B\u00FAsqueda de anualidades para facturaci\u00F3n");
		sctnAnualidades.setExpanded(true);

		Composite compositeBuscar = formToolkit.createComposite(sctnAnualidades, SWT.NONE);
		formToolkit.paintBordersFor(compositeBuscar);
		sctnAnualidades.setClient(compositeBuscar);
		GridLayout gl_compositeBuscar = new GridLayout(3, false);
		compositeBuscar.setLayout(gl_compositeBuscar);

		Label lblPeriodoInicial = formToolkit.createLabel(compositeBuscar, "Periodo inicial:", SWT.NONE);

		Composite composite_1 = new Composite(compositeBuscar, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		formToolkit.adapt(composite_1);
		formToolkit.paintBordersFor(composite_1);
		GridLayout gl_composite_1 = new GridLayout(7, false);
		gl_composite_1.marginHeight = 0;
		composite_1.setLayout(gl_composite_1);

		comboPeriodo = new Combo(composite_1, SWT.READ_ONLY);
		comboPeriodo.setBounds(0, 0, 93, 21);
		formToolkit.adapt(comboPeriodo);
		formToolkit.paintBordersFor(comboPeriodo);

		comboYY = new Combo(composite_1, SWT.NONE);
		formToolkit.adapt(comboYY);
		formToolkit.paintBordersFor(comboYY);

		Label lblCompania = formToolkit.createLabel(composite_1, "Compa\u00F1\u00EDa:", SWT.NONE);
		GridData gd_lblCompania = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblCompania.horizontalIndent = 20;
		lblCompania.setLayoutData(gd_lblCompania);

		comboCia = new Combo(composite_1, SWT.READ_ONLY);
		formToolkit.adapt(comboCia);
		formToolkit.paintBordersFor(comboCia);

		comboTasa = new Combo(composite_1, SWT.READ_ONLY);
		formToolkit.adapt(comboTasa);
		formToolkit.paintBordersFor(comboTasa);

		Label lblNumeroDeExpediente = formToolkit.createLabel(composite_1, "No. de expediente:", SWT.NONE);
		GridData gd_lblNumeroDeExpediente = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNumeroDeExpediente.horizontalIndent = 25;
		lblNumeroDeExpediente.setLayoutData(gd_lblNumeroDeExpediente);

		txtNoExpediente = formToolkit.createText(composite_1, "New Text", SWT.NONE);
		txtNoExpediente.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtNoExpediente.setText("");

		Button btnBuscar = formToolkit.createButton(compositeBuscar, "Buscar", SWT.NONE);
		btnBuscar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (txtNoExpediente.getText().equals("") && (comboPeriodo.getSelectionIndex() == -1 || comboCia.getSelectionIndex() == -1 || comboTasa.getSelectionIndex() == -1)) {
					MessageDialog.openInformation(getSite().getShell(), "Búsqueda de expediente", "Debe suministrar un número de expediente o un periodo válido para utilizar esta función.");
				} else {
					if (!txtNoExpediente.getText().equals("")) {
						String noExp = txtNoExpediente.getText().trim();
						viewer.setInput(controller.buscarExpediente(noExp));
						lblRegistros.setText(viewer.getTable().getItemCount() + " registros");
					} else {
						PeriodoAnualidad periodo = (PeriodoAnualidad) cdPeriodo.getObjectByIndex(comboPeriodo.getSelectionIndex());
						boolean flagTasa = comboTasa.getText().equals("Con tasa") ? true : false;
						viewer.setInput(controller.buscarExpedientes(periodo, comboCia.getText(), flagTasa));
						lblRegistros.setText(viewer.getTable().getItemCount() + " registros");
					}
				}
			}
		});

		Label lblFechaFactura = formToolkit.createLabel(compositeBuscar, "Fecha factura:", SWT.NONE);
		lblFechaFactura.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		Composite composite = formToolkit.createComposite(compositeBuscar, SWT.NONE);
		formToolkit.paintBordersFor(composite);
		composite.setLayout(new GridLayout(5, false));

		calendarComboFechaFact = new CalendarCombo(composite, SWT.NONE, new CalComboSettings(), null);
		formToolkit.adapt(calendarComboFechaFact);
		formToolkit.paintBordersFor(calendarComboFechaFact);

		Label lblIdioma = formToolkit.createLabel(composite, "Idioma:", SWT.NONE);
		GridData gd_lblIdioma = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblIdioma.horizontalIndent = 52;
		lblIdioma.setLayoutData(gd_lblIdioma);

		comboIdioma = new Combo(composite, SWT.READ_ONLY);
		comboIdioma.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(comboIdioma);
		formToolkit.paintBordersFor(comboIdioma);

		Label lblNotificacion = formToolkit.createLabel(composite, "Notificaci\u00F3n:", SWT.NONE);
		GridData gd_lblNotificacion = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblNotificacion.horizontalIndent = 50;
		lblNotificacion.setLayoutData(gd_lblNotificacion);

		comboNotificacion = new Combo(composite, SWT.READ_ONLY);
		comboNotificacion.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(comboNotificacion);
		formToolkit.paintBordersFor(comboNotificacion);
		new Label(compositeBuscar, SWT.NONE);

		Composite compositeBotones = formToolkit.createComposite(scrldfrmMain.getBody(), SWT.NONE);
		compositeBotones.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.paintBordersFor(compositeBotones);
		GridLayout gl_compositeBotones = new GridLayout(5, false);
		gl_compositeBotones.marginHeight = 0;
		compositeBotones.setLayout(gl_compositeBotones);

		lblRegistros = formToolkit.createLabel(compositeBotones, "Registros", SWT.NONE);
		lblRegistros.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ImageHyperlink mghprlnkInforme = formToolkit.createImageHyperlink(compositeBotones, SWT.NONE);
		mghprlnkInforme.setUnderlined(false);
		mghprlnkInforme.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/excel_16.png"));
		mghprlnkInforme.setBounds(0, 0, 102, 17);
		formToolkit.paintBordersFor(mghprlnkInforme);
		mghprlnkInforme.setText("Listado");
		mghprlnkInforme.addHyperlinkListener(new IHyperlinkListener() {
			public void linkActivated(HyperlinkEvent e) {
				exportarListadoAnualidadesAExcel();
			}
			public void linkEntered(HyperlinkEvent e) {
			}
			public void linkExited(HyperlinkEvent e) {
			}
		});

		ImageHyperlink mghprlnkProforma = formToolkit.createImageHyperlink(compositeBotones, SWT.NONE);
		mghprlnkProforma.setUnderlined(false);
		mghprlnkProforma.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/pdf_16.png"));
		mghprlnkProforma.setBounds(0, 0, 102, 17);
		formToolkit.paintBordersFor(mghprlnkProforma);
		mghprlnkProforma.setText("Proforma");
		mghprlnkProforma.addHyperlinkListener(new IHyperlinkListener() {
			public void linkActivated(HyperlinkEvent e) {
				Expediente expediente = getElementoSeleccionado(viewer);
				if (validarProformaAnualidad(expediente)) {
					generarProformaAnualidad(expediente);
				}
			}
			public void linkEntered(HyperlinkEvent e) {
			}
			public void linkExited(HyperlinkEvent e) {
			}
		});

		ImageHyperlink mghprlnkFactura = formToolkit.createImageHyperlink(compositeBotones, SWT.NONE);
		mghprlnkFactura.setUnderlined(false);
		mghprlnkFactura.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/excel_16.png"));
		mghprlnkFactura.setBounds(0, 0, 102, 17);
		formToolkit.paintBordersFor(mghprlnkFactura);
		mghprlnkFactura.setText("Factura");
		mghprlnkFactura.addHyperlinkListener(new IHyperlinkListener() {
			public void linkActivated(HyperlinkEvent e) {
				Expediente expediente = getElementoSeleccionado(viewer);
				if (validarFacturaAnualidad(expediente)) {
					Factura factura = generarFacturaAnualidad(expediente);
					abrirFactura(new Browser(getSite().getShell(), SWT.None), factura);
				}
			}
			public void linkEntered(HyperlinkEvent e) {
			}
			public void linkExited(HyperlinkEvent e) {
			}
		});

		ImageHyperlink mghprlnkActualizar = formToolkit.createImageHyperlink(compositeBotones, SWT.NONE);
		mghprlnkActualizar.setUnderlined(false);
		mghprlnkActualizar.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/refresh_16.png"));
		mghprlnkActualizar.setBounds(0, 0, 102, 17);
		formToolkit.paintBordersFor(mghprlnkActualizar);
		mghprlnkActualizar.setText("Actualizar");
		mghprlnkActualizar.addHyperlinkListener(new IHyperlinkListener() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				viewer.setInput(controller.buscarExpedientesConAnualidad());
				lblRegistros.setText(viewer.getTable().getItemCount() + " registros");
			}

			@Override
			public void linkEntered(HyperlinkEvent e) {		}

			@Override
			public void linkExited(HyperlinkEvent e) {		}					
		});

		viewer = new TableViewer(scrldfrmMain.getBody(), SWT.BORDER | SWT.FULL_SELECTION);
		table = viewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		formToolkit.paintBordersFor(table);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnNoExp = tableViewerColumn.getColumn();
		tblclmnNoExp.setAlignment(SWT.CENTER);
		tblclmnNoExp.setWidth(80);
		tblclmnNoExp.setText("No Exp");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnNombre = tableViewerColumn_1.getColumn();
		tblclmnNombre.setWidth(250);
		tblclmnNombre.setText("Nombre del expediente");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnInscripcion = tableViewerColumn_2.getColumn();
		tblclmnInscripcion.setAlignment(SWT.CENTER);
		tblclmnInscripcion.setWidth(90);
		tblclmnInscripcion.setText("Inscripci\u00F3n");

		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnTasa = tableViewerColumn_5.getColumn();
		tblclmnTasa.setAlignment(SWT.RIGHT);
		tblclmnTasa.setWidth(70);
		tblclmnTasa.setText("Tasa");

		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnAnualidad = tableViewerColumn_6.getColumn();
		tblclmnAnualidad.setAlignment(SWT.RIGHT);
		tblclmnAnualidad.setWidth(70);
		tblclmnAnualidad.setText("Anualidad");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnCreado = tableViewerColumn_3.getColumn();
		tblclmnCreado.setAlignment(SWT.CENTER);
		tblclmnCreado.setWidth(90);
		tblclmnCreado.setText("Creado");

		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnEstado = tableViewerColumn_4.getColumn();
		tblclmnEstado.setWidth(75);
		tblclmnEstado.setText("Estado");

		llenarControles();
	}


	private boolean validarProformaAnualidad(Expediente expediente) {
		if (expediente == null) {
			MessageDialog.openInformation(getSite().getShell(), "Generar proforma (anualidad)", "Debe seleccionar un expediente para ejecutar esta acción.");
			return false;
		}
		if (comboPeriodo.getSelectionIndex() == -1) {
			MessageDialog.openInformation(getSite().getShell(), "Generar proforma (anualidad)", "Seleccione el periodo que corresponde al expediente " 
					+ expediente.getNoExpediente() + ".");
			return false;
		}
		if (comboNotificacion.getSelectionIndex() != -1 && comboNotificacion.getSelectionIndex() != 0) {
			String noCia = expediente.getNoCia();
			PeriodoAnualidad periodo = (PeriodoAnualidad) cdPeriodo.getObjectByIndex(comboPeriodo.getSelectionIndex());
			Integer noPeriodo = periodo.getIdPeriodo().intValue();
			Keyword tipoNotificacion = (Keyword) cdNotificacion.getObjectByIndex(comboNotificacion.getSelectionIndex());
			if (!notificacionesController.existeNotificacion(noCia, noPeriodo, tipoNotificacion)) {
				return MessageDialog.openConfirm(getSite().getShell(), "Generar proforma (anualidad)", "No se ha definido una notificación de tipo '" + 
						tipoNotificacion.getDescripcion() +	"' para el expediente " + expediente.getNoExpediente() + 
						" (Compañía: " + noCia + ", periodo: " + periodo.getNombre() + ").\n" +
						"Desea continuar la generación de la proforma sin esta notificación?");
			}
		}
		return true;
	}
	
	
	private boolean validarFacturaAnualidad(Expediente expediente) {
		if (expediente == null) {
			MessageDialog.openInformation(getSite().getShell(), "Generar factura (anualidad)", "Debe seleccionar un expediente para ejecutar esta acción.");
			return false;
		}
		if (comboPeriodo.getSelectionIndex() == -1) {
			MessageDialog.openInformation(getSite().getShell(), "Generar factura (anualidad)", "Seleccione el periodo que corresponde al expediente " 
					+ expediente.getNoExpediente() + ".");
			return false;
		}
		if (comboNotificacion.getSelectionIndex() != -1 && comboNotificacion.getSelectionIndex() != 0) {
			String noCia = expediente.getNoCia();
			PeriodoAnualidad periodo = (PeriodoAnualidad) cdPeriodo.getObjectByIndex(comboPeriodo.getSelectionIndex());
			Integer noPeriodo = periodo.getIdPeriodo().intValue();
			Keyword tipoNotificacion = (Keyword) cdNotificacion.getObjectByIndex(comboNotificacion.getSelectionIndex());
			if (!notificacionesController.existeNotificacion(noCia, noPeriodo, tipoNotificacion)) {
				return MessageDialog.openConfirm(getSite().getShell(), "Generar factura (anualidad)", "No se ha definido una notificación de tipo '" + 
						tipoNotificacion.getDescripcion() +	"' para el expediente " + expediente.getNoExpediente() + 
						" (Compañía: " + noCia + ", periodo: " + periodo.getNombre() + ").\n" +
						"Desea continuar la generación de la factura sin esta notificación?");
			}
		}
		return true;
	}


	private void generarProformaAnualidad(Expediente expediente) {

		//Float impuesto = casosController.calcularImpuestos(caso);
		ProformasEditorInput input = new ProformasEditorInput(TipoProforma.ANUALIDAD.getTitulo());
		input.setTipoProforma(TipoProforma.ANUALIDAD.getNombre());

		Integer idExpediente = Integer.valueOf(expediente.getIdExpediente().toString());
		Float impuesto = 0F;
		//impuesto = expediente.getCargoExpediente().getRetainer().getTotalRetainerConImpuesto(0.07f);
		impuesto = controller.calcularImpuestoCargosAnuales(expediente, 0.07F);
		String idioma = "1";
		if (comboIdioma.getSelectionIndex() != -1) {
			LOGGER.info("Idioma x formulario: " + comboIdioma.getText());
			idioma = cdIdioma.getKeyByIndex(comboIdioma.getSelectionIndex());
		} else {
			idioma = expediente.getCargoExpediente().getResponsable().getIdioma();
			LOGGER.info("Idioma x responsable: " + idioma);
		}
		PeriodoAnualidad periodo = (PeriodoAnualidad) cdPeriodo.getObjectByIndex(comboPeriodo.getSelectionIndex());
		String concepto = periodo.getConceptoFactura(Integer.valueOf(comboYY.getText()), idioma);
		Long noPeriodo = periodo.getIdPeriodo();
		
		cdNotificacion.getObjectByIndex(comboNotificacion.getSelectionIndex());
		LOGGER.info("TTT> " + cdNotificacion.getKeyByIndex(comboNotificacion.getSelectionIndex()));
		String tipoNotificacion = cdNotificacion.getKeyByIndex(comboNotificacion.getSelectionIndex());

		input.configurarParametrosAnualidad(idExpediente, impuesto, idioma, concepto, noPeriodo, tipoNotificacion);

		try {
			getSite().getPage().openEditor(input, ProformasEditor.ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}

	}

	
	private void exportarListadoAnualidadesAExcel() {
		abrirReporteExcel(new Browser(getSite().getShell(), SWT.None), (List<Expediente>) viewer.getInput());
	}

	private Factura generarFacturaAnualidad(Expediente expediente) {
		String idiomaCodigo = "1";  // idioma español
		Idioma idiomaEnum = null;
		if (comboIdioma.getSelectionIndex() != -1) {
			LOGGER.info("Idioma seleccionado x formulario: " + comboIdioma.getText());
			idiomaCodigo = cdIdioma.getKeyByIndex(comboIdioma.getSelectionIndex());
			idiomaEnum = Idioma.fromCodigo(idiomaCodigo);
		} else {
			// se prepara la factura con el idioma indicado en los datos del responsable
			idiomaCodigo = expediente.getCargoExpediente().getResponsable().getIdioma();
			idiomaEnum = Idioma.fromCodigo(idiomaCodigo);
			LOGGER.info("Idioma seleccionado x responsable: " + idiomaCodigo);
		}
		Integer periodoYY = Integer.valueOf(comboYY.getText());
		PeriodoAnualidad periodo = (PeriodoAnualidad) cdPeriodo.getObjectByIndex(comboPeriodo.getSelectionIndex());
		String concepto = periodo.getConceptoFactura(periodoYY, idiomaCodigo);
		LOGGER.info("No de factura: " + facturasController.getSiguienteNoFactura());
		String numeroFactura = facturasController.getSiguienteNoFactura().toString();
		LOGGER.info("Fecha de factura: " + calendarComboFechaFact.getDate().getTime());
		Date fechaFactura = calendarComboFechaFact.getDate().getTime();
		
		Long noPeriodo = periodo.getIdPeriodo();
		
		cdNotificacion.getObjectByIndex(comboNotificacion.getSelectionIndex());
		String tipoNotificacion = cdNotificacion.getKeyByIndex(comboNotificacion.getSelectionIndex());
		
		LOGGER.info("PERIODO: " + noPeriodo + ", TIPO_NOTIF: " + tipoNotificacion);
		Keyword keywordNotificacion = (Keyword) cdNotificacion.getObjectByIndex(comboNotificacion.getSelectionIndex());
		LOGGER.info("K: " + keywordNotificacion.getCodigo());
		
		Factura factura = facturasController.generarFacturaAnualidad(expediente, fechaFactura, numeroFactura, 
				idiomaEnum, concepto, periodoYY, Integer.valueOf(noPeriodo.toString()), keywordNotificacion);
		facturasController.doSave(factura);
		LOGGER.info("EXPEDIENTE: " + factura.getNombreExpediente());
		return factura;
	}


	private void abrirFactura(Browser browser, Factura factura) {
		VerFacturasController gfc = new VerFacturasController();
		gfc.agregarParametro("idFactura", Integer.valueOf(factura.getIdFactura().toString()));
		gfc.agregarParametro("idioma", factura.getIdioma());
		gfc.generarFacturaExcel(browser, TipoFactura.ANUALIDAD);
	}
	
	
	private void abrirReporteExcel(Browser browser, List<Expediente> listadoExpedientes) {
		VerFacturasController gfc = new VerFacturasController();
		gfc.agregarParametro("listadoExpedientes", listadoExpedientes);
		gfc.generarDocumentoExcel(browser, "/reports/listadoAnualidades.rptdesign");
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
		controller.finalizarSesion();
		super.dispose();
	}


	@Override
	protected void llenarControles() {
		// inicializamos los valores de los keywords
		comboPeriodo.setItems(cdPeriodo.getTexto());
		comboYY.setItems(new String[] {"" + Calendar.getInstance().get(Calendar.YEAR), "" + (Calendar.getInstance().get(Calendar.YEAR) + 1)});
		comboCia.setItems(IBaseKeywords.COMPANIAS);
		comboTasa.setItems(IBaseKeywords.MODO_TASA);
		comboIdioma.setItems(cdIdioma.getTexto());
		// suministramos los valores default
		comboYY.setText("" + Calendar.getInstance().get(Calendar.YEAR));
		comboTasa.select(0);
		calendarComboFechaFact.setDate(new Date());
		comboNotificacion.setItems(cdNotificacion.getTexto());
		comboNotificacion.select(0);
		// e inicializamos el viewer
		inicializarViewer();
	}


	protected void inicializarViewer() {
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(controller.buscarExpedientesConAnualidad());
		//viewer.setInput(controller.getListado());
		// registramos el tableViewer como un selection provider
		// esto es requerido para obtener el elemento seleccionado en las acciones
		getSite().setSelectionProvider(viewer);
		// habilitamos el listener para doble clic
		//this.hookDoubleClickListener(viewer, DireccionesEditor.ID);
		this.agregarSingleClickListener(viewer);
		lblRegistros.setText(viewer.getTable().getItemCount() + " registros");
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
				resultado = FechaUtil.toString(k.getFechaInscripcion());
				break;
			case 3:
				resultado = valor2Txt(k.getCargoExpediente().getCargosAnuales().getTasa(), "#,##0.00");
				break;
			case 4:
				resultado = valor2Txt(k.getCargoExpediente().getTotalCargosAnuales(), "#,##0.00");
				break;
			case 5:
				resultado = FechaUtil.toString(k.getFechaCreado());
				break;
			case 6:
				resultado = k.getDspEstado();
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


	private void agregarSingleClickListener(final StructuredViewer viewer) {
		Control control = viewer.getControl();
		TypedListener listener = new TypedListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				Expediente expediente = getElementoSeleccionado(viewer);
				LOGGER.info("E: " + expediente.getNombre());
			}
		});
		control.addListener(SWT.Selection, listener);
	}
}
