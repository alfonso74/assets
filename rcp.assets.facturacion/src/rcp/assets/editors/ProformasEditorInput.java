package rcp.assets.editors;

import java.util.HashMap;
import java.util.Map;

import rcp.assets.services.HibernateUtil;


public class ProformasEditorInput extends GenericEditorInput {
	
	private Map<String, Object> parametros;
	private String tipoProforma;
	
	
	public ProformasEditorInput() {
		super();
		inicializarParametros();
	
	}
	
	public ProformasEditorInput(Long id) {
		super(id);
		inicializarParametros();
	}
	
	public ProformasEditorInput(String name) {
		super(name);
		inicializarParametros();
	}
	
	
	private void inicializarParametros() {
		parametros = new HashMap<String, Object>();
		parametros.put("connectionURL", HibernateUtil.getConnectionURL());
		parametros.put("idCaso", 8729);			//Integer
		parametros.put("impuesto", 3.33F);		//Float
		parametros.put("idioma", "1");			//String
	}
	
	
	public void configurarParametrosCaso(Integer idCaso, Float impuesto, String idioma) {
		parametros.put("idCaso", idCaso);			//Integer
		parametros.put("impuesto", impuesto);		//Float
		parametros.put("idioma", idioma);			//String
	}
	
	
	public void configurarParametrosRetainer(Integer idExpediente, Float impuesto, String idioma, Integer idMes, Integer yyyy) {
		parametros.put("idExpediente", idExpediente);	//Integer
		parametros.put("impuesto", impuesto);			//Float
		parametros.put("idioma", idioma);				//String
		parametros.put("idMes", idMes);					//Integer
		parametros.put("yyyy", yyyy);					//Integer
	}
	
	
	public void configurarParametrosAnualidad(Integer idExpediente, Float impuesto, String idioma, String concepto, Long noPeriodo, String tipoNotificacion) {
		parametros.put("idExpediente", idExpediente);	//Integer
		parametros.put("impuesto", impuesto);			//Float
		parametros.put("idioma", idioma);				//String
		parametros.put("concepto", concepto);			//String
		parametros.put("noPeriodo", noPeriodo.toString());
		parametros.put("tipoNotificacion", tipoNotificacion);
	}
	

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public String getTipoProforma() {
		return tipoProforma;
	}

	/**
	 * Indica al Editor qué reporte utilizar al momento de generar la proforma
	 * @param tipoProforma puede ser:  "caso", "retainer" o "anualidad"
	 */
	public void setTipoProforma(String tipoProforma) {
		this.tipoProforma = tipoProforma;
	}

}
