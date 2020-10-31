package com.stock.market.exception;

/**
 * Exception to handle when no given {@code stock} found.
 */
public class NoStockFoundException extends RuntimeException {

    public NoStockFoundException(String message) {
        super(message);
    }
}
