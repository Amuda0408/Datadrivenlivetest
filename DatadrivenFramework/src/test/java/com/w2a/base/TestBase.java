package com.w2a.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.Utilities.ExcelReader;
import com.w2a.Utilities.ExtentManager;
import com.w2a.Utilities.TestUtil;
import com.w2a.listeners.CustomListeners;

public class TestBase {
	/* we will be initializing
	 * WebDriver
	 * Properties
	 * Logs-log4j jar, .log,log4j.properties, logger
	 * Extent Reports
	 * DB
	 * Excel
	 * Mail
	 * ReportNG,ExtentReports
	 */
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	//devpinoyLogger is standard name getLogger
	public static Logger log = Logger.getLogger("devpinoyLogger");
	//read excel sheet
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\TestData.xlsx");
	//we need explicit wait to handle the alert message, once click Add customer button
	public static WebDriverWait wait;
	//extent reports
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	
	
	@BeforeSuite
	public void setUP() {
		if (driver==null) {
		
		try {
			fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			config.load(fis);
			log.debug("Config file loaded !!!");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			//fis= new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
			fis= new FileInputStream("C:\\Users\\amuda\\Amuda_liveProject\\DatadrivenFramework\\src\\test\\resources\\properties\\OR.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			OR.load(fis);
			log.debug("OR file loaded !!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//invoke the browser
		if(config.getProperty("browser").equals("firefox")){
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+ "\\src\\test\\resources\\executables\\gecko.exe");
			driver = new FirefoxDriver();
		}else if(config.getProperty("browser").equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+ "\\src\\test\\resources\\executables\\chromedriver.exe");
			driver = new ChromeDriver();
			log.debug("Chrome Launched !!!");
		}else if(config.getProperty("browser").equals("ie")) {
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+ "\\src\\test\\resources\\executables\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		//get the url
		driver.get(config.getProperty("testsiteurl"));
		log.debug("Navigated to : " + config.getProperty("testsiteurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//BasicConfigurator.configure();
		
		wait= new WebDriverWait(driver,5);
		
			
	}
	}
	
	public void click(String locator) {
		if (locator.endsWith("_CSS")) {
	driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();	
		}else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		
	test.log(LogStatus.INFO, "Clicking on : "+locator);
		}
	
	public void type(String locator, String value) {
		if (locator.endsWith("_CSS")) {
			//System.out.println("locator_ CSS: "+ OR.getProperty(locator));
			//System.out.println("value CSS: " + value);
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_XPATH")) {
			//System.out.println("locator XPATH: "+ OR.getProperty(locator));
			//System.out.println("value XPATH: " + value);
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);	
		}else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		test.log(LogStatus.INFO, "Typing in : "+locator+" entered value as "+ value);
	}
	static WebElement dropdown;
	
	//create method to select from dropfown
	public void select(String locator, String value) {
		if (locator.endsWith("_CSS")) {
			dropdown= driver.findElement(By.cssSelector(OR.getProperty(locator)));
			} else if (locator.endsWith("_XPATH")) {
				dropdown=driver.findElement(By.xpath(OR.getProperty(locator)));	
			}else if (locator.endsWith("_ID")) {
				dropdown=driver.findElement(By.id(OR.getProperty(locator)));
			}
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
			test.log(LogStatus.INFO, "selecting from dropdown in : "+locator+" entered value as "+ value);
	}
	
	
	
	
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
			}catch(NoSuchElementException e) {
				return false;
			
		}
	}
	public static void verifyEquals(String expected,String actual) throws IOException {
		try {
			Assert.assertEquals(actual, expected);
		}catch (Throwable t) {
			TestUtil.captureScreenshot();
			//log in to reportng
			Reporter.log("<br>"+"Verification Failure : "+t.getMessage()+"<br>");
			Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			//log into extent report
			test.log(LogStatus.FAIL, " Verification Failed with exception : "+t.getMessage());
			System.out.println("screenshot name: "+ TestUtil.screenshotName);
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
			
			//CustomListeners.testReport.get().log(Status.FAIL, " Verification failed with exception : " + t.getMessage());
			//CustomListeners.testReport.get().log(Status.FAIL, CustomListeners.testReport.get().addScreenCaptureFromPath(TestUtil.screenshotName));
			
		}
	}
	
	
	
	
	@AfterSuite
	public void tearDown() {
		//below condition needed to see whether session active then quit
		if (driver != null) {
			driver.quit();
		}

		log.debug("test execution completed !!!");
	}

}
