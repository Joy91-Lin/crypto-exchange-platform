package com.joycodes.dataserver.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CoinsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getMarketCoinsSuccess() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/coins/markets/2");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol", notNullValue()))
                .andExpect(jsonPath("$[0].name", notNullValue()));
    }

    @Test
    void getMarketCoinsFail() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/coins/markets/0");

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());

        request = MockMvcRequestBuilders
                .get("/coins/markets/11");

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());

        request = MockMvcRequestBuilders
                .get("/coins/markets/string");

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void getTrendingCoins() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/coins/trending");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol", notNullValue()))
                .andExpect(jsonPath("$[0].name", notNullValue()));
    }

    @Test
    void getCoinSuccess() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/coins/bitcoin");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol", notNullValue()))
                .andExpect(jsonPath("$.name", notNullValue()));
    }

    @Test
    void getCoinFail() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/coins/bb");

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());
    }
}