package cn.gz.lk.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Menu extends ViewPart {

	public static final String ID = "cn.gz.lk.view.Menu"; //$NON-NLS-1$

	public Menu() {
	}

	/**
	 * Create contents of the view part.
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setBounds(0, 0, 185, 469);
		
		Button button = new Button(composite, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(MenuView.ID);
			}
		});
		button.setBounds(37, 46, 116, 27);
		button.setText("\u52A0\u5BC6");
		
		Button button_1 = new Button(composite, SWT.NONE);
		button_1.setText("\u89E3\u5BC6");
		button_1.setBounds(37, 123, 116, 27);

	}

	@Override
	public void setFocus() {
		// set the focus
	}
}
