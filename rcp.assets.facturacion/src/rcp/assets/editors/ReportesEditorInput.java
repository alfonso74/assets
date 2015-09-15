package rcp.assets.editors;

public class ReportesEditorInput extends GenericEditorInput {
	
	private String rutaReporte;

	public ReportesEditorInput() {
		super();
	}

	public ReportesEditorInput(Long id) {
		super(id);
	}

	public ReportesEditorInput(String name) {
		super(name);
	}
	
	public ReportesEditorInput(String name, String rutaReporte) {
		super(name);
		this.rutaReporte = rutaReporte;
	}

	public String getRutaReporte() {
		return rutaReporte;
	}

	public void setRutaReporte(String rutaReporte) {
		this.rutaReporte = rutaReporte;
	}
	
}
