package pages.AutomationDemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import testbase.TestBase;

public class RegistrationPage {
    //implementing using page factory model
    public RegistrationPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "#email")
    private WebElement emailInput;

    @FindBy(id = "#enterimg")
    private WebElement enterButton;

    @FindBy(xpath = "//input[@placeholder='First Name']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    private WebElement lastNameInput;

    @FindBy(css = "textarea[ng-model='Adress']")
    private WebElement addressInput;

    public void inputEmailAndEnter(String email) {
        emailInput.sendKeys(email);
        enterButton.click();
    }

    public void firstAndLastNameInput(String fName, String lName) {
        firstNameInput.sendKeys(fName);
        lastNameInput.sendKeys(lName);
    }

    public void addressInput(String address) {
        addressInput.sendKeys(address);
    }
}
