package configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;

    public ConfigLoader() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/config.properties"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public String[] getCriteria() {
        return properties.getProperty("search.criteria").split(",");
    }
}