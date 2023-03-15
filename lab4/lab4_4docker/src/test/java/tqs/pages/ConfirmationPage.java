package tqs.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ConfirmationPage {
    private WebDriver driver;

    @FindBy(css = "tr:nth-child(2) > td:nth-child(2)")
    private WebElement pendingCapture;

    @FindBy(css = ".table tbody tr")
    private List<WebElement> confirmationTable;

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getPendingCapture() {
        return pendingCapture.getText();
    }

    public String getTabTitle() {
        return driver.getTitle();
    }

    public List<WebElement> getConfirmationTable() {
        return confirmationTable;
    }
}
