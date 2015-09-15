package rcp.assets.model;

public class GrupoCargo implements IEditableDocument {
	
	private Long idGrupo = -1L;
	private Caso caso;
	
	private int grupo;
	private String descripcion;
	

	@Override
	public Long getId() {
		return idGrupo;
	}

	
	// ******************************** métodos especiales ********************************
	
	@Override
	public String getTituloDocumento() {
		return "Grupo " + getId();
	}
	
	
	@Override
	public String toString() {
		return "Grupo (id-Grupo): " + getId() + "-" + getDescripcion();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		final GrupoCargo otro = (GrupoCargo) other;
		if (!this.getId().equals(otro.getId())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = 31 * this.getId().hashCode();
		return result;
	}


	
	// *********************************** getters y setters ******************************

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Caso getCaso() {
		return caso;
	}

	public void setCaso(Caso caso) {
		this.caso = caso;
	}

	public int getGrupo() {
		return grupo;
	}

	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
