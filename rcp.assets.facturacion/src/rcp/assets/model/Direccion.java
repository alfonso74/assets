package rcp.assets.model;

import java.util.Date;

public class Direccion implements IEditableDocument {

	private Long idDireccion = -1L;
	private Responsable responsable;
	
	private String noCia;
	private Integer noDireccion;
	private String nombreAlterno;
	private String direccion1;
	private String direccion2;
	private String direccion3;
	private String direccion4;
	private String direccion5;
	private String estado;
	private String dspEstado;
	
	private boolean actualizado;
	private Date fechaCreado;
	private Date fechaModificado;
	

	public Direccion() {
	}
	
	@Override
	public Long getId() {
		return idDireccion;
	}

	// TODO revisar para qué me sirve este setter
	public void setId(Long pId) {
		idDireccion = pId;
	}

	
	// ******************************** métodos especiales ********************************


	@Override
	public String getTituloDocumento() {
		String titulo = "Nueva dirección";
		titulo = getNombreAlterno() == null ? titulo : getNombreAlterno();
		return titulo;
	}

	@Override
	public String toString() {
		return "Dirección (id-nombre): " + getId() + "-" + getNombreAlterno();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		final Direccion otro = (Direccion) other;
		if (!this.getId().equals(otro.getId())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = 31 * this.getId().hashCode();
		return result;
	}

	
	// *********************************** getters y setters ******************************

	public Long getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
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

	public Integer getNoDireccion() {
		return noDireccion;
	}

	public void setNoDireccion(Integer noDireccion) {
		this.noDireccion = noDireccion;
	}

	public String getNombreAlterno() {
		return nombreAlterno;
	}

	public void setNombreAlterno(String nombreAlterno) {
		this.nombreAlterno = nombreAlterno;
	}

	public String getDireccion1() {
		return direccion1;
	}

	public void setDireccion1(String direccion1) {
		this.direccion1 = direccion1;
	}

	public String getDireccion2() {
		return direccion2;
	}

	public void setDireccion2(String direccion2) {
		this.direccion2 = direccion2;
	}

	public String getDireccion3() {
		return direccion3;
	}

	public void setDireccion3(String direccion3) {
		this.direccion3 = direccion3;
	}

	public String getDireccion4() {
		return direccion4;
	}

	public void setDireccion4(String direccion4) {
		this.direccion4 = direccion4;
	}

	public String getDireccion5() {
		return direccion5;
	}

	public void setDireccion5(String direccion5) {
		this.direccion5 = direccion5;
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
