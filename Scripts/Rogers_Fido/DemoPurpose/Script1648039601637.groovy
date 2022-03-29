import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.assertthat.selenium_shutterbug.utils.web.Browser
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.main.CustomKeywordDelegatingMetaClass
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.logging.LoggingPreferences
import org.openqa.selenium.logging.LogType
import org.apache.log4j.Logger
import java.util.logging.Level
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

//System.setProperty("webdriver.chrome.driver", "C:\\Users\\ppmukher\\chromedriver.exe");
//DesiredCapabilities caps = DesiredCapabilities.chrome()
//LoggingPreferences logPrefs = new LoggingPreferences()
//logPrefs.enable(LogType.BROWSER, Level.INFO)
//caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs)

//System.setProperty("webdriver.chrome.driver","C:\\Users\\ppmukher\\chromedriver.exe");
//ChromeOptions options = new ChromeOptions();
//options.addExtensions(new File("C:\\Users\\ppmukher\\extension_3_40_1_0.crx"));
//DesiredCapabilities capabilities = new DesiredCapabilities();
//capabilities.setCapability(ChromeOptions.CAPABILITY, options);
//options.merge(capabilities);
//WebDriver driver = new ChromeDriver();

//SoftAssert st =new SoftAssert(); 
//if(Browser.equalsIgnoreCase("chrome")) 
//	{
//		WebDriverManager.chromedriver().setup(); 
//		d=new ChromeDriver(); 
//	} 
//else if(Browser.equalsIgnoreCase("mozilla")) 
//	{
//		WebDriverManager.firefoxdriver().setup(); 
//		d=new FirefoxDriver();//OpenBrowser
//	} 
//WebDriver driver=new EventFiringDecorator(new Events1(d)).decorate(d);
 

WebUI.openBrowser('')

WebUI.navigateToUrl('https://www.orangehrm.com/')

WebUI.click(findTestObject('Object Repository/OrangeHRM/Page_OrangeHRM HR Software  Free  Open Sour_43e816/a_Performance Management'))

WebUI.click(findTestObject('Object Repository/OrangeHRM/Page_Performance Management Software  Appra_2e8d0a/a_Book a Free Demo'))

WebUI.closeBrowser()

