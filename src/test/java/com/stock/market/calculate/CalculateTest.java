package com.stock.market.calculate;


import com.stock.market.TestDataInit;
import com.stock.market.data.Stock;
import com.stock.market.exception.NoStockFoundException;
import com.stock.market.input.Trade;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class CalculateTest {

    private static List<Stock> stockList;
    private static List<Trade> tradeList;
    @BeforeClass
    public static void init() throws IOException {
        TestDataInit testDataInit = new TestDataInit();
        stockList = testDataInit.getStocks();
        tradeList = testDataInit.getTrades();
    }
    private final Calculate classUnderTest = new Calculate();
    @Test
    public void dividendYield() {
        Optional<Stock> testStock = stockList.stream().filter(stock -> "TEA".equals(stock.getStockSymbol())).findFirst();
        testStock.orElseThrow(() -> new NoStockFoundException(""));
        assertEquals(0.0, classUnderTest.dividendYield(testStock.get(), 32.00), 0);
        testStock = stockList.stream().filter(stock -> "ALE".equals(stock.getStockSymbol())).findFirst();
        testStock.orElseThrow(() -> new NoStockFoundException(""));
        assertEquals(0.5, classUnderTest.dividendYield(testStock.get(), 46.00), 0);
        testStock = stockList.stream().filter(stock -> "GIN".equals(stock.getStockSymbol())).findFirst();
        testStock.orElseThrow(() -> new NoStockFoundException(""));
        assertEquals(0.5, classUnderTest.dividendYield(testStock.get(), 4.0), 0);
    }

    @Test
    public void peRatio() {
        Optional<Stock> testStock = stockList.stream().filter(stock -> "TEA".equals(stock.getStockSymbol())).findFirst();
        testStock.orElseThrow(() -> new NoStockFoundException(""));
        assertEquals(0.0, classUnderTest.peRatio(testStock.get(), 32.00), 0);
        testStock = stockList.stream().filter(stock -> "ALE".equals(stock.getStockSymbol())).findFirst();
        testStock.orElseThrow(() -> new NoStockFoundException(""));
        assertEquals(2, classUnderTest.peRatio(testStock.get(), 46.00), 0);
    }

    @Test
    public  void gbce() {
        List<Double> prices = tradeList.stream().mapToDouble(Trade::getPrice).boxed().collect(Collectors.toList());
        assertEquals(18.507566707586086, classUnderTest.gbce(prices), 0.1);
    }

    @Test
    public void vwsp() {
        List<Trade> stockSpecificTrades = tradeList.stream().filter(trade -> "POP".equals(trade.getStockSymbol())).collect(Collectors.toList());
        stockSpecificTrades.get(0).setTimestamp(LocalDateTime.now().minusMinutes(20));
        assertEquals(24.29740186915888, classUnderTest.vwsp(stockSpecificTrades), 0.1);
        stockSpecificTrades.get(0).setTimestamp(LocalDateTime.now().minusMinutes(1));
        assertEquals(24.75491464510332, classUnderTest.vwsp(stockSpecificTrades), 0.1);
    }
}