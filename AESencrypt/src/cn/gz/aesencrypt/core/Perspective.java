package cn.gz.aesencrypt.core;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import cn.gz.lk.view.MenuView;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.addView(MenuView.ID, layout.LEFT, 1f, layout.getEditorArea());
		layout.setEditorAreaVisible(false);
	}
}
