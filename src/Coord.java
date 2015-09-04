import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;

public class Coord
{
	private double xmax, ymax;
    //protected Point P_10, P_20, P_30, P_40, P_63, P_60, P_70, P_80, P_90, ALLIN = new Point();
	//private double xpad = 0;
	//private double ypad = 105;
	protected ArrayList<Double> xButtonArray = new ArrayList<Double>();
	protected int yButtonVal, leftButtonXVal, rightButtonXVal, competeButtonsYVal;
	private double xButtonCalc;
	private final double xStartRatio = .1241045845;
	//private final double intervalRatio = .0808861826;
	private final double intervalRatio = .086;
	//private final double competButtonSizeRatio = 0.20729166666666666666666666666667;
	private final double leftButtonXRatio = .3697482639;
	private final double rightButtonXRatio = .6312282986;
	public String saltyBetWeb = "http://www.saltyBet.com";
	private int interval;
	private GraphicsEnvironment graphEnvCoord;
	private Rectangle screenRect;
	private BufferedImage capture;
	private Robot screenie;
	private String xPath;
	private int initialMoney;
	public File outputScreen;
	//You must use the screenCheck method to generate objects.
	//The Coord constructor instantiates a coord object with coordinates stored for the buttons 10-90% and all in
	public static Coord screenCheck(boolean checkBox, int xDim, int yDim) throws IOException, AWTException, NumberFormatException, XPathExpressionException
	{
		
			SaltyBotUI.getLogger().info("Checking Dimensions");
			if(checkBox)
			{
				SaltyBotUI.getLogger().info("Attempting to set screen dimensions..");
			
				if(yDim<=1080 && xDim<=1920)
				{
					SaltyBotUI.getLogger().info("Screen Check Passed, creating screenshot");
					return new Coord(xDim, yDim);
				}
				else
				{
					SaltyBotUI.getLogger().info("Error: Invalid Screen width/height. Please enter values within the 1920x1080 range");
				}
			}
			else
			{
				SaltyBotUI.getLogger().info("Force Screen Dimensions not checked, auto identifying screen");
				return new Coord();
			}
		SaltyBotUI.getLogger().info("Something Went Wrong in ScreenCheck, Returning null");
		return null;
	}
	
	
	private Coord(double xUser, double yUser) throws IOException, AWTException, NumberFormatException, XPathExpressionException
	{
		outputScreen = new File("screenImage.png");
		xPath = "/html/body/div[@id='wrapper']/div[@id='bottomcontent']/form[@id='fightcard']/div[@id='balancewrapper']/span[@class='dollar greentext']/span[@id='balance']";
		screenie = new Robot();
		xmax = xUser;
		ymax = yUser;
		leftButtonXVal = (int) (xmax * leftButtonXRatio);
		rightButtonXVal = (int) (xmax * rightButtonXRatio);
		competeButtonsYVal = (int) (ymax-150);
		xButtonCalc = xmax*xStartRatio;
		interval = (int) Math.floor(xmax*intervalRatio);
		screenRect = new Rectangle((int)xUser, (int)yUser);
		yButtonVal =  (int) ymax-63;
		capture = screenie.createScreenCapture(screenRect);
		ImageIO.write(capture, "png", outputScreen);
		
		//XPathOps xEval = new XPathOps("Http://www.SaltyBet.com", xPath);
		//initialMoney = Integer.parseInt(xEval.getXpathQueryResults());
		
		//SaltyBotUI.getLogger().info("Initial Money: " + initialMoney);
		
        for(int j = 0; j < 10; j++)
        {
        	xButtonArray.add(j, (xButtonCalc) + (interval*j));
        }
	}
	
	private Coord() throws IOException, AWTException, NumberFormatException, XPathExpressionException
	{		
		outputScreen = new File("screenImage.png");
		xPath = "/html/body/div[@id='wrapper']/div[@id='bottomcontent']/form[@id='fightcard']/div[@id='balancewrapper']/span[@class='dollar greentext']/span[@id='balance']";
		graphEnvCoord = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] screens = graphEnvCoord.getScreenDevices();
		screenRect = new Rectangle();
		for (GraphicsDevice screen : screens) 
		{
		    Rectangle screenBounds = screen.getDefaultConfiguration().getBounds();

		    screenRect.width += screenBounds.width;
		    screenRect.height = Math.max(screenRect.height, screenBounds.height);
		}
		screenie = new Robot();
		//screenRect = Toolkit.getDefaultToolkit().getScreenSize();
		xmax = screenRect.getWidth();
		ymax = screenRect.getHeight();
		xButtonCalc = xmax*xStartRatio;
		interval = (int) Math.floor(xmax*intervalRatio);
		yButtonVal =  (int) ymax-63;
		leftButtonXVal = (int) (xmax * leftButtonXRatio);
		rightButtonXVal = (int) (xmax * rightButtonXRatio);
		competeButtonsYVal = (int) (ymax-150);
		capture = screenie.createScreenCapture(screenRect);
		ImageIO.write(capture, "png", outputScreen);
		
		//XPathOps xEval = new XPathOps("Http://www.SaltyBet.com", xPath);
		//initialMoney = Integer.parseInt(xEval.getXpathQueryResults());
		
		//SaltyBotUI.getLogger().info("Initial Money: " + initialMoney);
		
        for(int j = 0; j < 10; j++)
        {
        	xButtonArray.add(j, (xButtonCalc) + (interval*j));
        }
	}
	
	
	public void screenCapture() throws IOException
	{
		capture = screenie.createScreenCapture(screenRect);
		ImageIO.write(capture, "png", outputScreen);
	}
	
	public BufferedImage getCurrentScreenCap() throws IOException
	{
		screenCapture();
		return capture;
	}
	
	/**
	 * @return the xmax
	 */
	public double getXmax() 
	{
		return xmax;
	}


	/**
	 * @param xmax the xmax to set
	 */
	public void setXmax(double xmax) 
	{
		this.xmax = xmax;
	}


	/**
	 * @return the ymax
	 */
	public double getYmax() 
	{
		return ymax;
	}


	/**
	 * @param ymax the ymax to set
	 */
	public void setYmax(double ymax) 
	{
		this.ymax = ymax;
	}


	/**
	 * @return the competeButtonsYVal
	 */
	public int getCompeteButtonsYVal() {
		return competeButtonsYVal;
	}

	/**
	 * @return the yButtonVal
	 */
	public int getYButtonVal() {
		return yButtonVal;
	}

	/**
	 * @return the leftButtonXVal
	 */
	public int getLeftButtonXVal() {
		return leftButtonXVal;
	}

	/**
	 * @return the rightButtonXVal
	 */
	public int getRightButtonXVal() {
		return rightButtonXVal;
	}

	public int getYCoord()
	{
		return yButtonVal;
	}
	
	public int getXCoord(int identifier)
	{
		
		return (int)((double) xButtonArray.get(identifier));
	}
	
	public int getInitialMoney()
	{
		return initialMoney;
	}
}
