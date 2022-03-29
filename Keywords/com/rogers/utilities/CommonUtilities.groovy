package com.rogers.utilities

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.util.concurrent.TimeUnit
import java.util.function.Function

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
//import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
public class CommonUtilities
{


	@Keyword
	def OpenBrowserAndMaximize()
	{
		//		WebUI.openBrowser('')
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\ppmukher\\chromedriver.exe")
		ChromeOptions options = new ChromeOptions()
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		WebDriver driver = new ChromeDriver(options)
		DriverFactory.changeWebDriver(driver)
		WebUI.maximizeWindow()

	}

	@Keyword
	def Login()
	{
		WebUI.navigateToUrl(GlobalVariable.url)
		WebUI.setText(findTestObject('Page_Login/input_Username_j_username'), GlobalVariable.username)
		WebUI.setEncryptedText(findTestObject('Page_Login/input_Password_j_password'), GlobalVariable.password)
		WebUI.click(findTestObject('Page_Login/input_Password_submit'))

	}

	@Keyword
	def Logout()
	{
		WebUI.click(findTestObject('Page_Worklist/a_Logout oms-automation'))
		WebUI.closeBrowser()
	}


	@Keyword
	def SearchOrderInWorkList(String referenceID)
	{
		WebUI.waitForPageLoad(10)
		WebUI.click(findTestObject('Page_Worklist/img_worklistbutton'))
		WebUI.setText(findTestObject('Page_Worklist/input_Reference_referenceFilter'), referenceID)
		WebUI.click(findTestObject('Page_Worklist/input_Logout oms-automation_button'))

	}

	@Keyword
	def ActivateHSIUpdateWithSuccess()
	{
		WebUI.click(findTestObject('Test1/Page_Worklist/Refresh Button'))
		WebUI.waitForElementPresent(findTestObject('Page_Worklist/input_Version_move'), 20, FailureHandling.STOP_ON_FAILURE)
		WebUI.click(findTestObject('Page_Worklist/input_Version_move'))
		WebUI.selectOptionByValue(findTestObject('Page_Order Editor/span_Update'), '12710', true)
		WebUI.click(findTestObject('Page_Order Editor/span_Update'))

	}

	@Keyword
	def ActivateHSIUpdateWithFailure()
	{
		// Wait for Page to Load
		WebUI.waitForPageLoad(15, FailureHandling.STOP_ON_FAILURE)

		// Actions on Activation Response List
		WebUI.click(findTestObject('Page_Worklist/input_Version_move'))
		WebUI.waitForElementPresent(findTestObject('ActivateHSIWithFailureTasks/Activation_Response_List/Textbox'), 20, FailureHandling.STOP_ON_FAILURE)
		WebUI.sendKeys(findTestObject('ActivateHSIWithFailureTasks/Activation_Response_List/Textbox'), "1")
		WebUI.waitForElementPresent(findTestObject('ActivateHSIWithFailureTasks/Activation_Response_List/Plus_Button'), 10, FailureHandling.STOP_ON_FAILURE)
		WebUI.click(findTestObject('ActivateHSIWithFailureTasks/Activation_Response_List/Plus_Button'))

		// Actions on Event Data List
		WebUI.waitForElementPresent(findTestObject('ActivateHSIWithFailureTasks/Event_Data_List/Textbox'), 10, FailureHandling.STOP_ON_FAILURE)
		WebUI.sendKeys(findTestObject('ActivateHSIWithFailureTasks/Event_Data_List/Textbox'), "1")
		WebUI.waitForElementPresent(findTestObject('ActivateHSIWithFailureTasks/Event_Data_List/Plus_Button'), 10, FailureHandling.STOP_ON_FAILURE)
		WebUI.click(findTestObject('ActivateHSIWithFailureTasks/Event_Data_List/Plus_Button'))

		// Actions on Reason State List
		WebUI.waitForElementPresent(findTestObject('ActivateHSIWithFailureTasks/Reason_List/Textbox'), 10, FailureHandling.STOP_ON_FAILURE)
		WebUI.sendKeys(findTestObject('ActivateHSIWithFailureTasks/Reason_List/Textbox'), "1")
		WebUI.waitForElementPresent(findTestObject('ActivateHSIWithFailureTasks/Reason_List/Plus_Button'), 10, FailureHandling.STOP_ON_FAILURE)
		WebUI.click(findTestObject('ActivateHSIWithFailureTasks/Reason_List/Plus_Button'))
		WebUI.waitForElementPresent(findTestObject('ActivateHSIWithFailureTasks/Reason_List/Message_Textbox'), 10, FailureHandling.STOP_ON_FAILURE)
		WebUI.sendKeys(findTestObject('ActivateHSIWithFailureTasks/Reason_List/Message_Textbox'), "orderFailEvent.BACC-20016E:Invalid MAC Address Length")

		// Actions on Current State List
		WebUI.waitForElementPresent(findTestObject('ActivateHSIWithFailureTasks/Current_State_List/Textbox'), 10, FailureHandling.STOP_ON_FAILURE)
		WebUI.sendKeys(findTestObject('ActivateHSIWithFailureTasks/Current_State_List/Textbox'), "1")
		WebUI.waitForElementPresent(findTestObject('ActivateHSIWithFailureTasks/Current_State_List/Plus_Button'), 10, FailureHandling.STOP_ON_FAILURE)
		WebUI.click(findTestObject('ActivateHSIWithFailureTasks/Current_State_List/Plus_Button'))
		WebUI.waitForElementPresent(findTestObject('ActivateHSIWithFailureTasks/Current_State_List/Message_Textbox'), 10, FailureHandling.STOP_ON_FAILURE)
		WebUI.sendKeys(findTestObject('ActivateHSIWithFailureTasks/Current_State_List/Message_Textbox'), "open.running.suspended.failed")

		// Updating Status as Failed
		WebUI.selectOptionByValue(findTestObject('Page_Order Editor/select_Activation'), '12708', true)
		WebUI.click(findTestObject('Page_Order Editor/span_Update'))
	}

	@Keyword
	def SearchOrderInQueryView(String referenceID)
	{
		WebUI.waitForPageLoad(20)
		WebUI.click(findTestObject('Page_Query Results/img_Logout oms-automation_query_'))
		WebUI.setText(findTestObject('Page_Query/input_Ref_reference_number_0'), referenceID)
		WebUI.click(findTestObject('Page_Query/input_Logout oms-automation_button'))
		WebUI.refresh()

	}

	@Keyword
	def PropertyFileForXpaths(String propName) {
		String content=""
		Properties prop = new Properties()
		try {
			prop.load(new FileInputStream('.\\Include\\config\\xpaths.properties'))
			content = prop.getProperty(propName)
		}catch(Exception e) {
			e.printStackTrace()
		}
		return content
	}

	@Keyword
	def PropertyFileForTasknames(String propName) {
		String content=""
		Properties prop = new Properties()
		try {
			prop.load(new FileInputStream('.\\Include\\config\\tasknames.properties'))
			content = prop.getProperty(propName)
		}catch(Exception e) {
			e.printStackTrace()
		}
		return content
	}

	@Keyword
	def waitAndRefresh(String xpath,String expectedText){
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, 60)
		wait.pollingEvery(15, TimeUnit.SECONDS)
		WebElement refreshButton = driver.findElement(By.xpath("//input[@value='Refresh']"))
		while(!(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).getText().equals(expectedText))){
			//			wait.until(ExpectedConditions.elementToBeClickable(refreshButton));
			wait.until(ExpectedConditions.elementToBeClickable(refreshButton))
		}
	}

	@Keyword
	def dynamicWait(String xpath, String expectedText) {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, 10)
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).getText().equals(expectedText)
		}catch(Exception e) {
			e.printStackTrace()
		}

	}

	@Keyword
	def waitForElement (String xpath,String expectedText) {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement refreshButton = driver.findElement(By.xpath("//input[@value='Refresh']"))
		while(!(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).getText().equals(expectedText)))
		{
			Thread.sleep(12000)
			refreshButton.click()
		}
	}

	@Keyword
	def CheckOrderState(String expectedText) {
		assert WebUI.getText(findTestObject('Page_Worklist/Worklist_OrderState')).equals(expectedText)
	}
}