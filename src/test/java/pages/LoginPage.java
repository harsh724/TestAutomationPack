package pages;


import testbase.TestBase;

import static utilities.Utilities.*;

public class LoginPage extends TestBase {

    public void initialLogin() throws InterruptedException {
        sendKeys("//input[@name=\"username\"]", "Admin");
        sendKeys("//input[@name=\"password\"]", "admin123");
        onClick("//button[text() = ' Login ']");
        Thread.sleep(3000);
        sendKeys("//input[@name=\"username\"]", "Admin");
        Thread.sleep(10000);
        sendKeys("//input[@name=\"password\"]", "admin123");
        onClick("//button[text() = ' Login ']");
        logOut();
    }

    public void logOut(){
        try {
            waitForElementToBeClickable("//i[@class='oxd-icon bi-caret-down-fill oxd-userdropdown-icon']", 30).click();
            waitForElementToBeVisible("//a[@class='oxd-userdropdown-link' and text() = 'Logout']", 30).click();
            waitForElementToBeVisible("//input[@name=\"username\"]", 30);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void login(){
        sendKeys("//input[@name=\"username\"]", "Admin");
        sendKeys("//input[@name=\"password\"]", "admin123");
        onClick("//button[text() = ' Login ']");
    }
}
