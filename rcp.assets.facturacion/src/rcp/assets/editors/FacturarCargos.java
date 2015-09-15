package rcp.assets.editors;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import rcp.assets.controllers.DetalleCargosController;
import rcp.assets.controllers.GrupoCargosController;
import rcp.assets.dialogs.AgregarGrupoDialogo;
import rcp.assets.facturacion.Application;
import rcp.assets.model.Cargo;
import rcp.assets.model.Caso;
import rcp.assets.model.DetalleCargo;
import rcp.assets.model.GrupoCargo;
import rcp.assets.services.FechaUtil;

import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.CellEditor;

public class FacturarCargos extends AbstractEditor {

	public static final String ID = "rcp.assets.editors.FacturarCargos"; //$NON-NLS-1$
	private String idSession = ID + FechaUtil.getMilisegundos();
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private DetalleCargosController controller;
	private GrupoCargosController controllerGrupo;

	private Caso caso = null;
	private DetalleCargo detalleCargoSeleccionado = null;
	private GrupoCargo grupoSeleccionado = null;

	private Text txtNoCia;
	private Text txtNoCaso;
	private Table tableDetCargos;
	private Table tableGrupos;
	private TableViewer viewerDetCargos;
	private TableViewer viewerGrupos;



	public FacturarCargos() {
		controller = new DetalleCargosController();
		controllerGrupo = new GrupoCargosController();
	}

	/**
	 * Create contents of the editor part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		ScrolledForm scrldfrmNewScrolledform = formToolkit.createScrolledForm(parent);
		formToolkit.paintBordersFor(scrldfrmNewScrolledform);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.verticalSpacing = 20;
		gridLayout.marginHeight = 10;
		gridLayout.marginWidth = 10;
		scrldfrmNewScrolledform.getBody().setLayout(gridLayout);

		Section sctnConsultaCargos = formToolkit.createSection(scrldfrmNewScrolledform.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnConsultaCargos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.paintBordersFor(sctnConsultaCargos);
		sctnConsultaCargos.setText("Consulta de cargos para facturar");
		sctnConsultaCargos.setExpanded(true);

		Composite compositeConsulta = formToolkit.createComposite(sctnConsultaCargos, SWT.NONE);
		formToolkit.paintBordersFor(compositeConsulta);
		sctnConsultaCargos.setClient(compositeConsulta);
		compositeConsulta.setLayout(new GridLayout(6, false));

		Label lblCompania = formToolkit.createLabel(compositeConsulta, "Compa\u00F1\u00EDa:", SWT.NONE);
		lblCompania.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtNoCia = formToolkit.createText(compositeConsulta, "New Text", SWT.NONE);
		txtNoCia.setText("01");
		GridData gd_txtNoCia = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtNoCia.widthHint = 50;
		txtNoCia.setLayoutData(gd_txtNoCia);
		new Label(compositeConsulta, SWT.NONE);

		Label lblCaso = formToolkit.createLabel(compositeConsulta, "N\u00FAmero de caso:", SWT.NONE);
		lblCaso.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtNoCaso = formToolkit.createText(compositeConsulta, "New Text", SWT.NONE);
		txtNoCaso.setText("");
		//txtNoCaso.setText("12597");
		GridData gd_txtNoCaso = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtNoCaso.widthHint = 60;
		txtNoCaso.setLayoutData(gd_txtNoCaso);

		Button btnBuscar = formToolkit.createButton(compositeConsulta, "Buscar", SWT.NONE);
		btnBuscar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				actualizarDetallesCaso();
			}
		});

		Composite compositeDetalles = formToolkit.createComposite(scrldfrmNewScrolledform.getBody(), SWT.NONE);
		compositeDetalles.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		formToolkit.paintBordersFor(compositeDetalles);
		compositeDetalles.setLayout(new GridLayout(3, false));

		Section sctnDetalleCargos = formToolkit.createSection(compositeDetalles, Section.TWISTIE | Section.TITLE_BAR);
		sctnDetalleCargos.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		//sctnDetalleCargos.setBounds(0, 0, 95, 19);
		formToolkit.paintBordersFor(sctnDetalleCargos);
		sctnDetalleCargos.setText("Detalle de cargos");
		sctnDetalleCargos.setExpanded(true);

		Composite composite = formToolkit.createComposite(sctnDetalleCargos, SWT.NONE);
		formToolkit.paintBordersFor(composite);
		sctnDetalleCargos.setClient(composite);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		viewerDetCargos = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		tableDetCargos = viewerDetCargos.getTable();
		tableDetCargos.setLinesVisible(true);
		tableDetCargos.setHeaderVisible(true);
		formToolkit.paintBordersFor(tableDetCargos);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(viewerDetCargos, SWT.NONE);
		TableColumn tblclmnNocargo = tableViewerColumn.getColumn();
		tblclmnNocargo.setWidth(53);
		tblclmnNocargo.setText("Cargo");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(viewerDetCargos, SWT.NONE);
		TableColumn tblclmnTipoDeCargo = tableViewerColumn_2.getColumn();
		tblclmnTipoDeCargo.setWidth(200);
		tblclmnTipoDeCargo.setText("Tipo de cargo");

		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(viewerDetCargos, SWT.NONE);
		TableColumn tblclmnCodigo = tableViewerColumn_6.getColumn();
		tblclmnCodigo.setAlignment(SWT.RIGHT);
		tblclmnCodigo.setWidth(50);
		tblclmnCodigo.setText("C\u00F3digo");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(viewerDetCargos, SWT.NONE);
		TableColumn tblclmnMonto = tableViewerColumn_3.getColumn();
		tblclmnMonto.setAlignment(SWT.RIGHT);
		tblclmnMonto.setWidth(65);
		tblclmnMonto.setText("Monto");
		
		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(viewerDetCargos, SWT.NONE);
		TableColumn tblclmnPrioridad = tableViewerColumn_7.getColumn();
		tblclmnPrioridad.setAlignment(SWT.CENTER);
		tblclmnPrioridad.setWidth(60);
		tblclmnPrioridad.setText("Prioridad");

		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(viewerDetCargos, SWT.NONE);
		TableColumn tblclmnGrupo_1 = tableViewerColumn_5.getColumn();
		tblclmnGrupo_1.setAlignment(SWT.CENTER);
		tblclmnGrupo_1.setWidth(45);
		tblclmnGrupo_1.setText("Grupo");

		Composite compositeAsignacion = formToolkit.createComposite(compositeDetalles, SWT.NONE);
		formToolkit.paintBordersFor(compositeAsignacion);
		compositeAsignacion.setLayout(new GridLayout(1, false));

		Button btnAsignar = formToolkit.createButton(compositeAsignacion, "Asignar", SWT.NONE);
		GridData gd_btnAsignar = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAsignar.widthHint = 55;
		btnAsignar.setLayoutData(gd_btnAsignar);
		btnAsignar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Detalles seleccionados: " + getElementosSeleccionados(viewerDetCargos));
				System.out.println("Detalle seleccionado: " + getElementoSeleccionado(viewerDetCargos));
				System.out.println("Grupo seleccionado: " + getElementoSeleccionado(viewerGrupos));
				DetalleCargo detCargo = getElementoSeleccionado(viewerDetCargos);
				GrupoCargo grupo = getElementoSeleccionado(viewerGrupos);
				if (detCargo == null | grupo == null) {
					MessageDialog.openInformation(getSite().getShell(), "Asignar grupo", "Debe seleccionar un cargo y un grupo para ejecutar esta acción.");
				} else {
					detCargo.setGrupo(grupo);
					controller.doSave(detCargo);
					actualizarDetallesCaso();
				}
			}
		});

		Button btnCancelar = formToolkit.createButton(compositeAsignacion, "Cancelar", SWT.NONE);
		GridData gd_btnCancelar = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnCancelar.widthHint = 55;
		btnCancelar.setLayoutData(gd_btnCancelar);
		//btnCancelar.setBounds(0, 0, 68, 23);
		btnCancelar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DetalleCargo detCargo = getElementoSeleccionado(viewerDetCargos);
				if (detCargo == null) {
					MessageDialog.openInformation(getSite().getShell(), "Cancelar asignación de grupo", "Debe seleccionar un cargo para ejecutar esta acción.");
				} else {
					detCargo.setGrupo(null);
					controller.doSave(detCargo);
					actualizarDetallesCaso();
				}
			}
		});

		Section sctnAgrupacionCargos = formToolkit.createSection(compositeDetalles, Section.TWISTIE | Section.TITLE_BAR);
		sctnAgrupacionCargos.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		formToolkit.paintBordersFor(sctnAgrupacionCargos);
		sctnAgrupacionCargos.setText("Agrupaci\u00F3n de cargos");
		sctnAgrupacionCargos.setExpanded(true);

		Composite composite_1 = formToolkit.createComposite(sctnAgrupacionCargos, SWT.NONE);
		formToolkit.paintBordersFor(composite_1);
		sctnAgrupacionCargos.setClient(composite_1);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));

		viewerGrupos = new TableViewer(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		tableGrupos = viewerGrupos.getTable();
		tableGrupos.setLinesVisible(true);
		tableGrupos.setHeaderVisible(true);
		formToolkit.paintBordersFor(tableGrupos);

		final TextCellEditor textCellEditor = new TextCellEditor(viewerGrupos.getTable());

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(viewerGrupos, SWT.NONE);
		/*
		tableViewerColumn_1.setEditingSupport(new EditingSupport(viewerGrupos) {
			protected boolean canEdit(Object element) {
				return true;
			}
			protected CellEditor getCellEditor(Object element) {
				return textCellEditor;
			}
			protected Object getValue(Object element) {
				return ((GrupoCargo) element).getGrupo() + "";
			}
			protected void setValue(Object element, Object value) {
				((GrupoCargo) element).setGrupo(Integer.parseInt(value.toString()));
				viewerGrupos.update(element, null);
			}
		});
		 */
		TableColumn tblclmnGrupo = tableViewerColumn_1.getColumn();
		tblclmnGrupo.setWidth(50);
		tblclmnGrupo.setText("Grupo");

		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(viewerGrupos, SWT.NONE);
		//tableViewerColumn_4.setEditingSupport(new GrupoCellEditor(viewerGrupos));
		TableColumn tblclmnDescripcion = tableViewerColumn_4.getColumn();
		tblclmnDescripcion.setWidth(180);
		tblclmnDescripcion.setText("Descripci\u00F3n");

		createSectionToolbar(sctnAgrupacionCargos, formToolkit);

		llenarControles();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// Do the Save operation
	}

	@Override
	public void doSaveAs() {
		// Do the Save As operation
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		GenericEditorInput genericInput = (GenericEditorInput) input;
		setSite(site);
		setInput(genericInput);
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	protected void llenarControles() {
		viewerDetCargos.setContentProvider(new ViewContentProviderDetCargos());
		viewerDetCargos.setLabelProvider(new ViewLabelProviderDetCargos());
		viewerGrupos.setContentProvider(new ViewContentProviderGrupos());
		viewerGrupos.setLabelProvider(new ViewLabelProviderGrupos());

		// registramos el tableViewer como un selection provider
		// esto es requerido para obtener el elemento seleccionado en las acciones
		getSite().setSelectionProvider(viewerDetCargos);
		getSite().setSelectionProvider(viewerGrupos);
	}


	/**
	 * Obtiene el listado de Detalles de Cargo y de Grupos que estan asociados a un caso, y
	 * actualiza los viewers.
	 */
	public void actualizarDetallesCaso() {
		//Caso registro = null;
		String noCia = txtNoCia.getText();
		Long noCaso = Long.valueOf(txtNoCaso.getText());
		List<DetalleCargo> resultado = controller.buscarDetalleCargosPorCaso(noCia, txtNoCaso.getText());
		viewerDetCargos.setInput(resultado);
		List<GrupoCargo> resultado2 = controllerGrupo.getGruposByCaso(noCia, noCaso);
		viewerGrupos.setInput(resultado2);
		if (resultado != null && resultado.size() > 0) {
			Cargo cargo = resultado.get(0).getCargo();
			System.out.println("CARGO: " + cargo);
			caso = cargo.getCaso();
			System.out.println("CASO: " + caso);
		}
	}


	/**
	 * Obtiene los elementos seleccionados en un viewer
	 * @param viewer
	 * @return lista de elementos seleccionados o null si no hay nada seleccionado
	 */
	public <T> List<T> getElementosSeleccionados(StructuredViewer viewer) {
		List<T> seleccion = null;
		if (!viewer.getSelection().isEmpty()) {
			seleccion = (List<T>) ((IStructuredSelection) viewer.getSelection()).toList();
		}
		return seleccion;
	}

	/**
	 * Obtiene el primer elemento seleccionado en un viewer
	 * @param viewer
	 * @return elemento seleccionado en el viewer o null si no hay nada seleccionado
	 */
	public <T> T getElementoSeleccionado(StructuredViewer viewer) {
		T seleccion = null;
		if (!viewer.getSelection().isEmpty()) {
			seleccion = (T) ((IStructuredSelection) viewer.getSelection()).getFirstElement();
		}
		return seleccion;
	}


	private void createSectionToolbar(Section section, FormToolkit toolkit) {
		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
		ToolBar toolbar = toolBarManager.createControl(section);
		final Cursor handCursor = new Cursor(Display.getCurrent(),
				SWT.CURSOR_HAND);
		toolbar.setCursor(handCursor);
		// Cursor needs to be explicitly disposed
		toolbar.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if ((handCursor != null) && (handCursor.isDisposed() == false)) {
					handCursor.dispose();
				}
			}
		});

		// Acción de crear nuevo grupo
		CommandContributionItemParameter saveContributionParameter = new CommandContributionItemParameter(
				getSite(), null,
				"rcp.assets.commands.AgregarGrupoHandler",
				CommandContributionItem.MODE_FORCE_TEXT);
		saveContributionParameter.icon = AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID,"/icons/agregar.gif");
		CommandContributionItem saveMenu = new CommandContributionItem(saveContributionParameter);

		// Acción de borrar grupo de cargos
		CommandContributionItemParameter borrarGrupo = new CommandContributionItemParameter(
				getSite(), null,
				"rcp.assets.commands.BorrarGrupoHandler",
				CommandContributionItem.MODE_FORCE_TEXT);
		borrarGrupo.icon = AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID,"/icons/borrar.gif");
		CommandContributionItem borrarGrupoItem = new CommandContributionItem(borrarGrupo);

		toolBarManager.add(saveMenu);
		toolBarManager.add(borrarGrupoItem);
		toolBarManager.update(true);

		section.setTextClient(toolbar);
	}


	class ViewContentProviderDetCargos implements IStructuredContentProvider {
		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public Object[] getElements(Object parent) {
			@SuppressWarnings("unchecked")
			Collection<DetalleCargo> n = (Collection<DetalleCargo>) parent; 
			Object[] resultados = n.toArray(new DetalleCargo[n.size()]);
			return resultados;
		}
	}


	class ViewLabelProviderDetCargos extends LabelProvider implements ITableLabelProvider {
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			String resultado = "";
			DetalleCargo k = (DetalleCargo) element;
			switch (columnIndex) {
			case 0:
				resultado = k.getCargo().getNoCargo();
				break;
			case 1:
				resultado = k.getTipoCargo().getDescripcion();
				break;
			case 2:
				resultado = Integer.toString(k.getTipoCargo().getNoTipoCargo());
				break;
			case 3:
				resultado = valor2Txt(k.getMonto(), "#,##0.00");
				break;
			case 4:
				resultado = k.getTipoCargo().getPrioridad().toString();
				break;
			case 5:
				resultado = k.getGrupo() == null ? "" : Integer.toString((k.getGrupo().getGrupo())); 
				break;
			}
			return resultado;
		}
	}



	class ViewContentProviderGrupos implements IStructuredContentProvider {
		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public Object[] getElements(Object parent) {
			@SuppressWarnings("unchecked")
			Collection<GrupoCargo> n = (Collection<GrupoCargo>) parent; 
			Object[] resultados = n.toArray(new GrupoCargo[n.size()]);
			return resultados;
		}
	}


	class ViewLabelProviderGrupos extends LabelProvider implements ITableLabelProvider {
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			String resultado = "";
			GrupoCargo k = (GrupoCargo) element;
			switch (columnIndex) {
			case 0:
				resultado = String.valueOf(k.getGrupo());
				break;
			case 1:
				resultado = k.getDescripcion();
				break;
			}
			return resultado;
		}
	}


	public void ejecutarAgregarGrupo() {
		GrupoCargo nuevoGrupo = new GrupoCargo();
		nuevoGrupo.setGrupo(0);    // indica al dialogo que el registro es nuevo
		AgregarGrupoDialogo d = new AgregarGrupoDialogo(getSite().getShell(), nuevoGrupo);
		if (d.open() == 0) {
			nuevoGrupo.setCaso(getCaso());
			System.out.println("id: " + nuevoGrupo.getGrupo());
			System.out.println("desc: " + nuevoGrupo.getDescripcion());
			controllerGrupo.doSave(nuevoGrupo);
			actualizarDetallesCaso();
		} else {
			System.out.println("Accion cancelada");
		}
	}


	public void ejecutarBorrarGrupo() {
		List<GrupoCargo> seleccion = getElementosSeleccionados(viewerGrupos);
		if (seleccion == null || seleccion.isEmpty()) {
			MessageDialog.openInformation(getSite().getShell(), "Borrar grupo", "Debe seleccionar un grupo para ejecutar esta acción.");
		} else if (seleccion.size() > 1) {
			MessageDialog.openInformation(getSite().getShell(), "Borrar grupo", "Solo puede seleccionar un grupo a ser eliminado.");
		} else {
			GrupoCargo grupo = getElementoSeleccionado(viewerGrupos);
			boolean flag = MessageDialog.openConfirm(getSite().getShell(), "Borrar grupo", "Está seguro de borrar el grupo: " + grupo.getDescripcion() + "?");
			if (flag) {
				if (controllerGrupo.hasCargosAsociados(grupo)) {
					MessageDialog.openInformation(getSite().getShell(), "Borrar grupo", "No puede eliminar un grupo que tenga cargos asociados. Debe cancelar la\n" +
							"asociación y luego ejecutar esta acción.");
				} else {
					controllerGrupo.doDelete(grupo);
					actualizarDetallesCaso();
				}
			}
		}
	}


	public Caso getCaso() {
		return caso;
	}

	public void setCaso(Caso caso) {
		this.caso = caso;
	}

	public DetalleCargo getDetalleCargoSeleccionado() {
		detalleCargoSeleccionado = getElementoSeleccionado(viewerDetCargos);
		return detalleCargoSeleccionado;
	}

	public void setDetalleCargoSeleccionado(DetalleCargo detalleCargoSeleccionado) {
		this.detalleCargoSeleccionado = detalleCargoSeleccionado;
	}

	public GrupoCargo getGrupoSeleccionado() {
		grupoSeleccionado = getElementoSeleccionado(viewerGrupos);
		return grupoSeleccionado;
	}

	public void setGrupoSeleccionado(GrupoCargo grupoSeleccionado) {
		this.grupoSeleccionado = grupoSeleccionado;
	}
}
