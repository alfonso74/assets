package rcp.assets.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import rcp.assets.controllers.FacturasController;
import rcp.assets.controllers.PeachTreeController;
import rcp.assets.model.Factura;
import rcp.assets.services.FechaUtil;

import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.wb.swt.ResourceManager;


public class ExportarPeachtreeEditor extends AbstractEditor {

	public static final String ID = "rcp.assets.editors.ExportarPeachtreeEditor"; //$NON-NLS-1$
	private String idSession = ID + FechaUtil.getMilisegundos();

	private FacturasController controller;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	private String[] txtComboEnviados = {"Facturas pendientes", "Facturas exportadas"};

	private TableViewer viewer;
	private Table table;
	private HashMap<String, Factura> itemsChecked;

	private Text txtRutaArchivo;
	private Button button;
	private Text txtPeriodoPeachtree;
	private Combo comboFormatoFecha;
	
	private Button btnPendientes;
	private Button btnExportadas;
	private Label lblRegistros;


	public ExportarPeachtreeEditor() {
		controller = new FacturasController(idSession);
		itemsChecked = new HashMap<String, Factura>();
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

		Section sctnParametros = formToolkit.createSection(scrldfrmMain.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnParametros.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		sctnParametros.setBounds(0, 0, 95, 19);
		formToolkit.paintBordersFor(sctnParametros);
		sctnParametros.setText("Par\u00E1metros para generar el archivo");
		sctnParametros.setExpanded(true);

		Composite compositeParametros = formToolkit.createComposite(sctnParametros, SWT.NONE);
		formToolkit.paintBordersFor(compositeParametros);
		sctnParametros.setClient(compositeParametros);
		GridLayout gl_compositeParametros = new GridLayout(5, false);
		gl_compositeParametros.marginBottom = 5;
		gl_compositeParametros.verticalSpacing = 10;
		compositeParametros.setLayout(gl_compositeParametros);

		Label lblRutaDelArchivo = formToolkit.createLabel(compositeParametros, "Ruta del archivo:", SWT.NONE);
		lblRutaDelArchivo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtRutaArchivo = formToolkit.createText(compositeParametros, "New Text", SWT.NONE);
		txtRutaArchivo.setText("");
		txtRutaArchivo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		button = formToolkit.createButton(compositeParametros, "...", SWT.NONE);
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(getSite().getShell(), SWT.SAVE);
				fd.setText("Archivo para exportar a Peachtree");
				fd.setFilterPath("C:/");
				fd.setFileName(txtRutaArchivo.getText());
				String[] filterExt = { "*.csv" };
				fd.setFilterExtensions(filterExt);
				fd.setFilterIndex(0);
				String selected = fd.open();
				if (selected != null) {
					System.out.println(selected);
					txtRutaArchivo.setText(selected);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		Label lblPeriodoPeachtree = formToolkit.createLabel(compositeParametros, "Periodo de Peachtree:", SWT.NONE);
		lblPeriodoPeachtree.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtPeriodoPeachtree = formToolkit.createText(compositeParametros, "New Text", SWT.NONE);
		txtPeriodoPeachtree.setText("");
		txtPeriodoPeachtree.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		Label lblFormatoDeFecha = formToolkit.createLabel(compositeParametros, "Formato de fecha:", SWT.NONE);
		GridData gd_lblFormatoDeFecha = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblFormatoDeFecha.horizontalIndent = 40;
		lblFormatoDeFecha.setLayoutData(gd_lblFormatoDeFecha);

		comboFormatoFecha = new Combo(compositeParametros, SWT.READ_ONLY);
		comboFormatoFecha.setItems(new String[] {"MM/dd/yyyy", "dd/MM/yyyy"});
		formToolkit.adapt(comboFormatoFecha);
		formToolkit.paintBordersFor(comboFormatoFecha);
		new Label(compositeParametros, SWT.NONE);

		Composite compositeBotones = new Composite(scrldfrmMain.getBody(), SWT.NONE);
		compositeBotones.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_compositeBotones = new GridLayout(5, false);
		gl_compositeBotones.marginWidth = 10;
		gl_compositeBotones.marginHeight = 0;
		compositeBotones.setLayout(gl_compositeBotones);
		compositeBotones.setBounds(0, 0, 64, 64);
		formToolkit.adapt(compositeBotones);
		formToolkit.paintBordersFor(compositeBotones);
		
		btnPendientes = new Button(compositeBotones, SWT.RADIO);
		formToolkit.adapt(btnPendientes, true, true);
		btnPendientes.setText("Ver pendientes");
		btnPendientes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//actualizarViewer();
			}
		});
		
		btnExportadas = new Button(compositeBotones, SWT.RADIO);
		formToolkit.adapt(btnExportadas, true, true);
		btnExportadas.setText("Ver exportadas");
		btnExportadas.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				actualizarViewer();
			}
		});

		lblRegistros = formToolkit.createLabel(compositeBotones, "(1,000 registros)", SWT.NONE);
		lblRegistros.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));

		ImageHyperlink mghprlnkPeachtree = formToolkit.createImageHyperlink(compositeBotones, SWT.NONE);
		mghprlnkPeachtree.setToolTipText("Con las facturas seleccionadas se genera un archivo .csv que puede ser importado al Peachtree.");
		mghprlnkPeachtree.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/peachtree_16.png"));
		mghprlnkPeachtree.setUnderlined(false);
		formToolkit.paintBordersFor(mghprlnkPeachtree);
		mghprlnkPeachtree.setText("Exportar a Peachtree");
		mghprlnkPeachtree.addHyperlinkListener(new IHyperlinkListener() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				generarArchivoPeachtree();
			}

			@Override
			public void linkEntered(HyperlinkEvent e) {		}

			@Override
			public void linkExited(HyperlinkEvent e) {		}					
		});

		ImageHyperlink mghprlnkActualizar = formToolkit.createImageHyperlink(compositeBotones, SWT.NONE);
		mghprlnkActualizar.setToolTipText("Actualiza el contenido de la vista actual.");
		mghprlnkActualizar.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/refresh_16.png"));
		mghprlnkActualizar.setUnderlined(false);
		formToolkit.paintBordersFor(mghprlnkActualizar);
		mghprlnkActualizar.setText("Actualizar");
		mghprlnkActualizar.addHyperlinkListener(new IHyperlinkListener() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				actualizarViewer();
			}

			@Override
			public void linkEntered(HyperlinkEvent e) {		}

			@Override
			public void linkExited(HyperlinkEvent e) {		}					
		});

		viewer = new TableViewer(scrldfrmMain.getBody(), SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK);
		table = viewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setBounds(0, 0, 85, 85);
		formToolkit.paintBordersFor(table);

		TableViewerColumn tableViewerColumn_10 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnCk = tableViewerColumn_10.getColumn();
		tblclmnCk.setResizable(false);
		tblclmnCk.setWidth(25);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnFactura = tableViewerColumn.getColumn();
		tblclmnFactura.setAlignment(SWT.RIGHT);
		tblclmnFactura.setWidth(55);
		tblclmnFactura.setText("Factura");

		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnFisico = tableViewerColumn_7.getColumn();
		tblclmnFisico.setAlignment(SWT.RIGHT);
		tblclmnFisico.setWidth(55);
		tblclmnFisico.setText("Fisico");

		TableViewerColumn tableViewerColumn_8 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnTx = tableViewerColumn_8.getColumn();
		tblclmnTx.setAlignment(SWT.CENTER);
		tblclmnTx.setWidth(30);
		tblclmnTx.setText("Tx");

		TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnIdPeachtree = tableViewerColumn_9.getColumn();
		tblclmnIdPeachtree.setAlignment(SWT.CENTER);
		tblclmnIdPeachtree.setWidth(75);
		tblclmnIdPeachtree.setText("idPeachtree");


		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnTipo = tableViewerColumn_1.getColumn();
		tblclmnTipo.setAlignment(SWT.CENTER);
		tblclmnTipo.setWidth(35);
		tblclmnTipo.setText("Tipo");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnExpediente = tableViewerColumn_2.getColumn();
		tblclmnExpediente.setWidth(75);
		tblclmnExpediente.setText("Expediente");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnConcepto = tableViewerColumn_3.getColumn();
		tblclmnConcepto.setWidth(250);
		tblclmnConcepto.setText("Concepto");

		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnFecha = tableViewerColumn_4.getColumn();
		tblclmnFecha.setAlignment(SWT.CENTER);
		tblclmnFecha.setWidth(80);
		tblclmnFecha.setText("Fecha");

		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnMonto = tableViewerColumn_5.getColumn();
		tblclmnMonto.setAlignment(SWT.RIGHT);
		tblclmnMonto.setWidth(75);
		tblclmnMonto.setText("Monto");

		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnEstado = tableViewerColumn_6.getColumn();
		tblclmnEstado.setAlignment(SWT.CENTER);
		tblclmnEstado.setWidth(60);
		tblclmnEstado.setText("Estado");

		final TextCellEditor textCellEditor = new TextCellEditor(viewer.getTable());
		tableViewerColumn_7.setEditingSupport(new EditingSupport(viewer) {
			protected boolean canEdit(Object element) {
				return true;
			}
			protected CellEditor getCellEditor(Object element) {
				return textCellEditor;
			}
			protected Object getValue(Object element) {
				String noFisico = ((Factura) element).getNoFisico() == null ? "" : ((Factura) element).getNoFisico();
				return noFisico;
			}
			protected void setValue(Object element, Object value) {
				((Factura) element).setNoFisico(value.toString());
				viewer.update(element, null);
			}
		});


		llenarControles();
	}


	private void generarArchivoPeachtree() {

		//List<Factura> seleccion = getElementosSeleccionados(viewer);

		List<Factura> seleccion = new ArrayList<Factura>(itemsChecked.values());

		// verificamos que esté seleccionado algo, cualquier cosa
		if (seleccion != null && !seleccion.isEmpty()) {
			// verificamos que el campo de periodo tenga un valor
			if (!txtPeriodoPeachtree.getText().trim().equals("")) {
				String rutaArchivo = txtRutaArchivo.getText();
				Integer periodo = txt2Integer(txtPeriodoPeachtree.getText().trim());
				String formato = comboFormatoFecha.getText();
				PeachTreeController ptc = new PeachTreeController();
				boolean resultado = ptc.generarArchivoCSV(getSite().getShell(), seleccion, periodo, formato, rutaArchivo);
				System.out.println("RES: " + resultado);
				if (resultado) {
					for (Factura factura : seleccion) {
						controller.doSave(factura);
					}
					MessageDialog.openInformation(getSite().getShell(), "Exportar a PeachTree", 
							"El archivo ha sido generado exitosamente.");
					// limpiamos los checkboxes y el listado de items marcados
					for (TableItem ti : viewer.getTable().getItems()) {
						ti.setChecked(false);
						itemsChecked.clear();
					}
				}
				viewer.refresh();
			} else {
				MessageDialog.openInformation(getSite().getShell(), "Exportar a PeachTree", 
						"Debe suministrar el periodo de transacciones en Peachtree.");
				txtPeriodoPeachtree.setFocus();
			}
		} else {
			MessageDialog.openInformation(getSite().getShell(), "Exportar a PeachTree", 
					"Debe seleccionar al menos una factura para ejecutar esta acción.");
		}

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
		inicializarViewer();
		txtRutaArchivo.setText("c:\\SALES-LegalFact.csv");
		comboFormatoFecha.select(0);
		btnPendientes.setSelection(true);
		lblRegistros.setText("    (" + controller.getFacturasPendientesPeachtree().size() + " registros)");

		/*
		 * Permite mantener un listado de las facturas que han sido seleccionadas utilizando el checkbox
		 * habilitado en la tabla.
		 */
		table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				String string = event.detail == SWT.CHECK ? "Checked"
						: "Selected";
				System.out.println(event.item + " " + string);
				if (event.detail == SWT.CHECK) {
					TableItem item = (TableItem) event.item;
					Factura itemFactura = (Factura) item.getData();
					if (item.getChecked()) {
						itemsChecked.put(itemFactura.getNoFactura(), itemFactura);
					} else {
						itemsChecked.remove(itemFactura.getNoFactura());
					}
				}
			}
		});
	}

	

	protected void inicializarViewer() {
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(controller.getFacturasPendientesPeachtree());
		//viewer.setInput(controller.getListado());
		// registramos el tableViewer como un selection provider
		// esto es requerido para obtener el elemento seleccionado en las acciones
		getSite().setSelectionProvider(viewer);
		// habilitamos el listener para doble clic
		//this.hookDoubleClickListener(viewer, DireccionesEditor.ID);
	}


	private void actualizarViewer() {
		System.out.println("Act");
		List<Factura> listado;
		if (btnExportadas.getSelection()) {
			listado = controller.getFacturasEnviadasPeachtree();
		} else {
			listado = controller.getFacturasPendientesPeachtree();
		}
		lblRegistros.setText("(" + listado.size() + " registros)");
		viewer.setInput(listado);
		viewer.refresh();
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
			Collection<Factura> n = (Collection<Factura>) parent; 
			Object[] resultados = n.toArray(new Factura[n.size()]);
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
			Factura k = (Factura) element;
			switch (columnIndex) {
			case 0:
				resultado = "";
				break;
			case 1:
				resultado = k.getNoFactura();
				break;
			case 2:
				resultado = k.getNoFisico();
				break;
			case 3:
				resultado = valor2Txt(k.getNoPeriodoTx());
				break;
			case 4:
				resultado = k.getDspIdPeachtree();
				break;
			case 5:
				resultado = k.getTipo();
				break;
			case 6:
				resultado = k.getNoExpediente();
				break;
			case 7:
				resultado = k.getConcepto();
				break;
			case 8:
				resultado = FechaUtil.toString(k.getFechaFactura());
				break;
			case 9:
				resultado = valor2Txt(k.getTotal(), "#,##0.00");
				break;
			case 10:
				resultado = k.getEstado();
				break;
			}
			return resultado;
		}
	}

}
