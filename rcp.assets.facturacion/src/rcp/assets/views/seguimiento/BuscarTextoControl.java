package rcp.assets.views.seguimiento;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.ResourceManager;

import rcp.assets.views.filters.ResponsablesFilter;


public class BuscarTextoControl {
	
	private FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	
	private TableViewer viewer;
	private ResponsablesFilter filtro;
	
	private Text txtFiltro;
	
	private Label lblRegistros;
	private Label lblFiltrados;
	

	public BuscarTextoControl(FormToolkit toolkit, TableViewer viewer) {
		this.toolkit = toolkit;
		this.viewer = viewer;
	}
	
	
	
	public void crearControles(Composite container) {
		Composite composite = toolkit.createComposite(container, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.paintBordersFor(composite);
		GridLayout gl_composite = new GridLayout(5, false);
		gl_composite.marginBottom = 5;
		gl_composite.marginHeight = 0;
		composite.setLayout(gl_composite);

		Label lblFiltrar = toolkit.createLabel(composite, "Filtrar:", SWT.NONE);
		lblFiltrar.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFiltrar.setBounds(0, 0, 49, 13);

		txtFiltro = toolkit.createText(composite, "New Text", SWT.NONE);
		txtFiltro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == 13 || e.keyCode == 16777296) {
					aplicarFiltro(txtFiltro.getText());
				}
			}
		});
		txtFiltro.setText("");
		GridData gd_txtFiltro = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtFiltro.widthHint = 100;
		txtFiltro.setLayoutData(gd_txtFiltro);
		{
			ControlDecoration controlDecoration = new ControlDecoration(txtFiltro, SWT.RIGHT | SWT.CENTER);
			controlDecoration.setImage(ResourceManager.getPluginImage("rcp.assets.facturacion", "icons/borrar.gif"));
			controlDecoration.setDescriptionText("Quitar filtro de búsqueda");
			controlDecoration.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					aplicarFiltro("");
					txtFiltro.setText("");
				}
			});
		}

		Label labelSeparador = toolkit.createLabel(composite, " | ", SWT.NONE);
		GridData gd_labelSeparador = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_labelSeparador.horizontalIndent = 20;
		labelSeparador.setLayoutData(gd_labelSeparador);

		lblRegistros = toolkit.createLabel(composite, "0 registros", SWT.NONE);
		lblRegistros.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblFiltrados = toolkit.createLabel(composite, "-  0 filtrados", SWT.NONE);
		lblFiltrados.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}
	
	
	
	
	private void aplicarFiltro(String cadena) {
		// solo aplicamos un filtro a la vez, así que reseteamos los filtros
		viewer.resetFilters();
		// obtenemos el total de registros cuando no hay ningún filtro aplicado, para mostrar el conteo original posteriormente
		int totalRegistros = viewer.getTable().getItemCount();  
		if (!cadena.equals("")) {
			//viewer.setItemCount(0);    // hace que se actualice más rápido el viewer (visualmente) al aplicar el filtro
			filtro = new ResponsablesFilter();
			filtro.setSearchString(cadena.toLowerCase());
			viewer.addFilter(filtro);
			lblRegistros.setText(totalRegistros + " registros");
			lblFiltrados.setText(" -  " + filtro.getCantidad() + (filtro.getCantidad() == 1 ? " filtrado" : " filtrados"));
		} else {
			lblRegistros.setText(totalRegistros + " registros");
			lblFiltrados.setText(" -  " + "0" + " filtrados");
		}
	}
	
	/**
	 * Permite indicar el total de registros que tiene el viewer.  Debe ser llamado al inicializar el viewer
	 * y al ejecutar un refresh o cambio de input.
	 * @param noRegistros cantidad de registros que tiene el viewer
	 */
	public void setNoRegistros(Integer noRegistros) {
		lblRegistros.setText(noRegistros + " registros");
	}
	
}
