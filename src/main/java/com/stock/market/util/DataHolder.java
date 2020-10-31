package com.stock.market.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.market.data.Stock;
import com.stock.market.input.Trade;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads initial {@code stock} data and stores {@code trade} and {@code stock} data.
 */
public class DataHolder {
    @Getter
    private final List<Stock> stocks = new ArrayList<>();
    @Getter
    private final List<Trade> trades = new ArrayList<>();

    public DataHolder() throws IOException {
        init();
    }
    private void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        stocks.addAll(mapper.readValue(getClass().getClassLoader().getResource("initialStockDetails.json"), new TypeReference<List<Stock>>() {}));
    }

    public void addTrade(Trade trade) {
        trades.add(trade);
    }

    public boolean addStock(Stock stock) {
        return stocks.add(stock);
    }

}
