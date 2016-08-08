package com.epam;


import com.epam.tam.module4.task3.browser.Browser;
import com.epam.tam.module4.task3.page_object.GoogleHomePage;
import com.epam.tam.module4.task3.page_object.LipsumHomePage;
import com.epam.tam.module4.task3.util.SentanceSeparator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;


public class AppTest {
    // private static final Logger LOG = LogManager.getLogger(AppTest.class);

    protected static final String START_URL = "http://lipsum.com/";

    protected static RemoteWebDriver driver = Browser.getInstance().getDriver();
    LipsumHomePage lipsumHomePage = new LipsumHomePage();
    GoogleHomePage sf = new GoogleHomePage();
    JavascriptExecutor js = (JavascriptExecutor) driver;

    @BeforeClass(description = "Maximize window")
    public void maximizeBrowser() {
        driver.manage().window().maximize();
    }


    @Test()
    public void selectionActions() throws InterruptedException {

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

    @Test(description = "JS")
    public void selectionJS() throws InterruptedException {

        driver.navigate().to(START_URL);
        lipsumHomePage.openGeneratedPage();

        WebElement paragraph = driver.findElement(By.cssSelector("#lipsum p:nth-child(2)"));
        String text = copyTextFrom(paragraph);
        driver.navigate().to("google.com");
        runSearchFor(text);
        verifyResult();

    }


    public String copyTextFrom(WebElement element) {
        return js.executeScript(
                "var text =(arguments[0].innerHTML.toString().split('.')[2]);\n" +
                        "var selection = window.getSelection();\n" +
                        "var range = document.createRange();\n" +
                        "  range.selectNodeContents(arguments[0]);\n" +
                        " selection.removeAllRanges();\n" +
                        " selection.addRange(range);\n" +
                        " return window.getSelection().toString();", element).toString();
    }

    public void runSearchFor(String text) {
        js.executeScript("var input = document.getElementById(\"lst-ib\"); \n" +
                "input.value = arguments[0];\n" +
                "document.forms[0].submit();", text);
    }

    public void verifyResult() {
        //js.executeScript("return document.getElementById('resultStats');");
        assertNotNull(js.executeScript("return document.getElementById('resultStats');"),"Null results");

    }

    @AfterClass(description = "Stop Browser")
    public void stopBrowser() {
        driver.quit();
    }
}
