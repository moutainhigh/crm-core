package com.cafe.crm.configs.loggers;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class Hibernate {

    @Bean(name = "hibLogger")
    public Logger getHibLogger() {
        LoggerContext logCtx = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logSQL = logCtx.getLogger("org.hibernate");
        logSQL.setLevel(Level.INFO);
        return logSQL;
    }

}
