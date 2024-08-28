package org.afob.limit;

import org.afob.execution.ExecutionClient;
import org.afob.prices.PriceListener;

import java.math.BigDecimal;

public class LimitOrderAgent implements PriceListener {
    public static final String PRODUCT_ID="IBM";
    public static final BigDecimal PRICE= BigDecimal.valueOf(100);
    public static final String BUY= "buy";
    public static final String SELL= "sell";



    private final ExecutionClient executionClient;

    public LimitOrderAgent(final ExecutionClient ec) {
        this.executionClient=ec;

    }

    @Override
    public void priceTick(String productId, BigDecimal price) {
        if(productId.equals(PRODUCT_ID) && price.compareTo(PRICE) < 0)
        {
addOrder(BUY,PRODUCT_ID,1000,price);
        }

    }
    public void addOrder(String type,String productId,int amount,BigDecimal limitPrice)  {
        try {
            if (type.equalsIgnoreCase(BUY) && limitPrice.compareTo(PRICE) <= 0) {
                executionClient.buy(productId, amount);
            }
            if(type.equalsIgnoreCase(SELL) && limitPrice.compareTo(PRICE) >= 0)
            {
                executionClient.sell(productId, amount);

            }
        }
        catch (ExecutionClient.ExecutionException e)
        {
            System.err.println("Failed to add order" + e.getMessage());
        }
    }

}
