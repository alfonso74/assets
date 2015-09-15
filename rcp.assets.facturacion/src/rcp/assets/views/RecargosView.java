package rcp.assets.views;

import java.util.Collection;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;

import rcp.assets.controllers.RecargosController;
import rcp.assets.editors.RecargosEditor;
import rcp.assets.model.Recargo;
import rcp.assets.services.FormUtils;

import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;


public class RecargosView extends AbstractViewPart {

	//public static final String ID = "rcp.assets.views.RecargosView"; //$NON-NLS-1$
	public static final String ID = Class.class.getName();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private RecargosController controller;
	private TableViewer viewer;
	private Table table;
	private FormUtils formUtils;

	
	public RecargosView() {
		controller = new RecargosController();
		formUtils = new FormUtils();
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));
		Composite container = toolkit.createComposite(parent, SWT.NONE);
		toolkit.paintBordersFor(container);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		viewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = viewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		toolkit.paintBordersFor(table);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnId = tableViewerColumn.getColumn();
		tblclmnId.setWidth(35);
		tblclmnId.setText("ID");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnCia = tableViewerColumn_1.getColumn();
		tblclmnCia.setAlignment(SWT.CENTER);
		tblclmnCia.setWidth(50);
		tblclmnCia.setText("Cia");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnPeriodo = tableViewerColumn_2.getColumn();
		tblclmnPeriodo.setAlignment(SWT.CENTER);
		tblclmnPeriodo.setWidth(50);
		tblclmnPeriodo.setText("Per");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnTipoRecargo = tableViewerColumn_3.getColumn();
		tblclmnTipoRecargo.setWidth(150);
		tblclmnTipoRecargo.setText("Tipo recargo");
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnMonto = tableViewerColumn_4.getColumn();
		tblclmnMonto.setAlignment(SWT.CENTER);
		tblclmnMonto.setWidth(80);
		tblclmnMonto.setText("Monto");
		
		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnTextoEnFactura = tableViewerColumn_5.getColumn();
		tblclmnTextoEnFactura.setWidth(250);
		tblclmnTextoEnFactura.setText("Texto en factura");
		
		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnEstado = tableViewerColumn_6.getColumn();
		tblclmnEstado.setWidth(60);
		tblclmnEstado.setText("Estado");

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
		this.hookDoubleClickListener(viewer, RecargosEditor.ID);
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
			Collection<Recargo> n = (Collection<Recargo>) parent; 
			Object[] resultados = n.toArray(new Recargo[n.size()]);
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
			Recargo k = (Recargo) element;
			switch (columnIndex) {
			case 0:
				resultado = formUtils.valor2Txt(k.getId());
				break;
			case 1:
				resultado = k.getNoCia();
				break;
			case 2:
				resultado = formUtils.valor2Txt(k.getPeriodo());
				break;	
			case 3:
				resultado = k.getTipoRecargo() == null ? "Null" : k.getTipoRecargo().getDescripcion();
				break;
			case 4:
				resultado = formUtils.valor2Txt(k.getMonto(), "#,##0.00");
				break;
			case 5:
				resultado = k.getTextoFactura();
				break;
			case 6:
				resultado = k.getEstado() == null ? "Null" : k.getEstado().getDescripcion();
				break;
			}
			return resultado;
		}
	}
	
}
