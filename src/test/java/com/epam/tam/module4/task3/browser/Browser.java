package com.epam.tam.module4.task3.browser;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Browser {
    private static final long IMPLICITY_WAIT = 60L;
    private static Browser instance;
    private static RemoteWebDriver driver;

    public static Browser getInstance() {
        if (instance == null) {
            File file = new File("d:\\TAM\\grid\\chromedriver.exe");

            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
            // driver = new FirefoxDriver();
            driver = new ChromeDriver();


            driver.manage().timeouts().implicitlyWait(IMPLICITY_WAIT, TimeUnit.SECONDS);
            instance = new Browser();
        }
        return instance;
    }

    public static RemoteWebDriver getDriver() {
        return driver;
    }

}
