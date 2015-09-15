package rcp.assets.model;

public class FacturacionEspecial {

	private Long idFactEspecial = -1L;
	
	private Mes mes;
	private Expediente expediente;
	
	private String detalle;
	
	
	public Long getId() {
		return idFactEspecial;
	}
	
	// ******************************** métodos especiales ********************************
	
	@Override
	public String toString() {
		return "Fact. especial (id-noFact): " + getId() + "-" + getIdFactEspecial();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		final FacturacionEspecial otro = (FacturacionEspecial) other;
		if (!this.getId().equals(otro.getId())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = 31 * this.getId().hashCode();
		return result;
	}


	
	// *********************************** getters y setters ******************************
	
	public Long getIdFactEspecial() {
		return idFactEspecial;
	}

	public void setIdFactEspecial(Long idFactEspecial) {
		this.idFactEspecial = idFactEspecial;
	}

	public Mes getMes() {
		return mes;
	}

	public void setMes(Mes mes) {
		this.mes = mes;
	}

	public Expediente getExpediente() {
		return expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
}
