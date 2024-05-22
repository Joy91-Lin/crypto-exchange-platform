package com.joycodes.dataserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceTrend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int increaseId;
    private String id;
    private Float current_price;
    private Float market_cap;
    private Float market_cap_rank;
    private Float high_24h;
    private Float low_24h;
    private Float price_change_percentage_24h;
    private Timestamp last_updated;

    public int getIncreaseId() {
        return increaseId;
    }

    public void setIncreaseId(int increaseId) {
        this.increaseId = increaseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Float getMarket_cap_rank() {
        return market_cap_rank;
    }

    public void setMarket_cap_rank(Float market_cap_rank) {
        this.market_cap_rank = market_cap_rank;
    }

    public Float getHigh_24h() {
        return high_24h;
    }

    public void setHigh_24h(Float high_24h) {
        this.high_24h = high_24h;
    }

    public Float getLow_24h() {
        return low_24h;
    }

    public void setLow_24h(Float low_24h) {
        this.low_24h = low_24h;
    }

    public Float getPrice_change_percentage_24h() {
        return price_change_percentage_24h;
    }

    public void setPrice_change_percentage_24h(Float price_change_percentage_24h) {
        this.price_change_percentage_24h = price_change_percentage_24h;
    }

    public Timestamp getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(Timestamp last_updated) {
        this.last_updated = last_updated;
    }

}
