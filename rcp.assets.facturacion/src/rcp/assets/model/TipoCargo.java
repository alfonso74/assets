package rcp.assets.model;

public class TipoCargo implements IEditableDocument {

	private Long idTipoCargo = -1L;
	private String noTipoCargo;
	private String descripcion;
	private String descripcionIngles;
	private Float valor;
	private Integer prioridad;
	private String grupo;
	private String dspGrupo;
	private String estado;
	private String dspEstado;
	
	private String honorario;
	private String pagaImpuesto;
	private String ingreso;

	private String nivel1;
	private String nivel2;
	private String nivel3;
	private String nivel4;
	private String nivel5;
	

	public TipoCargo() {
	}

	@Override
	public Long getId() {
		return idTipoCargo;
	}

	// TODO revisar para qué me sirve este setter
	public void setId(Long pId) {
		idTipoCargo = pId;
	}


	// ******************************** métodos especiales ********************************

	// retorna la cuenta contable que corresponde al tipo de cargo en PeachTree
	public String getCuentaContablePeachTree() {
		return getNivel1() + "-" + getNivel2() + "-" + getNivel3();
	}
	
	
	public String getIngreso() {
		return ingreso;
	}

	public void setIngreso(String ingreso) {
		this.ingreso = ingreso;
	}

	@Override
	public String getTituloDocumento() {
		String titulo = "Nuevo tipo de cargo";
		titulo = getDescripcion() == null ? titulo : getDescripcion();
		return titulo;
	}
	
	@Override
	public String toString() {
		return "Tipo de cargo (id-nombre): " + getId() + "-" + getDescripcion();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		final TipoCargo otro = (TipoCargo) other;
		if (!this.getId().equals(otro.getId())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = 31 * this.getId().hashCode();
		return result;
	}
	
	
	// *********************************** getters y setters ******************************
	

	public Long getIdTipoCargo() {
		return idTipoCargo;
	}

	public void setIdTipoCargo(Long idTipoCargo) {
		this.idTipoCargo = idTipoCargo;
	}

	public String getNoTipoCargo() {
		return noTipoCargo;
	}

	public void setNoTipoCargo(String noTipoCargo) {
		this.noTipoCargo = noTipoCargo;
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

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}
	
	public Integer getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}

	public String getHonorario() {
		return honorario;
	}

	public void setHonorario(String honorario) {
		this.honorario = honorario;
	}

	public String getPagaImpuesto() {
		return pagaImpuesto;
	}

	public void setPagaImpuesto(String pagaImpuesto) {
		this.pagaImpuesto = pagaImpuesto;
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
	
	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getDspGrupo() {
		return dspGrupo;
	}

	public void setDspGrupo(String dspGrupo) {
		this.dspGrupo = dspGrupo;
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

}
