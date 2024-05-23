# Description
使用 Java Spring Boot 框架開發的後端服務，旨在與 CoinGecko API 交互，從中獲取加密貨幣市場數據，進行數據處理並使用 JPA 存儲到 MySQL 數據庫中。

# API Document
ping
---
  
| Method | Path                 | 說明                       |
|--------|----------------------|--------------------------|
| Get    | ping/test-connection | 測試是否能夠與 CoinGecko API 交互 |

### response 
200 text/plain
```
{"gecko_says":"(V3) To the Moon!"}
```



coins
---

| Method | Path                        | 參數                        | 說明              |
|--------|-----------------------------|---------------------------|-----------------|
| GET    | coins/markets/\{rankRange\} | rankRange\[int\]\-require<br>rankRange=1~10 | 取得市場排名的Coin 資訊  |
| GET    | coins/trending              | 無                         | 取得熱度前15的Coin 資訊 |
| GET    | coins/\{id\}                | id\[String\]\-require<br>refers to [coingecko id list](<https://docs.google.com/spreadsheets/d/1wTTuxXt8n9q7C4NDXqQpI3wpKu1_5bGVmP9Xz0XGSyU/edit#gid=0>)    | 取得Coin 資訊       |


### response 
200 application/json

* market_cap_rank[int]
* symbol[string]
* name[string]
* image[string]
* current_price[float]
* market_cap[float]
* price_change_percentage_24h[float]


example
```
{
    "market_cap_rank": 1,
    "symbol": "btc",
    "name": "Bitcoin",
    "image": "https://assets.coingecko.com/coins/images/1/small/bitcoin.png?1696501400",
    "current_price": 69838.0,
    "market_cap": 1.37869499E12,
    "price_change_percentage_24h": 0.00315
}
```


### How to start

1. After clone and cd to DataServer
2. Modify src/resources/application.properties and src/resources/coingecko.properties<br>Make sure to setup mysql database and coingecko api key.<br>You can get coingecko api key [here](<https://www.coingecko.com/zh-tw/api/pricing>)
3. Interact with APIs

