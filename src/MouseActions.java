/**
 * @author Jackson Beard
 *
 */
import java.awt.Robot;
import java.io.IOException;
import java.awt.event.*;
import cz.pscheidl.randomOrg.RandomOrg;

public class MouseActions 
{
	private static Robot mousey = null;
	private static RandomOrg gen = new RandomOrg();
	private static int buttonID = InputEvent.BUTTON1_DOWN_MASK;
	
	//Click method clicks mouse button once, accounts for human delay and mouse movement within method
	public static void click(int xC, int yC) throws IllegalArgumentException, IOException
	{
		try
		{
			mousey = new Robot();
		}
		catch (Exception exc) 
		{
			   System.err.println("Error instantiating robot class: " + exc);
		}
		mousey.mouseMove(xC, yC);
		mousey.delay(gen.getSingleRandomInt(80, 400));
		mousey.mousePress(buttonID);
		mousey.delay(gen.getSingleRandomInt(80, 400));
		mousey.mouseRelease(buttonID);
	}
	
	public static void doubleClick(int xCD, int yCD) throws IllegalArgumentException, IOException
	{
		click(xCD, yCD);
		click(xCD, yCD);
	}
}
