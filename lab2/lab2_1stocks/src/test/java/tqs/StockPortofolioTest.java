package tqs;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StockPortofolioTest {
    @InjectMocks
    private StocksPortfolio myStocks;

    @Mock
    private IStockmarketService marketService;

    @Test
    public void getTotalValue() {
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
    }
}
