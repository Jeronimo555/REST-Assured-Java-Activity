package com.globant.automation.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class TestRunner {

    private static final String PROPERTIES_FILE = "src/test/resources/config.properties";
    private static final Properties PROPERTIES = new Properties();

    @Getter
    private static String baseurl;

    @Getter
    private static String apikey;

    @BeforeSuite
    public void setupEnvironment() {
        loadProperties();
        baseurl = PROPERTIES.getProperty("url.base");
        apikey = PROPERTIES.getProperty("apikey");
    }

    private void loadProperties() {
        try {
            FileInputStream inputStream = new FileInputStream(PROPERTIES_FILE);
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
