package com.joycodes.dataserver.controller;

import com.joycodes.dataserver.model.CoinSimpleInfo;
import com.joycodes.dataserver.service.CoinService;
import com.joycodes.dataserver.service.PriceTrendService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/coins")
@PropertySource("classpath:coingecko.properties")
public class CoinsController {
    @Autowired
    private Environment env;

    @Autowired
    private CoinService coinService;

    @Autowired
    private PriceTrendService priceTrendService;

    @GetMapping("/markets/{rankRange}")
    public ResponseEntity<List<CoinSimpleInfo>> getMarketCoins(@PathVariable Integer rankRange) throws IOException {
        if(rankRange < 1 || rankRange > 10){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String jsonString = coinService.callGeckoApi(env.getProperty("gecko.coin.markets.usd.url")+rankRange.intValue(),
                env.getProperty("gecko.api-key"));
        coinService.saveCoins(jsonString);
        priceTrendService.saveCoinsPrice(jsonString);

        List<CoinSimpleInfo> coinSimpleInfos = coinService.getCoinsMarket(jsonString);

        return ResponseEntity.status(HttpStatus.OK).body(coinSimpleInfos);
    }

    @GetMapping("/trending")
    public ResponseEntity<List<CoinSimpleInfo>> getTrendingCoins() throws IOException {
        String jsonString = coinService.callGeckoApi(env.getProperty("gecko.coin.trending.url"),
                env.getProperty("gecko.api-key"));

        List<CoinSimpleInfo> coins = coinService.getTrendingCoins(jsonString);
        return ResponseEntity.status(HttpStatus.OK).body(coins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoinSimpleInfo> getCoin(@PathVariable String id) throws IOException {
        String jsonString = coinService.callGeckoApi(env.getProperty("gecko.coin.info.url")+id,
                env.getProperty("gecko.api-key"));
        JSONObject json = new JSONObject(jsonString);
        if(json.has("error")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        CoinSimpleInfo coin = coinService.getCoin(jsonString);

        return ResponseEntity.status(HttpStatus.OK).body(coin);
    }


}
