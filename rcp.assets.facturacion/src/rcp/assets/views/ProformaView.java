package rcp.assets.views;

import java.io.ByteArrayOutputStream;
import java.net.URL;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.osgi.framework.Bundle;

public class ProformaView extends ViewPart {

	//public static final String ID = "rcp.assets.views.ProformaView"; //$NON-NLS-1$
	public static final String ID = Class.class.getName();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	
	private Browser browser;

	public ProformaView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = toolkit.createComposite(parent, SWT.NONE);
		toolkit.paintBordersFor(container);
		container.setLayout(new GridLayout(1, false));
		
		browser = new Browser(container, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.adapt(browser);
		toolkit.paintBordersFor(browser);

		createActions();
		initializeToolBar();
		initializeMenu();
		
		try {
			previewReport();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	public void dispose() {
		toolkit.dispose();
		super.dispose();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		@SuppressWarnings("unused")
		IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		@SuppressWarnings("unused")
		IMenuManager manager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
	
	
	private void previewReport() throws EngineException {
		
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
			URL url = FileLocator.find(bundle, new Path("/reports/casoProforma.rptdesign"), null);
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
			options2.setOutputFileName( "c:/testpdf.pdf" );

			task.setParameterValue("idCaso", new Integer(2587));
			task.setRenderOption(options2);
			task.run();
			task.close();

			//browser.setText(bos.toString("UTF-8"));
			browser.setUrl("file:///c:/testpdf.pdf");
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
