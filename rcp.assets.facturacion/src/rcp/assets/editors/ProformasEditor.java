package rcp.assets.editors;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.osgi.framework.Bundle;

import rcp.assets.services.HibernateUtil;
import rcp.assets.services.JdbcPathService;


public class ProformasEditor extends EditorPart {

	public static final String ID = "rcp.assets.editors.ProformasEditor"; //$NON-NLS-1$
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	private Map<String, Object> parametros;
	private HashMap<String, String> reportesMap;

	private String tipoProforma;

	/*
	private Integer parametroId;
	private Float parametroImpuesto;
	private String parametroIdioma;
	private Date parametroPeriodo;
	 */

	public ProformasEditor() {
		parametros = new HashMap<String, Object>();
		reportesMap = new HashMap<String, String>();
		reportesMap.put("caso", "/reports/casoProforma.rptdesign");
		reportesMap.put("retainer", "/reports/retainerProforma.rptdesign");
		reportesMap.put("anualidad", "/reports/anualidadProforma.rptdesign");
	}

	/**
	 * Create contents of the editor part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		ScrolledForm scrldfrmMain = formToolkit.createScrolledForm(parent);
		formToolkit.paintBordersFor(scrldfrmMain);
		scrldfrmMain.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));

		Browser browser = new Browser(scrldfrmMain.getBody(), SWT.NONE);
		browser.setBounds(0, 0, 64, 64);
		formToolkit.adapt(browser);
		formToolkit.paintBordersFor(browser);

		String rutaReporte = reportesMap.get(tipoProforma);
		if (rutaReporte == null) {
			MessageDialog.openError(getSite().getShell(), "Generar proforma", "Error al especificar el tipo de la proforma: " + tipoProforma + ".");
		} else {
			previewProforma(browser, rutaReporte);
		}
		/*
			if (parametroTipoProforma.equals("Caso")) {
				previewCasoReport("/reports/casoProforma.rptdesign");
			} else if (parametroTipoProforma.equals("Retainer")) {
				previewRetainerReport("/reports/retainerProforma.rptdesign");
			} else {
				previewCasoReport("/reports/casoProforma.rptdesign");
			}
		 */
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// Do the Save operation
	}

	@Override
	public void doSaveAs() {
		// Do the Save As operation
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		ProformasEditorInput editorInput = (ProformasEditorInput) input;
		setSite(site);
		setInput(editorInput);
		tipoProforma = editorInput.getTipoProforma();
		parametros = editorInput.getParametros();
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public void dispose() {
		formToolkit.dispose();
		super.dispose();
	}


	private void previewProforma(Browser browser, String rutaReporte) {

		IReportEngine engine = inicializarEngine();
		String rpt = getReportURL(rutaReporte);
		RenderOption options = configurarRendererPDF(rpt);
		String reporteFileName = ejecutarReporte(engine, options, rpt);

		System.out.println("Archivo de salida: " + reporteFileName);

		//browser.setText(bos.toString("UTF-8"));
		//browser.setUrl("file:///c:/proformaCaso.pdf");
		browser.setUrl("file://" + reporteFileName);
		engine.destroy();

	}


	private IReportEngine inicializarEngine() {
		EngineConfig config = new EngineConfig();
		//add to the classpath-Set Parent Classloader		
//		config.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, this.getClass().getClassLoader());
		//config.getAppContext().put(EngineConstants.WEBAPP_CLASSPATH_KEY, "c:/jars/mjo.jar");
		config.getAppContext().put("OdaJDBCDriverClassPath", JdbcPathService.INSTANCE.getMySqlJdbcDriverPath());
		IReportEngineFactory factory = (IReportEngineFactory) org.eclipse.birt.core.framework.Platform
				.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
		IReportEngine engine = factory.createReportEngine( config );
		return engine;
	}


	/**
	 * Obtiene la ubicación del reporte (dentro del bundle de eclipse)
	 * @param rutaReporte Ruta del reporte dentro del bundle de eclipse
	 * @return Ruta para accesar el reporte
	 */
	private String getReportURL(String rutaReporte) {
		//use this to set the resource path	
		//Bundle bundle = org.eclipse.core.runtime.Platform.getBundle("org.eclipse.birt.examples.rcpengine");		
		//URL url = FileLocator.find(bundle, new Path("/resources"), null);
		//String myresourcepath = FileLocator.toFileURL(url).getPath();		
		//config.setResourcePath(myresourcepath);

		Bundle bundle = org.eclipse.core.runtime.Platform.getBundle("rcp.assets.facturacion");		
		URL url = FileLocator.find(bundle, new Path(rutaReporte), null);
		String rpt = "";
		try {
			rpt = FileLocator.toFileURL(url).getPath();
		} catch (IOException e) {
			MessageDialog.openError(getSite().getShell(), "Error en: " + this.getClass().getName(), "Error buscando archivo: " + rutaReporte + ".\n" +
					"Error: " + e.toString() + "\n\nStack trace: " + e.getStackTrace()[0] + "\n" + e.getStackTrace()[1]);
			e.printStackTrace();
		}
		System.out.println("Ruta del reporte (bundle): " + rpt);
		return rpt;
	}


	/**
	 * Configura un renderer de excel para generar una hoja de cálculo basada en el reporte indicado
	 * @param rpt Ruta del reporte usado como template
	 * @return RenderOption configurado para excel
	 */
	private RenderOption configurarRendererPDF(String rpt) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String outputPDF = rpt.replaceFirst( ".rptdesign", ".pdf" );
		PDFRenderOption options = new PDFRenderOption();
		options.setOutputStream(bos);
		options.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_PDF);
		options.setOutputFileName( outputPDF );
		System.out.println("Ruta del archivo a generar: " + outputPDF);
		return options;
	}


	/**
	 * Gnenera un archivo de acuerdo al reporte y formato suministrados
	 * @param engine Engine de BIRT
	 * @param options renderer configurado para generar el archivo en un formato específico
	 * @param rpt ruta del reporte de BIRT a usar como template
	 * @return
	 */
	private String ejecutarReporte(IReportEngine engine, RenderOption options, String rpt) {
		// Pasamos los parámetros para el reporte y lo ejecutamos
		try {
			IReportRunnable design = engine.openReportDesign(rpt);
			IRunAndRenderTask task = engine.createRunAndRenderTask(design);
			task.setParameterValue("connectionURL", HibernateUtil.getConnectionURL());
			task.setParameterValues(parametros);
			task.setRenderOption(options);
			task.run();
			task.close();
		} catch (EngineException e) {
			MessageDialog.openError(getSite().getShell(), "Error en: " + this.getClass().getName(), "Error al generar el reporte: " + rpt + ".\n" +
					"Error: " + e.toString() + "\n\nStack trace: " + e.getStackTrace()[0] + "\n" + e.getStackTrace()[1]);
			e.printStackTrace();
		}
		return options.getOutputFileName();
	}


	private void previewCasoReport(Browser browser, String rutaReporte) throws EngineException {

		EngineConfig config = new EngineConfig();

		try{

			//use this to set the resource path	
			//Bundle bundle = org.eclipse.core.runtime.Platform.getBundle("org.eclipse.birt.examples.rcpengine");		
			//URL url = FileLocator.find(bundle, new Path("/resources"), null);
			//String myresourcepath = FileLocator.toFileURL(url).getPath();		
			//config.setResourcePath(myresourcepath);

			// Create the report engine
			IReportEngineFactory factory = (IReportEngineFactory) org.eclipse.birt.core.framework.Platform
					.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
			IReportEngine engine = factory.createReportEngine( config );

			IReportRunnable design = null;
			//String rpt = reportLocation.getText();

			//use this if the report is in the bundle
			//Bundle bundle = org.eclipse.core.runtime.Platform.getBundle("org.eclipse.birt.examples.rcpengine");		
			//URL url = FileLocator.find(bundle, new Path("/reports/TopNPercent.rptdesign"), null);
			//String rpt = FileLocator.toFileURL(url).getPath();

			Bundle bundle = org.eclipse.core.runtime.Platform.getBundle("rcp.assets.facturacion");		
			//URL url = FileLocator.find(bundle, new Path("/reports/casoProforma.rptdesign"), null);
			URL url = FileLocator.find(bundle, new Path(rutaReporte), null);
			String rpt = FileLocator.toFileURL(url).getPath();

			//add to the classpath-Set Parent Classloader		
			config.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, this.getClass().getClassLoader());
			//config.getAppContext().put(EngineConstants.WEBAPP_CLASSPATH_KEY, "c:/jars/mjo.jar");


			design = engine.openReportDesign(rpt);

			IRunAndRenderTask task = engine.createRunAndRenderTask(design);

			HTMLRenderOption options = new HTMLRenderOption();

			options = new HTMLRenderOption( );
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			options.setOutputStream(bos);
			options.setOutputFormat("html");

			PDFRenderOption options2 = new PDFRenderOption();
			options2.setOutputStream(bos);
			options2.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_PDF);

			//String output = rpt.replaceFirst( ".rptdesign", "." + format );
			String output = rpt.replaceFirst( ".rptdesign", ".pdf" );
			//options2.setOutputFileName( "c:/proformaCaso.pdf" );
			options2.setOutputFileName( output );
			/*
			task.setParameterValue("idCaso", parametroId);
			task.setParameterValue("impuesto", parametroImpuesto);
			task.setParameterValue("connectionURL", HibernateUtil.getConnectionURL());
			task.setParameterValue("idioma", parametroIdioma);
			 */
			task.setRenderOption(options2);
			task.run();
			task.close();

			System.out.println("SSS: " + output);
			//browser.setText(bos.toString("UTF-8"));
			//browser.setUrl("file:///c:/proformaCaso.pdf");
			browser.setUrl("file://" + output);
			System.out.println("finished");
			//engine.destroy();

			/*
			File file = new File("c:/temp/testpdf.pdf");
			try {
			    browser.setUrl(file.toURI().toURL().toString());
			} catch (MalformedURLException e) {
			    e.printStackTrace();
			}
			 */

		} catch (Exception e) {
			e.printStackTrace();

		}

	}



	private void previewRetainerReport(Browser browser, String rutaReporte) throws EngineException {

		EngineConfig config = new EngineConfig();

		try{

			//use this to set the resource path	
			//Bundle bundle = org.eclipse.core.runtime.Platform.getBundle("org.eclipse.birt.examples.rcpengine");		
			//URL url = FileLocator.find(bundle, new Path("/resources"), null);
			//String myresourcepath = FileLocator.toFileURL(url).getPath();		
			//config.setResourcePath(myresourcepath);

			// Create the report engine
			IReportEngineFactory factory = (IReportEngineFactory) org.eclipse.birt.core.framework.Platform
					.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
			IReportEngine engine = factory.createReportEngine( config );

			IReportRunnable design = null;
			//String rpt = reportLocation.getText();

			//use this if the report is in the bundle
			//Bundle bundle = org.eclipse.core.runtime.Platform.getBundle("org.eclipse.birt.examples.rcpengine");		
			//URL url = FileLocator.find(bundle, new Path("/reports/TopNPercent.rptdesign"), null);
			//String rpt = FileLocator.toFileURL(url).getPath();

			Bundle bundle = org.eclipse.core.runtime.Platform.getBundle("rcp.assets.facturacion");		
			//URL url = FileLocator.find(bundle, new Path("/reports/casoProforma.rptdesign"), null);
			URL url = FileLocator.find(bundle, new Path(rutaReporte), null);
			String rpt = FileLocator.toFileURL(url).getPath();

			//add to the classpath-Set Parent Classloader		
			config.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, this.getClass().getClassLoader());
			//config.getAppContext().put(EngineConstants.WEBAPP_CLASSPATH_KEY, "c:/jars/mjo.jar");


			design = engine.openReportDesign(rpt);

			IRunAndRenderTask task = engine.createRunAndRenderTask(design);

			HTMLRenderOption options = new HTMLRenderOption();

			options = new HTMLRenderOption( );
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			options.setOutputStream(bos);
			options.setOutputFormat("html");

			PDFRenderOption options2 = new PDFRenderOption();
			options2.setOutputStream(bos);
			options2.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_PDF);

			//String output = rpt.replaceFirst( ".rptdesign", "." + format );
			String output = rpt.replaceFirst( ".rptdesign", ".pdf" );
			//options2.setOutputFileName( "c:/proformaCaso.pdf" );
			options2.setOutputFileName( output );

			/*
			task.setParameterValue("periodo", parametroPeriodo);
			task.setParameterValue("idExpediente", parametroId);
			task.setParameterValue("impuesto", parametroImpuesto);
			task.setParameterValue("connectionURL", HibernateUtil.getConnectionURL());
			task.setParameterValue("idioma", parametroIdioma);
			 */
			task.setRenderOption(options2);
			task.run();
			task.close();

			System.out.println("SSS: " + output);
			//browser.setText(bos.toString("UTF-8"));
			//browser.setUrl("file:///c:/proformaCaso.pdf");
			browser.setUrl("file://" + output);
			System.out.println("finished");
			//engine.destroy();

			/*
			File file = new File("c:/temp/testpdf.pdf");
			try {
			    browser.setUrl(file.toURI().toURL().toString());
			} catch (MalformedURLException e) {
			    e.printStackTrace();
			}
			 */

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
}
