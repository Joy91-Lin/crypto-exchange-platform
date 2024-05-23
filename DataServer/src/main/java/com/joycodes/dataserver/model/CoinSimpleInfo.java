package com.joycodes.dataserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinSimpleInfo {
    private Integer market_cap_rank;
    private String symbol;
    private String name;
    private String image;
    private Float current_price;
    private Float market_cap;
    private Float price_change_percentage_24h;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Float getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(Float current_price) {
        this.current_price = current_price;
    }

    public Float getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(Float market_cap) {
        this.market_cap = market_cap;
    }

    public Integer getMarket_cap_rank() {
        return market_cap_rank;
    }

    public void setMarket_cap_rank(Integer market_cap_rank) {
        this.market_cap_rank = market_cap_rank;
    }

    public Float getPrice_change_percentage_24h() {
        return price_change_percentage_24h;
    }

    public void setPrice_change_percentage_24h(Float price_change_percentage_24h) {
        this.price_change_percentage_24h = price_change_percentage_24h;
    }
}
