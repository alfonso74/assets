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
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.layout.FillLayout;

import rcp.assets.controllers.UsuariosController;
import rcp.assets.editors.UsuariosEditor;
import rcp.assets.model.Usuario;


public class UsuariosView extends AbstractViewPart {

	//public static final String ID = "rcp.assets.views.UsuariosView"; //$NON-NLS-1$
	public static final String ID = Class.class.getName();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private UsuariosController controller;
	private Table table;
	private TableViewer viewer;

	public UsuariosView() {
		controller = new UsuariosController();
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
		
		viewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = viewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		toolkit.paintBordersFor(table);
		
		TableColumn tblclmnId = new TableColumn(table, SWT.NONE);
		tblclmnId.setWidth(35);
		tblclmnId.setText("ID");
		
		TableColumn tblclmnNombreYApellido = new TableColumn(table, SWT.NONE);
		tblclmnNombreYApellido.setWidth(200);
		tblclmnNombreYApellido.setText("Nombre y apellido");
		
		TableColumn tblclmnUsername = new TableColumn(table, SWT.NONE);
		tblclmnUsername.setWidth(120);
		tblclmnUsername.setText("Username");
		
		TableColumn tblclmnEstado = new TableColumn(table, SWT.NONE);
		tblclmnEstado.setWidth(100);
		tblclmnEstado.setText("Estado");

		createActions();
		initializeToolBar();
		initializeMenu();
		
		inicializarViewer();
	}
	
	
	protected void inicializarViewer() {
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(controller.getListado());
		// registramos el tableViewer como un selection provider
		// esto es requerido para obtener el elemento seleccionado en las acciones
		getSite().setSelectionProvider(viewer);
		// habilitamos el listener para doble clic
		this.hookDoubleClickListener(viewer, UsuariosEditor.ID);
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
			Collection<Usuario> n = (Collection<Usuario>) parent; 
			Object[] resultados = n.toArray(new Usuario[n.size()]);
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
			Usuario k = (Usuario) element;
			switch (columnIndex) {
			case 0:
				resultado = Long.toString(k.getIdUsuario());
				break;
			case 1:
				resultado = k.getNombreCompleto();
				break;
			case 2:
				resultado = k.getUserName();
				break;
			case 3:
				resultado = k.getDspEstado();
				break;
			}
			return resultado;
		}
	}
}
