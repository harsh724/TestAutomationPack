package pages;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import testbase.TestBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static utilities.Utilities.*;

public class PIMPage extends TestBase {
    public static void editRecord(Hashtable<String, String> data) {
        try {
            executeJSCommand(driver, "window.open()");
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            driver.get(getProperty("baseURL"));
            onClick("pimIcon");
            sendKeys("name", data.get("First Name"));
            onClick("search");
            //onClick("firstRecordCheck");
            onClick("editIcon");
            onClick("bloodTypeDropDown");
            List<WebElement> bloodGroupList = getWebElementList("dropdownList");
            bloodGroupList.stream()
                    .filter(group->group.getText().trim().equalsIgnoreCase(data.get("Blood Group")))
                    .findFirst()
                    .ifPresentOrElse(WebElement::click, ()->{
                        try {
                            throw new RuntimeException();
                        } catch (RuntimeException e) {
                            throw new RuntimeException(e);
                        }
                    });
            onClick("nationalityDropdown");
            List<WebElement> nationalityList = getWebElementList("nationalityList");
            nationalityList.stream()
                    .filter(nationality->nationality.getText().equalsIgnoreCase(data.get("Nationality")))
                    .findFirst().ifPresentOrElse(WebElement::click, ()->{
                        try {
                            throw new RuntimeException();
                        } catch (RuntimeException e) {
                            throw new RuntimeException(e);
                        }
                    });

            onClick("saveButton1");
            waitForElementToBeVisible("successToast", 5);
            onClick("saveButton2");
            waitForElementToBeVisible("successToast", 5);

            driver.switchTo().window(tabs.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
