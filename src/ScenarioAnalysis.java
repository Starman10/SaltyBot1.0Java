/**
 * @author Jackson Beard
 *
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ScenarioAnalysis
{
	public ScenarioAnalysis()
	{
		
	}

	public static void main(String[] args) throws Exception 
	{
		try {
            URL google = new URL("http://s0.2mdn.net/instream/video/client.js");
            BufferedReader in = new BufferedReader(new InputStreamReader(google.openStream()));
            String inputLine; 
 
            while ((inputLine = in.readLine()) != null) {
                // Process each line.
                System.out.println(inputLine);
            }
            in.close(); 
 
        } catch (MalformedURLException me) {
            System.out.println(me); 
 
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}