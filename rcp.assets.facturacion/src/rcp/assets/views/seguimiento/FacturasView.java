package rcp.assets.views.seguimiento;

import java.util.Collection;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;

import rcp.assets.controllers.FacturasController;
import rcp.assets.model.Factura;
import rcp.assets.services.FechaUtil;
import rcp.assets.services.FormUtils;
import rcp.assets.views.AbstractViewPart;

import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;


public class FacturasView extends AbstractViewPart {

	//public static final String ID = "rcp.assets.views.FacturasView"; //$NON-NLS-1$
	public static final String ID = Class.class.getName();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private FacturasController controller;
	private TableViewer viewer;
	private Table table;
	
	private FormUtils formUtils;
	
	private BuscarTextoControl buscarControl;
	
	
	public FacturasView() {
		formUtils = new FormUtils();
		controller = new FacturasController();
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
		
		viewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		table = viewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		toolkit.paintBordersFor(table);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnNumero = tableViewerColumn.getColumn();
		tblclmnNumero.setAlignment(SWT.RIGHT);
		tblclmnNumero.setWidth(60);
		tblclmnNumero.setText("No Fact");
		
		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnTipo = tableViewerColumn_6.getColumn();
		tblclmnTipo.setWidth(40);
		tblclmnTipo.setText("Tipo");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnExpediente = tableViewerColumn_1.getColumn();
		tblclmnExpediente.setAlignment(SWT.CENTER);
		tblclmnExpediente.setWidth(80);
		tblclmnExpediente.setText("Expediente");
		
		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnCaso = tableViewerColumn_5.getColumn();
		tblclmnCaso.setAlignment(SWT.RIGHT);
		tblclmnCaso.setWidth(60);
		tblclmnCaso.setText("Caso");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnConcepto = tableViewerColumn_2.getColumn();
		tblclmnConcepto.setWidth(250);
		tblclmnConcepto.setText("Concepto");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnFecha = tableViewerColumn_3.getColumn();
		tblclmnFecha.setAlignment(SWT.CENTER);
		tblclmnFecha.setWidth(80);
		tblclmnFecha.setText("Fecha");
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnMonto = tableViewerColumn_4.getColumn();
		tblclmnMonto.setAlignment(SWT.RIGHT);
		tblclmnMonto.setWidth(80);
		tblclmnMonto.setText("Monto");
		
		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnEstado = tableViewerColumn_7.getColumn();
		tblclmnEstado.setAlignment(SWT.CENTER);
		tblclmnEstado.setWidth(70);
		tblclmnEstado.setText("Estado");
		
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
		viewer.setInput(controller.getFacturasActivas());
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
		viewer.setInput(controller.getFacturasActivas());
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
				resultado = k.getNoFactura();
				break;
			case 1:
				resultado = k.getTipo();
				break;
			case 2:
				resultado = k.getNoExpediente();
				break;
			case 3:
				resultado = k.getRefSecuencia().toString() + " ";
				break;
			case 4:
				resultado = k.getConcepto();
				break;
			case 5:
				resultado = FechaUtil.toString(k.getFechaFactura());
				break;
			case 6:
				resultado = formUtils.valor2Txt(k.getTotal(), "#,##0.00");
				break;
			case 7:
				resultado = k.getEstado();
				break;
			}
			return resultado;
		}
	}
}
