package utilities;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import testbase.TestBase;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Hashtable;

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
                table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
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
    public static WebElement waitForElementToBeClickable(String key, int duration){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(getObjProperty(key))));
    }
    public static WebElement waitForElementToBeVisible(String key, int duration){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getObjProperty(key))));
    }
    public static Object getFieldValueRecursive(Object json, String key){
        if(json instanceof JSONObject jsonObject){
            if(jsonObject.has(key)){
                return jsonObject.get(key);
            }
            for(String jsonKey : jsonObject.keySet()){
                Object value = jsonObject.get(jsonKey);
                Object result = getFieldValueRecursive(value, key);
                if(result != null){
                    return result;
                }
            }
            }
        else if(json instanceof JSONArray jsonArray){
            for(int i =0; i<jsonArray.length(); i++){
                Object result = getFieldValueRecursive(jsonArray.get(i), key);
                if(result!= null) return result;
            }
        }
        return null;
    }


}
