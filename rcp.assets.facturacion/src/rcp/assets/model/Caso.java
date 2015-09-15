package rcp.assets.model;

import java.util.Date;
import java.util.Set;

public class Caso implements IEditableDocument {

	private Long idCaso = -1L;
	private Expediente expediente;
	private Responsable responsable;
	private Direccion direccion;
	private Atencion atencion;
	private Set<Cargo> listaCargos;
	
	private String noCia;
	private Long noCaso;
	private String naturaleza;
	private String referencia;
	private Date fechaCierre;
	private String estado;
	private String dspEstado;
	
	private boolean actualizado;
	private Date fechaCreado;
	private Date fechaModificado;
	
	@Override
	public Long getId() {
		return idCaso;
	}
	
	// ******************************** métodos especiales ********************************

	@Override
	public String getTituloDocumento() {
		return "Caso " + getNoCaso();
	}

	
	@Override
	public String toString() {
		return "Caso (id-noCaso): " + getId() + "-" + getNoCaso();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		final Caso otro = (Caso) other;
		if (!this.getId().equals(otro.getId())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = 31 * this.getId().hashCode();
		return result;
	}
	
	
	public boolean isFacturable() {
		if (getEstado().equals("PF") || getEstado().equals("FA")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	// *********************************** getters y setters ******************************
	
	
	public Long getIdCaso() {
		return idCaso;
	}

	public void setIdCaso(Long idCaso) {
		this.idCaso = idCaso;
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
	

	public Set<Cargo> getListaCargos() {
		return listaCargos;
	}

	public void setListaCargos(Set<Cargo> listaCargos) {
		this.listaCargos = listaCargos;
	}

	public String getNoCia() {
		return noCia;
	}

	public void setNoCia(String noCia) {
		this.noCia = noCia;
	}

	public Long getNoCaso() {
		return noCaso;
	}

	public void setNoCaso(Long noCaso) {
		this.noCaso = noCaso;
	}

	public String getNaturaleza() {
		return naturaleza;
	}

	public void setNaturaleza(String naturaleza) {
		this.naturaleza = naturaleza;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDspEstado() {
		return dspEstado;
	}

	public void setDspEstado(String dspEstado) {
		this.dspEstado = dspEstado;
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
