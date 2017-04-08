package cn.gz.lk.view;


import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Composite;

public class PhotoDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	int srcWidth=0;
	int srcHeight=0;
	String str="";
	Shell parent;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public PhotoDialog(Shell parent, int style) {
		
		super(parent, style);
		this.parent=parent;
		setText("�鿴ͼƬ");
	}
	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open(int srcHeight,int srcWidth,String str) {
		this.srcHeight=srcHeight;
		this.srcWidth=srcWidth;
		this.str=str;
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(srcHeight, srcWidth);
		shell.setText(getText());	
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, srcHeight, srcWidth);
		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setBounds(0, 0, srcHeight, srcWidth);
		lblNewLabel_1.setImage(SWTResourceManager.getImage(str));
	}
}
