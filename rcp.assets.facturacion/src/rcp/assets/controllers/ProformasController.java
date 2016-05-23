package rcp.assets.controllers;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IExcelRenderOption;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchSite;
import org.osgi.framework.Bundle;

import rcp.assets.services.HibernateUtil;


public class ProformasController {

	private Browser browser;

	private Integer parametroId;
	private Float parametroImpuesto;
	private String parametroIdioma;
	private Date parametroPeriodo;


	public ProformasController() {
		parametroId = 1;
		parametroImpuesto = 0F;
		parametroIdioma = "1";
		parametroPeriodo = new Date();
	}


	public void previewRetainerReport(Composite composite, String rutaReporte) throws EngineException {

		browser = new Browser(composite, SWT.NONE);
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

			task.setParameterValue("periodo", parametroPeriodo);
			task.setParameterValue("idExpediente", parametroId);
			task.setParameterValue("impuesto", parametroImpuesto);
			task.setParameterValue("connectionURL", HibernateUtil.getConnectionURL());
			task.setParameterValue("idioma", parametroIdioma);
			//task.setParameterValue("connectionURL", "jdbc:mysql://localhost:3306/assets");
			//task.setParameterValue("connectionURL", "jdbc:mysql://orion:3306/assets");
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




	/**
	 * Genera un archivo de excel en base al reporte indicado
	 * @param rutaReporte Ruta donde se encuentra el diseño del reporte a ser generado
	 * Ejemplo:  "/reports/casoFactura.rptdesign"
	 * @throws EngineException
	 */
	public void previewReportFinal(Composite composite, String rutaReporte) throws EngineException {

		browser = new Browser(composite, SWT.NONE);
		EngineConfig config = new EngineConfig();

		try{

			// Create the report engine
			IReportEngineFactory factory = (IReportEngineFactory) org.eclipse.birt.core.framework.Platform
					.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
			IReportEngine engine = factory.createReportEngine( config );

			Bundle bundle = org.eclipse.core.runtime.Platform.getBundle("rcp.assets.facturacion");		
			//URL url = FileLocator.find(bundle, new Path("/reports/casoFactura.rptdesign"), null);
			URL url = FileLocator.find(bundle, new Path(rutaReporte), null);
			String rpt = FileLocator.toFileURL(url).getPath();
			String outputXLS = rpt.replaceFirst( ".rptdesign", ".xls" );

			//add to the classpath-Set Parent Classloader		
			config.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, this.getClass().getClassLoader());

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			// Preparamos las opciones del formato de salida (en este caso Excel)
			EXCELRenderOption options = new EXCELRenderOption();
			options.setOutputStream(bos);
			options.setOutputFormat("xls");
			options.setOutputFileName(outputXLS);
			//options.setOption( IExcelRenderOption.OFFICE_VERSION, "office2007");
			options.setOption( IExcelRenderOption.OFFICE_VERSION, "office2003"); 
			options.setOption(IRenderOption.EMITTER_ID,"org.eclipse.birt.report.engine.emitter.nativexls");

			// Pasamos los parámetros para el reporte y lo ejecutamos
			IReportRunnable design = engine.openReportDesign(rpt);
			IRunAndRenderTask task = engine.createRunAndRenderTask(design);
			task.setParameterValue("idFactura", parametroId);
			task.setParameterValue("impuesto", parametroImpuesto);
			task.setParameterValue("connectionURL", HibernateUtil.getConnectionURL());
			task.setParameterValue("idioma", parametroIdioma);
			task.setRenderOption(options);
			task.run();
			task.close();

			System.out.println("Ruta del archivo: " + outputXLS);

			// Procesamos el archivo de Excel generado, para asignarle la fuente adecuada (Courier)
			procesarFuentesExcel(outputXLS);

			System.out.println("SSS: " + outputXLS);
			browser.setUrl("file://" + outputXLS);
			System.out.println("CLOSE: " + browser.close());

		} catch (Exception e) {
			e.printStackTrace();

		}

	}


	/**
	 * Procesa una hoja de Excel para asegurarse de que tenga las fuentes adecuadas para
	 * una impresora de pines (fuente Courier)
	 * @param rutaArchivoExcel Ruta donde se encuentra la hoja de excel
	 * 
	 */
	private void procesarFuentesExcel(String rutaArchivoExcel) {

		try {

			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(rutaArchivoExcel));
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFFont font = workbook.createFont();
			font.setFontHeightInPoints((short)10);
			font.setFontName("Courier");

			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext()) {
				HSSFRow row = (HSSFRow) rows.next();
				System.out.print("ROW " + row.getRowNum() + ": ");
				Iterator<Cell> cells = row.cellIterator();
				while (cells.hasNext()) {
					HSSFCell cell = (HSSFCell) cells.next();
					if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						System.out.print(cell.getNumericCellValue());
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						System.out.print(cell.getRichStringCellValue());
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
						System.out.print(cell.getBooleanCellValue());
					}
					HSSFCellStyle style = cell.getCellStyle();
					style.setFont(font);
				}

				//System.out.println("ROW: " + row.getRowNum());
				if (row.getRowNum() > 0 & row.getRowNum() < 7) {
					row.setHeightInPoints(12.75F);
				}
				System.out.println();
			}


			HSSFPrintSetup margenes = sheet.getPrintSetup();
			System.out.println("Footer margin: " + margenes.getFooterMargin());
			margenes.setFooterMargin((double) 0.2);
			System.out.println("Footer margin: " + margenes.getFooterMargin());

			FileOutputStream fileOut = new FileOutputStream(rutaArchivoExcel);
			workbook.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
