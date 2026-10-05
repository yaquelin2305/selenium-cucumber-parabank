package com.parabank.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Lee la configuración desde config.properties.
 * Un valor pasado por línea de comandos (-Dclave=valor) tiene prioridad sobre el archivo.
 */
public final class ConfigReader {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new IllegalStateException("No se encontró config.properties en src/test/resources");
            }
            PROPS.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo leer config.properties", e);
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        String fromCommandLine = System.getProperty(key);
        if (fromCommandLine != null) {
            return fromCommandLine;
        }
        String value = PROPS.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Falta la propiedad en config.properties: " + key);
        }
        return value.trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}