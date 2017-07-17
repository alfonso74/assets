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

import rcp.assets.controllers.TipoCargosController;
import rcp.assets.editors.TipoCargosEditor;
import rcp.assets.model.TipoCargo;
import rcp.assets.services.FormUtils;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;


public class TipoCargosView extends AbstractViewPart {

	//public static final String ID = "rcp.assets.views.TipoCargosView"; //$NON-NLS-1$
	public static final String ID = Class.class.getName();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private TipoCargosController controller;
	private Table table;
	private TableViewer viewer;
	private FormUtils formUtils;
	

	public TipoCargosView() {
		controller = new TipoCargosController();
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
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		{
			viewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
			table = viewer.getTable();
			table.setLinesVisible(true);
			table.setHeaderVisible(true);
			toolkit.paintBordersFor(table);
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnCodigo = tableViewerColumn.getColumn();
				tblclmnCodigo.setWidth(53);
				tblclmnCodigo.setText("C\u00F3digo");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnGrupo = tableViewerColumn.getColumn();
				tblclmnGrupo.setWidth(52);
				tblclmnGrupo.setText("Grupo");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.LEFT);
				TableColumn tblclmnNombre = tableViewerColumn.getColumn();
				tblclmnNombre.setWidth(200);
				tblclmnNombre.setText("Descripci\u00F3n");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnDescIngles = tableViewerColumn.getColumn();
				tblclmnDescIngles.setWidth(200);
				tblclmnDescIngles.setText("Descripci\u00F3n (Ingl\u00E9s)");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.RIGHT);
				TableColumn tblclmnValor = tableViewerColumn.getColumn();
				tblclmnValor.setWidth(60);
				tblclmnValor.setText("Valor");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnPrioridad = tableViewerColumn.getColumn();
				tblclmnPrioridad.setAlignment(SWT.CENTER);
				tblclmnPrioridad.setWidth(40);
				tblclmnPrioridad.setText("Prior");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnHon = tableViewerColumn.getColumn();
				tblclmnHon.setAlignment(SWT.CENTER);
				tblclmnHon.setWidth(45);
				tblclmnHon.setText("Hon");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnItbms = tableViewerColumn.getColumn();
				tblclmnItbms.setAlignment(SWT.CENTER);
				tblclmnItbms.setWidth(45);
				tblclmnItbms.setText("ITBMS");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
				TableColumn tblclmnIngreso = tableViewerColumn.getColumn();
				tblclmnIngreso.setAlignment(SWT.CENTER);
				tblclmnIngreso.setWidth(50);
				tblclmnIngreso.setText("No Ing");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.CENTER);
				TableColumn tblclmnEstado = tableViewerColumn.getColumn();
				tblclmnEstado.setAlignment(SWT.LEFT);
				tblclmnEstado.setWidth(70);
				tblclmnEstado.setText("Estado");
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
		viewer.setInput(controller.getListadoOrdenado("noTipoCargo", true));
		// registramos el tableViewer como un selection provider
		// esto es requerido para obtener el elemento seleccionado en las acciones
		getSite().setSelectionProvider(viewer);
		// habilitamos el listener para doble clic
		this.hookDoubleClickListener(viewer, TipoCargosEditor.ID);
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
		viewer.setInput(controller.getListadoOrdenado("noTipoCargo", true));
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
			Collection<TipoCargo> n = (Collection<TipoCargo>) parent; 
			Object[] resultados = n.toArray(new TipoCargo[n.size()]);
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
			TipoCargo k = (TipoCargo) element;
			switch (columnIndex) {
			case 0:
				resultado = k.getNoTipoCargo();
				break;
			case 1:
				resultado = k.getGrupo();
				break;
			case 2:
				resultado = k.getDescripcion();
				break;
			case 3:
				resultado = k.getDescripcionIngles();
				break;	
			case 4:
				resultado = formUtils.valor2Txt(k.getValor(), "#,##0.00");
				break;
			case 5:
				resultado = formUtils.valor2Txt(k.getPrioridad());
				break;
			case 6:
				resultado = k.getHonorario();
				break;
			case 7:
				resultado = k.getPagaImpuesto();
				break;
			case 8:
				resultado = k.getIngreso();
				break;
			case 9:
				resultado = k.getDspEstado();
				break;
			}
			return resultado;
		}
	}
}
