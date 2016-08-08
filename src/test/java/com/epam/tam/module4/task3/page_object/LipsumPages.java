package com.epam.tam.module4.task3.page_object;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LipsumPages extends AbstractPage {

    public LipsumPages() {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(xpath = "//div[@id='lipsum']/p[2]")
    private WebElement secondParagraph;

    @FindBy(css = "#lipsum p:nth-child(2)")
    public WebElement secondParagraphCSS;

    @FindBy(xpath = "//input[@name='generate']")
    public WebElement generateButton;


    public void openGeneratedPage() {
        generateButton.click();
    }

    public WebElement secondParagraph() {
        return secondParagraph;
    }

    public String getTextOfParagraph() {
      return  secondParagraph.getText();
    }


}
