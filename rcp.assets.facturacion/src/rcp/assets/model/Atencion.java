package rcp.assets.model;

import java.util.Date;

public class Atencion implements IEditableDocument {

	private Long idAtencion = -1L;
	private Responsable responsable;
	
	private String noCia;
	private Integer noAtencion;
	private String descripcion;
	private String estado;
	private String dspEstado;

	private boolean actualizado;
	private Date fechaCreado;
	private Date fechaModificado;


	public Atencion() {
	}

	@Override
	public Long getId() {
		return idAtencion;
	}

	// TODO revisar para qué me sirve este setter
	public void setId(Long pId) {
		idAtencion = pId;
	}


	// ******************************** métodos especiales ********************************


	@Override
	public String getTituloDocumento() {
		String titulo = "Nueva atención";
		titulo = getDescripcion() == null ? titulo : getDescripcion();
		return titulo;
	}
	
	@Override
	public String toString() {
		return "Atención (id-nombre): " + getId() + "-" + getDescripcion();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		final Atencion otro = (Atencion) other;
		if (!this.getId().equals(otro.getId())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = 31 * this.getId().hashCode();
		return result;
	}

	
	// *********************************** getters y setters ******************************
	
	
	public Long getIdAtencion() {
		return idAtencion;
	}

	public void setIdAtencion(Long idAtencion) {
		this.idAtencion = idAtencion;
	}

	public Responsable getResponsable() {
		return responsable;
	}

	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}

	public String getNoCia() {
		return noCia;
	}

	public void setNoCia(String noCia) {
		this.noCia = noCia;
	}

	public Integer getNoAtencion() {
		return noAtencion;
	}

	public void setNoAtencion(Integer noAtencion) {
		this.noAtencion = noAtencion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
