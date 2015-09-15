package rcp.assets.model;

import rcp.assets.services.ComboData;
import rcp.assets.services.ComboDataManager;

public class Periodo {
	
	private Integer mm;
	private Integer yy;
	
	private ComboData cdMesesEsp;
	private ComboData cdMesesEng;
	

	public Periodo(Integer mm, Integer yy) {
		this.mm = mm;
		this.yy = yy;
		cdMesesEsp = ComboDataManager.getInstance().getComboData("Meses español");
		cdMesesEng = ComboDataManager.getInstance().getComboData("Meses inglés");
	}
	
	public Integer getMM() {
		return mm;
	}
	
	public Integer getYY() {
		return yy;
	}
	
	public String getPeriodoMMYYYY() {
		String yyTxt = String.valueOf(yy);
		String mmTxt = String.valueOf(mm);
		String periodo = yyTxt + ("0" + mmTxt).substring(0, 2);
		return periodo;
	}
	
	public String getPeriodoMMMMYYYY(String idioma) {
		String mes = "Sin asignar";
		if (idioma != null && idioma.equals("1")) {
			mes = cdMesesEsp.getTextoByKey(Long.parseLong("" + mm));
		} else {
			mes = cdMesesEng.getTextoByKey(Long.parseLong("" + mm));
		}
		String periodo = mes + " " + yy;
		return periodo;
	}
}
