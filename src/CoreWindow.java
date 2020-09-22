import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import org.eclipse.swt.graphics.Image;

import org.eclipse.swt.*;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.widgets.Text;
//import org.eclipse.swt.events.ModifyListener;
//import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.widgets.Button;


public class CoreWindow {

	protected Shell shell;
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CoreWindow window = new CoreWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
//		Image LUL = new Image(display,"thonkhmm.jpg");
//		shell.setImage(LUL);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(shell);
		shell.setSize(450, 300);
		shell.setText("hehe");
		
		Label lblClick = new Label(shell, SWT.SHADOW_NONE);
		Image out = SWTResourceManager.getImage("src\\img\\thonkhmm.gif");
		Image in = SWTResourceManager.getImage("src\\img\\thonkhmm.jpg");
		shell.setImage(in);
		lblClick.setImage(out);
		lblClick.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				lblClick.setImage(in);
			}
			@Override
			public void mouseExit(MouseEvent e) {
				lblClick.setImage(out);
			}
		});
		
		lblClick.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				MessageBox kek = new MessageBox(shell);
				kek.setText("WHAT");
				kek.setMessage("HAHA");
				kek.open();
			}
		});
		lblClick.setBounds(23, 27, 170, 150);
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(266, 70, 76, 21);
		
		Button btnNein = new Button(shell, SWT.NONE);
		btnNein.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				MessageBox kek = new MessageBox(shell);
				kek.setText("WHAT");
				kek.setMessage(Integer.toString(Integer.parseInt(text.getText()) + 10));
				kek.open();
			}
		});
		btnNein.setBounds(267, 159, 75, 25);
		btnNein.setText("nein");

	}
}
