package rcp.assets.model;

import rcp.assets.services.ComboDataItem;

public class Keyword implements IEditableDocument {
	
	private Long idKeyword = -1L;
	private String codigo;
	private String descripcion;
	private String tipo;
	private Integer posicion;
	private String estado;
	
	
	public Keyword() {
	}
	
	public Keyword(String codigo, String descripcion, String tipo, Integer orden, Estado estado) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.posicion = orden;
		this.estado = estado.getCodigo();
	}
	
	public Keyword(Long idKeyword, String codigo, String descripcion, String tipo, Integer orden, Estado estado) {
		this.idKeyword = idKeyword;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.posicion = orden;
		this.estado = estado.getCodigo();
	}
	
	
	
// ******************************** métodos especiales ********************************

	@Override
	public String getTituloDocumento() {
		String titulo = "Nuevo keyword";
		titulo = getDescripcion() == null ? titulo : getIdKeyword() + " - " + getDescripcion();
		return titulo;
	}
	
	@Override
	public Long getId() {
		return idKeyword;
	}

	@Override
	public String toString() {
		return "Keyword (id-descripción): " + getIdKeyword() + "-" + getDescripcion();
	}
	
	/**
	 * Formatea un keyword para ser agregado a un ComboData
	 * @return ComboDataItem listo para procesar
	 */
	public ComboDataItem toComboDataItem() {
		ComboDataItem cdItem = new ComboDataItem();
		cdItem.setKey(getCodigo());
		cdItem.setTexto(getDescripcion());
		cdItem.setObjeto(this);
		return cdItem;
	}

	
	
// ****************************** getters y setters *************************

	public Long getIdKeyword() {
		return idKeyword;
	}

	public void setIdKeyword(Long idKeyword) {
		this.idKeyword = idKeyword;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getPosicion() {
		return posicion;
	}

	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	


	public enum Estado {
		
		ACTIVO("A", "Activo"),
		INACTIVO("I", "Inactivo");
		
		private String codigo;
		private String descripcion;
		
		private Estado(String codigo, String descripcion) {
			this.codigo = codigo;
			this.descripcion = descripcion;
		}
		
		public String getCodigo() {
			return codigo;
		}
		
		public String getDescripcion() {
			return descripcion;
		}
		
		public static Estado fromCodigo(String codigo) {
			if (codigo == null || codigo.equals("")) {
				return null;
			}
			for (Estado v : values()) {
				if (v.getCodigo().equalsIgnoreCase(codigo)) {
					return v;
				}
			}
			throw new IllegalArgumentException("Código inválido: " + codigo);
		}
		
		public static Estado fromDescripcion(String descripcion) {
			if (descripcion == null || descripcion.equals("")) {
				return null;
			}
			for (Estado v : values()) {
				if (v.getDescripcion().equalsIgnoreCase(descripcion)) {
					return v;
				}
			}
			throw new IllegalArgumentException("Código inválido: " + descripcion);
		}
	}
}
