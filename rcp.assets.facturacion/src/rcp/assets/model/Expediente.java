package rcp.assets.model;

import java.util.Date;

public class Expediente implements IEditableDocument {
	
	private Long idExpediente = -1L;
	private CargoExpediente cargoExpediente;
	private String idPeachtree;
	private String idJobTasa;
	private String idJobGNF;
	
	private String noCia;
	private String noExpediente;
	private String noCliente;
	private String nombre;
	private String depto;
	private String subarea;
	private Date fechaCierre;
	private String estado;
	
	private String dspDepto;
	private String dspSubArea;
	private String dspEstado;
	
	private Date fechaInscripcion;
	private String tomo;
	private String folio;
	private String asiento;
	private String dv1;
	private String dv2;
	private String noEscritura;
	private String fechaEscritura;
	private String notaria;
	private String representanteLegal;

	private boolean actualizado;
	private Date fechaCreado;
	private Date fechaModificado;
	

	@Override
	public Long getId() {
		return idExpediente;
	}
	
	
	// ******************************** métodos especiales ********************************

	@Override
	public String getTituloDocumento() {
		return "Expediente " + getId();
	}

	@Override
	public String toString() {
		return "Expediente (id-noExp): " + getId() + "-" + getNoExpediente();
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
	
	
	public String getDspDepto() {
		return dspDepto;
	}


	public void setDspDepto(String dspDepto) {
		this.dspDepto = dspDepto;
	}


	public String getDspSubArea() {
		return dspSubArea;
	}


	public void setDspSubArea(String dspSubArea) {
		this.dspSubArea = dspSubArea;
	}


	public String getDspEstado() {
		return dspEstado;
	}


	public void setDspEstado(String dspEstado) {
		this.dspEstado = dspEstado;
	}


	
	// *********************************** getters y setters ******************************
	
	public Long getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(Long idExpediente) {
		this.idExpediente = idExpediente;
	}
	

	public CargoExpediente getCargoExpediente() {
		return cargoExpediente;
	}


	public void setCargoExpediente(CargoExpediente cargoExpediente) {
		this.cargoExpediente = cargoExpediente;
	}


	public String getIdPeachtree() {
		return idPeachtree;
	}


	public void setIdPeachtree(String idPeachtree) {
		this.idPeachtree = idPeachtree;
	}
	

	public String getIdJobTasa() {
		return idJobTasa;
	}


	public void setIdJobTasa(String idJobTasa) {
		this.idJobTasa = idJobTasa;
	}
	

	public String getIdJobGNF() {
		return idJobGNF;
	}


	public void setIdJobGNF(String idJobGNF) {
		this.idJobGNF = idJobGNF;
	}


	public String getNoCia() {
		return noCia;
	}


	public void setNoCia(String noCia) {
		this.noCia = noCia;
	}


	public String getNoExpediente() {
		return noExpediente;
	}


	public void setNoExpediente(String noExpediente) {
		this.noExpediente = noExpediente;
	}


	public String getNoCliente() {
		return noCliente;
	}


	public void setNoCliente(String noCliente) {
		this.noCliente = noCliente;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDepto() {
		return depto;
	}


	public void setDepto(String depto) {
		this.depto = depto;
	}


	public String getSubarea() {
		return subarea;
	}


	public void setSubarea(String subarea) {
		this.subarea = subarea;
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


	// ******************************** getters y setters (para sociedades) *****************************
	
	
	public Date getFechaInscripcion() {
		return fechaInscripcion;
	}


	public void setFechaInscripcion(Date fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}


	public String getTomo() {
		return tomo;
	}


	public void setTomo(String tomo) {
		this.tomo = tomo;
	}


	public String getFolio() {
		return folio;
	}


	public void setFolio(String folio) {
		this.folio = folio;
	}


	public String getAsiento() {
		return asiento;
	}


	public void setAsiento(String asiento) {
		this.asiento = asiento;
	}


	public String getDv1() {
		return dv1;
	}


	public void setDv1(String dv1) {
		this.dv1 = dv1;
	}


	public String getDv2() {
		return dv2;
	}


	public void setDv2(String dv2) {
		this.dv2 = dv2;
	}


	public String getNoEscritura() {
		return noEscritura;
	}


	public void setNoEscritura(String noEscritura) {
		this.noEscritura = noEscritura;
	}


	public String getFechaEscritura() {
		return fechaEscritura;
	}


	public void setFechaEscritura(String fechaEscritura) {
		this.fechaEscritura = fechaEscritura;
	}


	public String getNotaria() {
		return notaria;
	}


	public void setNotaria(String notaria) {
		this.notaria = notaria;
	}


	public String getRepresentanteLegal() {
		return representanteLegal;
	}


	public void setRepresentanteLegal(String representanteLegal) {
		this.representanteLegal = representanteLegal;
	}

	
}
