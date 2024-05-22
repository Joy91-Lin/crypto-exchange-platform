package com.joycodes.dataserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coin {
    @Id
    private String id;
    private String symbol;
    private String name;
    private String image;
    private Float total_supply;
    private Float max_supply;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Float getTotal_supply() {
        return total_supply;
    }

    public void setTotal_supply(Float total_supply) {
        this.total_supply = total_supply;
    }

    public Float getMax_supply() {
        return max_supply;
    }

    public void setMax_supply(Float max_supply) {
        this.max_supply = max_supply;
    }
}
