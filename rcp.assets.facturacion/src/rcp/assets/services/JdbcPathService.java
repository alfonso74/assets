package rcp.assets.services;

import java.security.CodeSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Guarda las rutas de los controladores JDBC utilizados por la aplicación.  La primera vez
 * que se solicita la ruta de un controlador en particular, se realiza la búsqueda de la misma
 * haciendo un Class.forName() y el resultado es guardado en un {@link Map} para tener una
 * rápida referencia en consultas posteriores.
 */
public enum JdbcPathService {
	
	INSTANCE;
	
	private Map<String, String> jdbcDriversPaths = new HashMap<String, String>();
	
	/**
	 * Permite obtener la ruta del controlador JDBC de MySQL que está siendo utilizado
	 * en la aplicación.
	 * @return la ruta del archivo JAR que corresponde al controlador JDBC.
	 */
	public String getMySqlJdbcDriverPath() {
		String jdbcDriverClassName = "com.mysql.jdbc.Driver";
		String mySqlJdbcPath = jdbcDriversPaths.get(jdbcDriverClassName);
		if (mySqlJdbcPath == null) {
			mySqlJdbcPath = locateJdbcDriverPath(jdbcDriverClassName);
			jdbcDriversPaths.put(jdbcDriverClassName, mySqlJdbcPath);
		}
		return mySqlJdbcPath;
	}
	
	
	private String locateJdbcDriverPath(String jdbcClassName) {
		String jdbcDriverPath = null;
		try {
			System.out.println("Locating JDBC driver path for: '" + jdbcClassName + "'");
			Class<?> mySqlClass = Class.forName(jdbcClassName);
			CodeSource codeSource = mySqlClass.getProtectionDomain().getCodeSource();
			if (codeSource != null) {
				jdbcDriverPath = codeSource.getLocation().getPath();
				System.out.println("Found JDBC driver at: '" + jdbcDriverPath + "'");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return jdbcDriverPath;
	}

}
