package testcase;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;
import pages.LoginPage;
import testbase.TestBase;
import utilities.ExcelReader;
import utilities.Utilities;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import static pages.Timesheet.editAttendance;
import static pages.Timesheet.editTimesheet;
import static utilities.Utilities.*;

public class RegressionUI extends TestBase {

    List<String> methodList = new ArrayList<>();
    LoginPage login = new LoginPage();

    @BeforeMethod
    public void beforeMethod(Method method){
        dataSheetMapping = new HashMap<>();
        String methodName = method.getName();
        if(!(methodList.contains(methodName))){
            methodList.add(methodName);
            rowNum = 2;
        }
    }
    @BeforeTest
    public void beforeTest(){
        path = System.getProperty("user.dir")+getProperty("excelFilePathUI");
        excel = new ExcelReader(path);
        login.login();
    }
    /*@AfterMethod
    public void afterMethod(){

    }*/
    @AfterTest
    public void afterTest(){
        login.logOut();
    }


    @Test(dataProviderClass = Utilities.class, dataProvider = "dp", priority = 1, enabled = true)
    public void timeSheet(Hashtable<String, String> data, Method m){
        if(data.get("Run").equalsIgnoreCase("yes")) {
            logger = extent.startTest(m.getName()+"_"+data.get("Testcase")+":"+rowNum);
            String sheetName = m.getName();
            try {
                editTimesheet(data);
                excel.setCellData(sheetName,"execution status", rowNum, "done" );
                rowNum++;
                logger.log(LogStatus.INFO, "Total Validations: "+totalValuesMatchedCount+". Total Failure : "+totalFailCount+ ". Total PASSED : "+totalPassCount);
            }
            catch (Exception e) {
                rowNum++;
                logger.log(LogStatus.FAIL, e.getMessage());
                throw new RuntimeException(e);
            }
        }
        else{
            rowNum++;
        }
        extent.endTest(logger);
    }

    @Test(dataProviderClass =  Utilities.class, dataProvider = "dp", priority = 2, enabled = true)
    public void attendance(Hashtable<String, String> data, Method m){
        if(data.get("Run").equalsIgnoreCase("yes")) {
            logger = extent.startTest(m.getName()+"_"+data.get("Testcase")+":"+rowNum);
            String sheetName = m.getName();
            try {
                editAttendance(data);
                excel.setCellData(sheetName,"execution status", rowNum, "done" );
                rowNum++;
                logger.log(LogStatus.INFO, "Total Validations: "+totalValuesMatchedCount+". Total Failure : "+totalFailCount+ ". Total PASSED : "+totalPassCount);
            }
            catch (Exception e) {
                rowNum++;
                logger.log(LogStatus.FAIL, e.getMessage());
                e.printStackTrace();
                Assert.fail();
                throw new RuntimeException(e);

            }
        }
        else{
            rowNum++;
            throw new SkipException("Skipping this test due to configuration or condition");
        }
        extent.endTest(logger);

    }

}
