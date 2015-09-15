package rcp.assets.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;

public class Vista00 extends ViewPart {

	//public static final String ID = "rcp.assets.views.Vista00"; //$NON-NLS-1$
	public static final String ID = Class.class.getName();
	//public static final String ID = "rcp.assets.views.Vista00x";
	
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text txtWao;

	public Vista00() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = toolkit.createComposite(parent, SWT.NONE);
		toolkit.paintBordersFor(container);
		
		txtWao = toolkit.createText(container, "New Text", SWT.WRAP | SWT.MULTI);
		txtWao.setText("Pantalla con botones maravillosos para el usuario");
		txtWao.setBounds(0, 0, 136, 90);
		
		createActions();
		initializeToolBar();
		initializeMenu();
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
		IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager manager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
}
