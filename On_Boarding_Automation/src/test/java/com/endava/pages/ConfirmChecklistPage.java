package com.endava.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by acotet on 9/14/2017.
 */
public class ConfirmChecklistPage {

    private WebDriver driver;

    @FindBy(id= "finishText")

    private WebElement finishText;

    public ConfirmChecklistPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isOpened() {
        return finishText.isDisplayed();
    }
}
