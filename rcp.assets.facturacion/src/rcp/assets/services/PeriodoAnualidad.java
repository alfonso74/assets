package rcp.assets.services;

import java.util.Calendar;

import rcp.assets.model.Idioma;


public class PeriodoAnualidad {
	
	private Long idPeriodo = -1L;
	
	private String nombre;
	
	private Integer periodoDesdeDD;
	private Integer periodoDesdeMM;
	private Integer periodoDesdeYY;
	//private Date fechaPeriodoDesde;
	
	private Integer periodoHastaDD;
	private Integer periodoHastaMM;
	private Integer periodoHastaYY;
	//private Date fechaPeriodoHasta;
	
	private Integer inscripcionDesdeDD;
	private Integer inscripcionDesdeMM;
	private Integer inscripcionDesdeYY;
	//private Date fechaInscripcionDesde;
	
	private Integer inscripcionHastaDD;
	private Integer inscripcionHastaMM;
	private Integer inscripcionHastaYY;
	//private Date fechaInscripcionHasta;
	
	
	public PeriodoAnualidad() {
	}
	
	public Long getId() {
		return getIdPeriodo();
	}


	
	// ******************************** métodos especiales ********************************
	
	public String getConceptoFactura(Integer yy, String idioma) {
		String concepto = "N/A";
		if (idioma.equals(Idioma.ESP.getCodigo())) {
			concepto = getConceptoFacturaEsp(yy);
		} else {
			concepto = getConceptoFacturaIngles(yy);
		}
		return concepto;
	}
	
	
	public String getConceptoFacturaEsp(Integer yy) {
		Calendar perDesde = Calendar.getInstance();
		Calendar perHasta = Calendar.getInstance();
		setPeriodoDesdeYY(yy);
		if (getPeriodoDesdeMM().intValue() == 0) {
			setPeriodoHastaYY(yy);
		} else {
			setPeriodoHastaYY(yy + 1);
		}
		perDesde.set(getPeriodoDesdeYY(), getPeriodoDesdeMM(), getPeriodoDesdeDD());
		perHasta.set(getPeriodoHastaYY(), getPeriodoHastaMM(), getPeriodoHastaDD());
		FechaUtil.setLocalES();
		String periodo01 = FechaUtil.toString(perDesde.getTime(), "dd 'de' MMMM 'de' yyyy");
		String periodo02 = FechaUtil.toString(perHasta.getTime(), "dd 'de' MMMM 'de' yyyy");
		return periodo01 + " hasta " + periodo02;
	}
	
	public String getConceptoFacturaIngles(Integer yy) {
		Calendar perDesde = Calendar.getInstance();
		Calendar perHasta = Calendar.getInstance();
		setPeriodoDesdeYY(yy);
		if (getPeriodoDesdeMM().intValue() == 0) {
			setPeriodoHastaYY(yy);
		} else {
			setPeriodoHastaYY(yy + 1);
		}
		perDesde.set(getPeriodoDesdeYY(), getPeriodoDesdeMM(), getPeriodoDesdeDD());
		perHasta.set(getPeriodoHastaYY(), getPeriodoHastaMM(), getPeriodoHastaDD());
		FechaUtil.setLocalEN();
		String periodo01 = FechaUtil.toString(perDesde.getTime(), "MMMM dd, yyyy");
		String periodo02 = FechaUtil.toString(perHasta.getTime(), "MMMM dd, yyyy");
		return periodo01 + " to " + periodo02;
	}
	
	@Override
	public String toString() {
		return "Periodo (id-desc): " + getId() + "-" + getNombre();
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;

		final PeriodoAnualidad otro = (PeriodoAnualidad) other;
		if (!this.getId().equals(otro.getId())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = 31 * this.getId().hashCode();
		return result;
	}
	
	
	/**
	 * Formatea un mes para ser agregado a un ComboData (en español)
	 * @return ComboDataItem listo para procesar
	 */
	public ComboDataItem toComboDataItem() {
		ComboDataItem cdItem = new ComboDataItem();
		cdItem.setKey(getId().toString());
		cdItem.setTexto(getNombre());
		cdItem.setObjeto(this);
		return cdItem;
	}
	
	
	
	// *********************************** getters y setters ******************************

	public Long getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Long idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getPeriodoDesdeDD() {
		return periodoDesdeDD;
	}

	public void setPeriodoDesdeDD(Integer periodoDesdeDD) {
		this.periodoDesdeDD = periodoDesdeDD;
	}

	public Integer getPeriodoDesdeMM() {
		return periodoDesdeMM;
	}

	public void setPeriodoDesdeMM(Integer periodoDesdeMM) {
		this.periodoDesdeMM = periodoDesdeMM;
	}

	public Integer getPeriodoDesdeYY() {
		return periodoDesdeYY;
	}

	public void setPeriodoDesdeYY(Integer periodoDesdeYY) {
		this.periodoDesdeYY = periodoDesdeYY;
	}

	public Integer getPeriodoHastaDD() {
		return periodoHastaDD;
	}

	public void setPeriodoHastaDD(Integer periodoHastaDD) {
		this.periodoHastaDD = periodoHastaDD;
	}

	public Integer getPeriodoHastaMM() {
		return periodoHastaMM;
	}

	public void setPeriodoHastaMM(Integer periodoHastaMM) {
		this.periodoHastaMM = periodoHastaMM;
	}

	public Integer getPeriodoHastaYY() {
		return periodoHastaYY;
	}

	public void setPeriodoHastaYY(Integer periodoHastaYY) {
		this.periodoHastaYY = periodoHastaYY;
	}

	public Integer getInscripcionDesdeDD() {
		return inscripcionDesdeDD;
	}

	public void setInscripcionDesdeDD(Integer inscripcionDesdeDD) {
		this.inscripcionDesdeDD = inscripcionDesdeDD;
	}

	public Integer getInscripcionDesdeMM() {
		return inscripcionDesdeMM;
	}

	public void setInscripcionDesdeMM(Integer inscripcionDesdeMM) {
		this.inscripcionDesdeMM = inscripcionDesdeMM;
	}

	public Integer getInscripcionDesdeYY() {
		return inscripcionDesdeYY;
	}

	public void setInscripcionDesdeYY(Integer inscripcionDesdeYY) {
		this.inscripcionDesdeYY = inscripcionDesdeYY;
	}

	public Integer getInscripcionHastaDD() {
		return inscripcionHastaDD;
	}

	public void setInscripcionHastaDD(Integer inscripcionHastaDD) {
		this.inscripcionHastaDD = inscripcionHastaDD;
	}

	public Integer getInscripcionHastaMM() {
		return inscripcionHastaMM;
	}

	public void setInscripcionHastaMM(Integer inscripcionHastaMM) {
		this.inscripcionHastaMM = inscripcionHastaMM;
	}

	public Integer getInscripcionHastaYY() {
		return inscripcionHastaYY;
	}

	public void setInscripcionHastaYY(Integer inscripcionHastaYY) {
		this.inscripcionHastaYY = inscripcionHastaYY;
	}
	
}
