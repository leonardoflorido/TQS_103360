package tqs;

// Generated by Selenium IDE

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.edge.EdgeDriver;
import tqs.pages.ConfirmationPage;
import tqs.pages.HomePage;
import tqs.pages.PurchasePage;
import tqs.pages.ReservePage;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class AppTest {
    private HomePage homePage;
    private ReservePage reservePage;
    private PurchasePage purchasePage;
    private ConfirmationPage confirmationPage;

    @BeforeEach
    public void initializeDriver(EdgeDriver driver) {
        homePage = new HomePage(driver);
        reservePage = new ReservePage(driver);
        purchasePage = new PurchasePage(driver);
        confirmationPage = new ConfirmationPage(driver);
    }

    @Test
    public void flight() {
        homePage.selectFromPort("Boston");
        homePage.selectToPort("Dublin");
        homePage.findFlights();

        reservePage.chooseFlight();

        purchasePage.fillOutForm("First Last", "123 Main St.", "Anytown", "State", "12345", "1234567890", "John Smith");

        purchasePage.purchaseFlight();

        assertThat(confirmationPage.getPendingCapture(), is("PendingCapture"));
        assertThat(confirmationPage.getTabTitle(), is("BlazeDemo Confirmation"));
        assert (confirmationPage.getConfirmationTable().size() > 0);
    }
}