/**
 * @author Jackson Beard
 *
 */
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.io.IOException;
import java.util.Random;
import java.awt.event.*;

public class MouseActions 
{
	private static Robot mousey = null;
	private static Random gen = new Random();
	private static int buttonID = InputEvent.BUTTON1_DOWN_MASK;
	private static GraphicsEnvironment gE;
	
	//Click method clicks mouse button once, accounts for human delay and mouse movement within method
	public static void click(int xC, int yC) throws IllegalArgumentException, IOException
	{
		try
		{
			gE = GraphicsEnvironment.getLocalGraphicsEnvironment();
			mousey= new Robot(gE.getDefaultScreenDevice());
		}
		catch (Exception exc) 
		{
			   System.err.println("Error instantiating robot class: " + exc);
		}
		
		mousey.mouseMove(xC, yC);
		mousey.delay(gen.nextInt(320)+80);
		mousey.mousePress(buttonID);
		mousey.delay(gen.nextInt(320)+80);
		mousey.mouseRelease(buttonID);
	}
	//Double click clicks twice, and doesn't have timing since click accounts for it.
	public static void doubleClick(int xCD, int yCD) throws IllegalArgumentException, IOException
	{
		click(xCD, yCD);
		click(xCD, yCD);
	}
	//Moves the mouse to a defined position
	public static void setMousePos(int xM, int yM) throws IllegalArgumentException, IOException
	{
		try
		{
			mousey = new Robot();
		}
		catch (Exception exc) 
		{
			   System.err.println("Error instantiating robot class: " + exc);
		}
		mousey.delay(gen.nextInt(320)+80);
		mousey.mouseMove(xM, yM);
	}
}
