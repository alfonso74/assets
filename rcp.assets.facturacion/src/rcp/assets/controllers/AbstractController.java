package rcp.assets.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import rcp.assets.dao.GenericDAOImpl;
import rcp.assets.services.HibernateUtil;


public abstract class AbstractController<X> {
	
	protected static Logger LOGGER = null;
	private Session session;
	private String editorId;
	private GenericDAOImpl<X, Long> dao;

	/**
	 * Inicializa una sesi�n de hibernate en base al editor indicado
	 * @param id que se asociar� a la nueva sesi�n
	 */
	public AbstractController(String editorId, GenericDAOImpl<X, Long> dao) {
		LOGGER = Logger.getLogger(getClass());
		session = HibernateUtil.getEditorSession(editorId);
		session.setFlushMode(FlushMode.MANUAL);
		//session.setFlushMode(FlushMode.NEVER);
		/*
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		 */
		this.editorId = editorId;
		this.dao = dao;
		this.dao.setSession(session);
	}

	public AbstractController(GenericDAOImpl<X, Long> dao) {
		LOGGER = Logger.getLogger(getClass());
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		this.dao = dao;
		this.dao.setSession(session);
	}

	/*
	public AbstractController() {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		this.dao = new GenericDAOImpl<X, Long>() {
		};
		this.dao.setSession(session);
	}
	 */

	/**
	 * Persiste un registro en la base de datos
	 * @param registro registro a ser guardado, instancia tipo X
	 */
	public void doSave(X registro) {
		try {
			dao.doSave(registro);
		} catch (HibernateException he) {
			if (getSession().isOpen()) {
				HibernateUtil.rollback(getSession().getTransaction());
				HibernateUtil.closeEditorSession(getEditorId());
			}
			HibernateUtil.procesarError(he);
			session = HibernateUtil.getEditorSession(editorId);
			session.setFlushMode(FlushMode.MANUAL);
			dao.setSession(session);
			HibernateUtil.verSesiones();
		}
	}


	public void doDelete(X registro) {
		try {
			dao.doDelete(registro);
		} catch (HibernateException he) {
			if (getSession().isOpen()) {
				HibernateUtil.rollback(getSession().getTransaction());
				HibernateUtil.closeEditorSession(getEditorId());
			}
			HibernateUtil.procesarError(he);
			session = HibernateUtil.getEditorSession(editorId);
			session.setFlushMode(FlushMode.MANUAL);
			dao.setSession(session);
			HibernateUtil.verSesiones();
		}
	}


	/**
	 * Finaliza una sesi�n de hibernate
	 * @param editorId id asociado a la sesi�n que ser� finalizada
	 */
	public void finalizarSesion() {
//		System.out.println("Finalizando sesi�n: " + getEditorId());
		LOGGER.info("Finalizando sesi�n: " + getEditorId());
		HibernateUtil.closeEditorSession(getEditorId());     // graba en la base de datos
	}

	/**
	 * Retorna el editor que est� asociado a este controller
	 * @return id del editor asociado
	 */
	public String getEditorId() {
		return editorId;
	}

	/**
	 * Retorna la sesi�n que ha sida creada para este editor
	 * @return sesi�n de hibernate
	 */
	public Session getSession() {
		return session;
	}

	public GenericDAOImpl<X, Long> getDAO() {
		return dao;
	}

	/**
	 * Obtiene un listado de todos los registros en la base de datos de tipo especificado.
	 * @return
	 */
	public List<X> getListado() {
		return getDAO().findAll();
	}


	/**
	 * Obtiene un registro en base a su identificador en la base de datos.  Este m�todo se utiliza frecuentemente en
	 * la inicializaci�n de los editores.
	 * @param id identificador del registro en la DB
	 * @return registro de tipo X
	 */
	public X getRegistroById(Long id) {
		return getDAO().findById(id, true);
	}


	// TODO evaluar este c�digo y borrarlo o implementarlo si es factible
	/*
	public ComboData getComboData() {
		ComboData data = new ComboData();
		List<X> listadoX = getListado();
		for (X item : listadoX) {
			data.agregarComboDataItem(item.toComboDataItem());
		}
	}
	 */


	/**
	 * Permite ver las sesiones activas de Hibernate
	 */
	public void verSesiones() {
		HibernateUtil.verSesiones();
	}

}
