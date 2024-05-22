package com.joycodes.dataserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joycodes.dataserver.model.Coin;
import com.joycodes.dataserver.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoinServiceImpl implements CoinService  {

    @Autowired
    private CoinRepository coinRepository;

    @Override
    public List<Coin> saveCoins(String jsonBody) {
        List<Coin> coins = new ArrayList<>();
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
    public Coin getCoin(String id) {
        return null;
    }
}
