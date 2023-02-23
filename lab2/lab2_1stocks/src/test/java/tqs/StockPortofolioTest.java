package tqs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StockPortofolioTest {
    // 1. Prepare a mock to substitute the remote service (@Mock annotation)
    @Mock
    private IStockmarketService market;

    // 2. Create an instance of the subject under test (SuT) and use the mock to set the (remote) service instance
    @InjectMocks
    private StocksPortfolio portfolio;

    @Test
    public void getTotalValueTestAnnotation() {
        // 3. Load the mock with the proper expectations (when...thenReturn)
        when(market.lookUpPrice("EBAY")).thenReturn(4.0);
        when(market.lookUpPrice("MSFT")).thenReturn(1.5);

        // 4. Execute the test (use the service in the SuT)
        portfolio.addStock(new Stock("EBAY", 2));
        portfolio.addStock(new Stock("MSFT", 4));
        double result = portfolio.getTotalValue();

        // 5. Verify the result (assert) and the use of the mock (verify)
        assertEquals(14.0, result);
        verify(market, times(1)).lookUpPrice("EBAY");
        verify(market, times(1)).lookUpPrice("MSFT");
    }

    @Test
    public void getTotalValueTest() {
        // 1. Prepare a mock to substitute the remote service (@Mock annotation)
        IStockmarketService mockedMarket = mock(IStockmarketService.class);

        // 2. Create an instance of the subject under test (SuT) and use the mock to set the (remote) service instance
        StocksPortfolio myStocks = new StocksPortfolio(mockedMarket);

        // 3. Load the mock with the proper expectations (when...thenReturn)
        when(mockedMarket.lookUpPrice("EBAY")).thenReturn(4.0);
        when(mockedMarket.lookUpPrice("MSFT")).thenReturn(1.5);

        // 4. Execute the test (use the service in the SuT)
        myStocks.addStock(new Stock("EBAY", 2));
        myStocks.addStock(new Stock("MSFT", 4));
        double result = myStocks.getTotalValue();

        // 5. Verify the result (assert) and the use of the mock (verify)
        assertEquals(14.0, result);
        verify(mockedMarket, times(1)).lookUpPrice("EBAY");
        verify(mockedMarket, times(1)).lookUpPrice("MSFT");
    }

    @Test
    public void getTotalValueTestHamcrest() {
        // 1. Prepare a mock to substitute the remote service (@Mock annotation)
        IStockmarketService mockedMarket = mock(IStockmarketService.class);

        // 2. Create an instance of the subject under test (SuT) and use the mock to set the (remote) service instance
        StocksPortfolio myStocks = new StocksPortfolio(mockedMarket);

        // 3. Load the mock with the proper expectations (when...thenReturn)
        when(mockedMarket.lookUpPrice("EBAY")).thenReturn(4.0);
        when(mockedMarket.lookUpPrice("MSFT")).thenReturn(1.5);

        // 4. Execute the test (use the service in the SuT)
        myStocks.addStock(new Stock("EBAY", 2));
        myStocks.addStock(new Stock("MSFT", 4));
        double result = myStocks.getTotalValue();

        // 5. Verify the result (assert) and the use of the mock (verify)
        assertThat(result, equalTo(14.0));
        assertThat(result, notNullValue());
        verify(mockedMarket, times(1)).lookUpPrice("EBAY");
        verify(mockedMarket, times(1)).lookUpPrice("MSFT");
    }
}
