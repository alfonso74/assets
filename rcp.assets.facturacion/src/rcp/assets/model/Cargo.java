package rcp.assets.model;

import java.util.Date;
import java.util.Set;

public class Cargo implements IEditableDocument {
	
	private Long idCargo = -1L;
	private Caso caso;
	private Set<DetalleCargo> listaDetalles;
	
	private String noCargo;
	private Float monto;

	private boolean actualizado;
	private Date fechaCreado;
	private Date fechaModificado;
	

	@Override
	public Long getId() {
		return idCargo;
	}
	
	// ******************************** métodos especiales ********************************

	@Override
	public String getTituloDocumento() {
		return "Cargo " + getNoCargo();
	}
	
	@Override
	public String toString() {
		return "Cargo (id-noCargo): " + getId() + "-" + getNoCargo();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		final Cargo otro = (Cargo) other;
		if (!this.getId().equals(otro.getId())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = 31 * this.getId().hashCode();
		return result;
	}

	
	
	
	// *********************************** getters y setters ******************************
	
	public Long getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(Long idCargo) {
		this.idCargo = idCargo;
	}
	
	public Caso getCaso() {
		return caso;
	}

	public void setCaso(Caso caso) {
		this.caso = caso;
	}

	public Set<DetalleCargo> getListaDetalles() {
		return listaDetalles;
	}

	public void setListaDetalles(Set<DetalleCargo> listaDetalles) {
		this.listaDetalles = listaDetalles;
	}

	public String getNoCargo() {
		return noCargo;
	}

	public void setNoCargo(String noCargo) {
		this.noCargo = noCargo;
	}

	public Float getMonto() {
		return monto;
	}

	public void setMonto(Float monto) {
		this.monto = monto;
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
