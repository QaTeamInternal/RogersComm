package com.rogers.utilities

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Hashtable;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import java.text.SimpleDateFormat;

public class QueuePoster {

	public final static String SERVER="t3://den00aor.us.oracle.com:7011,den00aov.us.oracle.com:7011";
	public final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
	public final static String JMS_FACTORY = "oracle/communications/ordermanagement/osm/ExternalClientConnectionFactory"; // Connection Factory Name

	//insert the name of the request JMS queue

	public final static String ORDERXMLQUEUE = "oracle/communications/ordermanagement/WebServiceQueue";
	public final static String OMSNOTIFICATIONQUEUE = "jms/OMS.OSM.UDPATECPE.REQ.Q";
	private QueueConnectionFactory queueConnectionFactory;
	private QueueConnection queueConnection;
	private QueueSession queueSession;
	private QueueSender queueSender;
	private Queue queue;
	private TextMessage message;

	public void init(Context context, String queuename) throws NamingException, JMSException
	{
		queueConnectionFactory = (QueueConnectionFactory) context.lookup(JMS_FACTORY);
		queueConnection = queueConnectionFactory.createQueueConnection();
		queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		queue = (Queue) context.lookup(queuename);
		queueSender = queueSession.createSender(queue);
		message = queueSession.createTextMessage();
		queueConnection.start();
	}
	public void postOrderXML(Context context, String msg) throws JMSException, NamingException
	{
		message.setText(msg);
		message.setStringProperty("_wls_mimehdrContent_Type", "text/xml; charset=utf-8");
		message.setStringProperty("URI", "/osm/wsapi");
		queueSender.send(message);
	}

	public void postNotification(Context context, String notificationXMLString, String referenceID) throws JMSException, NamingException
	{
		message.setText(notificationXMLString);
		message.setJMSCorrelationID(referenceID+"02");
		queueSender.send(message);
	}

	public void close() throws JMSException
	{
		queueSender.close();
		queueSession.close();
		queueConnection.close();
	}
	public static String sendToServer(QueuePoster queuePoster, InitialContext initialContext, String XMLPath)
			throws IOException, JMSException, NamingException, TransformerException, SAXException, ParserConfigurationException
	{
		String inputXMLString="";
		String referenceID = "";
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			File xmlFile = new File(XMLPath);
			Document doc = builder.parse(xmlFile);
			StringWriter stringWriter = new StringWriter();
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(new DOMSource(doc), new StreamResult(stringWriter));
			inputXMLString = stringWriter.toString(); //This is string data of xml file

			String dateTime = new SimpleDateFormat("MMdd_HHmmss").format(Calendar.getInstance().getTime()).replaceAll("_", "");
			Random r = new Random();
			int randomInt = r.nextInt(9)+1;
			String randomString = Integer.toString(randomInt);
			String refID = randomString+dateTime;
			System.out.println("Unique Reference ID: "+refID);
			// Replacing existing refID with unique refID in the converted String
			inputXMLString = inputXMLString.replaceAll("11111111111", refID);
			referenceID = StringUtils.substringBetween(inputXMLString, "<key>", "</key>");
			System.out.println("Reference ID ==== "+referenceID);
		}
		catch (Exception e){
			e.getMessage();
		}

		queuePoster.postOrderXML(initialContext, inputXMLString);
		return referenceID;

	}

	public static void postOMSNotification(QueuePoster queuePoster, InitialContext initialContext, String XMLPath, String referenceID)
			throws IOException, JMSException, NamingException, TransformerException, SAXException, ParserConfigurationException
	{
		String notificationXMLString="";
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			File xmlFile = new File(XMLPath);
			Document doc = builder.parse(xmlFile);
			StringWriter stringWriter = new StringWriter();
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(new DOMSource(doc), new StreamResult(stringWriter));
			notificationXMLString = stringWriter.toString(); //This is string data of xml file

			// Update existing OrderActionID and ProductInstanceID in the notification XML
			notificationXMLString = notificationXMLString.replaceAll("11111111111", referenceID);
			String orderActionID = StringUtils.substringBetween(notificationXMLString, "<OrderActionID>", "</OrderActionID>");
			String productInstanceID = StringUtils.substringBetween(notificationXMLString, "<ProductInstanceID>", "</ProductInstanceID>");
			System.out.println("OrderActionID ==== "+orderActionID);
			System.out.println("ProductInstanceID ==== "+productInstanceID);
		}
		catch (Exception e){
			e.getMessage();
		}

		queuePoster.postNotification(initialContext, notificationXMLString, referenceID);

	}


	public static InitialContext getInitialContext() throws NamingException
	{
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
		env.put(Context.PROVIDER_URL,SERVER);
		return new InitialContext(env);
	}

	@Keyword
	public static String InjectOrder(String XMLPath) throws Exception
	{
		InitialContext initialContext = getInitialContext();
		QueuePoster queuePoster = new QueuePoster();
		queuePoster.init(initialContext, ORDERXMLQUEUE);
		String referenceID = sendToServer(queuePoster, initialContext, XMLPath);
		queuePoster.close();
		return referenceID;
	}

	@Keyword
	public static void InjectOMSNotificationForCPEUpdate(String referenceID, String XMLPath) throws Exception
	{
		InitialContext initialContext = getInitialContext();
		QueuePoster queuePoster = new QueuePoster();
		queuePoster.init(initialContext, OMSNOTIFICATIONQUEUE);
		postOMSNotification(queuePoster, initialContext, XMLPath, referenceID);
		queuePoster.close();
	}
}




