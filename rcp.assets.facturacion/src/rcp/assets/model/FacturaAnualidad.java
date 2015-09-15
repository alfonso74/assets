package rcp.assets.model;


public class FacturaAnualidad extends Factura {

	private Factura factura;

	private Float agenteResidente;
	private Float directores;
	private Float custodiaLibros;
	private Float retransmision;
	private Float servFiduciarios;
	private Float servFirma;
	private Float tasa;
	private Float otros;
	private String otrosDetalle;


	public FacturaAnualidad() {
		setTipo(TipoFactura.ANUALIDAD.getCodigo());
	}
	
	
	// ******************************** métodos especiales ********************************

	
	@Override
	public void calcularTotalDetalle() {
		Float an01 = getAgenteResidente() == null ? 0F : getAgenteResidente();
		Float an02 = getDirectores() == null ? 0F : getDirectores();
		Float an03 = getCustodiaLibros() == null ? 0F : getCustodiaLibros();
		Float an04 = getRetransmision() == null ? 0F : getRetransmision();
		Float an05 = getServFiduciarios() == null ? 0F : getServFiduciarios();
		Float an06 = getServFirma() == null ? 0F : getServFirma();
		Float an07 = getTasa() == null ? 0F : getTasa();
		Float an08 = getOtros() == null ? 0F : getOtros();
		setTotalDetalle(an01 + an02 + an03 + an04 + an05 + an06 + an07 + an08);
		setTotal(getTotalDetalle() + getImpuesto());
	}

	@Override
	public void calcularImpuesto() {
		if (isExcentoImpuesto()) {
			setImpuesto(0F);
		} else {
			setImpuesto((getTotalDetalle() - getTasa()) * getImpuestoPorcentaje() / 100);
		}
		setTotal(getTotalDetalle() + getImpuesto());
	}

	@Override
	public Integer getCantidadLineas() {
		int cantidad = 0;
		if (getAgenteResidente() != null && getAgenteResidente() != 0) cantidad++;
		if (getDirectores() != null && getDirectores() != 0) cantidad++;
		if (getCustodiaLibros() != null && getCustodiaLibros() != 0) cantidad++;
		if (getRetransmision() != null && getRetransmision() != 0) cantidad++;
		if (getServFiduciarios() != null && getServFiduciarios() != 0) cantidad++;
		if (getServFirma() != null && getServFirma() != 0) cantidad++;
		if (getTasa() != null && getTasa() != 0) cantidad++;
		if (getOtros() != null && getOtros() != 0) cantidad++;
		return cantidad;
	}

	
	
	
	// *********************************** getters y setters ******************************

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	
	
	public Float getAgenteResidente() {
		return agenteResidente;
	}

	public void setAgenteResidente(Float agenteResidente) {
		this.agenteResidente = agenteResidente;
	}


	public Float getDirectores() {
		return directores;
	}

	public void setDirectores(Float directores) {
		this.directores = directores;
	}


	public Float getCustodiaLibros() {
		return custodiaLibros;
	}

	public void setCustodiaLibros(Float custodiaLibros) {
		this.custodiaLibros = custodiaLibros;
	}


	public Float getRetransmision() {
		return retransmision;
	}

	public void setRetransmision(Float retransmision) {
		this.retransmision = retransmision;
	}


	public Float getServFiduciarios() {
		return servFiduciarios;
	}

	public void setServFiduciarios(Float servFiduciarios) {
		this.servFiduciarios = servFiduciarios;
	}


	public Float getServFirma() {
		return servFirma;
	}

	public void setServFirma(Float servFirma) {
		this.servFirma = servFirma;
	}


	public Float getTasa() {
		return tasa;
	}

	public void setTasa(Float tasa) {
		this.tasa = tasa;
	}


	public Float getOtros() {
		return otros;
	}

	public void setOtros(Float otros) {
		this.otros = otros;
	}


	public String getOtrosDetalle() {
		return otrosDetalle;
	}

	public void setOtrosDetalle(String otrosDetalle) {
		this.otrosDetalle = otrosDetalle;
	}

}
