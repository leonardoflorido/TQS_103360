package tqs.flight;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FlightSteps {
    private final EdgeDriver driver = new EdgeDriver();

    @Given("the user is on the homepage")
    public void i_am_on_the_home_page() {
        driver.get("https://blazedemo.com/");
        driver.manage().window().setSize(new Dimension(1536, 835));
    }

    @When("the user selects {string} from the {string} dropdown")
    public void the_user_selects_from_the_dropdown(String city, String select) {
        if (select.equals("from")) {
            driver.findElement(By.name("fromPort")).click();
            {
                WebElement dropdown = driver.findElement(By.name("fromPort"));
                dropdown.findElement(By.xpath("//option[. = '" + city + "']")).click();
            }
        } else if (select.equals("to")) {
            driver.findElement(By.name("toPort")).click();
            {
                WebElement dropdown = driver.findElement(By.name("toPort"));
                dropdown.findElement(By.xpath("//option[. = '" + city + "']")).click();
            }
        }
    }

    @And("the user clicks the {string} button")
    public void the_user_clicks_the_button(String button) {
        switch (button) {
            case "Find Flights", "Purchase Flight" -> driver.findElement(By.cssSelector(".btn-primary")).click();
            case "Remember Me" -> driver.findElement(By.id("rememberMe")).click();
        }
    }

    @Then("the user should see the {string} page")
    public void the_user_should_see_page(String page) {
        switch (page) {
            case "reserve" -> assertThat(driver.getTitle(), is("BlazeDemo - reserve"));
            case "purchase" -> assertThat(driver.getTitle(), is("BlazeDemo Purchase"));
            case "confirmation" -> assertThat(driver.getTitle(), is("BlazeDemo Confirmation"));
        }
    }

    @When("the user selects the flight number {int} in the list")
    public void the_user_selects_the_flight_number_in_the_list(int flightNumber) {
        driver.findElement(By.cssSelector("tr:nth-child(" + flightNumber + ") .btn")).click();
    }

    @When("the user enters {string} in the {string} field")
    public void when_the_user_enters_in_the_field(String input, String field) {
        switch (field) {
            case "Name" -> driver.findElement(By.id("inputName")).sendKeys(input);
            case "Address" -> driver.findElement(By.id("address")).sendKeys(input);
            case "City" -> driver.findElement(By.id("city")).sendKeys(input);
            case "State" -> driver.findElement(By.id("state")).sendKeys(input);
            case "Zip Code" -> driver.findElement(By.id("zipCode")).sendKeys(input);
            case "Credit Card Number" -> driver.findElement(By.id("creditCardNumber")).sendKeys(input);
            case "Name on Card" -> driver.findElement(By.id("nameOnCard")).sendKeys(input);
        }
    }
}