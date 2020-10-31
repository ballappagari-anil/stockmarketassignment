package com.stock.market.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * A simple POJO object to store {@code trade}s
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trade {

    private Double price;

    private Integer quantity;

    private LocalDateTime timestamp;

    private TradeType tradeType;

    private String stockSymbol;
}
