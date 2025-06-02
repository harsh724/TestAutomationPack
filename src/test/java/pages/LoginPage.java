package pages;


import testbase.TestBase;

import static utilities.Utilities.*;

public class LoginPage extends TestBase {

    public void initialLogin() throws InterruptedException {
        login();
        Thread.sleep(3000);
        sendKeys("userNameField", "Admin");
        Thread.sleep(5000);
        sendKeys("passwordField", "admin123");
        onClick("loginButton");
        logOut();
    }

    public void logOut(){
        try {
            onClick("logoutDropDown");
            onClick("logoutButton");
            waitForElementToBeVisible("userNameField", 30);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void login(){
        sendKeys("userNameField", "Admin");
        sendKeys("passwordField", "admin123");
        onClick("loginButton");
    }
}
