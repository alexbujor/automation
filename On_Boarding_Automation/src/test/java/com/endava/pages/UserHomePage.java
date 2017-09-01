package com.endava.pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


/**
 * Created by acotet on 8/22/2017.
 */
public class UserHomePage {
    private WebDriver driver;


    public UserHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        load();
    }

    public boolean isOpened() {
        return "User home page title".equals(driver.getTitle());
    }


    protected void load() {
        driver.get("User home page link");
    }

    protected void isLoaded() throws Error {
        Assert.assertEquals("User home page title", driver.getTitle());
    }
}
