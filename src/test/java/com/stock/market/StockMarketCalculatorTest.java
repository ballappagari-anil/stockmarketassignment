package com.stock.market;

import com.stock.market.calculate.Calculate;
import com.stock.market.data.Stock;
import com.stock.market.exception.NoStockFoundException;
import com.stock.market.input.Trade;
import com.stock.market.input.TradeType;
import com.stock.market.util.DataHolder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class StockMarketCalculatorTest {
    private StockMarketCalculator classUnderTest;
    private static List<Stock> stockList;
    private static List<Trade> tradeList;
    private DataHolder dataHolder;

    @BeforeClass
    public static void init() throws Exception {
        TestDataInit testDataInit = new TestDataInit();
        stockList = testDataInit.getStocks();
        tradeList = testDataInit.getTrades();
    }

    @Before
    public void setUp() throws IOException {
        dataHolder = mock(DataHolder.class);
        Calculate calculate = new Calculate();
        classUnderTest = new StockMarketCalculator(dataHolder, calculate);
        when(dataHolder.getTrades()).thenReturn(tradeList);
        when(dataHolder.getStocks()).thenReturn(stockList);
    }

    @Test
    public void testCalculateDividendYield() {
        assertEquals(0.0625, classUnderTest.calculateDividendYield("GIN", 32.00), 0.1);
        assertEquals(0.5714, classUnderTest.calculateDividendYield("POP", 14.00), 0.1);
    }

    @Test
    public void testCalculatePERatio() {
        assertEquals(1.75, classUnderTest.calculatePERatio("POP", 14.00), 0.1);
    }

    @Test(expected = NoStockFoundException.class)
    public void testNoStockFoundExceptionOnDividendYield() {
        classUnderTest.calculateDividendYield("TES", 0.0);
    }

    @Test(expected = NoStockFoundException.class)
    public void testNoStockFoundExceptionOnPERatio() {
        classUnderTest.calculatePERatio("TES", 0.0);
    }

    @Test
    public void testRecordTrade() {
        classUnderTest.recordTrade("GIN", 12.0, 1, TradeType.BUY);
        verify(dataHolder, atLeastOnce()).addTrade(any(Trade.class));
    }

    @Test
    public void testCalculateGBCE() {
        assertEquals(18.507566707586086, classUnderTest.calculateGBCE(), 0.1);
    }

    @Test
    public void testCalculateVWSP() {
        assertEquals(24.75491464510332, classUnderTest.calculateVWSP("POP"), 0.1);
        assertEquals(13.14, classUnderTest.calculateVWSP("TEA"), 0.1);
    }
}