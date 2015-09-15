package rcp.assets.views;

import java.util.Collection;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.XViewerLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;

import rcp.assets.controllers.ExpedientesController;
import rcp.assets.model.Expediente;
import rcp.assets.services.FechaUtil;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

public class ConsultarCargosView extends AbstractViewPart {

	//public static final String ID = "rcp.assets.views.ConsultarCargosView"; //$NON-NLS-1$
	public static final String ID = Class.class.getName();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private ExpedientesController controller;



	public ConsultarCargosView() {
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

		XViewer viewer = new XViewer(container, SWT.MULTI | SWT.BORDER
				| SWT.FULL_SELECTION, new XViewerFactoryCargos());
		viewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer.setContentProvider(new ViewContentProvider2());
		viewer.setLabelProvider(new ViewLabelProvider2(viewer));
		viewer.setInput(controller.getListado());
		/*	
		IXViewerFactory xViewerFactory = new IX
		XViewer viewer = new XViewer(parent, style, xViewerFactory);
		 */
		createActions();
		initializeToolBar();
		initializeMenu();
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
		// TODO Auto-generated method stub

	}

	@Override
	protected void inicializarViewer() {
		// TODO Auto-generated method stub

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
				resultado = k.getEstado();
				break;
			case 3:
				resultado = FechaUtil.toString(k.getFechaCreado());
				break;
			}
			return resultado;
		}
	}


	class ViewContentProvider2 implements ITreeContentProvider {
		
		private final Object[] EMPTY_ARRAY = new Object[0];
		
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

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof Object[]) {
				return (Object[]) parentElement;
			}
			if (parentElement instanceof Collection) {
				return ((Collection) parentElement).toArray();
			}/*
			if (parentElement instanceof SomeTask) {
				return ((SomeTask) parentElement).getChildren().toArray();
			}*/
			return EMPTY_ARRAY;
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			// TODO Auto-generated method stub
			return false;
		}
	}


	class ViewLabelProvider2 extends XViewerLabelProvider {

		public ViewLabelProvider2(XViewer viewer) {
			super(viewer);
		}

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
				resultado = k.getDepto();
				break;
			case 3:
				resultado = FechaUtil.toString(k.getFechaCreado());
				break;
			}
			return resultado;
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub

		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub

		}

		@Override
		public Image getColumnImage(Object element, XViewerColumn xCol,
				int columnIndex) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getColumnText(Object element, XViewerColumn xCol,
				int columnIndex) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
