package rcp.assets.model;

import java.util.ArrayList;
import java.util.List;

public class CategoriasTree {

	private Mes mes;
	private List<FacturacionEspecial> listado;
	
	
	public CategoriasTree() {
		listado = new ArrayList<FacturacionEspecial>();
	}
	
	
	public String toString() {
		return ("Mes: " + mes.getDescripcion() + ", id: " + mes.getIdMes() + ", elementos: " + listado.size());
	}
	
	public Mes getMes() {
		return mes;
	}
	
	public void setMes(Mes mes) {
		this.mes = mes;
	}
	
	public List<FacturacionEspecial> getListado() {
		return listado;
	}
	
	public void agregarElemento(FacturacionEspecial elemento) {
		if (listado == null) {
			listado = new ArrayList<FacturacionEspecial>();
		}
		listado.add(elemento);
	}
	
}
