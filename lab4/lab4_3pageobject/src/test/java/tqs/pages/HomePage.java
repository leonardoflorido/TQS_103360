package tqs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;

    @FindBy(name = "fromPort")
    private WebElement fromPort;

    @FindBy(name = "toPort")
    private WebElement toPort;

    @FindBy(css = ".btn-primary")
    private WebElement findFlights;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://blazedemo.com/");
        driver.manage().window().setSize(new Dimension(1536, 835));
        PageFactory.initElements(driver, this);
    }

    public void selectFromPort(String from) {
        fromPort.click();
        fromPort.findElement(By.xpath("//option[. = '" + from + "']")).click();
    }

    public void selectToPort(String to) {
        toPort.click();
        toPort.findElement(By.xpath("//option[. = '" + to + "']")).click();
    }

    public void findFlights() {
        findFlights.click();
    }
}
