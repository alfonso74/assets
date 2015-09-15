package rcp.assets.services;

import java.util.List;

import org.apache.log4j.Logger;

import rcp.assets.dao.MesDAO;
import rcp.assets.model.Mes;

/**
 * Inicializar los keywords basicos que son utilizando en varias partes del sistema, como
 * Activo / Inactivo, Si / No, etc
 * @author Owner
 *
 */
public class InicializarMeses {
	
	private static final Logger LOGGER = Logger.getLogger(InicializarMeses.class);
	private List<Mes> listaMeses;
	private MesDAO dao;
	
	
	public InicializarMeses() {
		dao = new MesDAO();
		listaMeses = dao.findAll();
	}
	
	
	public void crearRegistros() {
		LOGGER.info("Verificando creación de meses...");
		crearRegistro(new Mes(0L, "Enero", "January"));
		crearRegistro(new Mes(1L, "Febrero", "February"));
		crearRegistro(new Mes(2L, "Marzo", "March"));
		crearRegistro(new Mes(3L, "Abril", "April"));
		crearRegistro(new Mes(4L, "Mayo", "May"));
		crearRegistro(new Mes(5L, "Junio", "June"));
		crearRegistro(new Mes(6L, "Julio", "July"));
		crearRegistro(new Mes(7L, "Agosto", "August"));
		crearRegistro(new Mes(8L, "Septiembre", "September"));
		crearRegistro(new Mes(9L, "Octubre", "October"));
		crearRegistro(new Mes(10L, "Noviembre", "November"));
		crearRegistro(new Mes(11L, "Diciembre", "December"));
	}
	
	
	private void crearRegistro(Mes mes) {
		if (listaMeses.contains(mes)) {
			LOGGER.info("Mes encontrado: " + mes.toString());
		} else {
			dao.doSave(mes);
			LOGGER.info("Mes creado: " + mes.toString());
		}
	}

}
