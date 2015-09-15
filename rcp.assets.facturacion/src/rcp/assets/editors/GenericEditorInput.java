package rcp.assets.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class GenericEditorInput implements IEditorInput {

	private final Long id;
	private final String name;
	
	public GenericEditorInput() {
		this.id = null;
		this.name = "";
	}
	 
	public GenericEditorInput(Long id) {
		this.id = id;
		this.name = "";
	}
	
	public GenericEditorInput(String name) {
		this.id = null;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists() {
		return true;    // ejemplo en vogella (Eclipse Editors)
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		return "Editor genérico";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id.intValue();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericEditorInput other = (GenericEditorInput) obj;
		if (id != null) {
			if (!id.equals(other.id))
				return false;
		} else {
			if (other.id != null)
				return false;
		}
		if (!this.getName().equals(other.getName())) {
			return false;
		}
		return true;
	}

}
