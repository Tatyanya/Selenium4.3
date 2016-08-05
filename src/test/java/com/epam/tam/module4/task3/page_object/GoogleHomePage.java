package com.epam.tam.module4.task3.page_object;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Tatyana_Korobitsina on 8/5/2016.
 */
public class GoogleHomePage extends AbstractPage {

    public GoogleHomePage() {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(name = "q")
    public WebElement searchField;

    @FindBy(xpath = "//div[@id='res']//div[@id='rso']/div[@class='srg']")
    public WebElement results;


}
