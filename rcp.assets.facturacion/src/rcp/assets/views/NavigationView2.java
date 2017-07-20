package rcp.assets.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.nebula.widgets.pshelf.RedmondShelfRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import rcp.assets.editors.GenericEditorInput;
import rcp.assets.editors.ReportesEditor;
import rcp.assets.editors.ReportesEditorInput;
import rcp.assets.facturacion.Application;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TableColumn;


public class NavigationView2 extends AbstractViewPart {
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Table table_1;
	private Table table_2;
	private Table table_3;
	private Table table_4;
	private Tree tree_5;
	
	private final String MAIN_MENU_VIEW_ID = "rcp.assets.views.NavigationView2";


	public NavigationView2() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void refrescar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));

		PShelf shelf = new PShelf(parent, SWT.BORDER);
		RedmondShelfRenderer renderer = new RedmondShelfRenderer();
		shelf.setRenderer(renderer);

		//set coloring gradient on shelf header
		renderer.setGradient1(new Color(null, 100, 150, 240));
		renderer.setGradient2(new Color(null, 40, 80, 160));

		//renderer.setGradient1(new Color(null, 90, 115, 220));
		//renderer.setGradient2(new Color(null, 20, 35, 100));

		//set a selected color
		renderer.setSelectedGradient1(new Color(null, 210, 230, 255));
		renderer.setSelectedGradient2(new Color(null, 160, 200, 255));

		//set a hover color
		//renderer.setHoverGradient1(new Color(null, 255, 0, 0));
		//renderer.setHoverGradient1(new Color(null, 110, 0, 0));


		PShelfItem shelfItem_1 = new PShelfItem(shelf, SWT.NONE);
		shelfItem_1.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/administrative_tools_16.png"));
		shelfItem_1.setText("Mantenimientos");
		shelfItem_1.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));

		TableViewer tableViewer1 = new TableViewer(shelfItem_1.getBody(), SWT.BORDER | SWT.FULL_SELECTION);
		table_1 = tableViewer1.getTable();
		table_1.setHeaderVisible(false);
		formToolkit.paintBordersFor(table_1);
		agregarColumnas(table_1);
		/*
		TableColumn tblclmnBlanco = new TableColumn(table_1, SWT.NONE);
		tblclmnBlanco.setWidth(10);
		tblclmnBlanco.setText("img");
		TableColumn tblclmnNombre = new TableColumn(table_1, SWT.NONE);
		tblclmnNombre.setWidth(100);
		tblclmnNombre.setText("nombre");
		 */
		PShelfItem shelfItem_2 = new PShelfItem(shelf, SWT.NONE);
		shelfItem_2.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/coins_16.png"));
		shelfItem_2.setText("Facturaci\u00F3n");
		shelfItem_2.getBody().setBackground(new Color(null, 110, 0, 0));
		shelfItem_2.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));

		TableViewer tableViewer2 = new TableViewer(shelfItem_2.getBody(), SWT.BORDER | SWT.FULL_SELECTION);
		table_2 = tableViewer2.getTable();
		formToolkit.paintBordersFor(table_2);
		agregarColumnas(table_2);

		PShelfItem shelfItem_3 = new PShelfItem(shelf, SWT.NONE);
		shelfItem_3.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/folder_invoices_16.png"));
		shelfItem_3.setText("Seguimiento");
		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		shelfItem_3.getBody().setLayout(fillLayout);

		TableViewer tableViewer3 = new TableViewer(shelfItem_3.getBody(), SWT.BORDER | SWT.FULL_SELECTION);
		table_3 = tableViewer3.getTable();
		formToolkit.paintBordersFor(table_3);
		agregarColumnas(table_3);

		PShelfItem shelfItem_4 = new PShelfItem(shelf, SWT.NONE);
		shelfItem_4.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/bar_chart_16.png"));
		shelfItem_4.setText("Reportes");
		shelfItem_4.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));

		TableViewer tableViewer4 = new TableViewer(shelfItem_4.getBody(), SWT.BORDER | SWT.FULL_SELECTION);
		table_4 = tableViewer4.getTable();
		formToolkit.paintBordersFor(table_4);
		agregarColumnas(table_4);

		PShelfItem shelfItem_5 = new PShelfItem(shelf, SWT.NONE);
		shelfItem_5.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/safe_16.png"));
		//shelfItem_5.setImage(SWTResourceManager.getImage(NavigationView2.class, "/icons/full/etool16/pin_editor.gif"));
		shelfItem_5.setText("Hist\u00F3ricos");
		shelfItem_5.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));

		TreeViewer treeViewer5 = new TreeViewer(shelfItem_5.getBody(), SWT.BORDER);
		tree_5 = treeViewer5.getTree();
		tree_5.setLinesVisible(true);
		formToolkit.paintBordersFor(tree_5);

		// seleccionamos la sección que está seleccionada por default
		shelf.setSelection(shelfItem_1);

		// asignamos los ContentProvider, LabelProvider y el Input para todos los viewers
		tableViewer1.setContentProvider(new ViewContentProvider());
		tableViewer1.setLabelProvider(new ViewLabelProvider2());
		tableViewer1.setInput(crearMenuMantenimiento());

		tableViewer2.setContentProvider(new ViewContentProvider());
		tableViewer2.setLabelProvider(new ViewLabelProvider2());
		tableViewer2.setInput(crearMenuFacturacion());

		tableViewer3.setContentProvider(new ViewContentProvider());
		tableViewer3.setLabelProvider(new ViewLabelProvider2());
		tableViewer3.setInput(crearMenuConsultas());

		tableViewer4.setContentProvider(new ViewContentProvider());
		tableViewer4.setLabelProvider(new ViewLabelProvider2());
		tableViewer4.setInput(crearMenuReportes());

		treeViewer5.setContentProvider(new ViewContentProvider2());
		treeViewer5.setLabelProvider(new ViewLabelProvider());
		treeViewer5.setInput(crearMenuHistorico());

		// agregamos soporte para activar las entradas de menú con un solo click
		agregarSingleClickListener(tableViewer1);
		agregarSingleClickListener(tableViewer2);
		agregarSingleClickListener(tableViewer3);
		agregarSingleClickListener(tableViewer4);
		agregarSingleClickListener(treeViewer5);

	}

	/**
	 * Agrega dos columnas a la tabla suministrada como parámetro, una para la imagen en blanco
	 * usada como separador, y la otra para la entrada de menú.
	 * @param table Tabla a la que se agregan las columnas
	 */
	private void agregarColumnas(Table table) {
		TableColumn tblclmnBlanco = new TableColumn(table, SWT.NONE);
		tblclmnBlanco.setWidth(10);
		tblclmnBlanco.setText("img");
		TableColumn tblclmnNombre = new TableColumn(table, SWT.NONE);
		tblclmnNombre.setWidth(120);
		tblclmnNombre.setText("nombre");
	}


	@Override
	protected void inicializarViewer() {
		// requerido por al AbstractViewPart
	}


	/**
	 * Permite activar las entradas de un viewer con un solo click (el default es
	 * con doble click).
	 * @param viewer puede ser TableViewer o TreeViewer
	 */
	private void agregarSingleClickListener(final StructuredViewer viewer) {
		Control control = viewer.getControl();
		TypedListener listener = new TypedListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("SELECT");
				String viewId = "";
				String viewName = "";
				IWorkbenchWindow window = getSite().getWorkbenchWindow();
				Object seleccion = ((IStructuredSelection) viewer.getSelection()).getFirstElement();

				if (seleccion instanceof MenuModel) {
					MenuModel seleccionMenu = (MenuModel) seleccion;
					viewId = seleccionMenu.getViewId();
					viewName = seleccionMenu.getName();
					if (viewId.equals("")) {
						MessageDialog.openInformation(window.getShell(), "Aviso", "La vista '" + viewName + "' no está habilitada.");
					} else {
						System.out.println("Vista seleccionada: " + viewName);
						try {
							if (viewId.equals("rcp.assets.views.ConsultarCargosView") | viewId.equals("rcp.assets.views.BuscarCaso")) {
								getSite().getPage().closeAllEditors(true);
								IViewReference[] viewList = getSite().getPage().getViewReferences();
								for (int n = 0; n < viewList.length; n++) {
									System.out.println("Vista: " + viewList[n].getPartName());
									if (!viewList[n].getId().equals(MAIN_MENU_VIEW_ID)) {
										getSite().getPage().hideView(viewList[n]);
									}
								}
								getSite().getPage().showView(viewId);
								getSite().getPage().setEditorAreaVisible(false);
							} else if (viewId.equals("rcp.assets.editors.FacturarCasosEditor") | viewId.equals("rcp.assets.editors.FacturarCargos")
									| viewId.equals("rcp.assets.editors.FacturarRetainersEditor") | viewId.equals("rcp.assets.editors.FacturarAnualidadesEditor")
									| viewId.equals("rcp.assets.editors.ExportarPeachtreeEditor")) {
								IViewReference[] viewList = getSite().getPage().getViewReferences();
								for (int n = 0; n < viewList.length; n++) {
									System.out.println("Vista: " + viewList[n].getPartName());
									if (!viewList[n].getId().equals(MAIN_MENU_VIEW_ID)) {
										getSite().getPage().hideView(viewList[n]);
									}
								}
								System.out.println("Editor: " + viewId);
								getSite().getPage().openEditor(new GenericEditorInput(viewId), viewId);
								//getSite().getPage().setEditorAreaVisible(false);
							} else if (viewId.equals("ReporteTipoCargos")){
								IViewReference[] viewList = getSite().getPage().getViewReferences();
								for (int n = 0; n < viewList.length; n++) {
									System.out.println("Vista: " + viewList[n].getPartName());
									if (!viewList[n].getId().equals(MAIN_MENU_VIEW_ID)) {
										getSite().getPage().hideView(viewList[n]);
									}
								}
								System.out.println("Reporte: " + viewId);
								getSite().getPage().openEditor(new ReportesEditorInput("Tipo de Cargos", "/reports/tipoCargosReporte.rptdesign"), ReportesEditor.ID);
							} else if (viewId.equals("ReporteFacturacionEspecial")){
								IViewReference[] viewList = getSite().getPage().getViewReferences();
								for (int n = 0; n < viewList.length; n++) {
									System.out.println("Vista: " + viewList[n].getPartName());
									if (!viewList[n].getId().equals(MAIN_MENU_VIEW_ID)) {
										getSite().getPage().hideView(viewList[n]);
									}
								}
								System.out.println("Reporte: " + viewId);
								getSite().getPage().openEditor(new ReportesEditorInput("Facturación especial", "/reports/facturacionEspecialReporte.rptdesign"), ReportesEditor.ID);
							} else if (viewId.equals("ReporteFacturas")){
								IViewReference[] viewList = getSite().getPage().getViewReferences();
								for (int n = 0; n < viewList.length; n++) {
									System.out.println("Vista: " + viewList[n].getPartName());
									if (!viewList[n].getId().equals(MAIN_MENU_VIEW_ID)) {
										getSite().getPage().hideView(viewList[n]);
									}
								}
								System.out.println("Reporte: " + viewId);
								getSite().getPage().openEditor(new ReportesEditorInput("Facturas", "/reports/facturaReporte.rptdesign"), ReportesEditor.ID);
							} else {
								//getSite().getPage().setEditorAreaVisible(true);
								getSite().getPage().resetPerspective();
								getSite().getPage().showView(viewId);
							}
						} catch (PartInitException ex) {
							mensajeError(window.getShell(), ex);
							ex.printStackTrace();
						}
					}
				} else {
					MessageDialog.openInformation(window.getShell(), "Aviso", "La entrada de menú tiene un formato desconocido: " + viewName);
				}
			}
		});
		control.addListener(SWT.Selection, listener);
	}




	class ViewContentProvider implements IStructuredContentProvider {

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return (MenuModel[]) inputElement;
		}

	}


	class ViewLabelProvider extends LabelProvider {

		@Override
		public Image getImage(Object element) {
			Image imagen = ((MenuModel) element).getImage(); 
			return imagen;
		}

		@Override
		public String getText(Object element) {
			return ((MenuModel) element).getName();
		}

	}

	/**
	 * LabelProvider para varias columnas en una tabla.
	 * @author Carlos Alfonso
	 *
	 */
	class ViewLabelProvider2 extends LabelProvider implements ITableLabelProvider {

		//ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, "icons/blanco.gif");
		Image imageWhite = ResourceManager.getPluginImage(Application.PLUGIN_ID, "icons/blanco.gif");
		Image image = null;

		@Override
		public String getColumnText(Object element, int columnIndex) {
			String resultado = "";
			resultado = "";

			switch (columnIndex) {
			case 0:
				break;
			case 1:
				resultado = ((MenuModel) element).getName();
				break;
			}

			return resultado;
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			switch (columnIndex) {
			case 0:
				image = imageWhite;
				break;
			case 1:
				image = ((MenuModel) element).getImage();
				break;
			}
			return image;
		}
	}


	public class MenuModel {
		private String name;
		private String viewId;
		@SuppressWarnings("unused")
		private String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		private Image image = null;

		public MenuModel(String name, String viewId, String imageKey) {
			this.name = name;
			this.viewId = viewId;
			this.imageKey = imageKey;
			if (!imageKey.equals("")) {
				ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, imageKey);
				image = imageDescriptor.createImage();
			}
		}

		public String getName() {
			return name;
		}
		public String getViewId() {
			return viewId;
		}
		public Image getImage() {
			return image;
		}
		public void setImage(Image image) {
			this.image = image;
		}
		public String toString() {
			return "Nombre: " + getName();
		}
	}


	class ViewContentProvider2 implements IStructuredContentProvider, ITreeContentProvider {

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			return null;
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return false;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return (MenuModel[]) inputElement;
		}

	}


	private MenuModel[] crearMenuMantenimiento() {
		MenuModel[] elements = new MenuModel[8];
		elements[0] = new MenuModel("Keywords", "rcp.assets.views.KeywordsView", "icons/mantenimiento.gif");
		elements[1] = new MenuModel("Usuarios", "rcp.assets.views.UsuariosView", "icons/mantenimiento.gif");
		elements[2] = new MenuModel("Responsables", "rcp.assets.views.ResponsablesView", "icons/mantenimiento.gif");
		elements[3] = new MenuModel("Direcciones", "rcp.assets.views.DireccionesView", "icons/mantenimiento.gif");
		elements[4] = new MenuModel("Atenciones", "rcp.assets.views.AtencionesView", "icons/mantenimiento.gif");
		elements[5] = new MenuModel("Tipo de cargos", "rcp.assets.views.TipoCargosView", "icons/mantenimiento.gif");
		elements[6] = new MenuModel("Recargos", "rcp.assets.views.RecargosView", "icons/mantenimiento.gif");
		elements[7] = new MenuModel("Notificaciones", "rcp.assets.views.NotificacionesView", "icons/mantenimiento.gif");
		return elements;
	}

	private MenuModel[] crearMenuFacturacion() {
		int nFact = 0;
		MenuModel[] elements = new MenuModel[5];
		elements[nFact++] = new MenuModel("Casos", "rcp.assets.editors.FacturarCasosEditor", "icons/menuReservas.gif");
		elements[nFact++] = new MenuModel("Cargos", "rcp.assets.editors.FacturarCargos", "icons/bombita.png");
		elements[nFact++] = new MenuModel("Anualidades", "rcp.assets.editors.FacturarAnualidadesEditor", "icons/cargo_anual.png");
		elements[nFact++] = new MenuModel("Retainers", "rcp.assets.editors.FacturarRetainersEditor", "icons/cargo_anual.png");
		elements[nFact++] = new MenuModel("Peachtree", "rcp.assets.editors.ExportarPeachtreeEditor", "icons/peachtree_16.png");
		return elements;
	}

	private MenuModel[] crearMenuConsultas() {
		int nConsultas = 0;
		MenuModel[] elements = new MenuModel[6];
		elements[nConsultas++] = new MenuModel("Responsables", "rcp.assets.views.seguimiento.ResponsablesSegView", "icons/user_16.png");
		elements[nConsultas++] = new MenuModel("Tipo de cargos", "ReporteTipoCargos", "icons/bombita.png");
		elements[nConsultas++] = new MenuModel("Casos", "rcp.assets.views.seguimiento.CasosView", "icons/auction-hammer-icon.png");
		elements[nConsultas++] = new MenuModel("Expedientes", "rcp.assets.views.ExpedientesView", "icons/expedientes.png");
		elements[nConsultas++] = new MenuModel("Fact. especial", "rcp.assets.views.FactEspecialView", "icons/cargo_anual.png");
		elements[nConsultas++] = new MenuModel("Facturas", "rcp.assets.views.seguimiento.FacturasView", "icons/carpeta_16.png");
		return elements;
	}
	
	private MenuModel[] crearMenuReportes() {
		int nConsultas = 0;
		MenuModel[] elements = new MenuModel[4];
		elements[nConsultas++] = new MenuModel("Responsables", "rcp.assets.views.seguimiento.ResponsablesSegView", "icons/user_16.png");
		elements[nConsultas++] = new MenuModel("Tipo de cargos", "ReporteTipoCargos", "icons/bombita.png");
		elements[nConsultas++] = new MenuModel("Fact. especial", "ReporteFacturacionEspecial", "icons/cargo_anual.png");
		elements[nConsultas++] = new MenuModel("Facturas", "ReporteFacturas", "icons/cargo_anual.png");
		return elements;
	}

	private MenuModel[] crearMenuHistorico() {
		int nHistorico = 0;
		MenuModel[] elements = new MenuModel[1];
		//		elements[nHistorico++] = new MenuModel("Proforma en view", "rcp.assets.views.ProformaView", "icons/menuReservas.gif");
		//		elements[nHistorico++] = new MenuModel("XViewer (cargos)", "rcp.assets.views.ConsultarCargosView", "icons/invoice_16.png");
		//		elements[nHistorico++] = new MenuModel("Buscar caso (x view)", "rcp.assets.views.BuscarCaso", "icons/monitor_24.png");
		//		elements[nHistorico++] = new MenuModel("Expedientes lazy", "rcp.assets.views.ExpedientesView", "icons/menuReservas.gif");
		elements[nHistorico++] = new MenuModel("Facturas", "rcp.assets.views.historico.FacturasHistoricoView", "icons/carpeta_16.png");
		return elements;
	}

	private void mensajeError(Shell shell, Exception e) {
		String nombre = this.toString();
		int puntoFinal = nombre.indexOf(".") + 1;
		int arroba = nombre.indexOf("@");
		MessageDialog.openError(shell, "Error en " + nombre.substring(puntoFinal, arroba), "Error: " + e.toString() + "\n\nStack trace: " + e.getStackTrace()[0] + "\n" + e.getStackTrace()[1]);
	}

}

