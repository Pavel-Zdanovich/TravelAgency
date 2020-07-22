package com.zdanovich.core.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.AppenderRefComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import java.net.URI;

@Plugin(name = "LoggingConfiguration", category = ConfigurationFactory.CATEGORY)
@Order(value = 50)
public class LoggingConfiguration extends ConfigurationFactory {

    public Configuration createConfiguration(String configurationName) {
        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationFactory.newConfigurationBuilder();

        builder.setConfigurationName(configurationName);
        builder.setStatusLevel(Level.ERROR);

        LayoutComponentBuilder layout = builder.newLayout("PatternLayout").
                addAttribute("pattern", "%d{HH:mm:ss.SSS} [%t] %-5level: %msg%n%throwable");

        AppenderComponentBuilder console = builder.newAppender("Stdout", "CONSOLE").
                addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT).
                add(layout);

        AppenderRefComponentBuilder appenderRef = builder.newAppenderRef("Stdout");

        LoggerComponentBuilder logger = builder.
                newLogger("com.zdanovich", Level.DEBUG).
                add(appenderRef).
                addAttribute("additivity", false);

        LoggerComponentBuilder springLogger = builder.
                newLogger("org.springframework", Level.DEBUG).
                add(appenderRef).
                addAttribute("additivity", false);

        LoggerComponentBuilder hibernateLogger = builder.
                newLogger("org.hibernate", Level.DEBUG).
                add(appenderRef).
                addAttribute("additivity", false);

        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.ERROR).
                add(appenderRef);

        builder.add(console);
        builder.add(logger);
        builder.add(springLogger);
        builder.add(hibernateLogger);
        builder.add(rootLogger);

        return builder.build();
    }

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource configurationSource) {
        return createConfiguration(configurationSource.toString());
    }

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, String name, URI configLocation) {
        Configuration configuration = super.getConfiguration(loggerContext, name, configLocation);
        return configuration != null ? configuration : createConfiguration(name);
    }

    @Override
    protected String[] getSupportedTypes() {
        return new String[] { "*" };
    }
}