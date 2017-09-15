package com.endava.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Created by acotet on 8/22/2017.
 */
public class ChangePasswordPage {
    private WebDriver driver;
    @FindBy(id = "password")
    private WebElement newPassword;
    @FindBy(css = ".showBtn")
    private WebElement showPassword;
    @FindBy(css = ".hideBtn")
    private WebElement hidePassword;
    @FindBy(id = "errorMessage")
    private WebElement errorMessage;
    @FindBy(css = ".shown-label.italic")
    private WebElement hintFormatPassword;
    @FindBy(id = "passwordConfirm")
    private WebElement confirmPassword;
    @FindBy(id = "submit")
    private WebElement btnChangePassword;
    @FindBy(css = ".glyphicon.glyphicon-ok.form-control-feedback")
    private WebElement newPassGreenValidation;
    @FindBy(css = ".glyphicon.glyphicon-remove.form-control-feedback")
    private WebElement newPassRedValidation;
    @FindBy(css = ".glyphicon.glyphicon-remove.form-control-feedback")
    private WebElement confirmPassRedValidation;
    @FindBy(css = ".glyphicon.glyphicon-ok.form-control-feedback")
    private WebElement confirmPassGreenValidation;


    public ChangePasswordPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //cazul in care user-ul isi schimba parola dupa prima logare
    public void fillFormsChange(String newPass, String confirmPass) throws InterruptedException {
        newPassword.clear();
        newPassword.sendKeys(newPass);
        Thread.sleep(1000);

        confirmPassword.clear();
        confirmPassword.sendKeys(confirmPass);
        Thread.sleep(1000);
    }

    public void fillNewPassword(String newPass) throws InterruptedException {
        newPassword.clear();
        newPassword.sendKeys(newPass);
        Thread.sleep(1000);
    }
    public void showPassword() throws InterruptedException {
        showPassword.click();
        Thread.sleep(1000);
    }

    public void hidePassword() throws InterruptedException {
        hidePassword.click();
        Thread.sleep(1000);
    }

    //Change password with valid data
    public UserHomePage pressChangePasswordButton() throws InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(btnChangePassword));
        btnChangePassword.click();
        Thread.sleep(1000);
        return new UserHomePage(driver);

    }

    public void pressChangePassBtn() throws InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(btnChangePassword));
        btnChangePassword.click();
        Thread.sleep(1000);

    }

    public boolean setErrorMessageWhenPassDontMatch(){
        if(! errorMessage.isDisplayed())
            return false;

        if(! confirmPassGreenValidation.isDisplayed())
            return false;

        if(! newPassRedValidation.isDisplayed())
            return false;


        return true;
    }

    public boolean setErrorMessageWhenDontFillMandatoryFields(){
        if(! errorMessage.isDisplayed())
            return false;

        if(! newPassRedValidation.isDisplayed())
            return false;

        if(! confirmPassRedValidation.isDisplayed())
            return false;

        return true;
    }

    public boolean hintFormatPassword(){
        if(! hintFormatPassword.isDisplayed())
            return false;

        if(! newPassRedValidation.isDisplayed())
            return false;


        return true;
    }

    public boolean validationsForPassword(){
        if(! newPassGreenValidation.isDisplayed())
            return false;

        if(! confirmPassGreenValidation.isDisplayed())
            return false;


        return true;
    }

    public boolean isOpened() {
        return btnChangePassword.isDisplayed();
    }

}
