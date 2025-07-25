package testcase;

import com.relevantcodes.extentreports.LogStatus;
import groovy.util.logging.Log;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;
import pages.Admin;
import pages.LoginPage;
import pages.PIMPage;
import pages.Timesheet;
import testbase.TestBase;
import utilities.ExcelReader;
import utilities.Utilities;
import utilities.WordReportUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import static utilities.Utilities.onClick;

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
            String testName = m.getName() + "_" + data.get("Testcase");

            // Start word doc for this test
            WordReportUtils.startDoc(testName);

            try {
                new Timesheet().editTimesheet(data);

                // Take multiple screenshots during execution
                File scr1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                WordReportUtils.addScreenshot(scr1, "After editTimesheet step");

                // Possibly more steps & screenshots...
                // File scr2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                // WordReportUtils.addScreenshot(scr2, "After submitting form");

                excel.setCellData(sheetName,"execution status", rowNum, "done");

                logger.log(LogStatus.INFO, "Total Validations: " + totalValuesMatchedCount +
                        ". Total Failure : " + totalFailCount +
                        ". Total PASSED : " + totalPassCount);
            }
            catch (Exception e) {
                logger.log(LogStatus.FAIL, e.getMessage());
                throw new RuntimeException(e);
            }
            finally {
                rowNum++;
                extent.endTest(logger);

                // Save the doc
                WordReportUtils.saveDoc();
            }
        } else {
            rowNum++;
        }
    }



    @Test(dataProviderClass = Utilities.class, dataProvider = "dp", priority = 1, enabled = true)
    public void pimUpdate(Hashtable<String, String> data, Method m){
        if(data.get("Run").equalsIgnoreCase("yes")) {
            logger = extent.startTest(m.getName()+"_"+data.get("Testcase")+":"+rowNum);
            String sheetName = m.getName();
            try {
                //editTimesheet(data);
                new PIMPage().editRecord(data);
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

    @Test(dataProviderClass =  Utilities.class, dataProvider = "dp", priority = 2, enabled = true)
    public void attendance(Hashtable<String, String> data, Method m){
        if(data.get("Run").equalsIgnoreCase("yes")) {
            logger = extent.startTest(m.getName()+"_"+data.get("Testcase")+":"+rowNum);
            String sheetName = m.getName();
            try {
                new Timesheet().editAttendance(data);
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

    @Test(dataProviderClass = Utilities.class, dataProvider = "dp", priority = 2, enabled = true)
    public void admin(Hashtable<String, String> data, Method m){
        if(data.get("Run").equalsIgnoreCase("yes")){
            logger = extent.startTest(m.getName()+"_"+data.get("Testcase")+":"+rowNum);
            String sheetName = m.getName();
            try{
                onClick("admin");
                new Admin().corporateBranding();
                rowNum++;
            }catch (Exception e){
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
    }

}
