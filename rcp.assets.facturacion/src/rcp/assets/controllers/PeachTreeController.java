package rcp.assets.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import rcp.assets.export.excel.ExcelCSVPrinter;
import rcp.assets.model.DetalleCargo;
import rcp.assets.model.Expediente;
import rcp.assets.model.Factura;
import rcp.assets.model.FacturaAnualidad;
import rcp.assets.model.FacturaCaso;
import rcp.assets.model.FacturaRetainer;
import rcp.assets.model.LineaFactura;
import rcp.assets.services.FechaUtil;

public class PeachTreeController {

	private ExpedientesController expedientesController;
	private DetalleCargosController detCargosController;

	private String[] etiquetas = {"Customer ID","Invoice/CM #","Apply to Invoice Number","Credit Memo","Progress Billing Invoice",
			"Date","Ship By","Quote","Quote #","Quote Good Thru Date","Drop Ship","Ship to Name","Ship to Address-Line One",
			"Ship to Address-Line Two","Ship to City","Ship to State","Ship to Zipcode","Ship to Country","Customer PO","Ship Via",
			"Ship Date","Date Due","Discount Amount","Discount Date","Displayed Terms","Sales Representative ID",
			"Accounts Receivable Account","Sales Tax ID","Invoice Note","Note Prints After Line Items","Statement Note",
			"Stmt Note Prints Before Ref","Internal Note","Beginning Balance Transaction","Number of Distributions",
			"Invoice/CM Distribution","Apply to Invoice Distribution","Apply To Sales Order","Apply to Proposal","Quantity",
			"SO/Proposal Number","Item ID","Serial Number","SO/Proposal Distribution","Description","G/L Account","Unit Price",
			"Tax Type","UPC / SKU","Weight","Amount","U/M ID","U/M No. of Stocking Units","Stocking Quantity",
			"Stocking Unit Price","Job ID","Sales Tax Agency ID","Transaction Period","Transaction Number","Return Authorization",
			"Voided by Transaction","Retainage Percent","Recur Number","Recur Frequency"};
	private String[] lineaFacturaBase = {"999-9999","99999","","FALSE","FALSE","11/11/2011","","FALSE","","","FALSE","","",
			"","","","","","","Airborne","","11/11/2011","0.00","11/11/2011","Net 30 Days","","100-111-110","ITBMS","","FALSE",
			"","FALSE","","FALSE","9","9","0","FALSE","FALSE","0.00","","","","0","TEXTO99","CUENTA99","0.00","0","","0.00",
			"-99.99","","0.00","0.00","0.00","","ITBMS","99","99999","","","0.00","0","0"};
	private String[] lineaFactura = {"999-9999","99999","","FALSE","FALSE","11/11/2011","","FALSE","","","FALSE","","",
			"","","","","","","Airborne","","11/11/2011","0.00","11/11/2011","Net 30 Days","","100-111-110","ITBMS","","FALSE",
			"","FALSE","","FALSE","9","9","0","FALSE","FALSE","0.00","","","","0","TEXTO99","CUENTA99","0.00","0","","0.00",
			"-99.99","","0.00","0.00","0.00","","ITBMS","99","99999","","","0.00","0","0"};

	public PeachTreeController() {
		expedientesController = new ExpedientesController();
		detCargosController = new DetalleCargosController();
	}


	public boolean generarArchivoCSV(Shell shell, List<Factura> listado, Integer noPeriodoTx, String formatoFecha, String rutaArchivo) {

		boolean resultado = false;
		//String rutaArchivo = "c:\\LegalFact-FacturasX.csv";
		ExcelCSVPrinter csv = null;

		try {

			OutputStream out = new FileOutputStream(rutaArchivo);
			csv = new ExcelCSVPrinter(out);

			Expediente expediente;
			int noFactura = 0;
			int currentRow = 0;

			//crearEtiquetas();
			//exportarLinea(csv, currentRow++);
			for (Factura factura : listado) {
				noFactura++;
				System.out.println("FISICO: " + factura.getNoFisico());
				expediente = expedientesController.buscarExpediente(factura.getNoExpediente());
				factura.setNoPeriodoTx(noPeriodoTx);
				if (factura.getTipo().equals("CA")) {
					currentRow = agregarFacturaCaso((FacturaCaso) factura, expediente, csv, new Integer(noFactura), currentRow, formatoFecha);
				} else if (factura.getTipo().equals("RE")) {
					currentRow = agregarFacturaRetainer((FacturaRetainer) factura, expediente, csv, new Integer(noFactura), currentRow, formatoFecha);
				} else if (factura.getTipo().equals("AN")) {
					currentRow = agregarFacturaAnualidad((FacturaAnualidad) factura, expediente, csv, new Integer(noFactura), currentRow, formatoFecha);
				}
			}

			csv.close();
			resultado = true;

		} catch (FileNotFoundException e) {
			MessageDialog.openError(shell, "Error en: " + this.getClass().getName(), "No se encontró el archivo: " + rutaArchivo + ".\n" +
					"Error: " + e.toString() + "\n\nStack trace: " + e.getStackTrace()[0] + "\n" + e.getStackTrace()[1]);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO crear metodo que presente mensajes de error
			MessageDialog.openError(shell, "Error en: " + this.getClass().getName(), "No se pudo modificar el archivo: " + rutaArchivo + ".\n" +
					"Error: " + e.toString() + "\n\nStack trace: " + e.getStackTrace()[0] + "\n" + e.getStackTrace()[1]);
			e.printStackTrace();
		} catch (Exception e) {
			MessageDialog.openError(shell, "Error en: " + this.getClass().getName(), "Error durante la creación del archivo: " + rutaArchivo + ".\n" +
					"Error: " + e.toString() + "\n\nStack trace: " + e.getStackTrace()[0] + "\n" + e.getStackTrace()[1]);
			e.printStackTrace();
		} finally {
			if (csv != null) {
				try {
					//csv.flush();
					csv.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultado;
	}


	public void generarArchivoXLS(Shell shell, List<Factura> listado, Integer noPeriodoTx, String formatoFecha) {

		String rutaArchivoExcel = "";

		try {

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("new sheet");

			Expediente expediente;
			int noFactura = 0;
			int currentRow = 0;
			for (Factura factura : listado) {
				noFactura++;
				expediente = expedientesController.buscarExpediente(factura.getNoExpediente());
				factura.setNoPeriodoTx(new Integer(noPeriodoTx));
				if (factura.getTipo().equals("CA")) {
					currentRow = agregarFacturaCaso((FacturaCaso) factura, expediente, sheet, new Integer(noFactura), currentRow, formatoFecha);
				} else if (factura.getTipo().equals("RE")) {
					currentRow = agregarFacturaRetainer((FacturaRetainer) factura, expediente, sheet, new Integer(noFactura), currentRow, formatoFecha);
				} else if (factura.getTipo().equals("AN")) {
					currentRow = agregarFacturaAnualidad((FacturaAnualidad) factura, expediente, sheet, new Integer(noFactura), currentRow, formatoFecha);
				}
			}

			FileOutputStream fileOut = new FileOutputStream("workbookPeachTree.xls");
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


	public void generarArchivoXLS(Shell shell, Factura factura, Integer txPeriodo, String formatoFecha) {
		String rutaArchivoExcel = "";

		try {

			Expediente expediente = expedientesController.buscarExpediente(factura.getNoExpediente());

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("new sheet");

			if (factura.getTipo().equals("CA")) {
				agregarFacturaCaso((FacturaCaso) factura, expediente, sheet, new Integer(1), 0, formatoFecha);
			} else if (factura.getTipo().equals("RE")) {
				agregarFacturaRetainer((FacturaRetainer) factura, expediente, sheet, new Integer(1), 0, formatoFecha);
			} else if (factura.getTipo().equals("AN")) {
				agregarFacturaAnualidad((FacturaAnualidad) factura, expediente, sheet, new Integer(1), 0, formatoFecha);
			}

			FileOutputStream fileOut = new FileOutputStream("workbookPeachTree.xls");
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

	
	/**
	 * 
	 * @param factura
	 * @param expediente
	 * @param sheet
	 * @param noTransaccion no de factura dentro del excel (iniciando en 1)
	 * @param currentRow fila del excel donde se inserta la factura (iniciando en 0) 
	 */
	private int agregarFacturaCaso(FacturaCaso factura, Expediente expediente, HSSFSheet sheet, Integer noTransaccion, int currentRow, String formatoFecha) {
		int noLinea = 0;
		crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
		agregarDetallesImpuesto(factura);
		exportarLinea(sheet, currentRow + noLinea++);
		List<DetalleCargo> listado = detCargosController.buscarDetalleCargosPorCaso(factura.getCaso().getNoCia(), factura.getCaso().getNoCaso().toString());
		for (DetalleCargo detalle : listado) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaCaso(detalle, expediente);
			exportarLinea(sheet, currentRow + noLinea++);
		}
		/*
		for (LineaFactura linea : factura.getListaDetalles()) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaCaso(linea);
			exportarLinea(csv, currentRow + noLinea++);
		}
		*/
		currentRow += noLinea;
		return currentRow;
	}

	
	private int agregarFacturaCaso(FacturaCaso factura, Expediente expediente, ExcelCSVPrinter csv, Integer noTransaccion, int currentRow, String formatoFecha) {
		int noLinea = 0;
		crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
		agregarDetallesImpuesto(factura);
		exportarLinea(csv, currentRow + noLinea++);
		List<DetalleCargo> listado = detCargosController.buscarDetalleCargosPorCaso(factura.getCaso().getNoCia(), factura.getCaso().getNoCaso().toString());
		for (DetalleCargo detalle : listado) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaCaso(detalle, expediente);
			exportarLinea(csv, currentRow + noLinea++);
		}
		currentRow += noLinea;
		return currentRow;
	}


	/**
	 * 
	 * @param factura
	 * @param expediente
	 * @param sheet
	 * @param noTransaccion no de factura dentro del excel (iniciando en 1)
	 * @param currentRow fila del excel donde se inserta la factura (iniciando en 0) 
	 */
	private int agregarFacturaRetainer(FacturaRetainer factura, Expediente expediente, HSSFSheet sheet, Integer noTransaccion, int currentRow, String formatoFecha) {
		int noLinea = 0;
		crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
		agregarDetallesImpuesto(factura);
		exportarLinea(sheet, currentRow + noLinea++);
		if (factura.getRetainer1() != null && factura.getRetainer1() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaRetainer("Servicios generales", "400-110-000", factura.getRetainer1());
			exportarLinea(sheet, currentRow + noLinea++);
		}
		if (factura.getRetainer2() != null && factura.getRetainer2() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaRetainer("Servicios de contabilidad", "400-055-000", factura.getRetainer2());
			exportarLinea(sheet, currentRow + noLinea++);
		}
		if (factura.getRetainer3() != null && factura.getRetainer3() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaRetainer("Servicios fiduciarios", "400-021-000", factura.getRetainer3());
			exportarLinea(sheet, currentRow + noLinea++);
		}
		if (factura.getOtroRetainer() != null && factura.getOtroRetainer() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaRetainer(factura.getOtroRetainerCom(), "400-110-000", factura.getOtroRetainer());
			exportarLinea(sheet, currentRow + noLinea++);
		}
		currentRow += noLinea;
		return currentRow;
	}


	private int agregarFacturaRetainer(FacturaRetainer factura, Expediente expediente, ExcelCSVPrinter csv, Integer noTransaccion, int currentRow, String formatoFecha) {
		int noLinea = 0;
		crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
		agregarDetallesImpuesto(factura);
		exportarLinea(csv, currentRow + noLinea++);
		if (factura.getRetainer1() != null && factura.getRetainer1() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaRetainer("Servicios generales", "400-110-000", factura.getRetainer1());
			exportarLinea(csv, currentRow + noLinea++);
		}
		if (factura.getRetainer2() != null && factura.getRetainer2() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaRetainer("Servicios de contabilidad", "400-055-000", factura.getRetainer2());
			exportarLinea(csv, currentRow + noLinea++);
		}
		if (factura.getRetainer3() != null && factura.getRetainer3() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaRetainer("Servicios fiduciarios", "400-021-000", factura.getRetainer3());
			exportarLinea(csv, currentRow + noLinea++);
		}
		if (factura.getOtroRetainer() != null && factura.getOtroRetainer() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaRetainer(factura.getOtroRetainerCom(), "400-110-000", factura.getOtroRetainer());
			exportarLinea(csv, currentRow + noLinea++);
		}
		currentRow += noLinea;
		return currentRow;
	}


	/**
	 * 
	 * @param factura
	 * @param expediente
	 * @param sheet
	 * @param noTransaccion no de factura dentro del excel (iniciando en 1)
	 * @param currentRow fila del excel donde se inserta la factura (iniciando en 0) 
	 */
	private int agregarFacturaAnualidad(FacturaAnualidad factura, Expediente expediente, HSSFSheet sheet, Integer noTransaccion, int currentRow, String formatoFecha) {
		int noLinea = 0;
		crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
		agregarDetallesImpuesto(factura);
		exportarLinea(sheet, currentRow + noLinea++);
		if (factura.getDirectores() != null && factura.getDirectores() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaAnualidad("Directores y dignatarios", "400-001-000", factura.getDirectores());
			agregarDetallesJobId(expediente.getIdJobTasa(), expediente.getIdJobGNF());
			exportarLinea(sheet, currentRow + noLinea++);
		}
		if (factura.getAgenteResidente() != null && factura.getAgenteResidente() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaAnualidad("Agente residente", "400-002-000", factura.getAgenteResidente());
			agregarDetallesJobId(expediente.getIdJobTasa(), expediente.getIdJobGNF());
			exportarLinea(sheet, currentRow + noLinea++);
		}
		if (factura.getRetransmision() != null && factura.getRetransmision() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaAnualidad("Retransmisión", "400-006-000", factura.getRetransmision());
			agregarDetallesJobId(expediente.getIdJobTasa(), expediente.getIdJobGNF());
			exportarLinea(sheet, currentRow + noLinea++);
		}
		if (factura.getCustodiaLibros() != null && factura.getCustodiaLibros() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaAnualidad("Custodia", "400-004-000", factura.getCustodiaLibros());
			agregarDetallesJobId(expediente.getIdJobTasa(), expediente.getIdJobGNF());
			exportarLinea(sheet, currentRow + noLinea++);
		}
		if (factura.getServFirma() != null && factura.getServFirma() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaAnualidad("Serv. de firmas", "400-007-000", factura.getServFirma());
			agregarDetallesJobId(expediente.getIdJobTasa(), expediente.getIdJobGNF());
			exportarLinea(sheet, currentRow + noLinea++);
		}
		if (factura.getServFiduciarios() != null && factura.getServFiduciarios() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaAnualidad("Serv. fiduciarios", "400-021-000", factura.getServFiduciarios());
			agregarDetallesJobId(expediente.getIdJobTasa(), expediente.getIdJobGNF());
			exportarLinea(sheet, currentRow + noLinea++);
		}
		if (factura.getTasa() != null && factura.getTasa() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaAnualidad("Tasa", "200-100-020", factura.getTasa());
			agregarDetallesJobId(expediente.getIdJobTasa(), expediente.getIdJobGNF());
			exportarLinea(sheet, currentRow + noLinea++);
		}
		if (factura.getOtros() != null && factura.getOtros() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaAnualidad(factura.getOtrosDetalle(), "400-110-000", factura.getOtros());
			agregarDetallesJobId(expediente.getIdJobTasa(), expediente.getIdJobGNF());
			exportarLinea(sheet, currentRow + noLinea++);
		}
		currentRow += noLinea;
		return currentRow;
	}



	private int agregarFacturaAnualidad(FacturaAnualidad factura, Expediente expediente, ExcelCSVPrinter csv, Integer noTransaccion, int currentRow, String formatoFecha) {
		int noLinea = 0;
		crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
		agregarDetallesImpuesto(factura);
		exportarLinea(csv, currentRow + noLinea++);
		if (factura.getDirectores() != null && factura.getDirectores() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaAnualidad("Directores y dignatarios", "400-001-000", factura.getDirectores());
			agregarDetallesJobId(expediente.getIdJobTasa(), expediente.getIdJobGNF());
			exportarLinea(csv, currentRow + noLinea++);
		}
		if (factura.getAgenteResidente() != null && factura.getAgenteResidente() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaAnualidad("Agente residente", "400-002-000", factura.getAgenteResidente());
			agregarDetallesJobId(expediente.getIdJobTasa(), expediente.getIdJobGNF());
			exportarLinea(csv, currentRow + noLinea++);
		}
		if (factura.getRetransmision() != null && factura.getRetransmision() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaAnualidad("Retransmisión", "400-006-000", factura.getRetransmision());
			agregarDetallesJobId(expediente.getIdJobTasa(), expediente.getIdJobGNF());
			exportarLinea(csv, currentRow + noLinea++);
		}
		if (factura.getCustodiaLibros() != null && factura.getCustodiaLibros() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaAnualidad("Custodia", "400-004-000", factura.getCustodiaLibros());
			agregarDetallesJobId(expediente.getIdJobTasa(), expediente.getIdJobGNF());
			exportarLinea(csv, currentRow + noLinea++);
		}
		if (factura.getServFirma() != null && factura.getServFirma() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaAnualidad("Serv. de firmas", "400-007-000", factura.getServFirma());
			agregarDetallesJobId(expediente.getIdJobTasa(), expediente.getIdJobGNF());
			exportarLinea(csv, currentRow + noLinea++);
		}
		if (factura.getServFiduciarios() != null && factura.getServFiduciarios() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaAnualidad("Serv. fiduciarios", "400-021-000", factura.getServFiduciarios());
			agregarDetallesJobId(expediente.getIdJobTasa(), expediente.getIdJobGNF());
			exportarLinea(csv, currentRow + noLinea++);
		}
		if (factura.getTasa() != null && factura.getTasa() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaAnualidad("Tasa", "200-100-020", factura.getTasa());
			agregarDetallesJobId(expediente.getIdJobTasa(), expediente.getIdJobGNF());
			exportarLinea(csv, currentRow + noLinea++);
		}
		if (factura.getOtros() != null && factura.getOtros() != 0) {
			crearLineaBase(factura, expediente, noTransaccion, noLinea, formatoFecha);
			agregarDetallesLineaAnualidad(factura.getOtrosDetalle(), "400-110-000", factura.getOtros());
			agregarDetallesJobId(expediente.getIdJobTasa(), expediente.getIdJobGNF());
			exportarLinea(csv, currentRow + noLinea++);
		}
		currentRow += noLinea;
		return currentRow;
	}


	private void exportarLinea(HSSFSheet sheet, int indice) {
		HSSFRow row = sheet.createRow((short)(indice));
		for (int n = 0; n < lineaFactura.length; n++) {
			HSSFCell cell = row.createCell(n);
			cell.setCellValue(lineaFactura[n]);
		}
	}


	private void exportarLinea(ExcelCSVPrinter csv, int indice) {
		try {
			csv.writeln(lineaFactura);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void crearEtiquetas() {
		lineaFactura = etiquetas;
	}


	private void crearLineaBase(Factura factura, Expediente expediente, Integer noTransaccion, Integer noLineaFactura, String formatoFecha) {
		/*
		for (int n = 0; n < etiquetas.length; n++) {
			HSSFCell cell = row.createCell(n);
			//cell.setCellFormula(etiquetas[n]);
			cell.setCellValue(etiquetas[n]);
			//lineaFactura[n] = etiquetas[n];
			lineaFactura = etiquetas;
		}
		 */
		//lineaFactura = etiquetas;
		lineaFactura = lineaFacturaBase;

		lineaFactura[0] = expediente.getIdPeachtree();				// id del cliente en peachtree
		lineaFactura[1] = factura.getNoFisico();
		lineaFactura[5] = FechaUtil.toString(factura.getFechaFactura(), formatoFecha);
		System.out.println("FECHA FACT: " + FechaUtil.toString(factura.getFechaFactura(), formatoFecha));
		lineaFactura[18] = "";							//Customer PO (usado para poner el numero de caso)
		lineaFactura[21] = FechaUtil.toString(FechaUtil.ajustarFecha(factura.getFechaFactura(), 30), formatoFecha);
		lineaFactura[22] = "0.00";
		lineaFactura[23] = FechaUtil.toString(factura.getFechaFactura(), formatoFecha);
		lineaFactura[26] = "100-111-110";
		lineaFactura[27] = "ITBMS";
		lineaFactura[34] = (factura.getCantidadLineas() + 1) + "";	//procesar x tipo de factura (siempre se suma 1 x la línea de ITBMS)
		lineaFactura[35] = noLineaFactura.toString();
		lineaFactura[39] = "0.00";
		lineaFactura[44] = "Descripción";				//procesar x tipo de factura
		lineaFactura[45] = "Cuenta contable";			//procesar x tipo de factura  
		//lineaFactura[47] = factura.isExcentoImpuesto() ? "0" : "1";	//1 paga impuesto, 0 es excento (lineas de ITBMS siempre llevan 0) 
		lineaFactura[47] = "1";							//1 para líneas normales, 0 para líneas de impuesto (ITBMS)
		lineaFactura[50] = "-99";						//procesar x tipo de factura
		lineaFactura[52] = "1";							//0 para líneas de ITBMS, sino es 1
		lineaFactura[55] = "";							//Job ID (solo tasa o GNF)
		lineaFactura[56] = "";							//"ITBMS" para línea de ITBMS, sino en ""
		lineaFactura[57] = factura.getNoPeriodoTx().toString();
		lineaFactura[58] = noTransaccion.toString();
		if (lineaFactura[45].equals("200-100-020")) {   //tasa
			// adición de Job IDs dependiendo de si el cargo es de tasa o gasto no facturado (GNF)
			lineaFactura[55] = "Job ID para tasa";
		} else if (lineaFactura[26].equals("100-111-120")) {   //GNF
			lineaFactura[55] = "Job ID para GNF";
		}
		// si es una factura de "Caso", le asignamos el numero de caso al campo de "Customer PO"
		if (factura.getTipo().equals("CA")) {
			lineaFactura[18] = factura.getRefSecuencia().toString();
		}
		// si es la primera línea, se inserta el "Concepto" de la factura en lugar de la descripción del cargo
		if (noLineaFactura.intValue() == 1) {
			lineaFactura[44] = factura.getConcepto();
		}
	}


	private void agregarDetallesImpuesto(Factura factura) {
		lineaFactura[44] = factura.getImpuestoPorcentaje().toString() + "%";
		lineaFactura[45] = "200-100-024";  
		lineaFactura[47] = "0";						//1 paga impuesto, 0 es excento (lineas de ITBMS siempre llevan 0) 
		lineaFactura[50] = "-" + factura.getImpuesto().toString();					//procesar x tipo de factura
		lineaFactura[52] = "0";						//0 para lineas de ITBMS, sino es 1
		lineaFactura[56] = "ITBMS";						//"ITBMS" para linea de ITBMS, sino en ""
	}


	@Deprecated
	private void agregarDetallesLineaCaso(LineaFactura linea) {
		// solo modificamos la columna de descripción si no ha sido alterada previamente (caso de linea no. 1, donde
		// se reemplaza la descripción de la linea por el concepto de la factura).
		if (lineaFactura[44].equals("Descripción"))
			lineaFactura[44] = linea.getDescripcion();							// descripción
		lineaFactura[45] = linea.getTipoCargo().getCuentaContablePeachTree();	// cuenta peachtree
		lineaFactura[50] = "-" + linea.getMonto().toString();					// monto sin impuesto
		lineaFactura[52] = "1";													//0 para lineas de ITBMS, sino es 1
	}
	
	/**
	 * Agrega una linea de detalle para la factura, pero utilizando los registros de cargos, ya que
	 * la factura de caso puede tener agrupaciones y se pierde la informacion contable.
	 * @param detalle Linea de detalle de cargos
	 * @param expediente Expediente asociado al caso (para obtener los JobId (tasa/GNF) que se usan en Peachtree)
	 */
	private void agregarDetallesLineaCaso(DetalleCargo detalle, Expediente expediente) {
		// solo modificamos la columna de descripción si no ha sido alterada previamente (caso de linea no. 1, donde
		// se reemplaza la descripción de la linea por el concepto de la factura).
		if (lineaFactura[44].equals("Descripción"))
			lineaFactura[44] = detalle.getTipoCargo().getDescripcion();							// descripción
		lineaFactura[45] = detalle.getTipoCargo().getCuentaContablePeachTree();	// cuenta peachtree
		lineaFactura[50] = "-" + detalle.getMonto().toString();					// monto sin impuesto
		lineaFactura[52] = "1";													//0 para lineas de ITBMS, sino es 1
		
		// adición de Job IDs dependiendo de si el cargo es de tasa o gasto no facturado (GNF)
		if (lineaFactura[45].equals("200-100-020")) {   //tasa
			lineaFactura[55] = expediente.getIdJobTasa() == null ? "" : expediente.getIdJobTasa();
		} else if (lineaFactura[26].equals("100-111-120")) {   //GNF
			lineaFactura[55] = expediente.getIdJobGNF() == null ? "" : expediente.getIdJobGNF();
		}
	}


	private void agregarDetallesLineaRetainer(String descripcion, String cuentaContable, Float monto) {
		if (lineaFactura[44].equals("Descripción"))
			lineaFactura[44] = descripcion;				// descripción
		lineaFactura[45] = cuentaContable;				// cuenta peachtree
		lineaFactura[50] = "-" + monto;					// monto sin impuesto
		lineaFactura[52] = "1";							//0 para lineas de ITBMS, sino es 1
	}


	private void agregarDetallesLineaAnualidad(String descripcion, String cuentaContable, Float monto) {
		if (lineaFactura[44].equals("Descripción"))
			lineaFactura[44] = descripcion;						// descripción
		lineaFactura[45] = cuentaContable;						// cuenta peachtree
		lineaFactura[50] = "-" + monto;							// monto sin impuesto
		lineaFactura[52] = "1";									//0 para lineas de ITBMS, sino es 1
	}


	private void agregarDetallesJobId(String jobIdTasa, String jobIdGNF) {
		// adición de Job IDs dependiendo de si el cargo es de tasa o gasto no facturado (GNF)
		if (lineaFactura[45].equals("200-100-020")) {   		//tasa
			lineaFactura[55] = jobIdTasa;						// "Job ID para tasa"
		} else if (lineaFactura[45].equals("100-111-120")) {	//GNF
			lineaFactura[55] = jobIdGNF;						//"Job ID para GNF"
		}
	}

}
