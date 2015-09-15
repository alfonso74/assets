package rcp.assets.controllers;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import rcp.assets.dao.GenericDAOImpl;
import rcp.assets.services.HibernateUtil;

public abstract class AbstractControllerV2<X> {
	private Session session;
	private String editorId;
	private GenericDAOImpl<X, Long> dao;
	
	/**
	 * Inicializa una sesión de hibernate en base al editor indicado
	 * @param id que se asociará a la nueva sesión
	 */
	public AbstractControllerV2(String editorId) {
		session = HibernateUtil.getEditorSession(editorId);
		session.setFlushMode(FlushMode.MANUAL);
		this.editorId = editorId;
		this.dao = new GenericDAOImpl<X, Long>();
		this.dao.setSession(session);
	}
	
	public AbstractControllerV2() {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		this.dao = new GenericDAOImpl<X, Long>();
		this.dao.setSession(session);
	}
	
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
		}
	}

	
	/**
	 * Finaliza una sesión de hibernate
	 * @param editorId id asociado a la sesión que será finalizada
	 */
	public void finalizarSesion() {
		System.out.println("Finalizando sesión: " + getEditorId());
		HibernateUtil.closeEditorSession(getEditorId());     // graba en la base de datos
	}
	
	/**
	 * Retorna el editor que está asociado a este controller
	 * @return id del editor asociado
	 */
	public String getEditorId() {
		return editorId;
	}
	
	/**
	 * Retorna la sesión que ha sida creada para este editor
	 * @return sesión de hibernate
	 */
	public Session getSession() {
		return session;
	}
	
	public GenericDAOImpl<X, Long> getDAO() {
		return dao;
	}
	
	
	/**
	 * Permite ver las sesiones activas de Hibernate
	 */
	public void verSesiones() {
		HibernateUtil.verSesiones();
	}

}
