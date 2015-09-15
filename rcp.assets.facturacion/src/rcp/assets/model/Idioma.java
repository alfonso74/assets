package rcp.assets.model;

public enum Idioma {
	
	ESP("1", "Español"),
	ING("2", "Inglés");
	
	private String codigo;
	private String descripcion;
	
	
	private Idioma(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	
	public static final Idioma fromCodigo(String codigo) {
		for (Idioma v : Idioma.values()) {
			if (v.getCodigo().equalsIgnoreCase(codigo)) {
				return v;
			}
		}
		throw new IllegalArgumentException("El codigo suministrado no existe: " + codigo);
	}

}