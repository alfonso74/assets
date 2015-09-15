package rcp.assets.editors;

public class ResponseEditorInput extends GenericEditorInput {
	
	private Object parent;

	public ResponseEditorInput() {
		super();
	}

	public ResponseEditorInput(Long id) {
		super(id);
	}

	public ResponseEditorInput(String name) {
		super(name);
	}

	public Object getParent() {
		return parent;
	}

	public void setParent(Object parent) {
		this.parent = parent;
	}
	
}
