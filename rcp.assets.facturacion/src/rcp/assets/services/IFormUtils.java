package rcp.assets.services;

public interface IFormUtils {
	
	/**
	 * Transforma un objeto String a un objeto de tipo Double.
	 * @param valorCampo objeto de tipo String que puede ser transformado a un número
	 * @return objeto de tipo Double con formato .
	 */
	public abstract Double txt2Currency(String valorCampo);
	
	/**
	 * Transforma un objeto String a un objeto de tipo Float.
	 * @param valorCampo objeto de tipo String que puede ser transformado a un número
	 * @return objeto de tipo Float.
	 */
	public Float txt2Float(String valorCampo);
	
	/**
	 * Transforma un objeto String a un objeto de tipo Integer.
	 * @param valorCampo objeto de tipo String que puede ser transformado a un número
	 * @return objeto de tipo Integer.
	 */
	public Integer txt2Integer(String valorCampo);
	
	/**
	 * Transforma un objeto String a un objeto de tipo Long.
	 * @param valorCampo objeto de tipo String que puede ser transformado a un número
	 * @return objeto de tipo Long.
	 */
	public Long txt2Long(String valorCampo);
	
	/**
	 * Transforma un valor numérico a su representacion de tipo String.
	 * @param valorCampo Objeto de tipo numérico
	 * @return El objeto original con el toString() aplicado, o un String vacío ("") en
	 * caso de recibir null.
	 */
	public String valor2Txt(Object valorCampo);
	
	/** 
	 * Presenta un valor numérico en el formato suministrado.
	 * @param valorCampo Objeto de tipo numérico
	 * @param formato ejm: "0.00", "#,##0.00"
	 * @return Un String numérico en el formato indicado, o un String vacío ("") en caso
	 * de recibir null.
	 */
	public String valor2Txt(Object valorCampo, String formato);
	
	/**
	 * Manejo de campos tipo Boolean donde puede existir null.  Si el valor
	 * es null entonces retorna false, sino retorna booleanValue(). 
	 * @param valorCampo
	 * @return true o false
	 */
	public boolean valor2Bool(Boolean valorCampo);
}
