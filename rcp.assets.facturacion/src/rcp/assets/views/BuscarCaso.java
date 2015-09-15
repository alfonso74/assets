package rcp.assets.views;

import java.util.Collection;
import java.util.Date;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;

import rcp.assets.controllers.CasosController;
import rcp.assets.model.Caso;
import rcp.assets.services.CalComboSettings;
import rcp.assets.services.FechaUtil;

import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.nebula.widgets.calendarcombo.CalendarCombo;

public class BuscarCaso extends AbstractViewPart {

	//public static final String ID = "rcp.assets.views.BuscarCaso"; //$NON-NLS-1$
	public static final String ID = Class.class.getName();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private CasosController controller;
	
	private TableViewer viewer;
	private Table table;
	
	private Text txtNoCaso;
	private CalendarCombo calendarComboFacturacion;
	

	public BuscarCaso() {
		controller = new CasosController();
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		
		ScrolledForm scrldfrmNewScrolledform = toolkit.createScrolledForm(parent);
		toolkit.paintBordersFor(scrldfrmNewScrolledform);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginHeight = 10;
		gridLayout.marginWidth = 10;
		scrldfrmNewScrolledform.getBody().setLayout(gridLayout);
		{
			Section sctnBusquedaDeCaso = toolkit.createSection(scrldfrmNewScrolledform.getBody(), Section.TWISTIE | Section.TITLE_BAR);
			sctnBusquedaDeCaso.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			toolkit.paintBordersFor(sctnBusquedaDeCaso);
			sctnBusquedaDeCaso.setText("Busqueda de caso(s) para facturacion");
			sctnBusquedaDeCaso.setExpanded(true);
			{
				Composite composite = toolkit.createComposite(sctnBusquedaDeCaso, SWT.NONE);
				toolkit.paintBordersFor(composite);
				sctnBusquedaDeCaso.setClient(composite);
				composite.setLayout(new GridLayout(2, false));
				{
					Label lblCaso = toolkit.createLabel(composite, "Numero de caso:", SWT.NONE);
					lblCaso.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				}
				{
					txtNoCaso = toolkit.createText(composite, "New Text", SWT.NONE);
					txtNoCaso.setText("");
					txtNoCaso.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				}
				{
					Label lblFecha = toolkit.createLabel(composite, "Fecha de facturacion:", SWT.NONE);
					lblFecha.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				}
				{
					calendarComboFacturacion = new CalendarCombo(composite, SWT.NONE, new CalComboSettings(), null);
					toolkit.adapt(calendarComboFacturacion);
					toolkit.paintBordersFor(calendarComboFacturacion);
				}
				new Label(composite, SWT.NONE);
				{
					Button btnBuscar = toolkit.createButton(composite, "Buscar", SWT.NONE);
					btnBuscar.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
					btnBuscar.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							System.out.println("Buscando casos...");
							if (!txtNoCaso.getText().equals("")) {
								Long noCaso = Long.valueOf(txtNoCaso.getText());								
								viewer.setInput(controller.buscarCaso(noCaso));
							} else if (!calendarComboFacturacion.getDateAsString().equals("")) {
								Date fecha = calendarComboFacturacion.getDate().getTime();
								viewer.setInput(controller.buscarCasosPorFecha(fecha));
								/*
							} else if (!txtFechaFacturacion.getText().equals("")) {
								Date fecha = FechaUtil.toDate(txtFechaFacturacion.getText());
								viewer.setInput(controller.buscarCasosPorFecha(fecha));*/
							} else {
								// mensaje
							}
						}
					});
				}
			}
		}
		new Label(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		{
			viewer = new TableViewer(scrldfrmNewScrolledform.getBody(), SWT.BORDER | SWT.FULL_SELECTION);
			table = viewer.getTable();
			table.setLinesVisible(true);
			table.setHeaderVisible(true);
			table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			toolkit.paintBordersFor(table);
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

		createActions();
		initializeToolBar();
		initializeMenu();
		inicializarViewer();
	}

	public void dispose() {
		toolkit.dispose();
		super.dispose();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		@SuppressWarnings("unused")
		IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		@SuppressWarnings("unused")
		IMenuManager manager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	public void refrescar() {
		viewer.setInput(controller.buscarCasosPorFacturar());
		viewer.refresh();
	}

	@Override
	protected void inicializarViewer() {
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(controller.buscarCasosPorFacturar());
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
				resultado = Long.toString(k.getNoCaso());
				break;
			case 1:
				resultado = k.getExpediente().getNoExpediente();
				break;
			case 2:
				resultado = k.getExpediente().getNombre();
				break;
			case 3:
				resultado = k.getNaturaleza();
				break;
			case 4:
				resultado = FechaUtil.toString(k.getFechaCreado());
				break;
			}
			return resultado;
		}
	}
}
