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

import rcp.assets.controllers.ResponsablesController;
import rcp.assets.editors.ResponsablesEditor;
import rcp.assets.forms.ResponsablesFormEditor;
import rcp.assets.model.Responsable;
import rcp.assets.services.FechaUtil;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;


public class ResponsablesView extends AbstractViewPart {

	//public static final String ID = "rcp.assets.views.ResponsablesView"; //$NON-NLS-1$
	public static final String ID = Class.class.getName();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private ResponsablesController controller;
	private Table table;
	private TableViewer viewer;
	

	public ResponsablesView() {
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
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		{
			viewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
			table = viewer.getTable();
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
		this.hookDoubleClickListener(viewer, ResponsablesEditor.ID);
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
		//viewer.setInput(controller.getListado());
		viewer.setInput(controller.getListadoOrdenado("idResponsable", false));
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
