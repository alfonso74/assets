package rcp.assets.model;

public class CargosAnuales {

	private Float agenteResidente = 0F;
	private Float directores = 0F;
	private Float custodiaLibros = 0F;
	private Float retransmision = 0F;
	private Float servFiduciarios = 0F;
	private Float servFirmas = 0F;
	private Float tasa = 0F;
	private Float otros = 0F;
	private String otrosDetalle = null;
	private Float totalCargosAnuales = 0F;
	
	
	// ******************************** métodos especiales ********************************
	
	@Override
	public String toString() {
		return "Cargos anuales"; 
	}
	
	
	public Float getTotalImpuestos(float impuestoBase) {
		Float totalSinTasa = getTotalCargosAnuales() - getTasa();
		return (totalSinTasa.floatValue() * impuestoBase);
	}
	
	
	
	// *********************************** getters y setters ******************************
	
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

	public void setCustodiaLibros(Float custoriaLibros) {
		this.custodiaLibros = custoriaLibros;
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


	public Float getServFirmas() {
		return servFirmas;
	}

	public void setServFirmas(Float servFirmas) {
		this.servFirmas = servFirmas;
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
	
	
	public Float getTotalCargosAnuales() {
		return totalCargosAnuales;
	}

	public void setTotalCargosAnuales(Float totalCargosAnuales) {
		this.totalCargosAnuales = totalCargosAnuales;
	}
	
}
