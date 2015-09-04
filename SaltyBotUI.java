import java.awt.AWTException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.xpath.XPathExpressionException;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.wb.swt.SWTResourceManager;


public class SaltyBotUI 
{
	protected Shell shlSaltybetBotV;
	private static Coord userCoord;
	private Thread betThread;
	private Combo percentCombo;
	private Combo styleCombo;
	private Label lblPercent;
	private Label lblStyle;
	private Button btnGo;
	private Button btnStop;
	private Label lblWidth;
	private Label lblNewLabel;
	private Button btnForceScreenDimensions;
	private Text txtEnterHeight;
	private Text txtEnterWidth;
	private Label lblConsole;
	private File log;
	private Scanner scannie;
	private boolean goFlag;
	private Document updateDocument;
	int x = 0;
	private static Logger logger = Logger.getLogger("MyLog");  
	public FileHandler fh;
	private StyledText styledText;
	private TextViewer logView;
	private boolean  startFlag = false;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) 
	{
				try 
				{
					SaltyBotUI window = new SaltyBotUI();
					window.open();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
	}

	/**
	 * Open the window.
	 */
	public void open() 
	{
		Display display = Display.getDefault();
		createContents(display);
		shlSaltybetBotV.open();
		shlSaltybetBotV.layout();
		while (!shlSaltybetBotV.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
			
		}
		display.dispose();
	}
	
	public static Logger getLogger()
	{
		return logger;
	}
	
	public void verifyTextMethod(VerifyEvent verif) 
	{
		Text text = (Text) verif.getSource();
		
		final String oldText = text.getText();
		String newText = oldText.substring(0, verif.start) + verif.text + oldText.substring(verif.end);
		
		boolean isInt = true;
		try
		{
			Integer.parseInt(newText);
		}
		catch(NumberFormatException ex)
		{
			isInt = false;
		}
		if(!isInt)
			verif.doit = false;
	}
	
	public void doBetting(int styleIndex, int percentIndex, String percentText, String styleText, boolean check, int height, int width)
	{
		class doBettingTask implements Runnable 
		{
			public void run()
			{
				updateLog("Verifying selections...");
				if(styleIndex !=(-1) && percentIndex != (-1))
				{
					updateLog("Checking Forced Dimensions");
					
					if (check)
					{
						try 
						{
							userCoord = Coord.screenCheck(check, height, width);
						} 
						catch (NumberFormatException | IOException | AWTException | XPathExpressionException e1) 
						{
							e1.printStackTrace();
							return;
						} 

						while(goFlag)
						{	
							try
							{
								updateLog("waiting 5 seconds before trying to bet");
								while(x< 51 && goFlag)
								{
									Thread.sleep(100);
									x++;
								}
								x=0;
							} 
							catch (InterruptedException e) 
							{
								e.printStackTrace();
							}

							try {
								if (Betting.evalForBetting(userCoord))
								{
									updateLog("betting...");
									if("Random".equals(percentText))
									{
										try
										{
											Betting.betRandomPercent(userCoord, styleText);
											updateLog("Waiting 40 seconds before betting again.");
											while(x< 401 && goFlag)
											{
												Thread.sleep(2000);
												x++;
											}
											x = 0;
										} 
										catch (IllegalArgumentException| IOException e1) 
										{
											e1.printStackTrace();
										} 
										catch (InterruptedException e) 
										{
											e.printStackTrace();
										}
									}
									else
									{
										try
										{
											Betting.bet(userCoord, styleText, percentIndex);
											updateLog("Waiting 40 seconds before betting again.");
											while(x< 401 && goFlag)
											{
												Thread.sleep(100);
												x++;
											}
											x = 0;
										} 
										catch (IllegalArgumentException| IOException e1) 
										{
											e1.printStackTrace();
										}
										catch (InterruptedException e) 
										{
											e.printStackTrace();
										}
									}
								}
							}
							catch (IOException e) 
							{
								e.printStackTrace();
							}
						}
					}
					else
					{
						updateLog("Forced Dimensions not selected, using alternate constructor");
						try 
						{
							userCoord = Coord.screenCheck(check, 2, 2);
						} 
						catch (NumberFormatException | IOException | AWTException | XPathExpressionException e1) 
						{
							e1.printStackTrace();
							//return;
						}
						while(goFlag)
						{
							try
							{
								updateLog("waiting 5 seconds before trying to bet");
								while(x< 51 && goFlag)
								{
									Thread.sleep(100);
									x++;
								}
								x=0;
							} 
							catch (InterruptedException e) 
							{
								e.printStackTrace();
							}

							try {
								if (Betting.evalForBetting(userCoord))
								{
									updateLog("betting...");
									if("Random".equals(percentText))
									{
										try
										{
											Betting.betRandomPercent(userCoord, styleText);
											updateLog("Waiting 40 seconds before betting again.");
											while(x< 401 && goFlag)
											{
												Thread.sleep(100);
												x++;
											}
											x = 0;
										} 
										catch (IllegalArgumentException| IOException e1) 
										{
											e1.printStackTrace();
										}
										catch (InterruptedException e) 
										{
											e.printStackTrace();
										}
									}
									else
									{
										try
										{
											Betting.bet(userCoord, styleText, percentIndex);
											updateLog("Waiting 40 seconds before betting again.");
											while(x< 401 && goFlag)
											{
												Thread.sleep(100);
												x++;
											}
											x = 0;
										} 
										catch (InterruptedException e1) 
										{				
											e1.printStackTrace();
										}

										catch (IllegalArgumentException| IOException e1) 
										{
											e1.printStackTrace();
										}
									}
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}				
				else
				{
					updateLog("Please Select A Style and percent");
					return;
				}
			}
		}
		updateLog("Starting betting in new thread");
		Thread betThread = new Thread(new doBettingTask());
		betThread.start();
	}
	
	public void updateLog(String updateString)
	{
		Display.getDefault().syncExec(new Runnable()
		{
			public void run() 
			{
				logger.info(updateString);
				String fileContents = "";
				try 
				{
					scannie = new Scanner(log);
				} 
				catch (FileNotFoundException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while(scannie.hasNextLine())
					fileContents += scannie.nextLine() + "\n";
				updateDocument.set(fileContents);
				logView.setDocument(updateDocument);
				return;
			}
		});

	}
	
	public void createLog()
	{
		 try 
		 {  
		     // configuring the logger, handler, and formatter 
			 DateFormat df1 = new SimpleDateFormat("dd_MM_yy");
			 String formattedDate1 = df1.format(new Date());
			 log = new File("Salty_bot_log_"+ formattedDate1 + System.currentTimeMillis());
			 //pathToLog = Paths.get(log.getCanonicalPath());
			 fh = new FileHandler(log.getName());  
			 SimpleFormatter formatter = new SimpleFormatter();  
		     fh.setFormatter(formatter); 
		     logger.addHandler(fh);
		     updateDocument = new Document();
		     updateLog("Salty Bet Bot V1.0 Log Initialized " + formattedDate1);
		 } 
		 catch (SecurityException e) 
		 {  
		        e.printStackTrace();  
		 } 
		 catch (IOException e) 
		 {  
		        e.printStackTrace();  
		 }  
	}
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents(Display display) {
		
		Executors.newSingleThreadScheduledExecutor();
		
		shlSaltybetBotV = new Shell();
		shlSaltybetBotV.setMinimumSize(new Point(350, 400));
		shlSaltybetBotV.setSize(377, 400);
		shlSaltybetBotV.setText("SaltyBet Bot V 1.0");
		GridLayout gl_shlSaltybetBotV = new GridLayout(2, true);
		gl_shlSaltybetBotV.horizontalSpacing = 8;
		gl_shlSaltybetBotV.marginWidth = 10;
		gl_shlSaltybetBotV.marginHeight = 10;
		shlSaltybetBotV.setLayout(gl_shlSaltybetBotV);
		
		lblStyle = new Label(shlSaltybetBotV, SWT.NONE);
		lblStyle.setBounds(10, 50, 55, 15);
		lblStyle.setText("Style:");
		lblStyle.pack();
		
		btnForceScreenDimensions = new Button(shlSaltybetBotV, SWT.CHECK);
		btnForceScreenDimensions.setBounds(180, 10, 163, 16);
		btnForceScreenDimensions.setText("Force Screen Dimensions?");
		btnForceScreenDimensions.pack();
		
		styleCombo = new Combo(shlSaltybetBotV, SWT.READ_ONLY);
		styleCombo.setToolTipText("Select a Value");
		styleCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		styleCombo.setItems(new String[] {"Always Left", "Always Right", "Random"});
		styleCombo.setBounds(71, 47, 91, 23);
		styleCombo.pack();
		//updateLog(Integer.toString(styleCombo.getSelectionIndex()));
		
		lblWidth = new Label(shlSaltybetBotV, SWT.NONE);
		lblWidth.setBounds(180, 63, 55, 15);
		lblWidth.setText("Width:");
		lblWidth.pack();
		
		lblPercent = new Label(shlSaltybetBotV, SWT.NONE);
		lblPercent.setBounds(10, 13, 55, 15);
		lblPercent.setText("Percent:");
		lblPercent.pack();
		
		txtEnterWidth = new Text(shlSaltybetBotV, SWT.BORDER);
		txtEnterWidth.addVerifyListener(new VerifyListener() 
		{
			public void verifyText(VerifyEvent e) 
			{
				verifyTextMethod(e);
			}
		});
		GridData gd_txtEnterWidth = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtEnterWidth.widthHint = 104;
		txtEnterWidth.setLayoutData(gd_txtEnterWidth);
		txtEnterWidth.setText("Enter Width");
		txtEnterWidth.setTextLimit(4);
		txtEnterWidth.setBounds(228, 62, 91, 21);
		txtEnterWidth.pack();
		
		percentCombo = new Combo(shlSaltybetBotV, SWT.READ_ONLY);
		percentCombo.setToolTipText("Select a Value");
		percentCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		percentCombo.setItems(new String[] {"10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "Random"});
		percentCombo.setBounds(71, 10, 91, 23);
		percentCombo.pack();
		//updateLog(Integer.toString(percentCombo.getSelectionIndex()));
		
		lblNewLabel = new Label(shlSaltybetBotV, SWT.NONE);
		lblNewLabel.setBounds(180, 30, 55, 15);
		lblNewLabel.setText("Height:");
		lblNewLabel.pack();
		new Label(shlSaltybetBotV, SWT.NONE);
		
		txtEnterHeight = new Text(shlSaltybetBotV, SWT.BORDER);
		txtEnterHeight.addVerifyListener(new VerifyListener() 
		{
			public void verifyText(VerifyEvent e) 
			{
					verifyTextMethod(e);
			}
		});
		GridData gd_txtEnterHeight = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtEnterHeight.widthHint = 104;
		txtEnterHeight.setLayoutData(gd_txtEnterHeight);
		txtEnterHeight.setText("Enter Height");
		txtEnterHeight.setTextLimit(4);
		txtEnterHeight.setBounds(228, 30, 91, 21);
		txtEnterHeight.pack();
		
		btnGo = new Button(shlSaltybetBotV, SWT.PUSH);
		btnGo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnGo.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				goFlag = true;
				if(!(txtEnterWidth.getText().equals("")) && (!(txtEnterHeight.getText().equals("")) && btnForceScreenDimensions.getSelection() == true))
				{
					doBetting(styleCombo.getSelectionIndex(), percentCombo.getSelectionIndex(), percentCombo.getText(), styleCombo.getText(), btnForceScreenDimensions.getSelection(), Integer.parseInt(txtEnterWidth.getText()), Integer.parseInt(txtEnterHeight.getText()));
				}
				else
				{
					doBetting(styleCombo.getSelectionIndex(), percentCombo.getSelectionIndex(), percentCombo.getText(), styleCombo.getText(), btnForceScreenDimensions.getSelection(), 2, 2);
				}

			}
		});
		btnGo.setText("Go");
		btnGo.pack();
		
		btnStop = new Button(shlSaltybetBotV, SWT.PUSH);
		btnStop.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				if(goFlag == true)
				{
					updateLog("Stop Requested, please wait.");
					goFlag = false;
				}
				else
					updateLog("You are trying to stop when nothing is running!");
			}
		});
		GridData gd_btnStop = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_btnStop.widthHint = 50;
		btnStop.setBounds(50, 85, 75, 25);
		btnStop.setText("Stop");
		btnStop.setLayoutData(gd_btnStop);
		btnStop.pack();
		
		
		lblConsole = new Label(shlSaltybetBotV, SWT.NONE);
		lblConsole.setBounds(10, 116, 55, 15);
		lblConsole.setText("Log:");
		lblConsole.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lblConsole.pack();
		new Label(shlSaltybetBotV, SWT.NONE);
		
		logView = new TextViewer(shlSaltybetBotV, SWT.BORDER | SWT.V_SCROLL | SWT.READ_ONLY);
		
		styledText = logView.getTextWidget();
//
		styledText.setMarginColor(SWTResourceManager.getColor(0, 0, 0));
		styledText.setBackground(SWTResourceManager.getColor(0, 0, 0));
		styledText.setForeground(SWTResourceManager.getColor(255,255,255));
		GridData gd_styledText = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_styledText.heightHint = 162;
		gd_styledText.widthHint = 266;
		styledText.setLayoutData(gd_styledText);
		
		if (startFlag == false)
		{
			createLog();
			startFlag = true;
		}
		
		//logView.setRedraw(true);
		//updateLog("Logging object instantiated.");
	}
}
