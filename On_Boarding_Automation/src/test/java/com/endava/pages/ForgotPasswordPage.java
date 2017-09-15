package com.endava.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by acotet on 8/30/2017.
 */
public class ForgotPasswordPage {

    protected WebDriver driver;
    @FindBy(id = "submit")
    private WebElement btn_resetPass;

    @FindBy(id = "email")
    private WebElement txtbox_Email;

    @FindBy(css = ".info-message")
    private WebElement errorMessageWrongEmail;

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void fillEmail(String sEmail) {
        txtbox_Email.sendKeys(sEmail);
    }

    public void resetPassword() {
        btn_resetPass.click();
    }

    public boolean errorMessageForWrongEmail() {
        if (!errorMessageWrongEmail.isDisplayed())
            return false;


        return true;

    }

    public boolean isOpened() {
        return btn_resetPass.isDisplayed();
    }
}

