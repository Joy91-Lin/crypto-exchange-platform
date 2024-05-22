package com.joycodes.dataserver.service;

import com.joycodes.dataserver.model.Coin;

import java.util.List;

public interface CoinService {
    public List<Coin> saveCoins(String jsonBody);
    public Coin getCoin(String id);

}
