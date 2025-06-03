package pages;

import testbase.TestBase;

import static utilities.Utilities.getText;
import static utilities.Utilities.onClick;

public class Timesheet extends TestBase {
    public void attendance(){
        onClick("attendance");
        onClick("myRecords");
        String duration = getText("duration");
        valuesMatched("duration", "10.00", duration);
    }
}
