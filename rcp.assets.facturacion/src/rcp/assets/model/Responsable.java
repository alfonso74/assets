package rcp.assets.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Responsable implements IEditableDocument {

	private Long idResponsable = -1L;
	private Set<Atencion> listaAtenciones;
	private Set<Direccion> listaDirecciones;
	
	private String noCia;
	private Integer noCliente;
	private String nombre;
	private String alias;
	private String nombreComercial;
	private String contacto;
	private String telefono1;
	private String telefono2;
	private String fax1;
	private String fax2;
	private String email1;
	private String email2;
	private String excentoImpuesto;
	private String idioma;
	private String estado;
	private String direccion1;
	private String direccion2;
	private String direccion3;
	private String direccion4;
	private String direccion5;

	private boolean actualizado;
	private Date fechaCreado;
	private Date fechaModificado;


	public Responsable() {
		listaAtenciones = new HashSet<Atencion>();
		listaDirecciones = new HashSet<Direccion>();
	}

	@Override
	public Long getId() {
		return idResponsable;
	}

	// TODO revisar para qué me sirve este setter
	public void setId(Long pId) {
		idResponsable = pId;
	}


	// ******************************** métodos especiales ********************************

	
	@Override
	public String getTituloDocumento() {
		String titulo = "Nuevo responsable";
		titulo = getNombre() == null ? titulo : getNombreCompleto();
		return titulo;
	}

	public String getNombreCompleto() {
		return getNombre();
	}

	@Override
	public String toString() {
		return "Responsable (id-nombre): " + getId() + "-" + getNombreCompleto();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		final Responsable otro = (Responsable) other;
		if (!this.getId().equals(otro.getId())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = 31 * this.getId().hashCode();
		return result;
	}


	// *********************************** getters y setters ******************************


	public Long getIdResponsable() {
		return idResponsable;
	}


	public void setIdResponsable(Long idResponsable) {
		this.idResponsable = idResponsable;
	}
	

	public Set<Atencion> getListaAtenciones() {
		return listaAtenciones;
	}

	public void setListaAtenciones(Set<Atencion> listaAtenciones) {
		this.listaAtenciones = listaAtenciones;
	}
	
	public Set<Direccion> getListaDirecciones() {
		return listaDirecciones;
	}

	public void setListaDirecciones(Set<Direccion> listaDirecciones) {
		this.listaDirecciones = listaDirecciones;
	}

	public String getNoCia() {
		return noCia;
	}


	public void setNoCia(String noCia) {
		this.noCia = noCia;
	}



	public Integer getNoCliente() {
		return noCliente;
	}



	public void setNoCliente(Integer noCliente) {
		this.noCliente = noCliente;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getAlias() {
		return (alias == null ? "" : alias);
	}



	public void setAlias(String alias) {
		this.alias = alias;
	}



	public String getNombreComercial() {
		return nombreComercial;
	}



	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}



	public String getContacto() {
		return (contacto == null ? "" : contacto);
	}



	public void setContacto(String contacto) {
		this.contacto = contacto;
	}



	public String getTelefono1() {
		return (telefono1 == null ? "" : telefono1);
	}



	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}



	public String getTelefono2() {
		return (telefono2 == null ? "" : telefono2);
	}



	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}



	public String getFax1() {
		return (fax1 == null ? "" : fax1);
	}



	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}

	public String getFax2() {
		return (fax2 == null ? "" : fax2);
	}

	public void setFax2(String fax2) {
		this.fax2 = fax2;
	}
	

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public boolean isActualizado() {
		return actualizado;
	}

	public void setActualizado(Boolean actualizado) {
		this.actualizado = (actualizado == null ? Boolean.FALSE : actualizado.booleanValue());
	}


	public String getExcentoImpuesto() {
		return excentoImpuesto;
	}

	public void setExcentoImpuesto(String excentoImpuesto) {
		this.excentoImpuesto = excentoImpuesto;
	}


	/**
	 * Usualmente utilizado para la factura
	 * @return 1-Español, 2-Inglés
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Usualmente utilizado para la factura
	 * @return 1-Español, 2-Inglés
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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
