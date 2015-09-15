package rcp.assets.views.seguimiento;

import java.util.Collection;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;

import rcp.assets.controllers.ResponsablesController;
import rcp.assets.forms.ResponsablesFormEditor;
import rcp.assets.model.Responsable;
import rcp.assets.services.FechaUtil;
import rcp.assets.views.AbstractViewPart;
import rcp.assets.views.filters.ResponsablesFilter;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;


public class ResponsablesSegView extends AbstractViewPart {

	//public static final String ID = "rcp.assets.views.ResponsablesView"; //$NON-NLS-1$
	public static final String ID = "rcp.assets.views.seguimiento.ResponsablesSegView"; //$NON-NLS-1$
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private ResponsablesController controller;
	private Table table;
	private TableViewer viewer;
	//private Text txtFiltro;

	//private Label lblRegistros;
	//private Label lblFiltrados;
	
	private BuscarTextoControl buscarControl;


	public ResponsablesSegView() {
		controller = new ResponsablesController();
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = toolkit.createComposite(parent, SWT.NONE);
		toolkit.paintBordersFor(container);
		GridLayout gl_container = new GridLayout(1, false);
		gl_container.marginHeight = 0;
		gl_container.marginWidth = 0;
		container.setLayout(gl_container);
		{
			viewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
			table = viewer.getTable();
			table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			table.setLinesVisible(true);
			table.setHeaderVisible(true);
			toolkit.paintBordersFor(table);
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnNo = tableViewerColumn.getColumn();
				tblclmnNo.setWidth(55);
				tblclmnNo.setText("Codigo");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnNombre = tableViewerColumn.getColumn();
				tblclmnNombre.setWidth(250);
				tblclmnNombre.setText("Nombre");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnTel = tableViewerColumn.getColumn();
				tblclmnTel.setWidth(100);
				tblclmnTel.setText("Tel\u00E9fono");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnCreado = tableViewerColumn.getColumn();
				tblclmnCreado.setWidth(100);
				tblclmnCreado.setText("Creado");
			}
		}
		{
			buscarControl = new BuscarTextoControl(toolkit, viewer);
			buscarControl.crearControles(container);
			/*
			Composite composite = toolkit.createComposite(container, SWT.NONE);
			composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			toolkit.paintBordersFor(composite);
			GridLayout gl_composite = new GridLayout(5, false);
			gl_composite.marginBottom = 5;
			gl_composite.marginHeight = 0;
			composite.setLayout(gl_composite);

			Label lblFiltrar = toolkit.createLabel(composite, "Filtrar:", SWT.NONE);
			lblFiltrar.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblFiltrar.setBounds(0, 0, 49, 13);

			txtFiltro = toolkit.createText(composite, "New Text", SWT.NONE);
			txtFiltro.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.keyCode == 13) {
						aplicarFiltro(txtFiltro.getText());
					}
				}
			});
			txtFiltro.setText("");
			GridData gd_txtFiltro = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_txtFiltro.widthHint = 100;
			txtFiltro.setLayoutData(gd_txtFiltro);
			{
				ControlDecoration controlDecoration = new ControlDecoration(txtFiltro, SWT.RIGHT | SWT.CENTER);
				controlDecoration.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/borrar.gif"));
				controlDecoration.setDescriptionText("Quitar filtro de búsqueda");
				controlDecoration.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						aplicarFiltro("");
						txtFiltro.setText("");
					}
				});
			}

			Label labelSeparador = toolkit.createLabel(composite, " | ", SWT.NONE);
			GridData gd_labelSeparador = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_labelSeparador.horizontalIndent = 20;
			labelSeparador.setLayoutData(gd_labelSeparador);

			lblRegistros = toolkit.createLabel(composite, "0 registros", SWT.NONE);
			lblRegistros.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			lblFiltrados = toolkit.createLabel(composite, "-  0 filtrados", SWT.NONE);
			lblFiltrados.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			*/
		}

		createActions();
		initializeToolBar();
		initializeMenu();

		inicializarViewer();
	}


	@Override
	protected void inicializarViewer() {
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		//viewer.setInput(controller.getListado());
		viewer.setInput(controller.getListadoOrdenado("idResponsable", false));
		// registramos el tableViewer como un selection provider
		// esto es requerido para obtener el elemento seleccionado en las acciones
		getSite().setSelectionProvider(viewer);
		// habilitamos el listener para doble clic
		this.hookDoubleClickListener(viewer, ResponsablesFormEditor.ID);
		//lblRegistros.setText(viewer.getTable().getItemCount() + " registros");
		buscarControl.setNoRegistros(viewer.getTable().getItemCount());
	}


	/*
	private void aplicarFiltro(String cadena) {
		// solo aplicamos un filtro a la vez, así que reseteamos los filtros
		viewer.resetFilters();
		// obtenemos el total de registros cuando no hay ningún filtro aplicado, para mostrar el conteo original posteriormente
		int totalRegistros = viewer.getTable().getItemCount();  
		if (!cadena.equals("")) {
			viewer.setItemCount(0);    // hace que se actualice más rápido el viewer (visualmente) al aplicar el filtro
			ResponsablesFilter filtro = new ResponsablesFilter();
			filtro.setSearchString(cadena.toLowerCase());
			viewer.addFilter(filtro);
			lblRegistros.setText(totalRegistros + " registros");
			lblFiltrados.setText(" -  " + filtro.getCantidad() + (filtro.getCantidad() == 1 ? " filtrado" : " filtrados"));
		} else {
			lblRegistros.setText(totalRegistros + " registros");
			lblFiltrados.setText(" -  " + "0" + " filtrados");
		}
	}
	*/

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
		viewer.setInput(controller.getListadoOrdenado("idResponsable", false));
		viewer.refresh();
		//lblRegistros.setText(viewer.getTable().getItemCount() + " registros");
		buscarControl.setNoRegistros(viewer.getTable().getItemCount());
	}


	class ViewContentProvider implements IStructuredContentProvider {
		int n = 0;
		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public Object[] getElements(Object parent) {
			@SuppressWarnings("unchecked")
			Collection<Responsable> n = (Collection<Responsable>) parent; 
			Object[] resultados = n.toArray(new Responsable[n.size()]);
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
			Responsable k = (Responsable) element;
			switch (columnIndex) {
			case 0:
				resultado = k.getNoCliente() == null ? "" : Integer.toString(k.getNoCliente());
				break;
			case 1:
				resultado = k.getNombreCompleto();
				break;
			case 2:
				resultado = k.getTelefono1();
				break;
			case 3:
				resultado = FechaUtil.toString(k.getFechaCreado());
				break;
			}
			return resultado;
		}
	}
}
