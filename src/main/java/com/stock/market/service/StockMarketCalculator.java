package com.stock.market.service;

import com.stock.market.calculate.Calculate;
import com.stock.market.data.Stock;
import com.stock.market.exception.NoStockFoundException;
import com.stock.market.input.Trade;
import com.stock.market.input.TradeType;
import com.stock.market.util.DataHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Calculator for StockMarket
 */
@AllArgsConstructor
@Slf4j
public class StockMarketCalculator {

    private final DataHolder memoryStore;

    private final Calculate calculate;

    /**
     * calculate the dividend yield based on {@code stockSymbol} and {@code price}
     *
     * @param stockSymbol for which dividend yield needs to calculated
     * @param price       against which dividend yield to be calculated
     * @return Dividend Yield
     */
    public Double calculateDividendYield(String stockSymbol, Double price) {
        log.info("calculating the dividend yield");
        Optional<Stock> stockOptional = memoryStore.getStocks().stream().filter(stock -> stock.getStockSymbol().equals(stockSymbol)).findAny();
        stockOptional.orElseThrow(() -> new NoStockFoundException("No Stock found for the given " + stockSymbol));
        return calculate.dividendYield(stockOptional.get(), price);
    }

    /**
     * calculate the dividend yield based on {@code stockSymbol} and {@code price}
     *
     * @param stockSymbol for which dividend yield needs to calculated
     * @param price       against which dividend yield to be calculated
     * @return PE Ratio
     */
    public Double calculatePERatio(String stockSymbol, Double price) {
        log.info("calculating the P/E ratio");
        Optional<Stock> stockOptional = memoryStore.getStocks().stream().filter(stock -> stock.getStockSymbol().equals(stockSymbol)).findAny();
        stockOptional.orElseThrow(() -> new NoStockFoundException("No Stock found for the given " + stockSymbol));
        return calculate.peRatio(stockOptional.get(), price);
    }

    /**
     * record any valid {@code trade}
     *
     * @param stockSymbol for the {@code trade}
     * @param price       for the {@code trade}
     * @param quantity    for {@code trade}
     * @param tradeType   for {@code trade}
     */
    public void recordTrade(String stockSymbol, Double price, Integer quantity, TradeType tradeType) {
        Trade trade = new Trade();
        trade.setPrice(price);
        trade.setQuantity(quantity);
        trade.setTimestamp(LocalDateTime.now());
        trade.setTradeType(tradeType);
        trade.setStockSymbol(stockSymbol);
        memoryStore.addTrade(trade);
    }

    /**
     * calculate GBCE All Share Index
     *
     * @return GBCE All Share Index
     */
    public Double calculateGBCE() {
        List<Double> allPrices = memoryStore.getTrades().stream().mapToDouble(Trade::getPrice).boxed().collect(Collectors.toList());
        return calculate.gbce(allPrices);
    }

    /**
     * Calculate Volume Weighted Stock Price based on trades in past 15 minutes
     *
     * @param stockSymbol for which Volume Weighted Stock Price needs to be calculated
     * @return Volume Weighted Stock Price
     */
    public Double calculateVWSP(String stockSymbol) {
        List<Trade> stockSpecificTrades = memoryStore.getTrades().stream().filter(trade -> trade.getStockSymbol().equals(stockSymbol)).collect(Collectors.toList());
        return calculate.vwsp(stockSpecificTrades);
    }
}
