package com.joycodes.dataserver.service;

import com.joycodes.dataserver.model.Coin;
import com.joycodes.dataserver.model.CoinSimpleInfo;

import java.io.IOException;
import java.util.List;

public interface CoinService {
    public String callGeckoApi(String url, String apiKey) throws IOException;
    public List<Coin> saveCoins(String jsonBody);

    public List<CoinSimpleInfo> getCoinsMarket(String jsonBody);

    public List<CoinSimpleInfo> getTrendingCoins(String jsonBody);

    public CoinSimpleInfo getCoin(String jsonBody);

}
