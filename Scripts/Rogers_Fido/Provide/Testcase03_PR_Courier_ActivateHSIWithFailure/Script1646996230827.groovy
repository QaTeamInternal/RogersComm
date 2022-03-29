import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import groovy.inspect.swingui.BytecodeCollector

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
import internal.GlobalVariable

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.asserts.Assertion as Keys

String orderXMLPath = findTestData('XMLPathTestData').getValue('OrderXMLPath', 3)

System.out.println('OrderXMLPath TC03 - ==' + orderXMLPath)

referenceID = CustomKeywords.'com.rogers.utilities.QueuePoster.InjectOrder'(orderXMLPath)

String notificationXMLPath = findTestData('XMLPathTestData').getValue('OrderXMLPath', 4)

CustomKeywords.'com.rogers.utilities.CommonUtilities.OpenBrowserAndMaximize'()

CustomKeywords.'com.rogers.utilities.CommonUtilities.Login'()

CustomKeywords.'com.rogers.utilities.CommonUtilities.SearchOrderInWorkList'(referenceID + '*')

//check if CPEUpdate task is created or not 

CustomKeywords.'com.rogers.utilities.CommonUtilities.dynamicWait'(CustomKeywords.'com.rogers.utilities.CommonUtilities.PropertyFileForXpaths'("WorkList_TaskName"), CustomKeywords.'com.rogers.utilities.CommonUtilities.PropertyFileForTasknames'("UpdateCPE_COM"))

CustomKeywords.'com.rogers.utilities.CommonUtilities.CheckOrderState'("In Progress")

CustomKeywords.'com.rogers.utilities.QueuePoster.InjectOMSNotificationForCPEUpdate'(referenceID, notificationXMLPath)

//Check for TOM order Fulfillment

CustomKeywords.'com.rogers.utilities.CommonUtilities.waitAndRefresh'(CustomKeywords.'com.rogers.utilities.CommonUtilities.PropertyFileForXpaths'("WorkList_TaskName"), CustomKeywords.'com.rogers.utilities.CommonUtilities.PropertyFileForTasknames'("ActivateHSI_TOM"))

CustomKeywords.'com.rogers.utilities.CommonUtilities.CheckOrderState'("In Progress")

CustomKeywords.'com.rogers.utilities.CommonUtilities.ActivateHSIUpdateWithFailure'()

CustomKeywords.'com.rogers.utilities.CommonUtilities.SearchOrderInWorkList'(referenceID + '*')

CustomKeywords.'com.rogers.utilities.CommonUtilities.waitAndRefresh'(CustomKeywords.'com.rogers.utilities.CommonUtilities.PropertyFileForXpaths'("Worklist_SOM_TaskState"),"Failure")

CustomKeywords.'com.rogers.utilities.CommonUtilities.Logout'()

