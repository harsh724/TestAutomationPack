package PageControllers;

import org.openqa.selenium.WebDriver;
import pages.AutomationDemo.RegistrationPage;

public class RegistrationController {
    private WebDriver driver;
    private RegistrationPage registrationPage;

    public RegistrationController(WebDriver driver) {
        this.driver = driver;
        this.registrationPage = new RegistrationPage(driver);
    }

    public void Registration(String email, String fName, String lName, String address) {
        registrationPage.inputEmailAndEnter(email);
        registrationPage.firstAndLastNameInput(fName, lName);
        registrationPage.addressInput(address);
    }
}
