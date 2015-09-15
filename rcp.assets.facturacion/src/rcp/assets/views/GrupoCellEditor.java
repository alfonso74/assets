package rcp.assets.views;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import rcp.assets.model.GrupoCargo;

public class GrupoCellEditor extends EditingSupport {

	private final TableViewer viewer;
	
	public GrupoCellEditor(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new TextCellEditor(viewer.getTable());
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		return ((GrupoCargo) element).getDescripcion();
	}

	@Override
	protected void setValue(Object element, Object value) {
		((GrupoCargo) element).setDescripcion(String.valueOf(value));
		viewer.refresh();
	}
	
	
	

}
