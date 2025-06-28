package pages;

import org.openqa.selenium.JavascriptExecutor;
import testbase.TestBase;

import java.util.ArrayList;

import static utilities.Utilities.executeJSCommand;
import static utilities.Utilities.onClick;

public class PIMPage extends TestBase {
    public static void deleteRecord() {
        executeJSCommand(driver, "window.open()");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(getProperty("baseURL"));
        onClick("pimIcon");
        onClick("firstRecordCheck");
        onClick("editIcon");
    }
}
