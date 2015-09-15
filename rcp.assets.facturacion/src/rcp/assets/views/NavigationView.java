package rcp.assets.views;

import java.util.ArrayList;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;

import rcp.assets.facturacion.Application;
import rcp.assets.services.IImageKeys;

public class NavigationView extends ViewPart {

	//public static final String ID = "rcp.assets.views.NavigationView"; //$NON-NLS-1$
	public static final String ID = Class.class.getName();
	private TreeViewer viewer;
	private TreeParent p1;

	public NavigationView() {
	}
	
	
	class TreeObject {
		private String name;
		private String viewId;
		private TreeParent parent;
		private String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		private Image image;
		
		public TreeObject(String name) {
			this.name = name;
			image = PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
		}
		public TreeObject(String name, String imageKey) {
			this.name = name;
			this.imageKey = imageKey;
			ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, imageKey);
			image = imageDescriptor.createImage();
		}
		public TreeObject(String name, String viewId, String imageKey) {
			this.name = name;
			this.viewId = viewId;
			this.imageKey = imageKey;
			ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, imageKey);
			image = imageDescriptor.createImage();
		}
		
		public String getName() {
			return name;
		}
		public String getViewId() {
			return viewId;
		}
		public void setParent(TreeParent parent) {
			this.parent = parent;
		}
		public TreeParent getParent() {
			return parent;
		}
		public Image getImage() {
			return image;
		}
		public void setImage(Image image) {
			this.image = image;
		}
		public String toString() {
			return getName();
		}
	}
	
	class TreeParent extends TreeObject {
		private ArrayList<TreeObject> children;
		private String imageKey = ISharedImages.IMG_OBJ_FOLDER;
		
		public TreeParent(String name) {
			super(name);
			setImage(PlatformUI.getWorkbench().getSharedImages().getImage(imageKey));
			children = new ArrayList<TreeObject>();
		}
		public TreeParent(String name, String imageKey) {
			super(name, imageKey);
		}
		public void addChild(TreeObject child) {
			children.add(child);
			child.setParent(this);
		}
		public void removeChild(TreeObject child) {
			children.remove(child);
			child.setParent(null);
		}
		public TreeObject[] getChildren() {
			return (TreeObject[]) children.toArray(new TreeObject[children.size()]);
		}
		public boolean hasChildren() {
			return children.size()>0;
		}
	}

	class ViewContentProvider implements IStructuredContentProvider, 
										   ITreeContentProvider {

        public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
        
		public void dispose() {
		}
        
		public Object[] getElements(Object parent) {
			return getChildren(parent);
		}
        
		public Object getParent(Object child) {
			if (child instanceof TreeObject) {
				return ((TreeObject)child).getParent();
			}
			return null;
		}
        
		public Object[] getChildren(Object parent) {
			if (parent instanceof TreeParent) {
				return ((TreeParent)parent).getChildren();
			}
			return new Object[0];
		}

        public boolean hasChildren(Object parent) {
			if (parent instanceof TreeParent)
				return ((TreeParent)parent).hasChildren();
			return false;
		}
	}
	
	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			return obj.toString();
		}
		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			Image image = PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);;
			if (obj instanceof TreeObject) {
				if (((TreeObject) obj).toString().equals("")) {
					return null;
				} else {
					image = ((TreeObject) obj).getImage();
				}
			};
			if (obj instanceof TreeParent) {
				//imageKey = ISharedImages.IMG_OBJ_FOLDER;
				//image = PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
				image = ((TreeParent) obj).getImage();
			};
			return image;
		}
	}

    /**
     * We will set up a dummy model to initialize tree hierarchy. In real
     * code, you will connect to a real model and expose its hierarchy.
     */
	private TreeObject crearMenu() {
		p1 = new TreeParent("Mantenimientos");
		TreeObject to101 = new TreeObject("Keywords", Vista00.ID, "icons/mantenimiento.gif");
		TreeObject to102 = new TreeObject("Etapas", Vista00.ID, IImageKeys.CONFIG);
		TreeObject to103 = new TreeObject("Secciones", Vista00.ID, IImageKeys.CONFIG);
		TreeObject to104 = new TreeObject("Usuarios", Vista00.ID, IImageKeys.CONFIG);
		TreeParent p4 = new TreeParent("Consultas");
		TreeObject to401 = new TreeObject("Transit 00", Vista00.ID, "icons/menuReservas.gif");
		TreeObject to402 = new TreeObject("Transit In", Vista00.ID, "icons/menuReservas.gif");
		TreeObject to403 = new TreeObject("Bodega In", Vista00.ID, "icons/menuReservas.gif");
		TreeObject to404 = new TreeObject("Bodega Al", Vista00.ID, "icons/menuReservas.gif");
		TreeObject to405 = new TreeObject("Bodega Out", Vista00.ID, "icons/menuReservas.gif");
		// Paquetes asignados a un vehículo, y en ruta a ser entregados
		//TreeObject to406 = new TreeObject("Transit Out", "", "icons/menuReservas.gif");
		TreeObject to406 = new TreeObject("", "", "icons/blanco.gif");
		TreeObject to407 = new TreeObject("Finalizados", Vista00.ID, "icons/menuReservas.gif");
		TreeObject to408 = new TreeObject("Cancelados", Vista00.ID, "icons/menuReservas.gif");
		
		p1.addChild(to101);
		p1.addChild(to102);
		p1.addChild(to103);
		p1.addChild(to104);
		
		//p1.addChild(new TreeObject("Roles", "", IImageKeys.CONFIG));
		//p1.addChild(new TreeObject("Paquetes", PaquetesView.ID, IImageKeys.CONFIG));
		//p1.addChild(new TreeObject("PaquetesTableC", PaquetesTCView.ID, IImageKeys.CONFIG));
		
		p4.addChild(to401);
		p4.addChild(to402);
		p4.addChild(to403);
		p4.addChild(to404);
		p4.addChild(to405);
		p4.addChild(to406);
		p4.addChild(to407);
		p4.addChild(to408);
		
		TreeParent root = new TreeParent("");
		root.addChild(p1);
		root.addChild(new TreeObject(""));
		root.addChild(p4);
		
		return root;
	}
	

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	//@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		viewer = new TreeViewer(container, SWT.BORDER);
		//Tree tree = viewer.getTree();
		
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(crearMenu());
		//viewer.expandAll();
		viewer.expandToLevel(p1, 1);
		
		final Tree tree = viewer.getTree();
		
		tree.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String viewId = "";
				String viewName = "";
				Object seleccion = getElementoSeleccionado(viewer);
				if (seleccion instanceof TreeParent) {
					// no hacemos nada, es un TreeParent
				} else {   // es un TreeObject
					//IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					IWorkbenchWindow window = getSite().getWorkbenchWindow();
					viewId = ((TreeObject) seleccion).getViewId();
					viewName = ((TreeObject) seleccion).getName();
					if (viewId.equals("")) {
						MessageDialog.openInformation(window.getShell(), "Aviso", "La vista '" + viewName + "' no está habilitada.");
					} else {
						try {
							getSite().getPage().showView(viewId);
							//TODO: eliminar esta prueba de perspectivas cuando finalizemos el layout del cliente
							if (viewName.equals("Guías")) {
								//IPerspectiveDescriptor perspectiva = PlatformUI.getWorkbench().getPerspectiveRegistry().findPerspectiveWithId(VentasPerspective.ID);
								//window.getActivePage().setPerspective(perspectiva);
							}
						} catch(PartInitException ex) {
							//MessageDialog.openError(window.getShell(), "Error", "Error abriendo vista: " + viewId);
							mensajeError(window.getShell(), ex);
							ex.printStackTrace();
						};	
					}
				}
				
				

			}
		});
		
		// registramos el tableViewer como un selection provider
		getSite().setSelectionProvider(viewer);

		createActions();
		initializeToolBar();
		initializeMenu();
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
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		@SuppressWarnings("unused")
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}
	
	
	private Object getElementoSeleccionado(TreeViewer viewer) {
		Object seleccion = ((IStructuredSelection) viewer.getSelection()).getFirstElement();
		return seleccion;
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	private void mensajeError(Shell shell, Exception e) {
		String nombre = this.toString();
		int puntoFinal = nombre.indexOf(".") + 1;
		int arroba = nombre.indexOf("@");
		MessageDialog.openError(shell, "Error en " + nombre.substring(puntoFinal, arroba), "Error: " + e.toString() + "\n\nStack trace: " + e.getStackTrace()[0] + "\n" + e.getStackTrace()[1]);
	}
	
}
