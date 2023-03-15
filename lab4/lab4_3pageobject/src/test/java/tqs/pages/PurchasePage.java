package tqs.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PurchasePage {
    @FindBy(id = "inputName")
    private WebElement inputNameField;

    @FindBy(id = "address")
    private WebElement addressField;

    @FindBy(id = "city")
    private WebElement cityField;

    @FindBy(id = "state")
    private WebElement stateField;

    @FindBy(id = "zipCode")
    private WebElement zipCodeField;

    @FindBy(id = "creditCardNumber")
    private WebElement creditCardNumberField;

    @FindBy(id = "nameOnCard")
    private WebElement nameOnCardField;

    @FindBy(css = ".checkbox")
    private WebElement rememberMeCheckbox;

    @FindBy(css = ".btn-primary")
    private WebElement purchaseFlightButton;


    public PurchasePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void fillOutForm(String name, String address, String city, String state, String zipCode, String creditCardNumber, String nameOnCard) {
        inputNameField.sendKeys(name);
        addressField.sendKeys(address);
        cityField.sendKeys(city);
        stateField.sendKeys(state);
        zipCodeField.sendKeys(zipCode);
        creditCardNumberField.sendKeys(creditCardNumber);
        nameOnCardField.sendKeys(nameOnCard);
    }

    public void checkRememberMe() {
        rememberMeCheckbox.click();
    }

    public void purchaseFlight() {
        purchaseFlightButton.click();
    }
}
