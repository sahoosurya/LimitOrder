package org.afob.limit;

import org.afob.execution.ExecutionClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class LimitOrderAgentTest {
    private ExecutionClient executionClient;
    private LimitOrderAgent limitOrderAgent;
    @Before
    public void setUp() {
        executionClient = Mockito.mock(ExecutionClient.class);
        limitOrderAgent = new LimitOrderAgent(executionClient);
    }

    @Test
    public void priceLessThan100() throws ExecutionClient.ExecutionException {
//  below $100
        limitOrderAgent.priceTick("IBM", new BigDecimal("80.00"));
        Mockito.verify(executionClient).buy("IBM", 1000);
    }
    @Test
    public void priceGreaterThan100() throws ExecutionClient.ExecutionException {
        limitOrderAgent.priceTick("IBM", new BigDecimal("120.25"));
        Mockito.verify(executionClient).sell("IBM", 500);
    }
}