package com.stock.market;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.market.calculate.Calculate;
import com.stock.market.input.TradeType;
import com.stock.market.util.DataHolder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * A sample main method which takes its input from a json files.
 */
@Slf4j
public class StockMarket {
    public static void main(String[] args) throws IOException {
        DataHolder dataHolder = new DataHolder();
        Calculate calculate = new Calculate();
        StockMarketCalculator marketCalculator = new StockMarketCalculator(dataHolder, calculate);
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, String>> stockInputs = mapper.readValue(StockMarket.class.getClassLoader().getResource("stockSampleInput.json"), new TypeReference<List<Map<String, String>>>() {
        });
        List<Map<String, String>> tradeInputs = mapper.readValue(StockMarket.class.getClassLoader().getResource("tradeSampleInput.json"), new TypeReference<List<Map<String, String>>>() {
        });
        log.info("recording the trades...");
        tradeInputs.forEach(tradeInput ->
            marketCalculator.recordTrade(String.valueOf(tradeInput.get("stockSymbol")), Double.valueOf(tradeInput.get("price")), Integer.parseInt(tradeInput.get("quantity")), TradeType.valueOf(tradeInput.get("tradeType")))
        );
        log.info("trades recorded");
        stockInputs.forEach(stockInput -> {
            String stockSymbol = stockInput.get("stockSymbol");
            Double price = Double.parseDouble(stockInput.get("price"));
            log.info("Dividend Yield for stock {} is {}", stockSymbol, marketCalculator.calculateDividendYield(stockSymbol, price));
            log.info("PE Ratio for stock {} is {}", stockSymbol, marketCalculator.calculatePERatio(stockSymbol, price));
            log.info("Volume Weighted Stock Price value for stock {} is {}", stockSymbol, marketCalculator.calculateVWSP(stockSymbol));
        });
        log.info("GBCE All share index value is {}", marketCalculator.calculateGBCE());
        log.info("Trades recorded are {}", dataHolder.getTrades());
    }
}
