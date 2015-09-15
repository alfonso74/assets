package rcp.assets.model;

public class DetalleCargo implements IEditableDocument {
	
	private Long idDetalle = -1L;
	private Cargo cargo;
	private TipoCargo tipoCargo;
	private GrupoCargo grupo;
	
	private Float monto;
	private int secuencia;
	private String nivel1;
	private String nivel2;
	private String nivel3;
	private String nivel4;
	private String nivel5;
	
	

	@Override
	public Long getId() {
		return idDetalle;
	}
	
	// ******************************** métodos especiales ********************************

	@Override
	public String getTituloDocumento() {
		return "DetalleCargo " + getId();
	}
	
	@Override
	public String toString() {
		return "DetalleCargo (id-TipoCargo): " + getId() + "-" + getTipoCargo().getDescripcion();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		final DetalleCargo otro = (DetalleCargo) other;
		if (!this.getId().equals(otro.getId())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = 31 * this.getId().hashCode();
		return result;
	}

	/**
	 * Permite obtener el monto de la linea de detalle más el impuesto indicado
	 * @param impuesto Valor para calcular impuesto (ej. 0.07 para el 7%)
	 * @return Monto del detalle con el impuesto agregado
	 */
	public Float getMontoConImpuesto(float impuesto) {
		return getMonto() * impuesto;
	}

	// *********************************** getters y setters ******************************

	public Long getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Long idDetalle) {
		this.idDetalle = idDetalle;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public TipoCargo getTipoCargo() {
		return tipoCargo;
	}

	public void setTipoCargo(TipoCargo tipoCargo) {
		this.tipoCargo = tipoCargo;
	}

	public GrupoCargo getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoCargo grupo) {
		this.grupo = grupo;
	}

	public Float getMonto() {
		return monto;
	}

	public void setMonto(Float monto) {
		this.monto = monto;
	}

	public int getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(int secuencia) {
		this.secuencia = secuencia;
	}

	public String getNivel1() {
		return nivel1;
	}

	public void setNivel1(String nivel1) {
		this.nivel1 = nivel1;
	}

	public String getNivel2() {
		return nivel2;
	}

	public void setNivel2(String nivel2) {
		this.nivel2 = nivel2;
	}

	public String getNivel3() {
		return nivel3;
	}

	public void setNivel3(String nivel3) {
		this.nivel3 = nivel3;
	}

	public String getNivel4() {
		return nivel4;
	}

	public void setNivel4(String nivel4) {
		this.nivel4 = nivel4;
	}

	public String getNivel5() {
		return nivel5;
	}

	public void setNivel5(String nivel5) {
		this.nivel5 = nivel5;
	}
	
}
