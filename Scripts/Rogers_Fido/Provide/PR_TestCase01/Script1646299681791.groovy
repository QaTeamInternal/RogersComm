import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.testng.asserts.Assertion as Keys

String orderXMLPath = findTestData('XMLPathTestData').getValue('OrderXMLPath', 1)
System.out.println('OrderXMLPath 01 - ==' + orderXMLPath)

referenceID = CustomKeywords.'com.rogers.utilities.InjectOrderXML.InjectXML'(orderXMLPath)

System.out.println('Executing Test case 01 - Reference ID ==' + referenceID)

CustomKeywords.'com.rogers.utilities.CommonUtilities.OpenBrowserAndMaximize'()

CustomKeywords.'com.rogers.utilities.CommonUtilities.Login'()

CustomKeywords.'com.rogers.utilities.CommonUtilities.SearchOrderInWorkList'(referenceID + '*')

CustomKeywords.'com.rogers.utilities.CommonUtilities.ActivateHSIUpdateWithSuccess'()

CustomKeywords.'com.rogers.utilities.CommonUtilities.SearchOrderInQueryView'(referenceID + '*')

assert WebUI.getText(findTestObject('Page_Query Results/td_Completed_TOM')).equals('Completed')

assert WebUI.getText(findTestObject('Page_Query Results/td_Completed_SOM')).equals('Completed')

assert WebUI.getText(findTestObject('Page_Query Results/td_Completed_COM')).equals('Completed')

CustomKeywords.'com.rogers.utilities.CommonUtilities.Logout'()

