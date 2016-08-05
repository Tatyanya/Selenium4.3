package com.epam.tam.module4.task3.page_object;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LipsumHomePage extends AbstractPage {

    public LipsumHomePage() {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(xpath = "//input[@name='generate']")
    private WebElement generateButton;

    public void openGeneratedPage() {
        generateButton.click();
    }

}
