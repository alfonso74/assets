package rcp.assets.views;

import java.util.List;

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

import rcp.assets.controllers.KeywordsController;
import rcp.assets.editors.KeywordsEditor;
import rcp.assets.model.Keyword;
import rcp.assets.services.FormUtils;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;

public class KeywordsView extends AbstractViewPart {

	//public static final String ID = "rcp.assets.views.KeywordsView"; //$NON-NLS-1$
	public static final String ID = Class.class.getName();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private KeywordsController controller;
	private Table table;
	private TableViewer viewer;
	private FormUtils formUtils;

	public KeywordsView() {
		controller = new KeywordsController();
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

		viewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = viewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		toolkit.paintBordersFor(table);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnId = tableViewerColumn.getColumn();
		tblclmnId.setWidth(50);
		tblclmnId.setText("Id");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnCdigo = tableViewerColumn_1.getColumn();
		tblclmnCdigo.setWidth(90);
		tblclmnCdigo.setText("C\u00F3digo");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnDescripcin = tableViewerColumn_2.getColumn();
		tblclmnDescripcin.setWidth(200);
		tblclmnDescripcin.setText("Descripci\u00F3n");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnTipo = tableViewerColumn_3.getColumn();
		tblclmnTipo.setWidth(150);
		tblclmnTipo.setText("Tipo");
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnPos = tableViewerColumn_4.getColumn();
		tblclmnPos.setAlignment(SWT.CENTER);
		tblclmnPos.setWidth(40);
		tblclmnPos.setText("Pos");
		
		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn tblclmnEstado = tableViewerColumn_5.getColumn();
		tblclmnEstado.setWidth(70);
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
		this.hookDoubleClickListener(viewer, KeywordsEditor.ID);
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

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			@SuppressWarnings("unchecked")
			List<Keyword> n = (List<Keyword>) parent; 
			Object[] resultados = n.toArray(new Keyword[n.size()]);
			return resultados;
		}
	}

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			String resultado = "";
			Keyword k = (Keyword) obj;
			switch (index) {
			case 0:
				resultado = Long.toString(k.getIdKeyword());
				break;
			case 1:
				resultado = k.getCodigo();
				break;
			case 2:
				resultado = k.getDescripcion();
				break;
			case 3:
				resultado = k.getTipo();
				break;
			case 4:
				resultado = formUtils.valor2Txt(k.getPosicion());
				break;
			case 5:
				resultado = k.getEstado();
				break;
			}
			return resultado;
		}
		public Image getColumnImage(Object obj, int index) {
			return null;		
		}
	}
}
