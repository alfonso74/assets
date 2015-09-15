package rcp.assets.views;

import java.util.Collection;
import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import rcp.assets.controllers.ExpedientesController;
import rcp.assets.model.Expediente;
import rcp.assets.services.FechaUtil;
import rcp.assets.views.seguimiento.BuscarTextoControl;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;

public class ExpedientesView extends AbstractViewPart {

	//public static final String ID = "rcp.assets.views.ExpedientesView"; //$NON-NLS-1$
	public static final String ID = Class.class.getName();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private ExpedientesController controller;

	private TableViewer viewer;
	private Table table;
	private List<Expediente> viewerInput;
	private BuscarTextoControl buscarControl;

	public ExpedientesView() {
		controller = new ExpedientesController();
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = toolkit.createComposite(parent, SWT.NONE);
		toolkit.paintBordersFor(container);
		container.setLayout(new GridLayout(1, false));
		{
			//table = new Table(container, SWT.BORDER | SWT.VIRTUAL);
			//table = new Table(container, SWT.BORDER);
			//viewer = new TableViewer(table);
			viewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL);
			table = viewer.getTable();
			table.setLinesVisible(true);
			table.setHeaderVisible(true);
			table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			toolkit.paintBordersFor(table);
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnNoexp = tableViewerColumn.getColumn();
				tblclmnNoexp.setWidth(80);
				tblclmnNoexp.setText("NoExp");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnNombre = tableViewerColumn.getColumn();
				tblclmnNombre.setWidth(250);
				tblclmnNombre.setText("Nombre del expediente");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnSubarea = tableViewerColumn.getColumn();
				tblclmnSubarea.setWidth(100);
				tblclmnSubarea.setText("SubArea");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnCreado = tableViewerColumn.getColumn();
				tblclmnCreado.setWidth(75);
				tblclmnCreado.setText("Creado");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnEstado = tableViewerColumn.getColumn();
				tblclmnEstado.setWidth(75);
				tblclmnEstado.setText("Estado");
			}
		}
		
		buscarControl = new BuscarTextoControl(toolkit, viewer);
		buscarControl.crearControles(container);

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
		viewer.setInput(controller.getListado());
		viewer.refresh();
		buscarControl.setNoRegistros(viewer.getTable().getItemCount());
	}

	@Override
	protected void inicializarViewer() {
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setUseHashlookup(true);
		viewerInput = controller.getListado();
		viewer.getTable().setItemCount(viewerInput.size());
		/*
		viewer.getTable().addListener(SWT.SetData, new Listener() {
			public void handleEvent(Event event) {
				TableItem item = (TableItem)event.item;
				int index = event.index;
				Expediente itemExp = viewerInput.get(index);
				item.setText(0, itemExp.getNoExpediente());
				item.setText(1, itemExp.getNombre());
				item.setText(2, itemExp.getDspSubArea());
				item.setText(3, FechaUtil.toString(itemExp.getFechaCreado()));
				item.setText(4, itemExp.getDspEstado());
			}
		});
		*/
		viewer.setInput(viewerInput);
		// registramos el tableViewer como un selection provider
		// esto es requerido para obtener el elemento seleccionado en las acciones
		getSite().setSelectionProvider(viewer);
		// habilitamos el listener para doble clic
		//this.hookDoubleClickListener(viewer, DireccionesEditor.ID);
		buscarControl.setNoRegistros(viewer.getTable().getItemCount());
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
				resultado = FechaUtil.toString(k.getFechaCreado());
				break;
			case 4:
				resultado = k.getDspEstado();
				break;
			}
			return resultado;
		}
	}

}
