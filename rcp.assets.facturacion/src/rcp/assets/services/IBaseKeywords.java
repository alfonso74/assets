package rcp.assets.services;

import rcp.assets.model.Compania;


public interface IBaseKeywords {
	
//	public static final String[] TIPO_KEYWORDS = {"Idioma", "Opción general", "Recargo de anualidad", 
//		"Status caso", "Status expediente", "Status general", "Tipo de aviso", "Z"}; 

	public static final String[] TIPO_KEYWORDS = {TipoKeyword.IDIOMA.getDescripcion(),
		TipoKeyword.CONDICIONAL.getDescripcion(),
		TipoKeyword.RECARGO.getDescripcion(),
		TipoKeyword.STATUS_CASO.getDescripcion(),
		TipoKeyword.STATUS_EXP.getDescripcion(),
		TipoKeyword.STATUS.getDescripcion(),
		TipoKeyword.NOTIFICACION.getDescripcion()};
	
	public static final String[] COMPANIAS = {Compania.PMA.getCodigo(), Compania.BVI.getCodigo()};
	
	public static final String[] CONDICIONAL = {"Si", "No"};
	
	public static final String[] ESTADO = {"Activo", "Inactivo"};
	
	public static final String[] MODO_TASA = {"Con tasa", "Sin tasa"};
	
	public static final String[] PERIODO_ANUALIDAD = {"Primer periodo", "Segundo periodo"};

	
	public enum TipoKeyword {
		IDIOMA("Idioma"),
		CONDICIONAL("Opción general"),
		RECARGO("Recargo de anualidad"),
		STATUS_CASO("Status caso"),
		STATUS_EXP("Status expediente"),
		STATUS("Status general"),
		NOTIFICACION("Tipo de notificación");
		
		private String descripcion;
		
		private TipoKeyword(String descripcion) {
			this.descripcion = descripcion;
		}
		
		public String getDescripcion() {
			return descripcion;
		}
	}
}
