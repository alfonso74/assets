package rcp.assets.model;

public class Usuario implements IEditableDocument {
	
	private Long idUsuario = -1L;
	private String nombre;
	private String apellido;
	private String userName;
	private String password;
	private String estado;
	private String dspEstado;
	
	
	public Usuario() {
	}
	
	@Override
	public Long getId() {
		return idUsuario;
	}

	// TODO revisar para qué me sirve este setter
	public void setId(Long pId) {
		idUsuario = pId;
	}
	
	// ******************************** métodos especiales ********************************

	public String getTituloDocumento() {
		String titulo = "Nuevo usuario";
		titulo = getNombre() == null ? titulo : getNombreCompleto();
		return titulo;
	}
	
	public String getNombreCompleto() {
		return getNombre() + " " + getApellido();
	}
	
	@Override
	public String toString() {
		return "Usuario (id-nombre): " + getIdUsuario() + "-" + getNombreCompleto();
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;
		
		final Usuario usuario = (Usuario) other;
		if (!this.getIdUsuario().equals(usuario.getIdUsuario())) return false;
		
		return true;
	}

	@Override
	public int hashCode() {
		int result = 31 * this.getIdUsuario().hashCode();
		return result;
	}
	
	
	// *********************************** getters y setters ******************************


	/**
	 * @return the idUsuario
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}


	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}


	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}


	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	/**
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}


	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}


	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}


	/**
	 * @return the dspEstado
	 */
	public String getDspEstado() {
		return dspEstado;
	}


	/**
	 * @param dspEstado the dspEstado to set
	 */
	public void setDspEstado(String dspEstado) {
		this.dspEstado = dspEstado;
	}


}
