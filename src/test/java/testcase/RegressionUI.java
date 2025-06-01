package testcase;

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

import static utilities.Utilities.onClick;
import static utilities.Utilities.waitForElementToBeVisible;

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
        path = System.getProperty("user.dir")+getProperty("excelFilePath");
        excel = new ExcelReader(path);
    }
    @AfterMethod
    public void afterMethod(){
        login.logOut();
    }

    @Test(dataProviderClass = Utilities.class, dataProvider = "dp", priority = 1, enabled = true)
    public void timeSheet(Hashtable<String, String> data, Method m){
        if(data.get("Run").equalsIgnoreCase("yes")) {
            try {
                login.login();
                onClick("//div[@title=\"Timesheets\"]/../button");
                waitForElementToBeVisible("//h6[text()=\"Select Employee\"]", 30);
                excel.setCellData("timeSheet","execution status", rowNum, "done" );
                rowNum++;
            }
            catch (Exception e) {
                rowNum++;
                throw new RuntimeException(e);
            }
        }
    }
}
