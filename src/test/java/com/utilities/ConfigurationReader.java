package com.utilities;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigurationReader {
    private static Properties properties= new Properties();

    static{
        try {
            FileInputStream file = new FileInputStream("configuration.properties");

            InputStreamReader reader = new InputStreamReader(file, StandardCharsets.UTF_8);
            properties.load(reader);

            file.close();
        } catch (IOException e) {
            System.out.println("file not found in the configuration reader class");
        }
    }

    public static String getProperty(String keyword){

        return properties.getProperty(keyword);
    }
}
