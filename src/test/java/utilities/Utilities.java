package utilities;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import testbase.TestBase;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.List;

public class Utilities extends TestBase {

    @DataProvider(name = "dp")
    public Object[][] getData(Method m){
        String sheetName = m.getName();
        int rows = excel.getRowCount(sheetName);
        int cols = excel.getColumnCount(sheetName);
        Object[][] data = new Object[rows-1][1];
        Hashtable<String, String> table;
        for(int rowNum = 2; rowNum<= rows; rowNum++){
            table = new Hashtable<>();
            for (int colNum = 0; colNum < cols; colNum++){
                table.put(excel.getCellData(sheetName, colNum, 1),
                        excel.getCellData(sheetName, colNum, rowNum));
                data[rowNum-2][0] = table;
            }
        }
        return data;
    }

    public static void onClick(String key){
        try {
            waitForElementToBeClickable(key, 30).click();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void sendKeys(String key, String value){
        try {
            waitForElementToBeVisible(key, 30).sendKeys(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void clearText(String key){
        try {
            waitForElementToBeVisible(key, 30).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendKeysWithJavaScript(String key, String command) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
        jsExecutor.executeScript("arguments[0].value='{0}';", command, waitForElementToBeVisible(key, 30));
    }

    public static String getText(String key){
        try {
            return waitForElementToBeVisible(key, 30).getText();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static WebElement waitForElementToBeClickable(String key, int duration){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(getObjProperty(key))));
    }
    public static WebElement waitForElementToBeVisible(String key, int duration){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getObjProperty(key))));
    }

    public static WebElement waitForElementToBePresent(String key, int duration){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(getObjProperty(key))));
    }
    public static Object getFieldValueRecursive(Object json, String key) {
        if (json instanceof JSONObject jsonObject) {
            if (jsonObject.has(key)) {
                return jsonObject.get(key);
            }
            for (String jsonKey : jsonObject.keySet()) {
                Object value = jsonObject.get(jsonKey);
                Object result = getFieldValueRecursive(value, key);
                if (result != null) {
                    return result;
                }
            }
        } else if (json instanceof JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                Object result = getFieldValueRecursive(jsonArray.get(i), key);
                if (result != null) return result;
            }
        }
        return null;
    }

    public static List<WebElement> getWebElementList(String key){
        return driver.findElements(By.xpath(getObjProperty(key)));
    }

    // get Actions class object
    public static JavascriptExecutor getJSExecutorInstance(WebDriver driver) {
        return (JavascriptExecutor)driver;
    }

    public static void executeJSCommand(WebDriver driver, String command, WebElement inputElement) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
        jsExecutor.executeScript("arguments[0].execute{0}", command, inputElement);
    }

    public static void takeFullScreenshot(WebDriver driver) throws IOException {
        String currentDir = System.getProperty("user.dir");
        File sc = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File ssLocation = new File(String.format("{0}/screenshot-{1}.png", currentDir, LocalDateTime.now().toString()));
        FileUtils.copyFile(sc,ssLocation);
    }

    public static void takeFullScreenshot(WebDriver driver, @Nullable String inputLocation) throws IOException {
        String currentDir = System.getProperty("user.dir");
        String saveLocation = inputLocation == null ? currentDir : inputLocation;
        File sc = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File ssLocation = new File(String.format("{0}/screenshot-{1}.png", saveLocation, LocalDateTime.now().toString()));
        FileUtils.copyFile(sc,ssLocation);
    }

    public static Actions getActionsObject (WebDriver driver) {
        return new Actions(driver);
        }

    public static WebElement getShadowElement(WebDriver driver, String... selectors) {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        StringBuilder script = new StringBuilder("return document");
        for(String selector : selectors) {
            script.append(".shadowRoot");
        }
        if(selectors.length > 0) {
            String lastSelector = selectors[selectors.length - 1];
            script.setLength(script.length() - ".shadowRoot".length());
            script.append(".querySelector('").append(lastSelector).append("')");
        }

        return (WebElement) js.executeScript(script.toString());
    }
}

