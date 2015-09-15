package rcp.assets.views;

import java.util.Collection;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;

import rcp.assets.controllers.NotificacionesController;
import rcp.assets.editors.NotificacionesEditor;
import rcp.assets.model.Notificacion;
import rcp.assets.services.FormUtils;


public class NotificacionesView extends AbstractViewPart {

	public static final String ID = "rcp.assets.views.NotificacionesView"; //$NON-NLS-1$
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private NotificacionesController controller;
	private TableViewer viewer;
	private Table table;
	private FormUtils formUtils;

	
	public NotificacionesView() {
		controller = new NotificacionesController();
		formUtils = new FormUtils();
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
		gl_container.marginWidth = 0;
		gl_container.marginHeight = 0;
		container.setLayout(gl_container);
		
		viewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = viewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		toolkit.paintBordersFor(table);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnId = tableViewerColumn.getColumn();
		tblclmnId.setWidth(45);
		tblclmnId.setText("ID");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnCia = tableViewerColumn_3.getColumn();
		tblclmnCia.setAlignment(SWT.CENTER);
		tblclmnCia.setWidth(50);
		tblclmnCia.setText("Cia");
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnPer = tableViewerColumn_4.getColumn();
		tblclmnPer.setAlignment(SWT.CENTER);
		tblclmnPer.setWidth(50);
		tblclmnPer.setText("Per");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnTipo = tableViewerColumn_1.getColumn();
		tblclmnTipo.setWidth(120);
		tblclmnTipo.setText("Tipo");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnDescripcin = tableViewerColumn_2.getColumn();
		tblclmnDescripcin.setWidth(250);
		tblclmnDescripcin.setText("Descripci\u00F3n");
		
		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnDescIngles = tableViewerColumn_5.getColumn();
		tblclmnDescIngles.setWidth(250);
		tblclmnDescIngles.setText("Descripci\u00F3n (ingl\u00E9s)");

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
		this.hookDoubleClickListener(viewer, NotificacionesEditor.ID);
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
			Collection<Notificacion> n = (Collection<Notificacion>) parent; 
			Object[] resultados = n.toArray(new Notificacion[n.size()]);
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
			Notificacion k = (Notificacion) element;
			switch (columnIndex) {
			case 0:
				resultado = Long.toString(k.getId());
				break;
			case 1:
				resultado = k.getNoCia();
				break;
			case 2:
				resultado = formUtils.valor2Txt(k.getPeriodo());
				break;	
			case 3:
				resultado = k.getTipo() == null ? "Null" : k.getTipo().getDescripcion();
				break;
			case 4:
				resultado = k.getDescripcion();
				break;
			case 5:
				resultado = k.getDescripcionIngles();
				break;
			}
			return resultado;
		}
	}
}
