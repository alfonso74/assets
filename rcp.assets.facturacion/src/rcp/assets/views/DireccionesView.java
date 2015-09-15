package rcp.assets.views;

import java.util.Collection;

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
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.widgets.FormToolkit;

import rcp.assets.controllers.DireccionesController;
import rcp.assets.editors.DireccionesEditor;
import rcp.assets.model.Direccion;
import rcp.assets.services.FechaUtil;
import rcp.assets.views.seguimiento.BuscarTextoControl;

import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;


public class DireccionesView extends AbstractViewPart {

	//public static final String ID = "rcp.assets.views.DireccionesView"; //$NON-NLS-1$
	public static final String ID = Class.class.getName();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private DireccionesController controller;
	private Table table;
	private TableViewer viewer;
	
	private BuscarTextoControl buscarControl;
	

	public DireccionesView() {
		controller = new DireccionesController();
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
				TableColumn tblclmnId = tableViewerColumn.getColumn();
				tblclmnId.setWidth(45);
				tblclmnId.setText("ID");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnResp = tableViewerColumn.getColumn();
				tblclmnResp.setWidth(45);
				tblclmnResp.setText("Resp");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
				tblclmnNewColumn.setWidth(250);
				tblclmnNewColumn.setText("Nombre Alterno");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnDireccion = tableViewerColumn.getColumn();
				tblclmnDireccion.setWidth(200);
				tblclmnDireccion.setText("Direccion");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnCreado = tableViewerColumn.getColumn();
				tblclmnCreado.setWidth(85);
				tblclmnCreado.setText("Creado");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnEstado = tableViewerColumn.getColumn();
				tblclmnEstado.setWidth(100);
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
	
	@Override
	protected void inicializarViewer() {
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(controller.getListado());
		// registramos el tableViewer como un selection provider
		// esto es requerido para obtener el elemento seleccionado en las acciones
		getSite().setSelectionProvider(viewer);
		// habilitamos el listener para doble clic
		this.hookDoubleClickListener(viewer, DireccionesEditor.ID);
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
			Collection<Direccion> n = (Collection<Direccion>) parent; 
			Object[] resultados = n.toArray(new Direccion[n.size()]);
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
			Direccion k = (Direccion) element;
			switch (columnIndex) {
			case 0:
				resultado = Long.toString(k.getIdDireccion());
				break;
			case 1:
				resultado = k.getResponsable() == null ? "Null" : k.getResponsable().getIdResponsable().toString();
				break;
			case 2:
				resultado = k.getNombreAlterno();
				break;
			case 3:
				resultado = k.getDireccion1();
				break;
			case 4:
				resultado = FechaUtil.toString(k.getFechaCreado());
				break;
			case 5:
				resultado = k.getDspEstado();
				break;
			}
			return resultado;
		}
	}
}
