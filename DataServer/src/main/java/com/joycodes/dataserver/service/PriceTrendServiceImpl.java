package com.joycodes.dataserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joycodes.dataserver.model.Coin;
import com.joycodes.dataserver.model.PriceTrend;
import com.joycodes.dataserver.repository.PriceTrendRepository;
import com.squareup.okhttp.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PriceTrendServiceImpl implements PriceTrendService {

    @Autowired
    private PriceTrendRepository priceTrendRepository;

    @Transactional
    @Override
    public List<PriceTrend> saveCoinsPrice(String jsonBody) {
        List<PriceTrend> prices;
        ObjectMapper mapper = new ObjectMapper();
        try{
            prices = mapper.readValue(jsonBody, new TypeReference<List<PriceTrend>>() {});
            priceTrendRepository.saveAll(prices);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return prices;
    }
}
