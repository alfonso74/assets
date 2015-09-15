package rcp.assets.forms;

import java.util.Collection;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import rcp.assets.model.Direccion;
import rcp.assets.model.Responsable;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;

public class DireccionesMasterPage extends MasterDetailsBlock {

	private FormToolkit toolkit;
	private FormPage page;
	private Table table;
	private Responsable registro;

	/**
	 * Create the master details block.
	 */
	public DireccionesMasterPage(FormPage page, Responsable registro) {
		this.page = page;
		this.registro = registro;
	}
	
	public DireccionesMasterPage(Responsable registro) {
		this.registro = registro;
	}

	/**
	 * Create contents of the master details block.
	 * @param managedForm
	 * @param parent
	 */
	@Override
	protected void createMasterPart(final IManagedForm managedForm, Composite parent) {
		toolkit = managedForm.getToolkit();
		//		
		Section sctnListadoDeDirecciones = toolkit.createSection(parent,
				Section.EXPANDED | Section.TITLE_BAR);
		sctnListadoDeDirecciones.setText("Listado de direcciones alternas");
		sctnListadoDeDirecciones.marginWidth = 10;
		sctnListadoDeDirecciones.marginHeight = 10;
		//
		Composite composite = toolkit.createComposite(sctnListadoDeDirecciones, SWT.NONE);
		toolkit.paintBordersFor(composite);
		sctnListadoDeDirecciones.setClient(composite);
		FillLayout fl_composite = new FillLayout(SWT.HORIZONTAL);
		fl_composite.marginHeight = 5;
		composite.setLayout(fl_composite);
		
		TableViewer viewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table = viewer.getTable();
		toolkit.paintBordersFor(table);
		
		final SectionPart spart = new SectionPart(sctnListadoDeDirecciones);
		managedForm.addPart(spart);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				managedForm.fireSelectionChanged(spart, event.getSelection());
			}
		});
		viewer.setContentProvider(new MasterContentProvider());
		viewer.setLabelProvider(new MasterLabelProvider());
		viewer.setInput(registro.getListaDirecciones());
	}

	
	/**
	 * Register the pages.
	 * @param part
	 */
	@Override
	protected void registerPages(DetailsPart part) {
		part.registerPage(Direccion.class, new DireccionesDetailsPage());
	}
	

	/**
	 * Create the toolbar actions.
	 * @param managedForm
	 */
	@Override
	protected void createToolBarActions(IManagedForm managedForm) {
	}
	
	
	class MasterContentProvider implements IStructuredContentProvider {
		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			@SuppressWarnings("unchecked")
			Collection<Direccion> n = (Collection<Direccion>) inputElement; 
			Object[] resultados = n.toArray(new Direccion[n.size()]);
			return resultados;
		}
	}
	
	class MasterLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			return element.toString();
		}
	}
}
