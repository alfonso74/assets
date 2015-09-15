package rcp.assets.facturacion;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import rcp.assets.views.NavigationView;
import rcp.assets.views.Vista00;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		/*
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);
		layout.addStandaloneView(NavigationView.ID, false, IPageLayout.LEFT, 0.2f, editorArea);
		layout.addView(Vista00.ID, IPageLayout.BOTTOM, 0.65f, editorArea);
		*/
	}
}
