package rcp.assets.model;

import java.util.HashSet;
import java.util.Set;


public class FacturaCaso extends Factura {

	private Set<LineaFactura> listaDetalles;


	public FacturaCaso() {
		listaDetalles = new HashSet<LineaFactura>();
		setTipo("CA");
//		Factura.Tipo.CA.toString();
	}


	// ******************************** métodos especiales ********************************

	@Override
	public String getTituloDocumento() {
		return "Factura de caso " + getNoFactura();
	}
	
	/* (non-Javadoc)
	 * @see rcp.assets.model.IFactura#calcularTotalDetalle()
	 */
	@Override
	public void calcularTotalDetalle() {
		setTotalDetalle(0F);
		for (LineaFactura linea : listaDetalles) {
			setTotalDetalle(getTotalDetalle() + linea.getMonto()); 
		}
		//setTotal(getTotalDetalle() + getImpuesto());
	}
	
	
	/* (non-Javadoc)
	 * @see rcp.assets.model.IFactura#calcularImpuesto()
	 */
	@Override
	@Deprecated
	public void calcularImpuesto() {
		if (isExcentoImpuesto()) {
			setImpuesto(0F);
		} else {
			/*
			setImpuesto(0F);
			for (LineaFactura linea : listaDetalles) {
				if (linea.getPagaImpuesto().equals("S")) {
					setImpuesto(getImpuesto() + linea.getMontoConImpuesto((float) 0.07)); 
				}
			}
			*/
		}
		setTotal(getTotalDetalle() + getImpuesto());
	}


	public void agregarLineaDetalle(LineaFactura linea) {
		listaDetalles.add(linea);
	}
	
	
	/**
	 * Cantidad de líneas de la factura, en base a 0
	 */
	@Override
	public Integer getCantidadLineas() {
		return listaDetalles.size();
	}
	
	
	// *********************************** getters y setters ******************************
	
	public Set<LineaFactura> getListaDetalles() {
		return listaDetalles;
	}


	public void setListaDetalles(Set<LineaFactura> listaDetalles) {
		this.listaDetalles = listaDetalles;
	}
	
}
