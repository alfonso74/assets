package rcp.assets.model;


public class LineaFactura implements IEditableDocument {

	private Long idDetalle = -1L;
	private FacturaCaso factura;
	private TipoCargo tipoCargo;
	private GrupoCargo grupo;
	
	private String descripcion;
	private Float monto;
	private String pagaImpuesto;
	private int secuencia;

	
	
	@Override
	public Long getId() {
		return idDetalle;
	}
	
	
	
	// ******************************** métodos especiales ********************************

	@Override
	public String getTituloDocumento() {
		return getClass().getName() + ": " + getId();
	}

	
	@Override
	public String toString() {
		return "LineaFactura (id-Descripción): " + getId() + "-" + getDescripcion();
	}
	
	/**
	 * Permite obtener el monto de la linea de factura más el impuesto indicado
	 * @param impuesto Valor para calcular impuesto (ej. 0.07 para el 7%)
	 * @return Monto del detalle con el impuesto agregado
	 */
	public Float getMontoConImpuesto(float impuesto) {
		return getMonto() * impuesto;
	}
	
/*
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		final DetalleFactura otro = (DetalleFactura) other;
		if (!this.getId().equals(otro.getId())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = 31 * this.getId().hashCode();
		return result;
	}
*/
	
	
	// *********************************** getters y setters ******************************
	
	public Long getIdDetalle() {
		return idDetalle;
	}


	public void setIdDetalle(Long idDetalle) {
		this.idDetalle = idDetalle;
	}
	
	
	public FacturaCaso getFactura() {
		return factura;
	}


	public void setFactura(FacturaCaso factura) {
		this.factura = factura;
	}


	public TipoCargo getTipoCargo() {
		return tipoCargo;
	}


	public void setTipoCargo(TipoCargo tipoCargo) {
		this.tipoCargo = tipoCargo;
	}


	public GrupoCargo getGrupo() {
		return grupo;
	}


	public void setGrupo(GrupoCargo grupo) {
		this.grupo = grupo;
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

	
	public String getPagaImpuesto() {
		return pagaImpuesto;
	}


	public void setPagaImpuesto(String pagaImpuesto) {
		this.pagaImpuesto = pagaImpuesto;
	}


	public int getSecuencia() {
		return secuencia;
	}


	public void setSecuencia(int secuencia) {
		this.secuencia = secuencia;
	}
	
	
}
