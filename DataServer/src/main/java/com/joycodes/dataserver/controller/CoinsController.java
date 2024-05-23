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

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(env.getProperty("gecko.coin.markets.usd.url")+rankRange.intValue())
                .get()
                .addHeader("accept", "application/json")
                .addHeader("x-cg-demo-api-key", env.getProperty("gecko.api-key"))
                .build();

        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();
        coinService.saveCoins(jsonString);
        priceTrendService.saveCoinsPrice(jsonString);

        List<CoinSimpleInfo> coinSimpleInfos = coinService.getCoinsMarket(jsonString);

        return ResponseEntity.status(HttpStatus.OK).body(coinSimpleInfos);
    }

    @GetMapping("/trending")
    public ResponseEntity<List<CoinSimpleInfo>> getTrendingCoins() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(env.getProperty("gecko.coin.trending.url"))
                .get()
                .addHeader("accept", "application/json")
                .addHeader("x-cg-demo-api-key", env.getProperty("gecko.api-key"))
                .build();

        Response response = client.newCall(request).execute();

        List<CoinSimpleInfo> coins = coinService.getTrendingCoins(response.body().string());
        return ResponseEntity.status(HttpStatus.OK).body(coins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoinSimpleInfo> getCoin(@PathVariable String id) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(env.getProperty("gecko.coin.info.url")+id)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("x-cg-demo-api-key", env.getProperty("gecko.api-key"))
                .build();

        Response response = client.newCall(request).execute();
        String jsonBody = response.body().string();
        JSONObject json = new JSONObject(jsonBody);
        if(json.has("error")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        CoinSimpleInfo coin = coinService.getCoin(jsonBody);

        return ResponseEntity.status(HttpStatus.OK).body(coin);
    }


}
