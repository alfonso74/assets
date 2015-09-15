package rcp.assets.editors;

import java.awt.Container;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.nebula.widgets.calendarcombo.CalendarCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import rcp.assets.controllers.CasosController;
import rcp.assets.controllers.FacturasController;
import rcp.assets.controllers.VerFacturasController;
import rcp.assets.model.Caso;
import rcp.assets.model.Factura;
import rcp.assets.model.FacturaCaso;
import rcp.assets.model.LineaFactura;
import rcp.assets.model.TipoFactura;
import rcp.assets.services.CalComboSettings;
import rcp.assets.services.ComboData;
import rcp.assets.services.ComboDataManager;
import rcp.assets.services.FechaUtil;
import rcp.assets.services.IBaseKeywords;

import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.wb.swt.ResourceManager;

public class XXXFacturarCasosEditor extends AbstractEditor {

	public static final String ID = "rcp.assets.editors.XXXFacturarCasosEditor"; //$NON-NLS-1$
	private String idSession = ID + FechaUtil.getMilisegundos();
	private final FormToolkit toolkit = new FormToolkit(Display.getDefault());
	private CasosController casosController;
	private FacturasController facturasController;

	private TableViewer viewer;
	private Table table;

	private Text txtNoCaso;
	private CalendarCombo calendarComboFacturacion;
	private Combo comboIdioma;

	private ComboData cdIdioma;


	public XXXFacturarCasosEditor() {
		casosController = new CasosController();
		facturasController = new FacturasController();
		cdIdioma = ComboDataManager.getInstance().getComboData(IBaseKeywords.TipoKeyword.IDIOMA.getDescripcion());
	}

	/**
	 * Create contents of the editor part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		ScrolledForm scrldfrmMain = toolkit.createScrolledForm(parent);
		toolkit.paintBordersFor(scrldfrmMain);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginHeight = 10;
		gridLayout.marginWidth = 10;
		scrldfrmMain.getBody().setLayout(gridLayout);
		{
			Section sctnBusquedaDeCaso = toolkit.createSection(scrldfrmMain.getBody(), Section.TWISTIE | Section.TITLE_BAR);
			sctnBusquedaDeCaso.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			toolkit.paintBordersFor(sctnBusquedaDeCaso);
			sctnBusquedaDeCaso.setText("Busqueda de caso(s) para facturacion");
			sctnBusquedaDeCaso.setExpanded(true);
			{
				Composite composite = toolkit.createComposite(sctnBusquedaDeCaso, SWT.NONE);
				toolkit.paintBordersFor(composite);
				sctnBusquedaDeCaso.setClient(composite);
				composite.setLayout(new GridLayout(3, false));
				{
					Label lblCaso = toolkit.createLabel(composite, "N\u00FAmero de caso:", SWT.NONE);
					lblCaso.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				}
				{
					txtNoCaso = toolkit.createText(composite, "New Text", SWT.NONE);
					txtNoCaso.setText("");
					txtNoCaso.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				}
				Button btnBuscar = toolkit.createButton(composite, "Buscar", SWT.NONE);
				btnBuscar.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				btnBuscar.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						System.out.println("Buscando casos...");
						if (!txtNoCaso.getText().equals("")) {
							Long noCaso = Long.valueOf(txtNoCaso.getText());								
							viewer.setInput(casosController.buscarCaso(noCaso));
						} else if (!calendarComboFacturacion.getDateAsString().equals("")) {
							Date fecha = calendarComboFacturacion.getDate().getTime();
							viewer.setInput(casosController.buscarCasosPorFecha(fecha));
						} else {
							MessageDialog.openInformation(getSite().getShell(), "Búsqueda de casos", "Debe suministrar un número de caso para utilizar esta función.");
						}
					}
				});
				{
					Label lblFecha = toolkit.createLabel(composite, "Fecha de factura:", SWT.NONE);
					lblFecha.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				}
				{
					Composite composite_1 = new Composite(composite, SWT.NONE);
					composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
					toolkit.adapt(composite_1);
					toolkit.paintBordersFor(composite_1);
					GridLayout gl_composite_1 = new GridLayout(3, false);
					gl_composite_1.marginWidth = 0;
					composite_1.setLayout(gl_composite_1);
					{
						calendarComboFacturacion = new CalendarCombo(composite_1, SWT.NONE, new CalComboSettings(), null);
						toolkit.adapt(calendarComboFacturacion);
						toolkit.paintBordersFor(calendarComboFacturacion);
					}
					{
						Label lblIdioma = toolkit.createLabel(composite_1, "Idioma:", SWT.NONE);
						GridData gd_lblIdioma = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
						gd_lblIdioma.horizontalIndent = 35;
						lblIdioma.setLayoutData(gd_lblIdioma);
					}
					{
						comboIdioma = new Combo(composite_1, SWT.NONE);
						comboIdioma.setItems(cdIdioma.getTexto());
						toolkit.adapt(comboIdioma);
						toolkit.paintBordersFor(comboIdioma);
					}
				}
				new Label(composite, SWT.NONE);
			}
		}
		{
			final Composite composite = new Composite(scrldfrmMain.getBody(), SWT.NONE);
			composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			toolkit.adapt(composite);
			toolkit.paintBordersFor(composite);
			composite.setLayout(new GridLayout(5, false));

			Button btnProforma = new Button(composite, SWT.NONE);
			btnProforma.setBounds(0, 0, 68, 23);
			toolkit.adapt(btnProforma, true, true);
			btnProforma.setText("Proforma");
			btnProforma.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Caso caso = getElementoSeleccionado(viewer);
					if (caso != null) {							
						generarProformaCaso(caso);
					} else {
						MessageDialog.openInformation(getSite().getShell(), "Generar proforma", "Debe seleccionar un caso para ejecutar esta acción.");
					}
				}
			});

			Button btnFactura = new Button(composite, SWT.NONE);
			btnFactura.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Caso caso = getElementoSeleccionado(viewer);
					if (caso != null) {
						boolean flagGenerarFactura = false; 
						if (facturasController.existeFactura(caso)) {
							/*
							boolean flagAbrirFactura = MessageDialog.openQuestion(getSite().getShell(), "Generar factura", "Ya existe una factura para el caso seleccionado.  Desea abrir\n" +
									"la factura existente?");
							if (flagAbrirFactura) {
								LOGGER.info("ABRIR FACTURA");
								Factura factura13 = facturasController.getFacturaByCaso(caso);
								abrirFactura(new Browser(composite, SWT.NONE), factura13);
							} else {
								flagGenerarFactura = true;
							}
							 */
							/*
							flagGenerarFactura = MessageDialog.openQuestion(getSite().getShell(), "Generar factura", "Ya existe una factura para el caso seleccionado.  Desea crear otra factura\n" +
									"para el mismo caso?");
							 */
							Factura facturaExistente = facturasController.getFacturaByCaso(caso);
							boolean flagAbrirFactura = MessageDialog.openQuestion(getSite().getShell(), "Generar factura", "Ya existe una factura para el caso seleccionado.  Para generar una nueva\n" +
									"factura debe anular la factura existente (número " + facturaExistente.getNoFactura() + ").  Desea abrir la\n" +
									"factura existente?");
							LOGGER.info("ABRIR FACTURA: " + flagAbrirFactura); 
							if (flagAbrirFactura) {
								abrirFactura(new Browser(composite, SWT.NONE), facturaExistente);
							}
						} else {
							flagGenerarFactura = MessageDialog.openConfirm(getSite().getShell(), "Generar factura", "Desea generar la factura del caso " + caso.getNoCaso() + "?");
						}
						if (flagGenerarFactura) {
							Factura factura = generarNuevaFactura(caso);
							if (!caso.getEstado().equals("FA")) {
								caso.setEstado("FA");
								casosController.doSave(caso);
							}
							abrirFactura(new Browser(getSite().getShell(), SWT.NONE), factura);
						}
					} else {
						MessageDialog.openInformation(getSite().getShell(), "Generar factura", "Debe seleccionar un caso para ejecutar esta acción.");
					}
				}
			});
			toolkit.adapt(btnFactura, true, true);
			btnFactura.setText("Factura");
			{
				ImageHyperlink mghprlnkProforma = toolkit.createImageHyperlink(composite, SWT.NONE);
				mghprlnkProforma.setUnderlined(false);
				mghprlnkProforma.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/pdf_16.png"));
				toolkit.paintBordersFor(mghprlnkProforma);
				mghprlnkProforma.setText("Proforma");
				mghprlnkProforma.addHyperlinkListener(new IHyperlinkListener() {
					@Override
					public void linkActivated(HyperlinkEvent e) {
						Caso caso = getElementoSeleccionado(viewer);
						if (caso != null) {							
							generarProformaCaso(caso);
						} else {
							MessageDialog.openInformation(getSite().getShell(), "Generar proforma", "Debe seleccionar un caso para ejecutar esta acción.");
						}
					}

					@Override
					public void linkEntered(HyperlinkEvent e) {		}

					@Override
					public void linkExited(HyperlinkEvent e) {		}					
				});
			}
			{
				ImageHyperlink mghprlnkFactura = toolkit.createImageHyperlink(composite, SWT.NONE);
				mghprlnkFactura.setUnderlined(false);
				mghprlnkFactura.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/excel_16.png"));
				toolkit.paintBordersFor(mghprlnkFactura);
				mghprlnkFactura.setText("Factura");
				mghprlnkFactura.addHyperlinkListener(new IHyperlinkListener() {
					@Override
					public void linkActivated(HyperlinkEvent e) {
						Caso caso = getElementoSeleccionado(viewer);
						if (caso != null) {
							boolean flagGenerarFactura = false; 
							if (facturasController.existeFactura(caso)) {
								Factura facturaExistente = facturasController.getFacturaByCaso(caso);
								boolean flagAbrirFactura = MessageDialog.openQuestion(getSite().getShell(), "Generar factura", "Ya existe una factura para el caso seleccionado.  Para generar una nueva\n" +
										"factura debe anular la factura existente (número " + facturaExistente.getNoFactura() + ").  Desea abrir la\n" +
										"factura existente?");
								LOGGER.info("ABRIR FACTURA: " + flagAbrirFactura); 
								if (flagAbrirFactura) {
									abrirFactura(new Browser(composite, SWT.NONE), facturaExistente);
								}
							} else {
								flagGenerarFactura = MessageDialog.openConfirm(getSite().getShell(), "Generar factura", "Desea generar la factura del caso " + caso.getNoCaso() + "?");
								if (flagGenerarFactura) {
									Factura factura = generarNuevaFactura(caso);
									if (!caso.getEstado().equals("FA")) {
										caso.setEstado("FA");
										casosController.doSave(caso);
									}
									abrirFactura(new Browser(getSite().getShell(), SWT.NONE), factura);
								}
							}
						} else {
							MessageDialog.openInformation(getSite().getShell(), "Generar factura", "Debe seleccionar un caso para ejecutar esta acción.");
						}
					}

					@Override
					public void linkEntered(HyperlinkEvent e) {		}

					@Override
					public void linkExited(HyperlinkEvent e) {		}					
				});
			}
			{
				ImageHyperlink mghprlnkActualizar = toolkit.createImageHyperlink(composite, SWT.NONE);
				mghprlnkActualizar.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/refresh_16.png"));
				mghprlnkActualizar.setUnderlined(false);
				toolkit.paintBordersFor(mghprlnkActualizar);
				mghprlnkActualizar.setText("Actualizar");
				mghprlnkActualizar.addHyperlinkListener(new IHyperlinkListener() {
					@Override
					public void linkActivated(HyperlinkEvent e) {
						viewer.refresh();
					}

					@Override
					public void linkEntered(HyperlinkEvent e) {		}

					@Override
					public void linkExited(HyperlinkEvent e) {		}					
				});
			}
		}
		{
			viewer = new TableViewer(scrldfrmMain.getBody(), SWT.BORDER | SWT.FULL_SELECTION);
			table = viewer.getTable();
			table.setLinesVisible(true);
			table.setHeaderVisible(true);
			table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			toolkit.paintBordersFor(table);
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnCia = tableViewerColumn.getColumn();
				tblclmnCia.setWidth(35);
				tblclmnCia.setText("Cia");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnNocaso = tableViewerColumn.getColumn();
				tblclmnNocaso.setWidth(60);
				tblclmnNocaso.setText("NoCaso");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnExpediente = tableViewerColumn.getColumn();
				tblclmnExpediente.setWidth(80);
				tblclmnExpediente.setText("Expediente");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnNombreExpediente = tableViewerColumn.getColumn();
				tblclmnNombreExpediente.setWidth(200);
				tblclmnNombreExpediente.setText("Nombre expediente");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnAsunto = tableViewerColumn.getColumn();
				tblclmnAsunto.setWidth(200);
				tblclmnAsunto.setText("Asunto");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnCreado = tableViewerColumn.getColumn();
				tblclmnCreado.setWidth(75);
				tblclmnCreado.setText("Creado");
			}
		}

		llenarControles();

	}


	private void generarProformaCaso(Caso caso) {
		try {							
			ProformasEditorInput input = new ProformasEditorInput("Caso PDF");
			input.setTipoProforma("caso");
			// preparamos los parametros para una proforma de caso...
			Integer idCaso = Integer.valueOf(caso.getIdCaso().toString());
			Float impuesto = casosController.calcularImpuestos(caso, 7F);
			String idioma = "1";
			if (comboIdioma.getSelectionIndex() != -1) {
				LOGGER.info("Idioma x formulario: " + comboIdioma.getText());
				idioma = cdIdioma.getKeyByIndex(comboIdioma.getSelectionIndex());
			} else {
				LOGGER.info("Idioma x responsable: " + caso.getResponsable().getIdioma());
				idioma = caso.getResponsable().getIdioma();
			}
			// y llamamos al editor para generar la proforma
			input.configurarParametrosCaso(idCaso, impuesto, idioma);
			getSite().getPage().openEditor(input, ProformasEditor.ID);
		} catch (PartInitException e1) {
			e1.printStackTrace();
		}
	}


	/**
	 * Genera una nueva factura en base al Caso suministrado.  Se debe agregar el idioma, el número de
	 * factura, y la fecha de la factura.
	 * @param caso Caso con la información necesaria para generar la factura
	 */
	private Factura generarNuevaFactura(Caso caso) {
		String idiomaFactura = "1";  // idioma español
		if (comboIdioma.getSelectionIndex() != -1) {
			LOGGER.info("Idioma seleccionado x formulario: " + comboIdioma.getText());
			idiomaFactura = cdIdioma.getKeyByIndex(comboIdioma.getSelectionIndex());
		} else {
			// se prepara la factura con el idioma indicado en los datos del responsable
			idiomaFactura = caso.getResponsable().getIdioma();
			LOGGER.info("Idioma seleccionado x responsable: " + idiomaFactura);
		}
		LOGGER.info("No de factura: " + facturasController.getSiguienteNoFactura());
		String numeroFactura = facturasController.getSiguienteNoFactura().toString();
		LOGGER.info("Fecha de factura: " + calendarComboFacturacion.getDate().getTime());
		Date fechaFactura = calendarComboFacturacion.getDate().getTime();

		FacturaCaso factura = casosController.generarFactura(caso, fechaFactura, numeroFactura, idiomaFactura, 7F);
		LOGGER.info("Imprimiento lineas de detalle: ");
		for(LineaFactura df : factura.getListaDetalles()) {
			LOGGER.info(df);
		}
		facturasController.doSave(factura);
		return factura;
	}


	private void abrirFactura(Browser browser, Factura factura) {
		VerFacturasController gfc = new VerFacturasController();
		gfc.agregarParametro("idFactura", Integer.valueOf(factura.getIdFactura().toString()));
		gfc.agregarParametro("idioma", factura.getIdioma());
		//gfc.agregarParametro("impuesto", factura.getImpuesto());
		gfc.generarFacturaExcel(browser, TipoFactura.CASO);
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
	public boolean isSaveAsAllowed() {
		return false;
	}

	public void dispose() {
		toolkit.dispose();
		super.dispose();
	}

	@Override
	protected void llenarControles() {
		inicializarViewer();
		calendarComboFacturacion.setDate(new Date());
	}

	protected void inicializarViewer() {
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(casosController.buscarCasosPorFacturar());
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
			Collection<Caso> n = (Collection<Caso>) parent; 
			Object[] resultados = n.toArray(new Caso[n.size()]);
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
			Caso k = (Caso) element;
			switch (columnIndex) {
			case 0:
				resultado = k.getNoCia();
				break;
			case 1:
				resultado = Long.toString(k.getNoCaso());
				break;
			case 2:
				resultado = k.getExpediente().getNoExpediente();
				break;
			case 3:
				resultado = k.getExpediente().getNombre();
				break;
			case 4:
				resultado = k.getNaturaleza();
				break;
			case 5:
				resultado = FechaUtil.toString(k.getFechaCreado());
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
