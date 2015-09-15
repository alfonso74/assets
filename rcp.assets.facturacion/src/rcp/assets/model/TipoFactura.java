package rcp.assets.model;

public enum TipoFactura {
	
	CASO("CA", "caso", "Factura de caso", "/reports/casoFactura.rptdesign"),
	RETAINER("RE", "retainer", "Factura de retainer", "/reports/retainerFactura.rptdesign"),
	ANUALIDAD("AN", "anualidad", "Factura de anualidad", "/reports/anualidadFactura.rptdesign");
	
	
	private String codigo;
	private String nombre;
	private String titulo;
	private String ruta;
	
	
	private TipoFactura(String codigo, String nombreProforma, String tituloProforma, String rutaProforma) {
		this.codigo = codigo;
		this.nombre = nombreProforma;
		this.titulo = tituloProforma;
		this.ruta = rutaProforma;
	}
	
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public String getRuta() {
		return ruta;
	}
	
	
	public static TipoFactura fromCodigo(String codigo) {
		for (TipoFactura v : TipoFactura.values()) {
			if (v.getCodigo().equalsIgnoreCase(codigo)) {
				return v;
			}
		}
		throw new IllegalArgumentException("Código no encontrado: " + codigo);
	}

}
