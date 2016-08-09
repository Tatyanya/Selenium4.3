package com.epam;


import com.epam.tam.module4.task3.browser.Browser;
import com.epam.tam.module4.task3.page_object.GooglePages;
import com.epam.tam.module4.task3.page_object.LipsumPages;
import com.epam.tam.module4.task3.util.SentanceSeparator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;


public class AppTest {
    private static final Logger LOG = LogManager.getLogger(AppTest.class);

    protected static final String START_URL = "http://lipsum.com/";

    protected static RemoteWebDriver driver = Browser.getInstance().getDriver();
    LipsumPages lp = new LipsumPages();
    GooglePages sf = new GooglePages();
    JavascriptExecutor js = (JavascriptExecutor) driver;
    SentanceSeparator ss;
    Actions act = new Actions(driver);
    String query;

    @BeforeClass(description = "Maximize window")
    public void maximizeBrowser() {
        driver.manage().window().maximize();
    }


    @Test()
    public void selectionActions() throws InterruptedException {
        LOG.info("Stars Actions selection scenario");

        openStartPage();

        ss = new SentanceSeparator(lp.getTextOfParagraph());
        int first = ss.getLegthOfSentenceByID(0);
        int second = ss.getLegthOfSentenceByID(1);
        query = ss.getSentencesByID(1).toString();

        selectTextForActions(lp.secondParagraph(), act, first, second, query);
        copySelectionActions(act);
        //  openNewTabActions();
        openGooglePage();
        runSearchForActions(act);
        verifyResultsForAction();
    }

    @Test(description = "JS")
    public void selectionJS() throws InterruptedException {
        LOG.info("Stars JS selection scenario");

        openStartPage();

        String text = copyTextFromJS(lp.secondParagraphCSS);
        openGooglePage();

        runSearchForJS(text);
        verifyResultforJS();

    }

    public void openStartPage() {
        LOG.info("Open start page");
        driver.navigate().to(START_URL);
        lp.openGeneratedPage();
    }

    public void copySelectionActions(Actions act) {
        LOG.info("Copy selection");
        act.sendKeys(Keys.chord(Keys.CONTROL, "c")).build().perform();
    }

    public void openNewTabActions() {
        LOG.info("Open new tab");
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
    }

    public void selectTextForActions(WebElement text, Actions act, int first, int second, String str) throws InterruptedException {
        LOG.info("Perform selection");
        if (driver.getClass().getName().contains("Chrome")) {
            LOG.info("Конченный хром браузер");
            act.sendKeys(Keys.chord(Keys.CONTROL, "f")).sendKeys(str).sendKeys(Keys.ESCAPE).build().perform();
            Thread.sleep(3000);

        } else if (driver.getClass().equals(FirefoxDriver.class)) {
            act.moveToElement(text, 0, 0).click().perform();
            act.keyDown(Keys.LEFT_SHIFT);

            for (int i = 0; i < first + second; i++) {
                act.sendKeys(Keys.ARROW_RIGHT);
            }

            act.keyUp(Keys.LEFT_SHIFT);
            act.build().perform();
        }

    }

    public void runSearchForActions(Actions act) throws InterruptedException {
        LOG.info("Perform search");

        act.click(sf.searchField).sendKeys(Keys.chord(Keys.CONTROL, "v")).build().perform();
        sf.searchField.submit();
        Thread.sleep(3000);
    }

    public void verifyResultsForAction() {
        LOG.info("Perform search");
        Assert.assertTrue(sf.results.isDisplayed(), "There is no any results");
    }

    public void openGooglePage() {
        LOG.info("Open google page");
        driver.navigate().to("http://google.com");
    }


    public String copyTextFromJS(WebElement element) {
        return js.executeScript(
                "var text =(arguments[0].innerHTML.toString().split('.')[2]);\n" +
                        " var selection = window.getSelection();\n" +
                        " var range = document.createRange();\n" +
                        " range.selectNodeContents(arguments[0]);\n" +
                        " selection.removeAllRanges();\n" +
                        " selection.addRange(range);\n" +
                        " return window.getSelection().toString();", element).toString();
    }

    public void runSearchForJS(String text) {
        LOG.info("Perform search");
        js.executeScript("var input = document.getElementById(\"lst-ib\"); \n" +
                "input.value = arguments[0];\n" +
                "document.forms[0].submit();", text);
    }

    public void verifyResultforJS() {
        LOG.info("Perform search");
        assertNotNull(js.executeScript("return document.getElementById('resultStats');"), "There is no any results");

    }

    @AfterClass(description = "Stop Browser")
    public void stopBrowser() {
        driver.quit();
    }
}
