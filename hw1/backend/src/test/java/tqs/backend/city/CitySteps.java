package tqs.backend.city;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class CitySteps {
    private WebDriver driver;

    @Given("the user is on the homepage")
    public void the_user_is_on_the_home_page() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new EdgeDriver(options);
        driver.manage().window().setSize(new Dimension(1552, 849));
        driver.get("http://localhost:3000");
    }

    @When("the user searches air pollution for {string}")
    public void the_user_searches_air_pollution_for(String city) {
        driver.findElement(By.cssSelector(".MuiInput-input")).click();
        driver.findElement(By.cssSelector(".MuiInput-input")).sendKeys(city);
        driver.findElement(By.cssSelector(".MuiInput-input")).sendKeys(Keys.ENTER);
    }

    @And("the user selects {string} as country")
    public void the_user_selects_as_country(String country) {
        driver.findElement(By.id("country-autocomplete")).sendKeys(country);
        driver.findElement(By.id("country-autocomplete")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("country-autocomplete-option-0")).click();
    }

    @And("the user clicks on search button")
    public void the_user_clicks_on_search_button() {
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
    }

    @Then("the user should see the air pollution for {string}")
    public void the_user_should_see_the_air_pollution_for(String city) {
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".MuiTableRow-root:nth-child(1) > .MuiTableCell-root:nth-child(2)"));
            assert (elements.size() > 0);
        }
    }

    @Given("the user is on the cachepage")
    public void the_user_is_on_the_cachepage() {
        driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(2) > .MuiButton-label")).click();
        driver.get("http://localhost:3000/cache");
    }

    @Then("the user should see the air pollution for {string} in the cache")
    public void the_user_should_see_the_air_pollution_for_in_the_cache(String city) {
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".MuiPaper-root:nth-child(3) .MuiTableBody-root .MuiTableCell-root:nth-child(1)"));
            assert (elements.size() > 0);
        }
        driver.quit();
    }
}
