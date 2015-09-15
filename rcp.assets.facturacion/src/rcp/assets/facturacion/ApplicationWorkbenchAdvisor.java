package rcp.assets.facturacion;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import rcp.assets.services.HibernateUtil;
import rcp.assets.services.InicializarKeywords;
import rcp.assets.services.InicializarMeses;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	private static final String PERSPECTIVE_ID = "rcp.assets.facturacion.perspective"; //$NON-NLS-1$

    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

	public String getInitialWindowPerspectiveId() {
		HibernateUtil.setShell(null);
		InicializarKeywords ik = new InicializarKeywords();
		ik.crearRegistros();
		InicializarMeses im = new InicializarMeses();
		im.crearRegistros();
		return PERSPECTIVE_ID;
	}
	
	
}
