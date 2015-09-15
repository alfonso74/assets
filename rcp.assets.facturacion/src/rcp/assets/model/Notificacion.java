package rcp.assets.model;

public class Notificacion implements IEditableDocument {

	private Long idNotificacion = -1L;
	
	private Keyword tipo;
	private String noCia;
	private Integer periodo;
	private String descripcion;
	private String descripcionIngles;
	
	
	@Override
	public Long getId() {
		return idNotificacion;
	}
	
	public void setId(Long pId) {
		idNotificacion = pId;
	}
	

	// ******************************** métodos especiales ********************************
	
	@Override
	public String getTituloDocumento() {
		String titulo = "Nueva notificación";
		titulo = getDescripcion() == null ? titulo : getTipo().getDescripcion();
		return titulo;
	}
	
	@Override
	public String toString() {
		return "Notificación (id-descripción): " + getId() + "-" + getDescripcion();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		final Notificacion otro = (Notificacion) other;
		if (!this.getId().equals(otro.getId())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = 31 * this.getId().hashCode();
		return result;
	}


	// *********************************** getters y setters ******************************
	
	public Long getIdNotificacion() {
		return idNotificacion;
	}

	public void setIdNotificacion(Long idNotificacion) {
		this.idNotificacion = idNotificacion;
	}

	public Keyword getTipo() {
		return tipo;
	}

	public void setTipo(Keyword tipoNotificacion) {
		this.tipo = tipoNotificacion;
	}

	public String getNoCia() {
		return noCia;
	}

	public void setNoCia(String noCia) {
		this.noCia = noCia;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
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

}
