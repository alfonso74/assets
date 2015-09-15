package rcp.assets.model;

public interface IFactura {

	/**
	 * Calcula el total de las líneas de cargos de la factura, y actualiza el monto
	 * total de la misma.
	 */
	public abstract void calcularTotalDetalle();

	/**
	 * Calcula el impuesto de la factura considerando si está excenta o no (dependiendo de el cliente 
	 * y de la compañía), y actualiza el total de la misma.
	 */
	public abstract void calcularImpuesto();

}