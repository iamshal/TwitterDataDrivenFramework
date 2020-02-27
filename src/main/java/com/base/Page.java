package com.base;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
//import org.json.JSONArray;
//import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.mysql.cj.api.result.RowList;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import com.utilities.ExcelReader;
import com.utilities.ExtentManager;
import com.utilities.TestUtil;

public class Page {

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "//src//test//resources//excel//TestData.xlsx");

	public static WebDriverWait wait;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	public static String browser;

	/*
	 * WebDriver ,Properties , Logs - log4j jar, .log, log4j.properties, Logger
	 * ExtentReports DB Excel Mail ReportNG, ExtentReports Jenkins ExtentReports--
	 * Extent Manager,Report Config test.log ExtentConfig
	 */
	public Page() {
		System.out.println("inside constructor.........");

		if (driver == null) {

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "//src//test//resources//properties//config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "//src//test//resources//properties//OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("OR file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Jenkins Browser filter configuration
			if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {

				browser = System.getenv("browser");
			} else {

				browser = config.getProperty("browser");

			}

			config.setProperty("browser", browser);

			if (config.getProperty("browser").equals("firefox")) {

				// System.setProperty("webdriver.gecko.driver", "gecko.exe");
				driver = new FirefoxDriver();

			} else if (config.getProperty("browser").equals("chrome")) {

				ChromeOptions options = new ChromeOptions();

				if (System.getProperty("os.name").contains("Windows")) {
					System.setProperty("webdriver.chrome.driver",
							System.getProperty("user.dir") + "//src//test//resources//executables//chromedriver.exe");
					System.out.println("this is windows");

				} else {
					System.setProperty("webdriver.chrome.driver",
							"//Users//admin//eclipse-workspace//code//drivers//chromedriver");
//					options.addArguments("--headless");
				}

//				

				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values.notifications", 2);
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);

				options.setExperimentalOption("prefs", prefs);
				options.addArguments("--disable-extensions");
				options.addArguments("--disable-infobars");

				options.addArguments("--no-sandbox");
				driver = new ChromeDriver(options);

				log.debug("Chrome Launched !!!");
			} else if (config.getProperty("browser").equals("ie")) {

				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "//src//test//resources//executables//IEDriverServer.exe");
				driver = new InternetExplorerDriver();

			}

			driver.get(config.getProperty("testsiteurl"));
			log.debug("Navigated to : " + config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 5);

		}

	}

	public static void tab() {
		try {
			Actions action = new Actions(driver);
			action.sendKeys(Keys.TAB).build().perform();
			Reporter.log("Tab pressed", true);

		} catch (Exception e) {
			Reporter.log("Tab pressed", false);
			e.printStackTrace();
			Assert.fail();
		}

	}

	public static void quit() {

		driver.quit();
	}

	// Common Keywords

	public static void clearCookies() {
		try {
			driver.manage().deleteAllCookies();
			log.debug("Cleared cookies");
			Reporter.log("Clear the cookies", true);
			// ATUReports.add("Clear the cookies", false);

		} catch (Exception e) {
			Reporter.log("Cookies not cleared", false);
			log.debug("Cleared cookies");
			// ATUReports.add("Clear the cookies", LogAs.FAILED,
			// new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
			Assert.fail();

			System.out.println("executed");
		}
	}

	public static void wait(String inputData) {
		// wait(5);
		try {
			int time = Integer.parseInt(inputData);
			int seconds = time * 1000;
			Thread.sleep(seconds);
			Reporter.log("Waited for element", true);
			// ATUReports.add("Waited for element", inputData, false);
		} catch (InterruptedException e) {
			Reporter.log("Waited for element", false);
			// ATUReports.add("Wait for element", inputData, LogAs.FAILED,
			// new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
			e.printStackTrace();
			Assert.fail();
		}
	}

	public static void refreshPage() {
		try {
			waitTime(driver, "5");
			driver.navigate().refresh();
			waitTime(driver, "5");
			Reporter.log("Page refreshed", true);
			log.debug("Page refreshed");
			// ATUReports.add("Page refreshed", false);
		} catch (Exception e) {
			Reporter.log("Page Not refreshed", false);
			log.debug("Page not refreshed");
			// ATUReports.add("Page Refresh", LogAs.FAILED, new
			// CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
			Assert.fail();
		}
	}

	public static void defaultWait() {
		wait("5");
	}

	public static void loadElement() {
		wait("30");
	}

	public static void click(String locator) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		log.debug("Clicking on : " + locator);
		test.log(LogStatus.INFO, "Clicking on : " + locator);
	}

	public static void clickEnter(String locator) {

		if (locator.endsWith("_XPATH")) {

			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(Keys.ENTER);
			((JavascriptExecutor) driver).executeScript("window.focus();");
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ENTER).build().perform();

			log.debug("clicked on enter");
			test.log(LogStatus.INFO, "Clicking on : " + locator);
		}

	}

	public static void jsClickByXPath(String locator) {

		try {
			waitForElement(locator);

			if (locator.endsWith("_XPATH")) {

				WebElement element = driver.findElement(By.xpath(OR.getProperty(locator)));

				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
				log.debug("Clicking on : " + locator);
				test.log(LogStatus.INFO, "Clicking on : " + locator);

			}

		} catch (Exception e) {

			e.printStackTrace();
			Assert.fail();
		}
	}

	public static void keysBackSpace(String locator) {

		if (locator.endsWith("_XPATH")) {

			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(Keys.BACK_SPACE);

		}

	}

	public static void refresh() {

		driver.navigate().refresh();
	}

	public void type(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		log.debug("Typing in : " + locator);

		test.log(LogStatus.INFO, "Typing in : " + locator + " entered value as " + value);

	}

	public boolean verifyElementAbsent(String locator) throws Exception {
		try {
			driver.findElement(By.xpath(locator));
			System.out.println("Element Present");
			return false;

		} catch (NoSuchElementException e) {
			System.out.println("Element absent");
			return true;
		}
	}

	static WebElement dropdown;
	private static String Null;

	public void select(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}

		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		log.debug("Selecting from dropdown : " + locator);

		test.log(LogStatus.INFO, "Selecting from dropdown : " + locator + " value as " + value);

	}

	public boolean isElementPresent(By by) {

		try {

			driver.findElement(by);
			return true;

		} catch (NoSuchElementException e) {

			return false;

		}

	}

	public static String clearAndType(String locator, String keysToSend) {

		try {
			Thread.sleep(500);
			if (locator.endsWith("_XPATH")) {
				WebElement webElement = driver.findElement(By.xpath(OR.getProperty(locator)));
				webElement.clear();
				webElement.sendKeys(keysToSend);
				log.debug("Clear and type : " + locator);
				test.log(LogStatus.INFO, "Clear and type : " + locator);

			}

		} catch (InterruptedException e) {

			// ATUReports.add("Clear and Type - " + values[0], keysToSend,
			// LogAs.FAILED,
			// new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
			e.printStackTrace();
			Assert.fail();
		}
		return keysToSend;
	}

	public static void mouseOver(String locator) {
		if (locator.endsWith("_XPATH")) {

			WebElement webElement = driver.findElement(By.xpath(OR.getProperty(locator)));
			try {
				Actions builder = new Actions(driver);
				builder.moveToElement(webElement).build().perform();
				log.debug("mouseOver : " + locator);
				test.log(LogStatus.INFO, "mouseOver : " + locator);
				// ATUReports.add("Mouse over - " +values[0], false);
			}

			catch (Exception e) {
				// ATUReports.add("Mouse over - " +values[0], LogAs.FAILED, new
				// CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
				Assert.fail();

			}
		}
	}

	public static void mouseHoverCss(String locator) {
		if (locator.endsWith("_CSS")) {

			WebElement webElement = driver.findElement(By.cssSelector(OR.getProperty(locator)));
			try {
				Actions builder = new Actions(driver);
				builder.moveToElement(webElement).build().perform();
				log.debug("mouseOver : " + locator);
				test.log(LogStatus.INFO, "mouseOver : " + locator);

				// ATUReports.add("Mouse over - " +values[0], false);
			}

			catch (Exception e) {
				// ATUReports.add("Mouse over - " +values[0], LogAs.FAILED, new
				// CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
				Assert.fail();

			}
		}
	}

	public static void mouseOverAndClick(String locator) {
		if (locator.endsWith("_XPATH")) {

			WebElement webElement = driver.findElement(By.xpath(OR.getProperty(locator)));
			try {
				Actions builder = new Actions(driver);
				builder.moveToElement(webElement).click().build().perform();
			} catch (Exception e) {
				// ATUReports.add(values[0], LogAs.FAILED, new
				// CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
				Assert.fail();

			}
		}
	}

	public static void doubleClick(WebDriver driver, String element, String locator) {

		try {
			if (locator.endsWith("_XPATH")) {
				WebElement webElement = driver.findElement(By.xpath(OR.getProperty(locator)));
				Actions action = new Actions(driver).doubleClick(webElement);
				action.build().perform();
				// ATUReports.add("Click - " + values[0], false);
			}
		} catch (Exception e) {
			// ATUReports.add(values[0], LogAs.FAILED, new
			// CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
			Assert.fail();
		}
	}

	public static void clear(String locator) {

		try {
			if (locator.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(locator))).clear();
				System.out.println("inside clear");
				// driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
				// webElement.clear();
				// ATUReports.add(values[0], false);
			}
		} catch (Exception e) {
			// ATUReports.add(values[0], LogAs.FAILED, new
			// CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
			Assert.fail();
		}
	}

	public static String selectByVisibletext(String inputData, String locator) {
		if (locator.endsWith("_XPATH")) {

			WebElement webElement = driver.findElement(By.xpath(OR.getProperty(locator)));
			try {
				Select selectBox = new Select(webElement);
				selectBox.selectByVisibleText(inputData);

			} catch (Exception e) {
				// ATUReports.add(values[0], inputData, LogAs.FAILED,
				// new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
				Assert.fail();

			}
		}
		return inputData;
	}

	public static void waitForElement(String locator) {

		try {
			String ElementWait1 = "30";
			int WaitElementSeconds1 = new Integer(ElementWait1);
			if (locator.endsWith("_XPATH")) {
				WebDriverWait wait = new WebDriverWait(driver, WaitElementSeconds1);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(locator))));
				// ATUReports.add("Wait - " + values[0], false);
			}
		} catch (Exception e) {
			// ATUReports.add("Wait - " + values[0], LogAs.FAILED,
			// new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
			Reporter.log(
					"Waited for Element and the Element does not appear in the given time period so test got failure",
					true);
			e.printStackTrace();
		}

	}

	public static void waitForElementHealthy(String locator) {

		try {
			String ElementWait1 = "200";
			int WaitElementSeconds1 = new Integer(ElementWait1);
			if (locator.endsWith("_XPATH")) {
				WebDriverWait wait = new WebDriverWait(driver, WaitElementSeconds1);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(locator))));
				// ATUReports.add("Wait - " + values[0], false);
			}
		} catch (Exception e) {
			// ATUReports.add("Wait - " + values[0], LogAs.FAILED,
			// new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
			Reporter.log(
					"Waited for Element and the Element does not appear in the given time period so test got failure",
					true);
			e.printStackTrace();
		}

	}

	public static String getText(String locator) {

		try {
			WebElement webElement = driver.findElement(By.xpath(OR.getProperty(locator)));

			String text = webElement.getText().trim();
			Reporter.log(text, true);
			// ATUReports.add(values[0], "", text, true);
			return text;

		} catch (Exception e) {
			e.printStackTrace();

			return null;

		}

	}

	public static boolean isDisplayed(String locator) {

		try {
			if (locator.endsWith("_XPATH")) {

				WebElement webElement = driver.findElement(By.xpath(OR.getProperty(locator)));
				return webElement.isDisplayed();
			}
		}

		catch (Exception e) {
		}
		return false;
	}

	public static boolean isSelected(String locator) {

		try {

			if (locator.endsWith("_XPATH")) {

				WebElement webElement = driver.findElement(By.xpath(OR.getProperty(locator)));
				return webElement.isSelected();
			}
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean isEnabled(String locator) {

		try {
			if (locator.endsWith("_XPATH")) {
				WebElement webElement = driver.findElement(By.xpath(OR.getProperty(locator)));
				System.out.println("Value======>" + webElement.isEnabled());
				return webElement.isEnabled();
			}
		} catch (Exception e) {
		}
		return false;
	}

	public static void highLightElement(String locator) {
		if (locator.endsWith("_XPATH")) {

			WebElement webElement = driver.findElement(By.xpath(OR.getProperty(locator)));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", webElement,
					"color: yellow; border: 3px solid yellow;");
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", webElement, "");
		}
	}

	public static void keyboardArrowUp(WebDriver driver) {
		Actions actionObject = new Actions(driver);
		actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.ARROW_UP).perform();
	}

	public static void keyboardArrowDown() {
		Actions actionObject = new Actions(driver);
		actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.ARROW_DOWN).perform();
	}

	public static void keyboardArrowLeft(WebDriver driver) {
		Actions actionObject = new Actions(driver);
		actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.ARROW_LEFT).perform();
	}

	public static void keyboardArrowRight(WebDriver driver) {
		Actions actionObject = new Actions(driver);
		actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.ARROW_RIGHT).perform();
	}

	public static void waitTime(WebDriver driver, String waitSeconds) {
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(waitSeconds), TimeUnit.SECONDS);
	}

	public static void pageDown(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,250)", "");
		waitTime(driver, "5");
	}

	public static void pageUp() {
		((JavascriptExecutor) driver).executeScript("scroll(0,-250);");
	}

	public static void scrollBottom() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,"
				+ "document.body.scrollHeight,document.documentElement.clientHeight));");
		waitTime(driver, "5");
		Reporter.log("Scroll bottom", true);
		// ATUReports.add("Scroll bottom", false);
	}

	public static void verifyEquals(String expected, String actual) throws IOException {

		try {

			Assert.assertEquals(actual, expected);

		} catch (Throwable t) {

			TestUtil.captureScreenshot();
			// ReportNG
			Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
					+ " height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			// Extent Reports
			test.log(LogStatus.FAIL, " Verification failed with exception : " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

		}

	}

}
