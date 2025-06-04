package pages;

import org.openqa.selenium.WebElement;
import testbase.TestBase;

import java.util.Hashtable;

import static utilities.Utilities.*;
import static utilities.Utilities.getWebElementList;
import static utilities.Utilities.sendKeys;
import static utilities.Utilities.waitForElementToBePresent;

public class Timesheet extends TestBase {
    public void attendance(){
        onClick("attendance");
        onClick("myRecords");
        String duration = getText("duration");
        valuesMatched("duration", "10.00", duration);
    }
    public static void editTimesheet(Hashtable<String, String> data){
        try {
            onClick("timeSheetButton");
            waitForElementToBeVisible("selectEmployee", 30);
            onClick("viewButton");
            onClick("editButton");
            onClick("trash");
            sendKeys("project", data.get("Project"));
            Thread.sleep(2000);
            onClick("projectSelection");
            onClick("activity");
            Thread.sleep(2000);
            waitForElementToBePresent("activityList", 30);
            for (WebElement we : getWebElementList("activityList")) {
                String activity = we.getText();
                System.out.println(activity);
                if (data.get("Activity").equalsIgnoreCase(activity)) {
                    we.click();
                    break;
                }
            }
            for (WebElement we : getWebElementList("days")) {
                we.sendKeys(data.get("hours"));
            }
            onClick("submit");
            waitForElementToBePresent("submitConfirmation", 30);
            onClick("dashboadButton");
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }
}
