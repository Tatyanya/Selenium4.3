package com.epam.tam.module4.task3.browser;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

public class Browser {
    private static final long IMPLICITY_WAIT = 60L;
    private static Browser instance;
    private static RemoteWebDriver driver;

    public static Browser getInstance() {
        if (instance == null) {
            driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(IMPLICITY_WAIT, TimeUnit.SECONDS);
            instance = new Browser();
        }
        return instance;
    }

    public static RemoteWebDriver getDriver() {
        return driver;
    }

}
