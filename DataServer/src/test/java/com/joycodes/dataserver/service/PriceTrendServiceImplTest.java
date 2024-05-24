package com.joycodes.dataserver.service;

import com.joycodes.dataserver.model.PriceTrend;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class PriceTrendServiceImplTest {

    @Autowired
    private Environment env;
    @Autowired
    private CoinService coinService;
    @Autowired
    private PriceTrendService priceTrendService;
    @Test
    void saveCoinsPrice() throws IOException {
        int rankRange = 10;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(env.getProperty("gecko.coin.markets.usd.url")+rankRange)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("x-cg-demo-api-key", env.getProperty("gecko.api-key"))
                .build();

        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();
        assertNotNull(jsonString);
        coinService.saveCoins(jsonString);
        List<PriceTrend> prices = priceTrendService.saveCoinsPrice(jsonString);
        assertEquals(rankRange, prices.size());
        assertNotNull(prices.get(0).getId());
        assertNotNull(prices.get(0).getCurrent_price());
        assertNotNull(prices.get(rankRange-1).getId());
        assertNotNull(prices.get(rankRange-1).getCurrent_price());
    }
}