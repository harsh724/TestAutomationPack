package pages;

import org.openqa.selenium.WebElement;
import testbase.TestBase;

import java.util.Hashtable;

import static utilities.Utilities.*;
import static utilities.Utilities.getWebElementList;
import static utilities.Utilities.sendKeys;
import static utilities.Utilities.waitForElementToBePresent;

public class Timesheet extends TestBase {
    public static void editAttendance(Hashtable<String, String> data){
        onClick("timeSheetButton");
        onClick("attendance");
        onClick("myRecords");
        onClick("editPencil");
        /*clearText("punchInTime");
        sendKeys("punchInTime", data.get("Punch In Time"));
        clearText("punchOutTime");
        sendKeys("punchOutTime", data.get("Punch Out Time"));*/
        onClick("punchInClock");
        clearText("hourText");
        sendKeysWithJavaScript("hourText", "09");
        clearText("minuteText");
        sendKeysWithJavaScript("minuteText", "00");
        onClick("PM");
        onClick("AM");
        onClick("punchOutClock");
        clearText("hourText");
        sendKeys("hourText", "06");
        clearText("minuteText");
        sendKeys("minuteText", "00");
        onClick("PM");
        onClick("saveButton");
        waitForElementToBeVisible("duration", 30);
        String duration = getText("duration");
        valuesMatched("duration", data.get("hours"), "duration");
        onClick("dashboadButton");

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
