package testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.LoginPage;
import services.JSONServices;
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

public class RegressionAPI extends TestBase {
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
        path = System.getProperty("user.dir")+getProperty("excelFilePathAPI");
        excel = new ExcelReader(path);
    }


    @Test(dataProviderClass = Utilities.class, dataProvider = "dp", priority = 1, enabled = true)
    public void apiTestingDemo(Hashtable<String, String> data, Method m){
        if(data.get("Run").equalsIgnoreCase("yes")) {
            logger = extent.startTest(m.getName()+"_"+data.get("Testcase")+":"+rowNum);
            String sheetName = m.getName();
            try {
                String response = new JSONServices().jsonRequest("Automation Test", "POST");
                //String nodeParsingForResponse =
                new JSONServices();
                System.out.println(response);
                excel.setCellData(sheetName, "Response", rowNum, response);
                excel.setCellData(sheetName, "execution status", rowNum, "Done");
                valuesMatched("title check", "Automation Test", JSONServices.response.jsonPath().getString("title"));
                valuesMatched("id check", "101", JSONServices.response.jsonPath().getString("id"));
                rowNum++;
            }
            catch (Exception e) {
                rowNum++;
                throw new RuntimeException(e);
            }
        }
    }
}
