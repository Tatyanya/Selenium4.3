package com.epam;


import com.epam.tam.module4.task3.browser.Browser;
import com.epam.tam.module4.task3.page_object.GoogleHomePage;
import com.epam.tam.module4.task3.page_object.LipsumHomePage;
import com.epam.tam.module4.task3.util.SentanceSeparator;
import mx4j.tools.config.DefaultConfigurationBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;


public class AppTest {
    // private static final Logger LOG = LogManager.getLogger(AppTest.class);

    protected static final String START_URL = "http://lipsum.com/";

    protected static RemoteWebDriver driver = Browser.getInstance().getDriver();
    LipsumHomePage lipsumHomePage = new LipsumHomePage();
    GoogleHomePage sf = new GoogleHomePage();

    @BeforeClass(description = "Maximize window")
    public void maximizeBrowser() {
        driver.manage().window().maximize();
    }


    @Test()
    public void goToSite() throws InterruptedException, AWTException {

        //  LOG.info("Open start page");
        driver.navigate().to(START_URL);
        lipsumHomePage.openGeneratedPage();
        WebElement text = driver.findElement(By.xpath("//div[@id='lipsum']/p[2]"));
        SentanceSeparator ss;

        Actions act = new Actions(driver);

        String fullText = text.getText();

        ss = new SentanceSeparator(fullText);
        int first = ss.getLegthOfSentenceByID(0);
        int second = ss.getLegthOfSentenceByID(1);

        act.moveToElement(text, 0, 0).click().perform();

        act.keyDown(Keys.LEFT_SHIFT);

        for (int i = 0; i < first + second; i++) {

            act.sendKeys(Keys.ARROW_RIGHT);
        }

        act.keyUp(Keys.LEFT_SHIFT);
        act.build().perform();


        //    act.clickAndHold(text).moveToElement(text, first, 0);

        act.sendKeys(Keys.chord(Keys.CONTROL, "c")).build().perform();
        Thread.sleep(1000);

        //act.sendKeys(Keys.chord(Keys.CONTROL, "c"));

        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
        driver.navigate().to("http://google.com");
        Thread.sleep(2000);

        //       act.click(sf.searchField).sendKeys(Keys.chord(Keys.CONTROL, "v")).build().perform();

     /*   WebElement element = driver.findElement(By.name("q"));
        element.sendKeys(Keys.chord(Keys.CONTROL, "v"));
        Thread.sleep(1000);*/

        act.click(sf.searchField).sendKeys(Keys.chord(Keys.CONTROL, "v")).build().perform();

        sf.searchField.submit();
        Thread.sleep(3000);

        Assert.assertTrue(sf.results.isDisplayed(), "error");
    }

    @Test(dependsOnMethods = "goToSite", description = "LogIn to PL")
    public void failedLogIn() {

    }


    @AfterClass(description = "Stop Browser")
    public void stopBrowser() {
        driver.quit();
    }
}
