package rcp.assets.model;

public class LineaRecargoTxt {
	
	private Long idRecargoTxt = -1L;
	private Factura factura;
	private String descripcion;
	private Float monto;
	private Integer secuencia;
	
	
//	@Override
//	public Long getId() {
//		return idRecargoTxt;
//	}
	
	
	// ******************************** métodos especiales ********************************
	
	@Override
	public String toString() {
		return "LineaRecargosTxt [factura=" + factura + ", descripcion="
				+ descripcion + ", monto=" + monto + ", secuencia=" + secuencia
				+ "]";
	}
	

	// *********************************** getters y setters ******************************
	
	public Long getIdRecargoTxt() {
		return idRecargoTxt;
	}
	public void setIdRecargoTxt(Long idRecargoTxt) {
		this.idRecargoTxt = idRecargoTxt;
	}
	public Factura getFactura() {
		return factura;
	}
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Float getMonto() {
		return monto;
	}
	public void setMonto(Float monto) {
		this.monto = monto;
	}
	public Integer getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}

}
