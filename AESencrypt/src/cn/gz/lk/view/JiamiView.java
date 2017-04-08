package cn.gz.lk.view;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Button;

public class JiamiView extends ViewPart {

	public static final String ID = "cn.gz.lk.view.JiamiView"; //$NON-NLS-1$

	public JiamiView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		
		Button button = new Button(container, SWT.NONE);
		button.setBounds(34, 72, 80, 27);
		button.setText("\u666E\u901A\u52A0\u5BC6");
		
		Button button_1 = new Button(container, SWT.NONE);
		button_1.setBounds(34, 175, 80, 27);
		button_1.setText("\u56FE\u7247\u52A0\u5BC6");

		createActions();
		initializeToolBar();
		initializeMenu();
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
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
}
