package rcp.assets.views.filters;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class ResponsablesFilter extends ViewerFilter {
	
	private String cadena = null;
	private String etiqueta = null;
	private Integer cantidad = 0;
	
	public void setSearchString(String cadena) {
		//this.cadena = ".*" + cadena + ".*";
		this.cantidad = 0;
		this.cadena = cadena;
		System.out.println("Filtro: " + this.cadena);
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {

		TableViewer v = (TableViewer) viewer;
		ITableLabelProvider labelProv = (ITableLabelProvider) v.getLabelProvider();
		//System.out.println("V: " + v.getTable().getColumnCount());
		int noColumnas = v.getTable().getColumnCount();
		for (int n = 0; n < noColumnas; n++) {
			//System.out.println("N" + n + ": " + labelProv.getColumnText(element, n));
			etiqueta = labelProv.getColumnText(element, n);
			if (etiqueta != null && etiqueta.toLowerCase().contains(cadena)) {
				cantidad++;
				return true;
			}
		}

		return false;
	}
	
	public Integer getCantidad() {
		return cantidad;
	}

}
