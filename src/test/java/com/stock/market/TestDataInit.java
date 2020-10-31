package com.stock.market;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.market.data.Stock;
import com.stock.market.input.Trade;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class TestDataInit {

    private final ObjectMapper mapper = new ObjectMapper();

    public List<Stock> getStocks() throws IOException {
        return mapper.readValue(getClass().getClassLoader().getResource("initialStockDetails.json"), new TypeReference<List<Stock>>() {});
    }

    public List<Trade> getTrades() throws IOException {
        List<Trade> tradeList = mapper.readValue(getClass().getClassLoader().getResource("testTrades.json"), new TypeReference<List<Trade>>() {
        });
        for(Trade trade : tradeList) {
            LocalDateTime now = LocalDateTime.now();
            Random random = new Random();
            now = now.minusMinutes(random.nextInt(15));
            trade.setTimestamp(now);
        }
        return tradeList;
    }
}
