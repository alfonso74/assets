package rcp.assets.model;


public class Retainer {
	
	private Float retainer1 = 0F;
	private Float retainer2 = 0F;
	private Float retainer3 = 0F;
	private Float retainerOtros = 0F;
	private String retainerOtrosDesc;
	private Float totalRetainers = 0F;

	
	// ******************************** métodos especiales ********************************
	
	@Override
	public String toString() {
		return "Retainer"; 
	}
	
	
	public Float getTotalRetainerConImpuesto(float impuesto) {
		return getTotalRetainers() * impuesto;
	}

	
	// *********************************** getters y setters ******************************

	public Float getRetainer1() {
		return retainer1 == null ? 0 : retainer1;
	}

	public void setRetainer1(Float retainer1) {
		this.retainer1 = retainer1;
	}


	public Float getRetainer2() {
		return retainer2 == null ? 0 : retainer2;
	}

	public void setRetainer2(Float retainer2) {
		this.retainer2 = retainer2;
	}


	public Float getRetainer3() {
		return retainer3 == null ? 0 : retainer3;
	}

	public void setRetainer3(Float retainer3) {
		this.retainer3 = retainer3;
	}


	public Float getRetainerOtros() {
		return retainerOtros == null ? 0 : retainerOtros;
	}

	public void setRetainerOtros(Float retainerOtros) {
		this.retainerOtros = retainerOtros;
	}


	public String getRetainerOtrosDesc() {
		return retainerOtrosDesc;
	}

	public void setRetainerOtrosDesc(String retainerOtrosDesc) {
		this.retainerOtrosDesc = retainerOtrosDesc;
	}
	

	public Float getTotalRetainers() {
		return totalRetainers;
	}

	public void setTotalRetainers(Float totalRetainers) {
		this.totalRetainers = totalRetainers;
	}

}
