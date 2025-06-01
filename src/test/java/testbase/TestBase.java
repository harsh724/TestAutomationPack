package testbase;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.NetworkMode;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
    public static int rowNum;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    String timeStamp = sdf.format(new Date());
    @BeforeSuite
    public void driverInitiator(){
        if(extent == null){
            extent = new ExtentReports(System.getProperty("user.dir")+"//Report//Archive/ExtentReport_"+timeStamp+"_.html", true, NetworkMode.OFFLINE);
            extent.loadConfig(new File(System.getProperty("user.dir")+"//Reports//ReportConfig.xml"));
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
            driver = new ChromeDriver();
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/webdriver/chromedriver");
            ChromeOptions options = new ChromeOptions();
            //LoggingPreferences loggingPreferences = new LoggingPreferences();
            //loggingPreferences.enable(LogType.PERFORMANCE, Level.ALL);
            options.addArguments("--remote-debugging-pipe");
            options.addArguments("--diable-gpu");
            options.addArguments("--diable-dev-shm-usage");
            //options.setCapability("goog:loggingPrefs", loggingPreferences);

            driver.get(getProperty("baseURL"));
            driver.manage().window().maximize();
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

    @AfterSuite
    public void afterSuite(){
        driver.quit();
    }
}
