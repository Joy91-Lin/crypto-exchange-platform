package com.joycodes.dataserver.service;

import com.joycodes.dataserver.model.Coin;
import com.joycodes.dataserver.model.CoinSimpleInfo;
import com.joycodes.dataserver.repository.CoinRepository;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@PropertySource("classpath:coingecko.properties")
public class CoinServiceImplTest {

    @Autowired
    private Environment env;
    @Autowired
    private CoinService coinService;

    @Test
    void callGeckoApi() throws IOException {
        String jsonString = coinService.callGeckoApi(env.getProperty("gecko.ping.url"), env.getProperty("gecko.api-key"));
        assertNotNull(jsonString);
    }

    @Transactional
    @Test
    void saveCoins() throws IOException {
        int rankRange = 10;
        String jsonString = coinService.callGeckoApi(env.getProperty("gecko.coin.markets.usd.url")+rankRange,
                env.getProperty("gecko.api-key"));
        List<Coin> coins = coinService.saveCoins(jsonString);
        assertEquals(rankRange, coins.size());
        assertNotNull(coins.get(0).getId());
        assertNotNull(coins.get(0).getName());
        assertNotNull(coins.get(0).getSymbol());
        assertNotNull(coins.get(rankRange-1).getId());
        assertNotNull(coins.get(rankRange-1).getName());
        assertNotNull(coins.get(rankRange-1).getSymbol());
    }

    @Test
    void getCoinsMarket() throws IOException {
        int rankRange = 10;
        String jsonString = coinService.callGeckoApi(env.getProperty("gecko.coin.markets.usd.url")+rankRange,
                env.getProperty("gecko.api-key"));
        assertNotNull(jsonString);
        List<CoinSimpleInfo> coinSimpleInfos = coinService.getCoinsMarket(jsonString);
        assertNotNull(coinSimpleInfos);
        assertEquals(rankRange, coinSimpleInfos.size());
        assertNotNull(coinSimpleInfos.get(0).getName());
        assertNotNull(coinSimpleInfos.get(0).getSymbol());
        assertNotNull(coinSimpleInfos.get(9).getName());
        assertNotNull(coinSimpleInfos.get(9).getSymbol());
    }

    @Test
    void getTrendingCoins() throws IOException {
        String jsonString = coinService.callGeckoApi(env.getProperty("gecko.coin.trending.url"),
                env.getProperty("gecko.api-key"));
        assertNotNull(jsonString);

        List<CoinSimpleInfo> coins = coinService.getTrendingCoins(jsonString);
        assertNotNull(coins);
        assertEquals(15, coins.size());
        assertNotNull(coins.get(0).getName());
        assertNotNull(coins.get(0).getSymbol());
        assertNotNull(coins.get(14).getName());
        assertNotNull(coins.get(14).getSymbol());
    }

    @Test
    void getCoin() throws IOException {
        String id = "bitcoin";
        String jsonString = coinService.callGeckoApi(env.getProperty("gecko.coin.info.url")+id,
                env.getProperty("gecko.api-key"));
        assertNotNull(jsonString);
        CoinSimpleInfo coin = coinService.getCoin(jsonString);
        assertNotNull(coin);
        assertNotNull(coin.getName());
        assertNotNull(coin.getSymbol());
    }
}