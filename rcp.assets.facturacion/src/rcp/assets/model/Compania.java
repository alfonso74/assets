package rcp.assets.model;

public enum Compania {
	
	PMA("01"),
	BVI("02");

	private String codigo;
	
	
	private Compania(String codigo) {
		this.codigo = codigo;
	}
	
	public String getCodigo() {
		return codigo;
	}
}
