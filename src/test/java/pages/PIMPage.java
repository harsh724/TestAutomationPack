package pages;

import org.openqa.selenium.WebElement;
import testbase.TestBase;

import java.util.ArrayList;
import java.util.List;

import static utilities.Utilities.*;

public class PIMPage extends TestBase {
    public static void editRecord() {
        executeJSCommand(driver, "window.open()");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(getProperty("baseURL"));
        onClick("pimIcon");
        //onClick("firstRecordCheck");
        onClick("editIcon");
        onClick("bloodTypeDropDown");
        List<WebElement> bloodGroupList = getWebElementList("dropdownList");
        System.out.println(bloodGroupList.size());


    }
}
