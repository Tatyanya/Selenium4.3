package com.epam.tam.module4.task3.page_object;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GooglePages extends AbstractPage {

    public GooglePages() {
        super();
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(name = "q")
    public WebElement searchField;

    @FindBy(xpath = "//div[@id='res']//div[@id='rso']/div[@class='srg']")
    public WebElement results;


}
