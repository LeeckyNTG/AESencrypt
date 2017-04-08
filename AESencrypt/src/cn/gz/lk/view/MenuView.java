package cn.gz.lk.view;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import cn.gz.lk.tools.AES;
import cn.gz.lk.tools.Tool;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.browser.Browser;

public class MenuView extends ViewPart {

	public static final String ID = "cn.gz.lk.view.MenuView"; //$NON-NLS-1$
	Label label;
	Label label_1;
	Label label_2;
	int srcWidth=0;
	int srcHeight=0;
	String str;
	
	int photoNum=34;
	AES aes=new AES();
	Tool tool=new Tool();
	FileOutputStream fil;
	FileOutputStream fil1;
	byte[] decryptData;
	byte [] content;
	String src;
	Key k;
	String hz;
	String wenjianName;
	byte [] jiami;
	public MenuView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		
		Button button = new Button(container, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				FileDialog fd = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.NONE);//打开文件
				str=fd.open();//获取文件路径
				hz=str.substring(str.lastIndexOf('.'));
				wenjianName=str.substring(str.lastIndexOf('\\')+1,str.lastIndexOf('.'));
				//System.out.println(wenjianName);
				BufferedImage src;
				try {
					InputStream is=new FileInputStream(str);
					src = javax.imageio.ImageIO.read(is);
				
					srcWidth = src.getWidth(null); //得到源图宽
					srcHeight = src.getHeight(null); //得到源图长
					//System.out.println(srcWidth+"*"+srcHeight);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} //构造Image对象
				label.setImage(SWTResourceManager.getImage(str));
			}
		});
		button.setBounds(148, 308, 80, 27);
		button.setText("\u9009\u62E9\u56FE\u7247");
		
		label = new Label(container, SWT.NONE);
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				
				PhotoDialog ae=new PhotoDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.CLOSE|SWT.MAX|SWT.MIN|SWT.APPLICATION_MODAL);	
				if (str!=null&&str.length()>0) {
					ae.open(srcHeight,srcWidth,str);
				}else {
					MessageBox mBox=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
					mBox.setText("系统消息");
					mBox.setMessage("请先选取文件！");
					mBox.open();
				}
				
			}
		});
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
		label.setBounds(68, 41, 162, 249);

		Button button_1 = new Button(container, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				
				
				try {
					jiami();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		
			}
		});
		button_1.setBounds(386, 308, 80, 27);
		button_1.setText("\u52A0\u5BC6");
		
		label_1 = new Label(container, SWT.NONE);
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		label_1.setBounds(304, 41, 162, 249);
		label_2 = new Label(container, SWT.NONE);
		label_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				PhotoDialog ae=new PhotoDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.CLOSE|SWT.MAX|SWT.MIN|SWT.APPLICATION_MODAL);	
				String src1=src+"/解密后"+wenjianName+hz;
				if (decryptData!=null&&decryptData.length>0) {
					ae.open(srcHeight,srcWidth,src1);	
				}else {
					MessageBox mBox=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
					mBox.setText("系统消息");
					mBox.setMessage("请先解密文件！");
					mBox.open();
				}
					
			}
		});
		label_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_MAGENTA));
		label_2.setBounds(540, 41, 162, 249);
		
		Button button_2 = new Button(container, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					jiemi();
					label_2.setImage(SWTResourceManager.getImage(src+"/解密后"+wenjianName+hz));
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_2.setText("\u89E3\u5BC6");
		button_2.setBounds(622, 308, 80, 27);
		
		Button button_3 = new Button(container, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(Menu.ID);
			}
		});
		button_3.setBounds(370, 406, 80, 27);
		button_3.setText("\u6253\u5F00\u83DC\u5355");

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
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}
	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}
	@Override
	public void setFocus() {
		// Set the focus
	}
	
	byte[] encryptData1;
	/*public  void jiami() throws Exception {
		AES aes=new AES();
		byte[] key = aes.initSecretKey();//通过给定的算法呢，由该内部的算法实现密钥的初始化
		k = aes.toKey(key);//将byte类型的密钥转换成Key对象。
		Tool tool=new Tool();
		File img=new File(str);
		src=img.getParent();
		String imgString=img.toString();
		FileInputStream inputStream=new FileInputStream(img);		
		DataInputStream dis=new DataInputStream(inputStream);
		FileOutputStream fil1=new FileOutputStream(src+"/加密后"+wenjianName+hz);		
		content = tool.getImageOrFile(imgString);
		
		jiami=new byte[content.length-photoNum];
		
		for(int i=photoNum,j=0;i<content.length;i++,j++){
			jiami[j]=content[i];
		}
		encryptData1 = aes.encrypt(jiami, k);// 获取加密后的数据

		fil1.write(content,0,photoNum);
		fil1.write(encryptData1,0,encryptData1.length);	
		MessageBox mBox=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		mBox.setText("系统消息");
		mBox.setMessage("加密完成，已经生成加密文件！");
		mBox.open();
		inputStream.close();
		fil1.close();
	}*/
	public  void jiami() throws Exception {
		AES aes=new AES();
		byte[] key = aes.initSecretKey();//通过给定的算法呢，由该内部的算法实现密钥的初始化
		k = aes.toKey(key);//将byte类型的密钥转换成Key对象。
		Tool tool=new Tool();
		File img=new File(str);
		src=img.getParent();
		String imgString=img.toString();
		FileInputStream inputStream=new FileInputStream(img);		
		DataInputStream dis=new DataInputStream(inputStream);
		FileOutputStream fil1=new FileOutputStream(src+"/加密后"+wenjianName+hz);		
		content = tool.getImageOrFile(imgString);
		jiami=new byte[content.length-photoNum];
		
		for(int i=photoNum,j=0;i<content.length;i++,j++){
			jiami[j]=content[i];
		}
		encryptData1 = aes.encrypt(jiami, k);// 获取加密后的数据

		fil1.write(content,0,photoNum);
		fil1.write(encryptData1,58,encryptData1.length);	
		MessageBox mBox=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		mBox.setText("系统消息");
		mBox.setMessage("加密完成，已经生成加密文件！");
		mBox.open();
		inputStream.close();
		fil1.close();
	}
	public  void jiemi() throws Exception {
		if (encryptData1!=null&&encryptData1.length>0) {
			AES aes=new AES();	
			fil=new FileOutputStream(src+"/解密后"+wenjianName+hz);
			decryptData =aes.decrypt(encryptData1, k);
			fil.write(content,0,photoNum);
			fil.write(decryptData,0,decryptData.length);
			fil.close();
		}else {
			MessageBox mBox=new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
			mBox.setText("系统消息");
			mBox.setMessage("还没有选取需要加密的文件！");
			mBox.open();
		}
	}
}
