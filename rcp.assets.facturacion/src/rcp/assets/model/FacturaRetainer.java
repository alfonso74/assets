package rcp.assets.model;

public class FacturaRetainer extends Factura {

	private Factura factura;
	
	private Float retainer1;
	private Float retainer2;
	private Float retainer3;
	private Float otroRetainer;
	private String otroRetainerCom;
	
	
	public FacturaRetainer() {
		setTipo("RE");
//		Factura.Tipo.RE.toString();
	}
	
	
	// ******************************** métodos especiales ********************************
	
	/* (non-Javadoc)
	 * @see rcp.assets.model.IFactura#calcularTotalDetalle()
	 */
	@Override
	public void calcularTotalDetalle() {
		Float ret1 = getRetainer1() == null ? 0F : getRetainer1();
		Float ret2 = getRetainer2() == null ? 0F : getRetainer2();
		Float ret3 = getRetainer3() == null ? 0F : getRetainer3();
		Float retOtro = getOtroRetainer() == null ? 0F : getOtroRetainer();
		setTotalDetalle(ret1 + ret2 + ret3 + retOtro);
		setTotal(getTotalDetalle() + getImpuesto());
	}
	
	
	/* (non-Javadoc)
	 * @see rcp.assets.model.IFactura#calcularImpuesto()
	 */
	@Override
	public void calcularImpuesto() {
		if (isExcentoImpuesto()) {
			setImpuesto(0F);
		} else {
			Float impuestoTmp = getImpuestoPorcentaje() / 100;
			Float impRet1 = getRetainer1() == null ? 0F : getRetainer1() * impuestoTmp;
			Float impRet2 = getRetainer2() == null ? 0F : getRetainer2() * impuestoTmp;
			Float impRet3 = getRetainer3() == null ? 0F : getRetainer3() * impuestoTmp;
			Float impRetOtro = getOtroRetainer() == null ? 0F : getOtroRetainer() * impuestoTmp;
			setImpuesto(impRet1 + impRet2 + impRet3 + impRetOtro);
		}
		setTotal(getTotalDetalle() + getImpuesto());
	}


	@Override
	public Integer getCantidadLineas() {
		int cantidad = 0;
		if (getRetainer1() != null && getRetainer1() != 0) cantidad++;
		if (getRetainer2() != null && getRetainer2() != 0) cantidad++;
		if (getRetainer3() != null && getRetainer3() != 0) cantidad++;
		if (getOtroRetainer() != null && getOtroRetainer() != 0) cantidad++;
		return cantidad;
	}	
	
	
	// *********************************** getters y setters ******************************

	public Factura getFactura() {
		return factura;
	}


	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	
	
	public Float getRetainer1() {
		return retainer1;
	}


	public void setRetainer1(Float retainer1) {
		this.retainer1 = retainer1;
	}


	public Float getRetainer2() {
		return retainer2;
	}


	public void setRetainer2(Float retainer2) {
		this.retainer2 = retainer2;
	}


	public Float getRetainer3() {
		return retainer3;
	}


	public void setRetainer3(Float retainer3) {
		this.retainer3 = retainer3;
	}


	public Float getOtroRetainer() {
		return otroRetainer;
	}


	public void setOtroRetainer(Float otroRetainer) {
		this.otroRetainer = otroRetainer;
	}


	public String getOtroRetainerCom() {
		return otroRetainerCom;
	}


	public void setOtroRetainerCom(String otroRetainerCom) {
		this.otroRetainerCom = otroRetainerCom;
	}

}
