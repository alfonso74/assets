package rcp.assets.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public abstract class Factura implements IEditableDocument, IFactura {
	
	private Long idFactura = -1L;
	private Caso caso;
	
	private Integer periodoMM;
	private Integer periodoYY;
	private String tipo;
	private String noFactura;
	private String noFisico;			// número físico de la factura
	private Integer noPeriodoTx;			// periodo de peachtree
	private String dspIdPeachtree;
	private String noExpediente;
	private Integer noCliente;
	private Long refSecuencia;
	private String nombreExpediente;
	private String concepto;
	private String nombreCliente;
	private String direccion1;
	private String direccion2;
	private String direccion3;
	private String direccion4;
	private String direccion5;
	private String atencion;
	private Date fechaFactura;
	private Float totalDetalle;
	private Float impuestoPorcentaje;
	private Float impuesto;
	private Float total;
	private Float saldo;
	private String notificacion;
	private boolean excentoImpuesto;
	private String idioma;
	private String estado;
	
	private Date fechaCreado;
	private Date fechaModificado;
	
	private List<LineaRecargoTxt> lineasRecargoTxt;
	

	public Factura() {
	}

	
	public abstract Integer getCantidadLineas();
	
	@Override
	public Long getId() {
		return getIdFactura();
	}
	
	
	// ******************************** métodos especiales ********************************

	@Override
	public String getTituloDocumento() {
		return "Factura " + getNoFactura();
	}
	
	
	/**
	 * Actualiza el campo del monto total de la factura (recalculando las líneas de la factura y los impuestos).
	 */
	public void recalcularTotales() {
		calcularTotalDetalle();
		calcularImpuesto();
		setTotal(getTotalDetalle() + getImpuesto());
	}
	
	@Override
	public String toString() {
		return "Factura (id-noFactura): " + getId() + "-" + getNoFactura();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		final Factura otro = (Factura) other;
		if (!this.getId().equals(otro.getId())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = 31 * this.getId().hashCode();
		return result;
	}
	
	
	public String getDspIdPeachtree() {
		return dspIdPeachtree;
	}
	
	public void setDspIdPeachtree(String dspIdPeachtree) {
		this.dspIdPeachtree = dspIdPeachtree;
	}
	
	
	/**
	 * Agrega una línea de descripción de recargo a la factura, y hace la asociación
	 * bidireccional del lado de la factura y de la descripción de recargo.
	 * @param linea objeto LineaRecargoTxt que se va a agregar
	 */
	public void addLineaRecargoTxt(LineaRecargoTxt linea) {
		linea.setFactura(this);
		getLineasRecargoTxt().add(linea);
	}
	
	// *********************************** getters y setters ******************************


	public Long getIdFactura() {
		return idFactura;
	}


	public void setIdFactura(Long idFactura) {
		this.idFactura = idFactura;
	}
	

	public Caso getCaso() {
		return caso;
	}


	public void setCaso(Caso caso) {
		this.caso = caso;
	}
	

	public Integer getPeriodoMM() {
		return periodoMM;
	}


	public void setPeriodoMM(Integer periodoMM) {
		this.periodoMM = periodoMM;
	}


	public Integer getPeriodoYY() {
		return periodoYY;
	}


	public void setPeriodoYY(Integer periodoYY) {
		this.periodoYY = periodoYY;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getNoFactura() {
		return noFactura;
	}


	public void setNoFactura(String noFactura) {
		this.noFactura = noFactura;
	}
	

	public String getNoFisico() {
		return noFisico;
	}

	public void setNoFisico(String noFisico) {
		this.noFisico = noFisico;
	}

	
	public Integer getNoPeriodoTx() {
		return noPeriodoTx;
	}

	public void setNoPeriodoTx(Integer noPeriodoTx) {
		this.noPeriodoTx = noPeriodoTx;
	}


	public String getNoExpediente() {
		return noExpediente;
	}


	/**
	 * Número de expediente en la factura
	 * @param refExpediente
	 */
	public void setNoExpediente(String refExpediente) {
		this.noExpediente = refExpediente;
	}


	public Integer getNoCliente() {
		return noCliente;
	}


	/**
	 * Número del cliente en la factura
	 * @return
	 */
	public void setNoCliente(Integer noCliente) {
		this.noCliente = noCliente;
	}


	public Long getRefSecuencia() {
		return refSecuencia;
	}


	/**
	 * Número de caso / expediente en la factura
	 * @return
	 */
	public void setRefSecuencia(Long refSecuencia) {
		this.refSecuencia = refSecuencia;
	}


	public String getNombreExpediente() {
		return nombreExpediente;
	}


	public void setNombreExpediente(String nombreExpediente) {
		this.nombreExpediente = nombreExpediente;
	}


	public String getConcepto() {
		return concepto;
	}


	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}


	public String getNombreCliente() {
		return nombreCliente;
	}


	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
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

	
	public String getAtencion() {
		return atencion;
	}

	public void setAtencion(String atencion) {
		this.atencion = atencion;
	}


	public Date getFechaFactura() {
		return fechaFactura;
	}


	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}


	/**
	 * @return El total de la factura (antes de impuestos).
	 */
	public Float getTotalDetalle() {
		return totalDetalle;
	}


	public void setTotalDetalle(Float totalDetalle) {
		this.totalDetalle = totalDetalle;
	}

	
	public Float getImpuestoPorcentaje() {
		return impuestoPorcentaje;
	}

	public void setImpuestoPorcentaje(Float impuestoPorcentaje) {
		this.impuestoPorcentaje = impuestoPorcentaje;
	}


	public Float getImpuesto() {
		return impuesto;
	}


	public void setImpuesto(Float impuesto) {
		this.impuesto = impuesto;
	}


	/**
	 * @return El monto total de la factura (incluyendo impuesto, si lo hay).
	 */
	public Float getTotal() {
		return total;
	}


	public void setTotal(Float total) {
		this.total = total;
	}


	public Float getSaldo() {
		return saldo;
	}


	public void setSaldo(Float saldo) {
		this.saldo = saldo;
	}
	
	
	public String getNotificacion() {
		return notificacion;
	}


	public void setNotificacion(String notificacion) {
		this.notificacion = notificacion;
	}


	public boolean isExcentoImpuesto() {
		return excentoImpuesto;
	}


	public void setExcentoImpuesto(Boolean excentoImpuesto) {
		this.excentoImpuesto = (excentoImpuesto == null ? Boolean.FALSE : excentoImpuesto.booleanValue());
	}

	/**
	 * Idioma de la factura
	 * @return 1-Español, 2-Inglés
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Idioma de la factura
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
	
	public List<LineaRecargoTxt> getLineasRecargoTxt() {
		if (lineasRecargoTxt == null) {
			lineasRecargoTxt = new ArrayList<LineaRecargoTxt>();
		}
		return lineasRecargoTxt;
	}

	public void setLineasRecargoTxt(List<LineaRecargoTxt> lineasRecargoTxt) {
		this.lineasRecargoTxt = lineasRecargoTxt;
	}

	
	public enum Estado {
		ACT,
		INA
	}
	
}
