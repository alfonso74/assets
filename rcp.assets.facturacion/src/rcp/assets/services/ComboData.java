package rcp.assets.services;

import java.util.Vector;

/**
 * Clase utilizada para ayudar en la manipulaci�n de los valores de 
 * c�digo, texto, objeto de un combo box
 * @author Carlos Alfonso
 *
 */

public class ComboData {
	private Vector<String> vTexto;
	private Vector<String> vKey;
	private Vector<Object> vObjeto;


	public ComboData() {
		vTexto = new Vector<String>();
		vKey = new Vector<String>();
		vObjeto = new Vector<Object>();
	}

	/**
	 * Agrega una entrada (item) al combo data
	 * @param item Objeto que contiene un identificador (llave), texto y el objeto a partir del cual se cre�
	 * el item.
	 */
	public void agregarComboDataItem(ComboDataItem item) {
		vTexto.addElement(item.getTexto());
		vKey.addElement(item.getKey());
		vObjeto.addElement(item.getObjeto());
	}

	/**
	 * Agrega una entrada (item) al combo data.
	 * @param texto texto que se presenta en el combodata
	 * @param valor valor o c�digo que representa al texto
	 */
	public void agregarItem(String texto, String key) {
		vTexto.addElement(texto);
		vKey.addElement(key);
		vObjeto.addElement(null);
	}

	/**
	 * Agrega una entrada (item) al combo data.
	 * @param texto texto que se presenta en el combodata
	 * @param valor valor o c�digo que representa al texto
	 */
	public void agregarItem(String texto, Long key) {
		vTexto.addElement(texto);
		vKey.addElement(key.toString());
		vObjeto.addElement(null);
	}

	/**
	 * Agrega una entrada (item) al combo data.
	 * @param texto texto que se presenta en el combodata
	 * @param valor valor o c�digo que representa al texto
	 * @param obj objeto de donde se sac� el texto y el c�digo de la entrada
	 */
	public void agregarItem(String texto, Long key, Object obj) {
		vTexto.addElement(texto);
		vKey.addElement(key.toString());
		vObjeto.addElement(obj);
	}


	/**
	 * Agrega una entrada (item) al combo data.
	 * @param texto texto que se presenta en el combodata
	 * @param valor valor o c�digo que representa al texto
	 * @param obj objeto de donde se sac� el texto y el c�digo de la entrada
	 */
	public void agregarItem(String texto, String key, Object obj) {
		vTexto.addElement(texto);
		vKey.addElement(key);
		vObjeto.addElement(obj);
	}

	/**
	 * Retorna un array con la parte de texto de todos los items incluidos en el ComboData
	 * @return array de tipo String[]
	 */
	public String[] getTexto() {
		return (String[]) vTexto.toArray(new String[vTexto.size()]);
	}

	/**
	 * Retorna la Descripci�n del elemento indicado en el �ndice
	 * @param indice Posici�n a retornar
	 * @return Descripci�n (como String) o null si el �ndice es -1
	 */
	public String getTextoByIndex(int indice) {
		if (indice != -1) {
			return (String) vTexto.elementAt(indice);
		} else {
			return null;
		}
	}

	/**
	 * Retorna la Descripci�n del elemento seleccionado
	 * @param codigo C�digo a buscar (de tipo Long)
	 * @return Descripci�n (como String) o null (si no encuentra nada)
	 */
	public String getTextoByKey(Long valor) {
		Long v;
		if (valor == null) {
			//return "Null";
			return null;
		} else {
			for (int n = 0; n < vKey.size(); n++) {
				v = Long.parseLong(((String) vKey.elementAt(n)));
				if (v == valor.longValue()) {
					return (String) vTexto.elementAt(n);
				}
			}
		}
		//return "Error";
		return null;
	}

	/**
	 * Retorna la Descripci�n del elemento seleccionado
	 * @param codigo C�digo a buscar
	 * @return Descripci�n (como String) o null (si no encuentra nada)
	 */
	public String getTextoByKey(String codigo) {
		String v;
		if (codigo == null) {
			//return "Null";
			return null;
		} else {
			for (int n = 0; n < vKey.size(); n++) {
				System.out.println("getTextoByKey: " + vKey.elementAt(n));
				v = (String) vKey.elementAt(n);
				if (v.equals(codigo)) {
					return (String) vTexto.elementAt(n);
				}
			}
		}
		//return "Error";
		return null;
	}


	/**
	 * Retorna el c�digo del elemento seleccionado
	 * @param indice posici�n del elemento a utilizar
	 * @return c�digo (como String) o null si no encuentra nada
	 */
	public String getKeyByIndex(int indice) {
		if (indice != -1) {
			return (String) vKey.elementAt(indice);
		} else {
			return null;
		}
	}

	/**
	 * Retorna el c�digo del elemento con el texto (Descripci�n) suministrado
	 * @param texto Descripci�n a buscar
	 * @return c�digo (como String) o null si no encuentra nada
	 */
	public String getKeyByTexto(String texto) {
		for (int n = 0; n < vKey.size(); n++) {
			if (vTexto.elementAt(n).equals(texto)) {
				return (String) vKey.elementAt(n);
			}
		}
		return null;
	}


	/**
	 * Retorna el c�digo o el id del elemento indicado como un valor de tipo Long
	 * @param indice posici�n del elemento a retornar
	 * @return c�digo o id (transformado a Long)
	 */
	public Long getKeyAsLongByIndex(int indice) {
		if (indice == -1) return 0L;
		String codigo = (String) vKey.elementAt(indice);
		Long valCodigo = Long.parseLong(codigo);
		return valCodigo;
	}


	/**
	 * Retorna el c�digo del elemento con el texto (Descripci�n) suministrado, como un valor de tipo Long
	 * @param texto Descripci�n a buscar
	 * @return c�digo (como Long) o -1L si no encuentra nada
	 */
	public Long getKeyAsLongByTexto(String texto) {
		for (int n = 0; n < vKey.size(); n++) {
			if (vTexto.elementAt(n).equals(texto)) {
				String codigo = (String) vKey.elementAt(n);
				return (Long.parseLong(codigo));
			}
		}
		return -1L;
	}




	public Object getObjectByIndex(int indice) {
		if (indice != -1) {
			if (vObjeto.size() > 0) {
				return vObjeto.elementAt(indice);
			} else {
				System.err.println("El vector de objetos no ha sido inicializado (ComboData.vObjeto)");
				return null;
			}
		} else {
			return null;
		}
	}
}
