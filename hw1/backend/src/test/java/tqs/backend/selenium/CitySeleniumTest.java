package tqs.backend.selenium;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

@ExtendWith(SeleniumJupiter.class)
public class CitySeleniumTest {
    @Test
    public void airPollution(EdgeDriver driver) {
        driver.get("http://localhost:3000/");
        driver.manage().window().setSize(new Dimension(1552, 849));
        driver.findElement(By.cssSelector(".MuiInput-input")).click();
        driver.findElement(By.cssSelector(".MuiInput-input")).sendKeys("Porto");
        driver.findElement(By.cssSelector(".MuiInput-input")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("country-autocomplete")).click();
        driver.findElement(By.id("country-autocomplete")).sendKeys("Portugal");
        driver.findElement(By.id("country-autocomplete")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("country-autocomplete-option-0")).click();
        {
            WebElement element = driver.findElement(By.cssSelector(".MuiButton-contained > .MuiButton-label"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        driver.findElement(By.cssSelector(".MuiButton-contained > .MuiButton-label")).click();
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".MuiTableRow-root:nth-child(1) > .MuiTableCell-root:nth-child(2)"));
            assert (elements.size() > 0);
        }
        driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(2) > .MuiButton-label")).click();
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".MuiPaper-root:nth-child(3) .MuiTableBody-root .MuiTableCell-root:nth-child(1)"));
            assert (elements.size() > 0);
        }
    }
}
