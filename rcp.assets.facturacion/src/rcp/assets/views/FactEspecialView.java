package rcp.assets.views;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;

import rcp.assets.controllers.FacturacionEspecialController;
import rcp.assets.model.CategoriasTree;
import rcp.assets.model.FacturacionEspecial;
import rcp.assets.services.FechaUtil;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.jface.viewers.TreeViewerColumn;


public class FactEspecialView extends AbstractViewPart {

	//public static final String ID = "rcp.assets.views.FactEspecialView"; //$NON-NLS-1$
	public static final String ID = Class.class.getName();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private FacturacionEspecialController controller;
	private TreeViewer viewer;
	private Tree tree;

	private Map<Long, CategoriasTree> categorias;


	public FactEspecialView() {
		controller = new FacturacionEspecialController();
		categorias = new HashMap<Long, CategoriasTree>();
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

		viewer = new TreeViewer(container, SWT.BORDER);
		tree = viewer.getTree();
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tree.setBounds(0, 0, 85, 85);
		toolkit.paintBordersFor(tree);

		TreeViewerColumn treeViewerColumn_1 = new TreeViewerColumn(viewer, SWT.NONE);
		TreeColumn trclmnExpediente = treeViewerColumn_1.getColumn();
		trclmnExpediente.setAlignment(SWT.CENTER);
		trclmnExpediente.setWidth(100);
		trclmnExpediente.setText("Expediente");

		TreeViewerColumn treeViewerColumn_2 = new TreeViewerColumn(viewer, SWT.NONE);
		TreeColumn trclmnNombre = treeViewerColumn_2.getColumn();
		trclmnNombre.setWidth(225);
		trclmnNombre.setText("Nombre");

		TreeViewerColumn treeViewerColumn_3 = new TreeViewerColumn(viewer, SWT.NONE);
		TreeColumn trclmnDetalleFacturacion = treeViewerColumn_3.getColumn();
		trclmnDetalleFacturacion.setWidth(250);
		trclmnDetalleFacturacion.setText("Detalle facturacion");

		TreeViewerColumn treeViewerColumn_4 = new TreeViewerColumn(viewer, SWT.NONE);
		TreeColumn trclmnCreado = treeViewerColumn_4.getColumn();
		trclmnCreado.setWidth(80);
		trclmnCreado.setText("Creado");

		TreeViewerColumn treeViewerColumn_5 = new TreeViewerColumn(viewer, SWT.NONE);
		TreeColumn trclmnEstado = treeViewerColumn_5.getColumn();
		trclmnEstado.setWidth(70);
		trclmnEstado.setText("Estado");

		createActions();
		initializeToolBar();
		initializeMenu();

		inicializarViewer();
	}

	@Override
	protected void inicializarViewer() {
		System.out.println("LLL: " + controller.getListado());
		viewer.setContentProvider(new TreeViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(controller.getListado());
		// registramos el tableViewer como un selection provider
		// esto es requerido para obtener el elemento seleccionado en las acciones
		//getSite().setSelectionProvider(viewer);
		// habilitamos el listener para doble clic
		//this.hookDoubleClickListener(viewer, AtencionesEditor.ID);

		Long idMes;
		for (FacturacionEspecial f : controller.getListado()) {
			idMes = f.getMes().getIdMes();
			if (categorias.containsKey(idMes)) {
				categorias.get(idMes).agregarElemento(f);
			} else {
				CategoriasTree categoria = new CategoriasTree();
				categoria.setMes(f.getMes());
				categoria.agregarElemento(f);
				categorias.put(idMes, categoria);
			}
		}

		for (CategoriasTree categoria : categorias.values()) {
			System.out.println(categoria);
		}
		
		viewer.setInput(categorias);
		Object[] n = {categorias.values().toArray()[0]};
		viewer.setExpandedElements(n);
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
		viewer.setInput(categorias);
		viewer.refresh();
	}


	class TreeViewContentProvider implements ITreeContentProvider {

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return categorias.values().toArray();
			/*
			@SuppressWarnings("unchecked")
			Collection<FacturacionEspecial> n = (Collection<FacturacionEspecial>) inputElement; 
			Object[] resultados = n.toArray(new FacturacionEspecial[n.size()]);
			return resultados;
			 */
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof CategoriasTree) {
				CategoriasTree categoria = (CategoriasTree) parentElement;
				return categoria.getListado().toArray();
			}
			return null;
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return (element instanceof CategoriasTree);
		}

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
			Collection<FacturacionEspecial> n = (Collection<FacturacionEspecial>) parent; 
			Object[] resultados = n.toArray(new FacturacionEspecial[n.size()]);
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

			if (element instanceof CategoriasTree) {
				CategoriasTree k = (CategoriasTree) element;
				switch (columnIndex) {
				case 0:
					resultado = k.getMes().getDescripcion();
					break;
				}
			} else {
				FacturacionEspecial k = (FacturacionEspecial) element;
				switch (columnIndex) {
//				case 0:
//					resultado = k.getMes().getDescripcion();
//					break;
				case 0:
					resultado = k.getExpediente().getNoExpediente();
					break;
				case 1:
					resultado = k.getExpediente().getNombre();
					break;
				case 2:
					resultado = k.getDetalle();
					break;
				case 3:
					resultado = FechaUtil.toString(k.getExpediente().getFechaCreado());
					break;
				case 4:
					resultado = k.getExpediente().getDspEstado();
					break;
				}
			}
			return resultado;
		}
	}
}
