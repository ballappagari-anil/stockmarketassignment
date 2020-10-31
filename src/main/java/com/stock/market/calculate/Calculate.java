package com.stock.market.calculate;

import com.stock.market.data.Stock;
import com.stock.market.input.Trade;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Calculate all types of stocks and trades
 */
public class Calculate {

    /**
     * calculate the dividend yield based on {@code StockType}
     * @param stock for which dividend yield needs to be calculated
     * @param price against which yield is calculated
     * @return dividendYield value
     */
    public Double dividendYield(Stock stock, Double price) {
        switch (stock.getType()) {
            case PREFERRED:
                return (((Double.parseDouble(stock.getFixedDividend()) / 100) * stock.getParValue()) / price);
            case COMMON:
                return (stock.getLastDividend() / price);
        }
        return 0d;
    }

    /**
     * calculate the PE ratio of the given {@code Stock}
     * @param stock for which pe ratio needs to be calculated
     * @param price against which pe ratios needs to be calculated
     * @return pe ratio value
     */
    public Double peRatio(Stock stock, Double price) {
        int dividend = stock.getLastDividend();
        return dividend == 0 ? 0: price / dividend;
    }

    /**
     * calculate GBCE All Share Index using geometric mean of all prices
     * @param allPrices for which GBCE All Share index to be calculated
     * @return gbce index value
     */
    public Double gbce(List<Double> allPrices) {
        return Math.pow(allPrices.stream().reduce(1.0, (p1, p2) -> p1 * p2), 1.0 / allPrices.size());
    }

    /**
     * Calculate Volume Weighted Stock Price based on {@code tradeList} in past 15 minutes
     * @param tradeList for which VWSP needs to be calculated
     * @return calculated VWSP value.
     */
    public Double vwsp(List<Trade> tradeList) {
        List<Trade> durationFilteredTradeList = tradeList.stream().filter(trade -> Duration.between(trade.getTimestamp(), LocalDateTime.now()).toMinutes() <= 15).collect(Collectors.toList());
        return durationFilteredTradeList.stream().mapToDouble(trade -> trade.getPrice() * trade.getQuantity()).sum() / durationFilteredTradeList.stream().mapToInt(Trade::getQuantity).sum();
    }
}
