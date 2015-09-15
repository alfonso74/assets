package rcp.assets.model;

import java.util.Date;

import rcp.assets.services.ComboDataItem;

public class Mes implements IEditableDocument {

	private Long idMes = -1L;
	
	private String descripcion;
	private String descripcionIngles;
	private Date modificado;
	
	@Override
	public Long getId() {
		return idMes;
	}
	
	
	public Mes() {
	}
	
	
	public Mes(Long idMes, String descripcion, String descripcionIngles) {
		this.idMes = idMes;
		this.descripcion = descripcion;
		this.descripcionIngles = descripcionIngles;
	}
	
	
	// ******************************** métodos especiales ********************************

	@Override
	public String getTituloDocumento() {
		return "Mes " + getId(); 
	}
	
	@Override
	public String toString() {
		return "Mes (id-desc): " + getId() + "-" + getDescripcion();
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		final Mes otro = (Mes) other;
		if (!this.getId().equals(otro.getId())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = 31 * this.getId().hashCode();
		return result;
	}


	/**
	 * Formatea un mes para ser agregado a un ComboData (en español)
	 * @return ComboDataItem listo para procesar
	 */
	public ComboDataItem toComboDataItemSpanish() {
		ComboDataItem cdItem = new ComboDataItem();
		cdItem.setKey(getId().toString());
		cdItem.setTexto(getDescripcion());
		cdItem.setObjeto(this);
		return cdItem;
	}
	
	
	/**
	 * Formatea un mes para ser agregado a un ComboData (en inglés)
	 * @return ComboDataItem listo para procesar
	 */
	public ComboDataItem toComboDataItemEnglish() {
		ComboDataItem cdItem = new ComboDataItem();
		cdItem.setKey(getId().toString());
		cdItem.setTexto(getDescripcionIngles());
		cdItem.setObjeto(this);
		return cdItem;
	}
	
	
	// *********************************** getters y setters ******************************
	
	
	public Long getIdMes() {
		return idMes;
	}


	public void setIdMes(Long idMes) {
		this.idMes = idMes;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getDescripcionIngles() {
		return descripcionIngles;
	}


	public void setDescripcionIngles(String descripcionIngles) {
		this.descripcionIngles = descripcionIngles;
	}


	public Date getModificado() {
		return modificado;
	}


	public void setModificado(Date modificado) {
		this.modificado = modificado;
	}

}
