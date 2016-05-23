package rcp.assets.controllers;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IExcelRenderOption;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

import rcp.assets.dto.FacturaRetainerParams;
import rcp.assets.model.TipoFactura;
import rcp.assets.services.HibernateUtil;
import rcp.assets.services.JdbcPathService;


public class VerFacturasController {

	private Shell shell;
	//	private HashMap<String, String> reportesMap;
	private Map<String, Object> parametros;


	public VerFacturasController() {
		shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		//		reportesMap = new HashMap<String, String>();
		//		reportesMap.put("caso", "/reports/casoFactura.rptdesign");
		//		reportesMap.put("retainer", "/reports/retainerFactura.rptdesign");
		//		reportesMap.put("anualidad", "/reports/anualidadFactura.rptdesign");
		//		reportesMap.put("CA", "/reports/casoFactura.rptdesign");
		//		reportesMap.put("RE", "/reports/retainerFactura.rptdesign");
		//		reportesMap.put("AN", "/reports/anualidadFactura.rptdesign");
		parametros = new HashMap<String, Object>();
		inicializarParametrosDefault();
	}

	/**
	 * Permite suministrar los parámetros requeridos para ejecutar un reporte
	 * @param parameterName Nombre del parámetro
	 * @param valor Valor del parámetro
	 */
	public void agregarParametro(String parameterName, Object valor) {
		parametros.put(parameterName, valor);
	}

	private void inicializarParametrosDefault() {
		parametros.put("connectionURL", HibernateUtil.getConnectionURL());
		parametros.put("idFactura", 1);
		parametros.put("idioma", "1");
		parametros.put("impuesto", 3.33F);
	}

	public void configurarParametrosCaso() {

	}

	public void configurarParametrosRetainer(FacturaRetainerParams params) {
		Map<String, Object> p = params.generarParametrosReporte();
		parametros = p;
	}

	/**
	 * Genera una factura de caso, retainer, o anualidad en excel.  La factura ya debe existir en la base de datos,
	 * y el reporte solo obtiene la informacion utilizando el id suministrado.
	 * @param tipoFactura El tipo de factura a generar, los parámetros válidos son:  "caso", "retainer", "anualidad".
	 */
	public void generarFacturaExcel(Browser browser, TipoFactura tipoFactura) {
		//		String rutaReporte = reportesMap.get(tipoFactura);
		String rutaReporte = tipoFactura.getRuta();
		if (rutaReporte == null) {
			MessageDialog.openError(shell, "Generar factura", "Error al especificar el tipo de factura: " + tipoFactura.getNombre() + ".");
		} else {
			System.out.println("Parametros: " + parametros);
			IReportEngine engine = inicializarEngine();
			String rpt = getReportURL(rutaReporte);
			RenderOption options = configurarRendererExcel2003(rpt);
			String reporteFileName = ejecutarReporte(engine, options, rpt);
			procesarFuentesExcel(reporteFileName, tipoFactura);

			System.out.println("Archivo de salida: " + reporteFileName);

			//browser.setText(bos.toString("UTF-8"));
			//browser.setUrl("file:///c:/proformaCaso.pdf");
			browser.setUrl("file://" + reporteFileName);
			engine.destroy();
		}
	}
	
	public void generarDocumentoExcel(Browser browser, String rutaReporte) {
		if (rutaReporte == null) {
			MessageDialog.openError(shell, "Generar factura", "La ruta del template del documento es incorrecta: " + rutaReporte + ".");
		} else {
			System.out.println("Parametros: " + parametros);
			IReportEngine engine = inicializarEngine();
			String rptTemplatePath = getReportURL(rutaReporte);
			RenderOption options = configurarRendererExcel2007(rptTemplatePath);
			
			// agrega la fecha/hora de generación del reporte
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HHmm");
			String generatedDateTxt = format.format(new Date());
			String tmpReportName = options.getOutputFileName().replace(".xlsx", " " + generatedDateTxt + ".xlsx");
			options.setOutputFileName(tmpReportName);
			
			String reporteFileName = ejecutarReporte(engine, options, rptTemplatePath);
			
			System.out.println("Archivo de salida: " + reporteFileName);
			
			browser.setUrl("file://" + reporteFileName);
			engine.destroy();
		}
	}

	private IReportEngine inicializarEngine() {
		EngineConfig config = new EngineConfig();
		//add to the classpath-Set Parent Classloader		
		//config.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, this.getClass().getClassLoader());
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
			MessageDialog.openError(shell, "Error en: " + this.getClass().getName(), "Error buscando archivo: " + rutaReporte + ".\n" +
					"Error: " + e.toString() + "\n\nStack trace: " + e.getStackTrace()[0] + "\n" + e.getStackTrace()[1]);
			e.printStackTrace();
		}
		System.out.println("Ruta del reporte (bundle): " + rpt);
		return rpt;
	}

	/**
	 * Configura un renderer de excel (versión 2003) para generar una hoja de cálculo basada 
	 * en el reporte indicado
	 * @param rpt Ruta del reporte usado como template
	 * @return RenderOption configurado para excel 2003
	 */
	private RenderOption configurarRendererExcel2003(String rpt) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String outputXLS = rpt.replaceFirst( ".rptdesign", ".xls" );
		EXCELRenderOption options = new EXCELRenderOption();
		options.setOutputStream(bos);
		options.setOutputFormat("xls");
		options.setOutputFileName(outputXLS);
		options.setOption( IExcelRenderOption.OFFICE_VERSION, "office2003"); 
		options.setOption(IRenderOption.EMITTER_ID,"org.eclipse.birt.report.engine.emitter.nativexls");
		System.out.println("Ruta del archivo a generar: " + outputXLS);
		return options;
	}
	
	/**
	 * Configura un renderer de excel (versión 2007) para generar una hoja de cálculo basada 
	 * en el reporte indicado
	 * @param rpt Ruta del reporte usado como template
	 * @return RenderOption configurado para excel 2007
	 */
	private RenderOption configurarRendererExcel2007(String rpt) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String outputXLS = rpt.replaceFirst( ".rptdesign", ".xlsx" );
		EXCELRenderOption options = new EXCELRenderOption();
		options.setOutputStream(bos);
		options.setOutputFileName(outputXLS);
		options.setOption( IExcelRenderOption.OFFICE_VERSION, "office2007"); 
		options.setOption(IRenderOption.EMITTER_ID,"org.eclipse.birt.report.engine.emitter.nativexls");
		System.out.println("Ruta del archivo a generar: " + outputXLS);
		return options;
	}

	/**
	 * Gnenera un archivo de acuerdo al reporte y formato suministrados
	 * @param engine Engine de BIRT
	 * @param options renderer configurado para generar el archivo en un formato específico
	 * @param rptTemplatePath ruta del reporte de BIRT a usar como template
	 * @return
	 */
	private String ejecutarReporte(IReportEngine engine, RenderOption options, String rptTemplatePath) {
		// Pasamos los parámetros para el reporte y lo ejecutamos
		try {
			IReportRunnable design = engine.openReportDesign(rptTemplatePath);
			IRunAndRenderTask task = engine.createRunAndRenderTask(design);
			task.setParameterValue("connectionURL", HibernateUtil.getConnectionURL());
			task.setParameterValues(parametros);
			task.setRenderOption(options);
			task.run();
			task.close();
		} catch (EngineException e) {
			MessageDialog.openError(shell, "Error en: " + this.getClass().getName(), "Error al generar el reporte: " + rptTemplatePath + ".\n" +
					"Error: " + e.toString() + "\n\nStack trace: " + e.getStackTrace()[0] + "\n" + e.getStackTrace()[1]);
			e.printStackTrace();
		}
		return options.getOutputFileName();
	}


	/*
	public void generarReporteExcel(Browser browser, String rutaReporte) {

		String rpt = "";

		try {

			EngineConfig config = new EngineConfig();

			IReportEngineFactory factory = (IReportEngineFactory) org.eclipse.birt.core.framework.Platform
					.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
			IReportEngine engine = factory.createReportEngine( config );

			Bundle bundle = org.eclipse.core.runtime.Platform.getBundle("rcp.assets.facturacion");		
			//URL url = FileLocator.find(bundle, new Path("/reports/casoFactura.rptdesign"), null);
			URL url = FileLocator.find(bundle, new Path(rutaReporte), null);
			rpt = FileLocator.toFileURL(url).getPath();
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
			task.setParameterValue("idFactura", parametroIdFactura);
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

		} catch (IOException e) {
			MessageDialog.openError(shell, "Error en: " + this.getClass().getName(), "Error buscando archivo: " + rutaReporte + ".\n" +
					"Error: " + e.toString() + "\n\nStack trace: " + e.getStackTrace()[0] + "\n" + e.getStackTrace()[1]);
			e.printStackTrace();
		} catch (EngineException e) {
			MessageDialog.openError(shell, "Error en: " + this.getClass().getName(), "Error al generar el reporte: " + rpt + ".\n" +
					"Error: " + e.toString() + "\n\nStack trace: " + e.getStackTrace()[0] + "\n" + e.getStackTrace()[1]);
			e.printStackTrace();
		}
	}
	 */


	/**
	 * Procesa una hoja de Excel para asegurarse de que tenga las fuentes adecuadas para
	 * una impresora de pines (fuente Courier)
	 * @param rutaArchivoExcel Ruta donde se encuentra la hoja de excel
	 * 
	 */
	private void procesarFuentesExcel(String rutaArchivoExcel, TipoFactura tipoFactura) {

		try {

			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(rutaArchivoExcel));
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFFont font = workbook.createFont();
			font.setFontHeightInPoints((short)10);
			font.setFontName("Courier");

			float heightInPoints = 0;
			int lastRow = 0;
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
				if (row.getRowNum() == 0) {
					row.setHeightInPoints(15.75f + 0.0f);    // el 0.0f podemos usarlo como first line offset
				} else if (row.getRowNum() > 0 & row.getRowNum() < 7) {
					row.setHeightInPoints(12.75F);
				}
				System.out.println();
				heightInPoints += row.getHeightInPoints();
				lastRow++;
			}
			System.out.println("Altura total en puntos (hoja de excel): " + heightInPoints);

			if (tipoFactura == TipoFactura.ANUALIDAD) {
				ajustarEspacioNotificacionesAnualidades(sheet, heightInPoints, lastRow);
			}

			HSSFPrintSetup margenes = sheet.getPrintSetup();
			System.out.println("Footer margin: " + margenes.getFooterMargin());
			margenes.setFooterMargin((double) 0.3);
			System.out.println("Footer margin: " + margenes.getFooterMargin());

			FileOutputStream fileOut = new FileOutputStream(rutaArchivoExcel);
			workbook.write(fileOut);
			fileOut.close();

		} catch (FileNotFoundException e) {
			MessageDialog.openError(shell, "Error en: " + this.getClass().getName(), "No se encontró el archivo: " + rutaArchivoExcel + ".\n" +
					"Error: " + e.toString() + "\n\nStack trace: " + e.getStackTrace()[0] + "\n" + e.getStackTrace()[1]);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO crear metodo que presente mensajes de error
			MessageDialog.openError(shell, "Error en: " + this.getClass().getName(), "No se pudo modificar el archivo: " + rutaArchivoExcel + ".\n" +
					"Error: " + e.toString() + "\n\nStack trace: " + e.getStackTrace()[0] + "\n" + e.getStackTrace()[1]);
			e.printStackTrace();
		}

	}

	private void ajustarEspacioNotificacionesAnualidades(HSSFSheet sheet,
			float heightInPoints, int lastRow) {
		Iterator<Row> rows;
		// ajuste de penúltimo row para bajar la posición del cuadro de notificaciones.
		float maxPoints = 710f * 3f / 4f;   // 740 * 3 / 4 ==>  555
		System.out.println("Max points: " + maxPoints + ", Last row: " + lastRow);
		if (heightInPoints < maxPoints) {
			System.out.println("Evaluando ajuste...");
			Row variableRow = null;
			rows = sheet.rowIterator();
			int n = 0;
			while (rows.hasNext()) {
				n++;
				variableRow = rows.next();
				if (n == (lastRow - 1)) {
					float ajuste = maxPoints - heightInPoints;
					variableRow.setHeightInPoints(variableRow.getHeightInPoints() + ajuste);
					System.out.println("Ajustando row " + (n - 1) + ": " + ajuste + " puntos");
				}
			}
		}
	}

}
