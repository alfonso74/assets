package rcp.assets.model;

public enum TipoProforma {
	
	CASO("caso", "Caso PDF", "/reports/casoProforma.rptdesign"),
	RETAINER("retainer", "Retainer PDF", "/reports/retainerProforma.rptdesign"),
	ANUALIDAD("anualidad", "Anualidad PDF", "/reports/anualidadProforma.rptdesign");
	
	
	private String nombre;
	private String titulo;
	private String ruta;
	
	
	private TipoProforma(String nombreProforma, String tituloProforma, String rutaProforma) {
		this.nombre = nombreProforma;
		this.titulo = tituloProforma;
		this.ruta = rutaProforma;
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

}
