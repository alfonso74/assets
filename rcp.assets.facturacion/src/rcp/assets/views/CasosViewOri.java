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
import org.eclipse.ui.part.ViewPart;

import rcp.assets.controllers.CasosController;
import rcp.assets.model.Caso;
import rcp.assets.services.FechaUtil;

public class CasosViewOri extends AbstractViewPart {

	//public static final String ID = "rcp.assets.views.CasosView"; //$NON-NLS-1$
	public static final String ID = Class.class.getName();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private CasosController controller;
	private Table table;
	private TableViewer viewer;
	

	public CasosViewOri() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = toolkit.createComposite(parent, SWT.NONE);
		toolkit.paintBordersFor(container);

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
