package com.jeffyjamzhd.jeffylib;

import btw.BTWAddon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JeffyLib extends BTWAddon {
    private static JeffyLib instance;
    private static Logger logger;

    public JeffyLib() {
        super();
        instance = this;
        logger = LogManager.getLogger(this.getName());
    }

    @Override
    public void initialize() {
        logInfo("The little Jeffies are working tirelessly... | {} v{}", this.getName(), this.getVersionString());
    }

    public void logInfo(String message, Object... params) {
        logger.info(message, params);
    }

    public void logInfo(String message) {
        logInfo(message, (Object) null);
    }
}