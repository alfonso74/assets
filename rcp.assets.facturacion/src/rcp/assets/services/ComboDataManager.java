package rcp.assets.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rcp.assets.dao.KeywordDAO;
import rcp.assets.dao.MesDAO;
import rcp.assets.model.Keyword;
import rcp.assets.model.Mes;
import rcp.assets.model.Keyword.Estado;


public final class ComboDataManager {

	private static ComboDataManager instance;
	private static HashMap<String, ComboData> listaCD;
	private static List<String> listaTipoKeywords;


	/**
	 * Singleton
	 */
	protected ComboDataManager() {
	}


	public static ComboDataManager getInstance() {
		if (instance == null) {
			instance = new ComboDataManager();
			inicializar();
		}
		return instance;
	}


	private static void inicializar() {
		KeywordDAO dao = new KeywordDAO();
		ComboData cd = null;
		listaCD = new HashMap<String, ComboData>();
		listaTipoKeywords = new ArrayList<String>();
		//		List<Keyword> listaKeywords = dao.findAllOrdered("tipo", true);
//		List<Keyword> listaKeywords = dao.findAllForComboDataManager();
		List<Keyword> listaKeywords = new ArrayList<Keyword>();
		listaKeywords.add(new Keyword(900L, "ANXX", "Sin notificación", 
				IBaseKeywords.TipoKeyword.NOTIFICACION.getDescripcion(), null, Estado.ACTIVO));
		listaKeywords.addAll(dao.findAllForComboDataManager());
		
		String tipoKeyword = null;
		for (Keyword k : listaKeywords) {
			tipoKeyword = k.getTipo();
			if (listaCD.containsKey(tipoKeyword)) {
				cd = listaCD.get(tipoKeyword);
				cd.agregarComboDataItem(k.toComboDataItem());
			} else {
				ComboData data = new ComboData();
				data.agregarComboDataItem(k.toComboDataItem());
				listaCD.put(tipoKeyword, data);
				listaTipoKeywords.add(tipoKeyword);
			}
		}

		inicializarMeses();
		inicializarPeriodos();
		
	}


	private static void inicializarMeses() {
		MesDAO daoMes = new MesDAO();
		List<Mes> listaMeses = daoMes.findAll();
		ComboData cdMesesSpanish = new ComboData();
		ComboData cdMesesEnglish = new ComboData();
		for (Mes m : listaMeses) {
			cdMesesSpanish.agregarComboDataItem(m.toComboDataItemSpanish());
			cdMesesEnglish.agregarComboDataItem(m.toComboDataItemEnglish());
		}
		listaCD.put("Meses español", cdMesesSpanish);
		listaCD.put("Meses inglés", cdMesesEnglish);
	}


	private static void inicializarPeriodos() {
		ComboData cdPeriodo = new ComboData();

		PeriodoAnualidad periodo01 = new PeriodoAnualidad();
		periodo01.setIdPeriodo(1L);
		periodo01.setNombre(IBaseKeywords.PERIODO_ANUALIDAD[0]);
		periodo01.setPeriodoDesdeDD(1);
		periodo01.setPeriodoDesdeMM(0);
		periodo01.setPeriodoHastaDD(31);
		periodo01.setPeriodoHastaMM(11);
		periodo01.setInscripcionDesdeDD(1);
		periodo01.setInscripcionDesdeMM(0);
		periodo01.setInscripcionHastaDD(30);
		periodo01.setInscripcionHastaMM(05);
		cdPeriodo.agregarComboDataItem(periodo01.toComboDataItem());

		PeriodoAnualidad periodo02 = new PeriodoAnualidad();
		periodo02 = new PeriodoAnualidad();
		periodo02.setIdPeriodo(2L);
		periodo02.setNombre(IBaseKeywords.PERIODO_ANUALIDAD[1]);
		periodo02.setPeriodoDesdeDD(1);
		periodo02.setPeriodoDesdeMM(6);
		periodo02.setPeriodoHastaDD(30);
		periodo02.setPeriodoHastaMM(5);
		periodo02.setInscripcionDesdeDD(1);
		periodo02.setInscripcionDesdeMM(6);
		periodo02.setInscripcionHastaDD(31);
		periodo02.setInscripcionHastaMM(11);
		cdPeriodo.agregarComboDataItem(periodo02.toComboDataItem());

		System.out.println("Periodo01 ES: " + periodo01.getConceptoFacturaEsp(2012));
		System.out.println("Periodo01 EN: " + periodo01.getConceptoFacturaIngles(2012));
		System.out.println("Periodo02 ES: " + periodo02.getConceptoFacturaEsp(2012));
		System.out.println("Periodo02 EN: " + periodo02.getConceptoFacturaIngles(2012));

		listaCD.put("Periodo anualidad", cdPeriodo);
	}


	public HashMap<String, ComboData> getListaCD() {
		return listaCD;
	}


	public String[] getListaTipoKeywordsDefault() {
		return IBaseKeywords.TIPO_KEYWORDS;
	}


	public String[] getListaTipoKeywords() {
		if (listaTipoKeywords.isEmpty()) {
			return getListaTipoKeywordsDefault();
		};
		return listaTipoKeywords.toArray(new String[listaTipoKeywords.size()]);
	}


	/**
	 * Obtiene un objeto ComboData con la información del tipo de keyword indicado.
	 * @param tipo indica el tipo de Keyword que debe ser retornado
	 * @return objeto ComboData conformado por el tipo de Keyword indicado, o un objeto
	 * ComboData vacío si no se encuentra el tipo de Keyword.
	 */
	public ComboData getComboData(String tipo) {
		ComboData data = new ComboData();
		if (listaCD.containsKey(tipo)) {
			data = listaCD.get(tipo);
		}
		return data;
	}

}
