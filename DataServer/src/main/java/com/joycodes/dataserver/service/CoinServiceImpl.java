package com.joycodes.dataserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joycodes.dataserver.model.Coin;
import com.joycodes.dataserver.model.CoinSimpleInfo;
import com.joycodes.dataserver.repository.CoinRepository;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoinServiceImpl implements CoinService {


    @Autowired
    private CoinRepository coinRepository;

    @Override
    public String callGeckoApi(String url, String apiKey) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("x-cg-demo-api-key", apiKey)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Transactional
    @Override
    public List<Coin> saveCoins(String jsonBody) {
        List<Coin> coins;
        ObjectMapper mapper = new ObjectMapper();
        try{
            coins = mapper.readValue(jsonBody, new TypeReference<List<Coin>>() {});
            coinRepository.saveAll(coins);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return coins;
    }

    @Override
    public List<CoinSimpleInfo> getCoinsMarket(String jsonBody){
        List<CoinSimpleInfo> coinSimpleInfos;
        ObjectMapper mapper = new ObjectMapper();
        try{
            coinSimpleInfos = mapper.readValue(jsonBody, new TypeReference<List<CoinSimpleInfo>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return coinSimpleInfos;
    }

    @Override
    public List<CoinSimpleInfo> getTrendingCoins(String jsonBody) {
        List<CoinSimpleInfo> trendingCoins = new ArrayList<>();
        JSONObject json = new JSONObject(jsonBody);
        JSONArray coins = json.getJSONArray("coins");
        coins.forEach(i->{
            JSONObject it = (JSONObject) i;
            JSONObject item = it.getJSONObject("item");

            CoinSimpleInfo coin = new CoinSimpleInfo();
            coin.setMarket_cap_rank(item.getInt("market_cap_rank"));
            coin.setSymbol(item.getString("symbol"));
            coin.setName(item.getString("name"));
            coin.setImage(item.getString("small"));

            JSONObject data = item.getJSONObject("data");
            coin.setCurrent_price(data.getFloat("price"));
            String market_cap = data.getString("market_cap").substring(1).replace(",","");
            coin.setMarket_cap(Float.parseFloat(market_cap));

            JSONObject price_change_percentage_24h = data.getJSONObject("price_change_percentage_24h");
            coin.setPrice_change_percentage_24h(price_change_percentage_24h.getFloat("usd"));

            trendingCoins.add(coin);
        });

        return trendingCoins;
    }

    @Override
    public CoinSimpleInfo getCoin(String jsonBody) {
        CoinSimpleInfo coin = new CoinSimpleInfo();

        JSONObject json = new JSONObject(jsonBody);
        coin.setMarket_cap_rank(json.getInt(  "market_cap_rank"));
        coin.setSymbol(json.getString("symbol"));
        coin.setName(json.getString("name"));
        coin.setImage(json.getJSONObject("image").getString("small"));
        JSONObject marketData = json.getJSONObject("market_data");
        coin.setCurrent_price(marketData.getJSONObject("current_price").getFloat("usd"));
        coin.setMarket_cap(marketData.getJSONObject("market_cap").getFloat("usd"));
        coin.setPrice_change_percentage_24h(marketData.getFloat("price_change_percentage_24h"));

        return coin;
    }
}
