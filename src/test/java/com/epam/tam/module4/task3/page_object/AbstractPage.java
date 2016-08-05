package com.epam.tam.module4.task3.page_object;

import com.epam.tam.module4.task3.browser.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Created by Tatyana_Korobitsina on 8/2/2016.
 */
public class AbstractPage {
    protected RemoteWebDriver driver;

    public AbstractPage() {
        this.driver = Browser.getInstance().getDriver();
    }

    public WebDriver getDriver() {
        return this.driver;
    }

}
