package com.virtual.loja;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    protected static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.trace("Trace - Entering method processOrder().");
        logger.debug("Debug - Received order with ID 12345.");
        logger.info("Info - Order shipped successfully.");
        logger.warn("Warn - Potential security vulnerability detected in user input: '...'");
        logger.error("Error - Failed to process order. Error: {. . .}");
        logger.fatal("Fatal - System crashed. Shutting down...");

        System.out.println("Hello world!");
    }
}