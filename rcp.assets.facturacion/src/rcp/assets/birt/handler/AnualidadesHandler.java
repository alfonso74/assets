package rcp.assets.birt.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.IUpdatableDataSetRow;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.eventadapter.ScriptedDataSetEventAdapter;
import org.eclipse.birt.report.engine.api.script.instance.IDataSetInstance;

import rcp.assets.model.Expediente;

/**
 * Clase que sirve como DataSet para el reporte de anualidades.  Debe agregarse en
 * el "Event Handler" de las propiedades del DataSet.
 * Adicionalmente, la clase debe ser agregada al ClassPath de BIRT (Window\Preferences\Report Design\Classpath)
 * @author Carlos Alfonso
 *
 */
public class AnualidadesHandler extends ScriptedDataSetEventAdapter {
 
	private List<Expediente> result = new ArrayList<Expediente>();
	private int index = 0;
	
	@Override
	public void open(IDataSetInstance dataSet) throws ScriptException {
		// TODO Auto-generated method stub
		super.open(dataSet);
		if (result != null) {
			index = 0;
		}
	}

	@Override
	public boolean fetch(IDataSetInstance dataSet, IUpdatableDataSetRow row)
			throws ScriptException {
		if (index >= result.size()) {
			return false;
		}
		row.setColumnValue("noExp", result.get(index).getNoExpediente());
		row.setColumnValue("nombre", result.get(index).getNombre());
		result.get(index).getCargoExpediente().getTotalCargosAnuales();
		result.get(index).getCargoExpediente().getCargosAnuales().getTotalCargosAnuales();
		index++;
		return true;
	}

	@Override
	public void beforeOpen(IDataSetInstance dataSet,
			IReportContext reportContext) throws ScriptException {
		super.beforeOpen(dataSet, reportContext);
		result = (List<Expediente>) reportContext.getParameterValue("listadoExpedientes");
		if (result == null || result.isEmpty()) {
			result = new ArrayList<Expediente>();
			Expediente e1 = new Expediente();
			e1.setNoExpediente("A001");
			e1.setNombre("Canaria01");
			Expediente e2 = new Expediente();
			e2.setNoExpediente("A002");
			e2.setNombre("Canaria02");
			result.add(e1);
			result.add(e2);
			reportContext.setParameterValue("listadoExpedientes", result);
		}
	}

}
