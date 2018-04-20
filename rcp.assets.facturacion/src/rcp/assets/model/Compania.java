package rcp.assets.model;

public enum Compania {
	
	PMA("01", true),
	BVI("02", false),
	ATC_SERVICES("04", true),
	ATC_BELIZE("05", false);

	private String codigo;
	private boolean pagaItbms;
	
	
	private Compania(String codigo, boolean pagaItbms) {
		this.codigo = codigo;
		this.pagaItbms = pagaItbms;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public boolean pagaItbms() {
		return pagaItbms;
	}
	
	public static Compania from(String codigo) {
		for(Compania v : values()) {
			if (v.codigo.equals(codigo)) {
				return v;
			}
		}
		throw new IllegalArgumentException("Código de compañía no registrado: " + codigo);
	}
}
