package com.stock.market.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * A plain POJO to store the {@code Stock} data
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {
    private String stockSymbol;
    private StockType type;
    private Integer lastDividend;
    private String fixedDividend;
    private Integer parValue;
}
