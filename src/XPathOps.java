import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class XPathOps 
{
	private String xPathCode;
	private DocumentBuilderFactory betSumDocBuilder;
	private Document saltyBetDoc;
	private String activeURL;
	private String XMLEvalOfSite;
	private XPath moneyPath;
	private XPathExpression expr = null;
	private String stringForReturn;

	public XPathOps(String urlForUse, String XPa) throws IOException
	{
		xPathCode = XPa;
		activeURL = urlForUse;
		XMLEvalOfSite = getURLContents(activeURL);
		buildXml();
	}
	
	private String getURLContents(String myURL) throws IOException 
	{
		SaltyBotUI.getLogger().info("Checking Your Salty Bet Record...");
		StringBuilder stringBuild = new StringBuilder();
		HttpURLConnection sbConn = null;
		InputStreamReader inputReader = null;
		try 
		{
			URL url = new URL(myURL);
			sbConn = (HttpURLConnection) url.openConnection();
			sbConn.addRequestProperty("User-Agent", "Mozilla/4.76"); 
			
			if (sbConn != null)
				sbConn.setReadTimeout(60 * 1000);
			if (sbConn != null && sbConn.getInputStream() != null) 
			{
				inputReader = new InputStreamReader(sbConn.getInputStream(), Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(inputReader);
				if (bufferedReader != null) 
				{
					int count;
					while ((count = bufferedReader.read()) != -1) 
					{
						stringBuild.append((char) count);
					}
					bufferedReader.close();
				}
			}
			inputReader.close();
		} 
		catch (IOException | RuntimeException e) 
		{
			e.printStackTrace();
		}
 
		return stringBuild.toString();
	}
	
	public void setXPathQuey(String newXPa)
	{
		xPathCode = newXPa;
	}
	
	public void setURL(String newUrl) throws IOException
	{
		XMLEvalOfSite = getURLContents(newUrl);
	}
	
	public void updateEval() throws IOException
	{
		XMLEvalOfSite = getURLContents(activeURL);
		buildXml();
	}
	
	private void buildXml()
	{
		betSumDocBuilder = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		try 
		{
		    builder = betSumDocBuilder.newDocumentBuilder();
		} 
		catch (ParserConfigurationException e) 
		{
		    e.printStackTrace();  
		}
		
		try 
		{
		    saltyBetDoc = builder.parse(new InputSource(new StringReader(XMLEvalOfSite)));
		    moneyPath =  XPathFactory.newInstance().newXPath();
		    expr = moneyPath.compile(xPathCode);
		    //initialMoney = (int) moneyPath.evaluate(saltyBetDoc, XPathConstants.STRING).toString();
		    //SaltyBotUI.getLogger().info("Initial Money: "+ initialMoney);
		} 
		catch (SAXException | NullPointerException e) 
		{
		    e.printStackTrace();
		} 
		catch (IOException e) 
		{
		    e.printStackTrace();
		} 
		catch (XPathExpressionException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getXpathQueryResults() throws XPathExpressionException, IOException
	{
		updateEval();
		stringForReturn = expr.evaluate(saltyBetDoc, XPathConstants.STRING).toString();
		return stringForReturn;
	}
}
