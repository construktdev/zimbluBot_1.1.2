package de.construkter.ressources;

import de.construkter.utils.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class BotConfig {

    private Properties properties;

    public BotConfig() {
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        String fileName = "bot.txt";  // Name der Textdatei, die gelesen werden soll
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Entferne führende und nachfolgende Leerzeichen
                line = line.trim();
                // Überspringe leere Zeilen und Kommentare
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                // Teile die Zeile in Schlüssel und Wert auf
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    properties.setProperty(parts[0].trim(), parts[1].trim());
                } else {
                    Logger.event("Skipping invalid property line: " + line);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
