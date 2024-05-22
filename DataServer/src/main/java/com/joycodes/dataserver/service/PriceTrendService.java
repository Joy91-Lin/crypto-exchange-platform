package com.joycodes.dataserver.service;

import com.joycodes.dataserver.model.PriceTrend;

import java.util.List;

public interface PriceTrendService {
    public List<PriceTrend> saveCoinsPrice(String jsonBody);
}
