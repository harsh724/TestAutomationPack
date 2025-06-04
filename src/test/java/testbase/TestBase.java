package testbase;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import pages.LoginPage;
import utilities.ExcelReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;


public class TestBase {
    public static ExcelReader excel;
    public static String path;
    public static WebDriver driver = null;
    public static ExtentReports extent;
    public static ExtentTest logger;
    public static final String PROPERTY_FILE_PATH = "src/test/java/resources/Config.properties";
    public static final String OBJ_PROP_FILE_PATH = "src/test/java/resources/Object.properties";
    public static Properties properties;
    public static Properties objProperties;
    public static HashMap<String, String> dataSheetMapping;
    public static  int totalFailCount = 0;
    public static  int totalPassCount = 0;
    public static  int totalValuesMatchedCount = 0;
    public static String allReportLogging;

    public static int rowNum;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    String timeStamp = sdf.format(new Date());
    @BeforeSuite
    public void driverInitiator(){
        if(extent == null){
            extent = new ExtentReports(System.getProperty("user.dir")+"//Report//Archive/ExtentReport_"+timeStamp+"_.html", true, NetworkMode.OFFLINE);
            extent.loadConfig(new File(System.getProperty("user.dir")+"//Report//ReportsConfig.xml"));
        }
        if(properties == null){
            properties = new Properties();
            try {
                FileInputStream fileInputStream = new FileInputStream(PROPERTY_FILE_PATH);
                properties.load(fileInputStream);
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                rowNum++;
                Assert.fail();
            }
        }
        if(objProperties == null){
            objProperties = new Properties();
            try {
                FileInputStream fileInputStream = new FileInputStream(OBJ_PROP_FILE_PATH);
                objProperties.load(fileInputStream);
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                rowNum++;
                Assert.fail();
            }
        }

        if(getProperty("webDriverRequired").equalsIgnoreCase("yes") && driver == null) {
            ChromeOptions options = new ChromeOptions();
            //LoggingPreferences loggingPreferences = new LoggingPreferences();
            //loggingPreferences.enable(LogType.PERFORMANCE, Level.ALL);
            options.addArguments("--remote-debugging-pipe", "--diable-gpu", "--diable-dev-shm-usage", "--start-maximized", "--disable-popup-blocking");
            driver = new ChromeDriver(options);
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/webdriver/chromedriver");
            //options.setCapability("goog:loggingPrefs", loggingPreferences);
            driver.get(getProperty("baseURL"));
            //driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            LoginPage login = new LoginPage();
            try {
                login.initialLogin();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public static String getProperty(String key){
        return properties.getProperty(key);
    }
    public static String getObjProperty(String key){
        return objProperties.getProperty(key);
    }
    public static void valuesMatched(String description, String expectedResult, String actualResult){
        try{
            totalValuesMatchedCount-=-1;
            if(expectedResult == null && actualResult == null){
                logger.log(LogStatus.PASS, "Result for : "+description+" is matched with expected : "+ expectedResult+ " . Where Actual Value is :"+ actualResult);
                totalPassCount-=-1;
            }
            else if(expectedResult.equals(actualResult)){
                logger.log(LogStatus.PASS, "Result for : "+description+" is matched with expected : "+ expectedResult+ " . Where Actual Value is :"+ actualResult);
                totalPassCount-=-1;
            }
            else{
                logger.log(LogStatus.FAIL, "Result for : "+description+" is not matched with expected : "+ expectedResult+ " . Where Actual Value is :"+ actualResult);
                totalFailCount-=-1;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void tearDown() {
        // End test and flush report
        extent.endTest(logger);
        extent.flush();
        extent.close();
    }

    @AfterSuite
    public void afterSuite(){
        if(getProperty("webDriverRequired").equalsIgnoreCase("yes")) {
            driver.quit();
        }
        tearDown();
    }

    // get browser instance based on user input (WILL USE ENUM INSTEAD OF INPUT STRING)
    public static WebDriver launchBrowser(String browserName) {
        if(browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-debugging-pipe", "--diable-gpu", "--diable-dev-shm-usage", "--start-maximized", "--disable-popup-blocking");
            driver = new ChromeDriver(options);
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/webdriver/chromedriver");
            driver.get(getProperty("baseURL"));
            driver.manage().deleteAllCookies();
            return driver;
        }

        else if (browserName.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--remote-debugging-pipe", "--diable-gpu", "--diable-dev-shm-usage", "--start-maximized", "--disable-popup-blocking");
            driver = new EdgeDriver(options);
            driver.get(getProperty("baseURL"));
            driver.manage().deleteAllCookies();
            return driver;
        }

        else if (browserName.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--remote-debugging-pipe", "--diable-gpu", "--diable-dev-shm-usage", "--start-maximized", "--disable-popup-blocking");
            driver = new FirefoxDriver(options);
            driver.get(getProperty("baseURL"));
            driver.manage().deleteAllCookies();
            return driver;
        }

        else {
            throw new RuntimeException("Invalid Browser input");
        }
    }

}
