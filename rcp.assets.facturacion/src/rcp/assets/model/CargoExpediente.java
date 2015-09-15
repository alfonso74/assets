package rcp.assets.model;

import java.util.Date;

public class CargoExpediente implements IEditableDocument {

	private Long idCargoExpediente = -1L;
	private Expediente expediente;
	private Responsable responsable;
	private Direccion direccion;
	private Atencion atencion;
	
	private Retainer retainer;
	private CargosAnuales cargosAnuales;
	
	/*
	private Float agenteResidente;
	private Float directores;
	private Float custodiaLibros;
	private Float retransmision;
	private Float servFiduciarios;
	private Float servFirmas;
	private Float tasa;
	private Float otros;
	private String otrosDetalle;
	private Float totalCargosAnuales;
	*/
	
	private String estado;
	
	private boolean actualizado;
	private Date fechaCreado;
	private Date fechaModificado;
	
	
	@Override
	public Long getId() {
		return idCargoExpediente;
	}

	
	// ******************************** métodos especiales ********************************
	
	@Override
	public String getTituloDocumento() {
		return "Cargos expediente: " + getId();
	}
	
	
	@Override
	public String toString() {
		return "Cargos expediente (id-noExp): " + getId() + "-" + getExpediente().getNoExpediente();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		final Expediente otro = (Expediente) other;
		if (!this.getId().equals(otro.getId())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = 31 * this.getId().hashCode();
		return result;
	}
	
	
	public Float getTotalRetainers() {
		return getRetainer().getTotalRetainers();
	}
	
	
	public Float getTotalCargosAnuales() {
		return getCargosAnuales().getTotalCargosAnuales();
	}
	
	
	/*
	public Float getTotalCargosAnuales() {
		return totalCargosAnuales;
	}

	public void setTotalCargosAnuales(Float totalCargosAnuales) {
		this.totalCargosAnuales = totalCargosAnuales;
	}
	*/
	
	
	// *********************************** getters y setters ******************************

	public Long getIdCargoExpediente() {
		return idCargoExpediente;
	}


	public void setIdCargoExpediente(Long idCargoExpediente) {
		this.idCargoExpediente = idCargoExpediente;
	}


	public Expediente getExpediente() {
		return expediente;
	}


	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}


	public Responsable getResponsable() {
		return responsable;
	}


	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}


	public Direccion getDireccion() {
		return direccion;
	}


	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}


	public Atencion getAtencion() {
		return atencion;
	}


	public void setAtencion(Atencion atencion) {
		this.atencion = atencion;
	}

	
	public Retainer getRetainer() {
		return retainer;
	}

	public void setRetainer(Retainer retainer) {
		this.retainer = retainer;
	}
	
	
	public CargosAnuales getCargosAnuales() {
		return cargosAnuales;
	}

	public void setCargosAnuales(CargosAnuales cargosAnuales) {
		this.cargosAnuales = cargosAnuales;
	}
	


	/*
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
	*/


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public boolean isActualizado() {
		return actualizado;
	}
	

	public void setActualizado(Boolean actualizado) {
		this.actualizado = (actualizado == null ? Boolean.FALSE : actualizado.booleanValue());
	}


	public Date getFechaCreado() {
		return fechaCreado;
	}


	public void setFechaCreado(Date fechaCreado) {
		this.fechaCreado = fechaCreado;
	}


	public Date getFechaModificado() {
		return fechaModificado;
	}


	public void setFechaModificado(Date fechaModificado) {
		this.fechaModificado = fechaModificado;
	}

}
