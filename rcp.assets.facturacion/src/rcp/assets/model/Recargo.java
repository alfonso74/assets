package rcp.assets.model;


public class Recargo implements IEditableDocument {

	private Long idRecargo = -1L;
	private Keyword tipoRecargo;
	
	private String noCia;
	private Integer periodo;
	private Float monto;
	private String textoFactura;
	private String textoIngles;
	private Keyword estado;
	
	
	@Override
	public Long getId() {
		return getIdRecargo();
	}


	// ******************************** métodos especiales ********************************
	
	@Override
	public String getTituloDocumento() {
		String titulo = "Nuevo recargo";
		titulo = getTipoRecargo().getDescripcion() == null ? titulo : getTipoRecargo().getDescripcion();
		return titulo;
	}
	
	@Override
	public String toString() {
		return "Recargo (id-descripción): " + getId() + "-" + getTipoRecargo().getDescripcion();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		final Recargo otro = (Recargo) other;
		if (!this.getId().equals(otro.getId())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = 31 * this.getId().hashCode();
		return result;
	}
	
	
	public LineaRecargoTxt toLineaRecargoTxt(Idioma idioma) {
		LineaRecargoTxt linea = new LineaRecargoTxt();
		if (idioma.equals(Idioma.ESP)) {
			linea.setDescripcion(this.getTextoFactura() == null ? "Por definir" : this.getTextoFactura());
		} else {
			linea.setDescripcion(this.getTextoIngles() == null ? "Pending definition" : this.getTextoIngles());
		}
		linea.setMonto(this.getMonto());
		return linea;
	}
	
	
	// *********************************** getters y setters ******************************

	public Long getIdRecargo() {
		return idRecargo;
	}

	public void setIdRecargo(Long idRecargo) {
		this.idRecargo = idRecargo;
	}

	public Keyword getTipoRecargo() {
		return tipoRecargo;
	}

	public void setTipoRecargo(Keyword tipoRecargo) {
		this.tipoRecargo = tipoRecargo;
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

	public Float getMonto() {
		return monto;
	}

	public void setMonto(Float monto) {
		this.monto = monto;
	}

	public String getTextoFactura() {
		return textoFactura;
	}

	public void setTextoFactura(String textoFactura) {
		this.textoFactura = textoFactura;
	}

	public String getTextoIngles() {
		return textoIngles;
	}

	public void setTextoIngles(String textoIngles) {
		this.textoIngles = textoIngles;
	}

	public Keyword getEstado() {
		return estado;
	}

	public void setEstado(Keyword estado) {
		this.estado = estado;
	}
	
}
