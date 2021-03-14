package hu.xannosz.tyho.factory;

import hu.xannosz.tyho.util.Configuration;

public class ConfigurationFactory {
    private static Configuration CONFIGURATION;

    public static Configuration getConfiguration() {
        if (CONFIGURATION == null) {
            CONFIGURATION = new Configuration();
        }
        return CONFIGURATION;
    }
}
