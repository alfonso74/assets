package rcp.assets.views.seguimiento;

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

import rcp.assets.controllers.CasosController;
import rcp.assets.model.Caso;
import rcp.assets.services.FechaUtil;
import rcp.assets.views.AbstractViewPart;

import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;


public class CasosView extends AbstractViewPart {

	//public static final String ID = "rcp.assets.views.CasosView"; //$NON-NLS-1$
	public static final String ID = Class.class.getName();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private CasosController controller;
	private Table table;
	private TableViewer viewer;
	
	private BuscarTextoControl buscarControl;
	

	public CasosView() {
		controller = new CasosController();
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		FillLayout fillLayout = (FillLayout) parent.getLayout();
		fillLayout.type = SWT.VERTICAL;
		{
			Composite container = new Composite(parent, SWT.NONE);
			toolkit.adapt(container);
			toolkit.paintBordersFor(container);
			GridLayout gl_container = new GridLayout(1, false);
			gl_container.marginHeight = 0;
			gl_container.marginWidth = 0;
			container.setLayout(gl_container);
			viewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL);
			table = viewer.getTable();
			table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			table.setLinesVisible(true);
			table.setHeaderVisible(true);
			toolkit.paintBordersFor(table);
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnCia = tableViewerColumn.getColumn();
				tblclmnCia.setAlignment(SWT.CENTER);
				tblclmnCia.setWidth(35);
				tblclmnCia.setText("Cia");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnNocaso = tableViewerColumn.getColumn();
				tblclmnNocaso.setAlignment(SWT.RIGHT);
				tblclmnNocaso.setWidth(55);
				tblclmnNocaso.setText("NoCaso");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnExpediente = tableViewerColumn.getColumn();
				tblclmnExpediente.setAlignment(SWT.CENTER);
				tblclmnExpediente.setWidth(75);
				tblclmnExpediente.setText("Expediente");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnNombre = tableViewerColumn.getColumn();
				tblclmnNombre.setWidth(210);
				tblclmnNombre.setText("Nombre expediente");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnAsunto = tableViewerColumn.getColumn();
				tblclmnAsunto.setWidth(210);
				tblclmnAsunto.setText("Asunto");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnCreado = tableViewerColumn.getColumn();
				tblclmnCreado.setAlignment(SWT.CENTER);
				tblclmnCreado.setWidth(75);
				tblclmnCreado.setText("Creado");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnEstado = tableViewerColumn.getColumn();
				tblclmnEstado.setWidth(75);
				tblclmnEstado.setText("Estado");
			}
			
			buscarControl = new BuscarTextoControl(toolkit, viewer);
			buscarControl.crearControles(container);
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
		viewer.setInput(controller.buscarCasosOrdenados());
		// registramos el tableViewer como un selection provider
		// esto es requerido para obtener el elemento seleccionado en las acciones
		getSite().setSelectionProvider(viewer);
		// habilitamos el listener para doble clic
		//this.hookDoubleClickListener(viewer, TipoCargosEditor.ID);
		buscarControl.setNoRegistros(viewer.getTable().getItemCount());
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
		viewer.setInput(controller.buscarCasosOrdenados());
		viewer.refresh();
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
			case 6:
				resultado = k.getDspEstado();
				break;
			}
			return resultado;
		}
	}
	
}
