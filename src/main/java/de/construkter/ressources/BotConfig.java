package de.construkter.ressources;

import de.construkter.utils.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class BotConfig {

    private final Properties properties;

    public BotConfig() {
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        String fileName = "bot.txt";
        RolesConfig.readFile(fileName, properties);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
