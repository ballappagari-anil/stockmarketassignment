package com.stock.market.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Type of {@code stock}
 */
public enum StockType {
    @JsonProperty("type")
    PREFERRED("Preferred"),
    @JsonProperty("type")
    COMMON("Common");
    private final String type;
    StockType(String type) {
        this.type = type;
    }
    @JsonValue
    public String getType() {
        return type;
    }
}
