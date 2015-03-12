import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Point;


public class SaltyBotUI {

	protected Shell shlSaltybetBotV;
	private Combo combo;
	private Combo combo_1;
	private Label lblPercent;
	private Label lblStyle;
	private Button btnGo;
	private Button btnStop;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SaltyBotUI window = new SaltyBotUI();
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
		shlSaltybetBotV.open();
		shlSaltybetBotV.layout();
		while (!shlSaltybetBotV.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlSaltybetBotV = new Shell();
		shlSaltybetBotV.setMinimumSize(new Point(200, 150));
		shlSaltybetBotV.setSize(201, 154);
		shlSaltybetBotV.setText("SaltyBet Bot V 1.0");
		shlSaltybetBotV.setLayout(null);
		
		combo = new Combo(shlSaltybetBotV, SWT.READ_ONLY);
		combo.setBounds(71, 10, 91, 23);
		
		combo_1 = new Combo(shlSaltybetBotV, SWT.READ_ONLY);
		combo_1.setBounds(71, 47, 91, 23);
		
		lblPercent = new Label(shlSaltybetBotV, SWT.NONE);
		lblPercent.setBounds(10, 13, 55, 15);
		lblPercent.setText("Percent:");
		
		lblStyle = new Label(shlSaltybetBotV, SWT.NONE);
		lblStyle.setBounds(10, 50, 55, 15);
		lblStyle.setText("Style:");
		
		btnGo = new Button(shlSaltybetBotV, SWT.NONE);
		btnGo.setBounds(10, 85, 75, 25);
		btnGo.setText("Go");
		
		btnStop = new Button(shlSaltybetBotV, SWT.NONE);
		btnStop.setBounds(91, 85, 75, 25);
		btnStop.setText("Stop");

	}
}
