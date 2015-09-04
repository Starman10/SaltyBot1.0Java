/**
 * @author Jackson Beard
 *
 */
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Betting
{
	protected final Color percentButtonColor = new Color(100, 65, 165);
	protected static final Color betLeftRedButtonColor = new Color(176, 68, 68);
	protected final Color betRightBlueButtonColor = new Color (52, 158, 255);
	private static int betDeterminer;
	private static Random gen = new Random();
	public static BufferedImage currentScreenCap;
	private static int percentVal;
	
	// in all three of the bet xxxx methods, the percentage is used as an identifier to get
	// the correct Coord object coordinates. Each Coord Object has an array valued 0-9 (10 total)
	// the int we feed to its object methods act as pointers to those array values.
	private static void betLeft(Coord cLeft, int leftPercent) throws IllegalArgumentException, IOException
	{
		if(leftPercent != 0)
		{
			percentVal = (leftPercent+1)*10;
		}
		else
			percentVal = 10;
		
		SaltyBotUI.getLogger().info("Betting Red/Left: " + percentVal + "%");
		MouseActions.setMousePos(cLeft.getXCoord(leftPercent), cLeft.getYCoord());
		MouseActions.doubleClick(cLeft.getXCoord(leftPercent), cLeft.getYCoord());
		
		MouseActions.setMousePos(cLeft.getLeftButtonXVal(), cLeft.getCompeteButtonsYVal());
		MouseActions.doubleClick(cLeft.getLeftButtonXVal(), cLeft.getCompeteButtonsYVal());
	}
	
	private static void betRight(Coord cRight, int rightPercent) throws IllegalArgumentException, IOException
	{
		if(rightPercent != 0)
		{
			percentVal = (rightPercent+1)*10;
		}
		else
			percentVal = 10;
		
		SaltyBotUI.getLogger().info("Betting Blue/right: " + percentVal + "%");
		MouseActions.setMousePos(cRight.getXCoord(rightPercent), cRight.getYCoord());
		MouseActions.doubleClick(cRight.getXCoord(rightPercent), cRight.getYCoord());
		
		MouseActions.setMousePos(cRight.getRightButtonXVal(), cRight.getCompeteButtonsYVal());
		MouseActions.doubleClick(cRight.getRightButtonXVal(), cRight.getCompeteButtonsYVal());
	}
	
	private static void betRandom(Coord cRand, int randomPercent) throws IllegalArgumentException, IOException
	{
		SaltyBotUI.getLogger().info("Deciding which side to bet on...");
		MouseActions.setMousePos(cRand.getXCoord(randomPercent), cRand.getYCoord());
		MouseActions.doubleClick(cRand.getXCoord(randomPercent), cRand.getYCoord());
		betDeterminer = gen.nextInt(2);
		if(randomPercent != 0)
		{
			percentVal = (randomPercent+1)*10;
		}
		else
			percentVal = 10;
		if(betDeterminer == 1)
		{
			SaltyBotUI.getLogger().info("Betting Red/Left: " + percentVal + "%");
			betLeft(cRand, randomPercent);
		}
		else
		{
			SaltyBotUI.getLogger().info("Betting Blue/Right: " + percentVal + "%");
			betRight(cRand, randomPercent);
		}
	}
	
	//One method for use when the user wants random percentages, one without
	
	public static void bet(Coord c, String style, int percent) throws IllegalArgumentException, IOException
	{
		switch (style)
		{
		case "Random": betRandom(c, percent);
			break;
		case "Always Right": betRight(c, percent);
			break;
		case "Always Left": betLeft(c, percent);
			break;
		default: 
			SaltyBotUI.getLogger().info("Something went wrong, you aren't betting today.");
			return;
		}
	}
	
	public static boolean evalForBetting(Coord cEval) throws IOException
	{
		currentScreenCap = cEval.getCurrentScreenCap();
		SaltyBotUI.getLogger().info("looking for color: " + betLeftRedButtonColor.getRGB());
		SaltyBotUI.getLogger().info("X Coord:" + cEval.getLeftButtonXVal() + " Y Coord:" + cEval.getCompeteButtonsYVal());
		SaltyBotUI.getLogger().info("Comparing Colors of Left Competitior Button and actual value" + currentScreenCap.getRGB(cEval.getLeftButtonXVal(), cEval.getCompeteButtonsYVal()));
		if(currentScreenCap.getRGB(cEval.getLeftButtonXVal(), cEval.getCompeteButtonsYVal()) == -5225404)
			{
			SaltyBotUI.getLogger().info("Identified betting as active");
			return true;
			}
		else
			{
				SaltyBotUI.getLogger().info("Betting not active");
				return false;
			}
	}
	
	public static void betRandomPercent(Coord c, String style) throws IllegalArgumentException, IOException
	{
		SaltyBotUI.getLogger().info("Betting a random percent");
		switch (style)
		{
		case "Random": betRandom(c, gen.nextInt(10));
			break;
		case "Always Right": betRight(c, gen.nextInt(10));
			break;
		case "Always Left": betLeft(c, gen.nextInt(10));
			break;
		default: 
			SaltyBotUI.getLogger().info("Something went wrong, you aren't betting today.");
			return;
		}
	}
}
