package com.joycodes.dataserver.controller;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/ping")
@PropertySource("classpath:coingecko.properties")
public class PingController {

    @Autowired
    private Environment env;

    @GetMapping("test-connection")
    public String ping() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(env.getProperty("gecko.ping.url"))
                .get()
                .addHeader("accept", "application/json")
                .addHeader("x-cg-demo-api-key", env.getProperty("gecko.api-key"))
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
