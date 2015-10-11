package rcp.assets.services;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	private static final SessionFactory sessionFactory;
	private static final Logger LOGGER = Logger.getLogger(HibernateUtil.class);
	private static Shell shell;
	private static String connectionURL = "";
	
	
	/**
	 * Location of hibernate.cfg.xml file. NOTICE: Location should be on the
	 * classpath as Hibernate uses #resourceAsStream style lookup for its
	 * configuration file. That is place the config file in a Java package - the
	 * default location is the default Java package.<br>
	 * <br>
	 * Examples: <br>
	 * <code>CONFIG_FILE_LOCATION = "/hibernate.conf.xml". 
	 * CONFIG_FILE_LOCATION = "/com/foo/bar/myhiberstuff.conf.xml".</code>
	 */
	private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
	
	static {
		try {
			
			String servidor = "192.168.0.179";
			servidor = "localhost";
			String schema = "assets";
			connectionURL = "jdbc:mysql://" + servidor + ":3306/" + schema;
			
			Configuration config = new Configuration().configure(CONFIG_FILE_LOCATION);
			//Configuration config = new Configuration().configure();
			String connUrl = config.getProperty("hibernate.connection.url");
			if (connUrl == null || connUrl.isEmpty()) {
				config.setProperty("hibernate.connection.url", connectionURL);
			} else {
				connectionURL = connUrl;
			}
			LOGGER.debug("URL de conexi�n (HibernateUtil.java): " + connectionURL);
			sessionFactory = config.buildSessionFactory();
			
			LOGGER.debug("Hash code: " + "kalito plaga".hashCode());
			
		} catch (Throwable ex) {
			LOGGER.error("Error durante inicio de sesi�n: " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static String getConnectionURL() {
		return connectionURL;
	}
	
	
	// ********************* Session per editor ******************************
	public static final HashMap<String, Session> sessionMap = new HashMap<String, Session>();
	
	
	/**
	 * Permite obtener la sesi�n de hibernate asociada a un editorID.  Si el editor
	 * no tiene una sesi�n asociada se crea una nueva sesi�n.
	 * @param editor ID del editor
	 * @return sesi�n existente que est� asociada al editor, o una nueva sesi�n
	 * @throws HibernateException
	 */
	public static Session getEditorSession(String editor) throws HibernateException {
		if (editor == null) {
			LOGGER.error("Error:  el valor del editor es NULL (HibernateUtil)");
		}
		Session s = (Session) sessionMap.get(editor);
		if (s == null) {
			s = sessionFactory.openSession();
			sessionMap.put(editor, s);
		}
		return s;
	}
	
	public static void closeEditorSession(String editor) throws HibernateException {
		Session s = (Session) sessionMap.get(editor);
		if (s != null)
			s.close();
		sessionMap.remove(editor);
	}
	
	public static void verSesiones() {
		LOGGER.debug("Total de sesiones de hibernate: " + sessionMap.size());
		for (String s : sessionMap.keySet()) {
			System.out.println("Sesi�n: " + s);
		}
	}
	
	
// *********************Inicio del c�digo copiado de la web **********************
	
	public static final ThreadLocal<Session> session = new ThreadLocal<Session>();
	
	/**
	 * Obtiene la sesi�n asociada a este thread, y si no existe se crea.
	 * Debe usarse en conjunto con closeSession().
	 * @return Sesi�n de Hibernate
	 * @throws HibernateException
	 */
	public static Session currentSession() throws HibernateException {
		Session s = (Session) session.get();
		// Open a new Session, if this thread has none yet
		if (s == null) {
			s = sessionFactory.openSession();
			// Store it in the ThreadLocal variable
			session.set(s);
		}
		return s;
	}

	public static void closeSession() throws HibernateException {
		LOGGER.debug("Cierre de sesi�n de hibernate");
		Session s = (Session) session.get();
		if (s != null)
			s.close();
		session.set(null);
	}
	
	/**
	 * This is a simple method to reduce the amount of code that needs
	 * to be written every time hibernate is used.
	 */
	public static void rollback(org.hibernate.Transaction tx) {
		if (tx != null) {
			try {
				tx.rollback( );
			} catch (HibernateException ex) {
				// Probably don't need to do anything -	this is likely being
				// called because of another exception, and we don't want to
				// mask it with yet another exception.
			}
		}
	}
	/**
	 * This is a simple method to reduce the amount of code that needs
	 * to be written every time hibernate is used.
	 */
	public static void commit(org.hibernate.Transaction tx) {
		if (tx != null) {
			try {
				tx.commit( );
			} catch (HibernateException ex) {
				// Probably don't need to do anything -	this is likely being
				// called because of another exception,	and we don't want to
				// mask it with yet another exception.
			}
		}
	}
	
	
	public static void setShell(Shell eclipseShell) {
		shell = eclipseShell;
	}
	
	/**
	 * Muestra un di�logo con el mensaje de la excepci�n de Hibernate...
	 * y autom�ticamente obtiene un shell para mostralo??
	 * @param he Excepci�n de Hibernate
	 */
	public static void procesarError(HibernateException he) {
		MessageDialog.openError(null, "Error de Hibernate", "Error: " + he.toString() + "\n\nStack trace: " + he.getStackTrace()[0] + "\n" + he.getStackTrace()[1]);
		he.printStackTrace();
	}

}
