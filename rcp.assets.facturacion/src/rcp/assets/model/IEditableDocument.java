package rcp.assets.model;

public interface IEditableDocument {
	
	/**
	 * Usado para tener siempre un Id al inicializar un objeto GenericEditorInput (desde una vista)
	 * @return id del objeto
	 */
	public abstract Long getId();
	
	
	public abstract String getTituloDocumento();

}
