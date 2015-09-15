package rcp.assets.dto;

import java.util.HashMap;
import java.util.Map;

import rcp.assets.model.Periodo;
import rcp.assets.services.HibernateUtil;

/**
 * Permite pasar los parametros a un reporte de BIRT.
 * @author Owner
 *
 */
public class FacturaRetainerParams {

	private String connectionURL;
	private Integer idExpediente;
	private String idioma;
	private Float impuesto;
	private Periodo periodo;
	
	public FacturaRetainerParams(Integer idExpediente) {
		connectionURL = HibernateUtil.getConnectionURL();
		this.idExpediente = idExpediente;
	}
	
	public Map<String, Object> generarParametrosReporte() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("connectionURL", getConnectionURL());
		parametros.put("idExpediente", getIdExpediente());
		if (getIdioma() != null) parametros.put("idioma", getIdioma());
		if (getImpuesto() != null) parametros.put("impuesto", getImpuesto());
		return parametros;
	}

	public String getConnectionURL() {
		return connectionURL;
	}

	public void setConnectionURL(String connectionURL) {
		this.connectionURL = connectionURL;
	}

	public Integer getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(Integer idExpediente) {
		this.idExpediente = idExpediente;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public Float getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(Float impuesto) {
		this.impuesto = impuesto;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
	
}
