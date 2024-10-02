package de.construkter.ressources;

import de.construkter.utils.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class RolesConfig {

    private final Properties properties;

    public RolesConfig() {
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        String fileName = "roles.txt";
        readFile(fileName, properties);
    }

    public static void readFile(String fileName, Properties properties) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    properties.setProperty(parts[0].trim(), parts[1].trim());
                } else {
                    Logger.event("Skipping invalid property line: " + line);
                }
            }
        } catch (IOException ex) {
            Logger.error("Failed to read the file " + fileName + ": \n" + ex.getMessage());
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
