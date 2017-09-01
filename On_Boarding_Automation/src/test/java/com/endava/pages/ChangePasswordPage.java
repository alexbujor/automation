package com.endava.pages;

import org.junit.Assert;
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
    @FindBy(id = "Show password")
    private WebElement showPassword;
    @FindBy(id = "Hide password")
    private WebElement hidePassword;
    @FindBy(id = "mesaj eroare")
    private WebElement errorMessage;
    @FindBy(id = "hint")
    private WebElement hintFormatPassword;
    @FindBy(id = "passwordConfirm")
    private WebElement confirmPassword;
    @FindBy(id = "submit")
    private WebElement btnChangePassword;
    @FindBy(id = "Green sign validation ")
    private WebElement newPassGreenValidation;
    @FindBy(id = "red X validation ")
    private WebElement confirmPassRedValidation;
    @FindBy(id = "red x validation ")
    private WebElement newPassRedValidation;
    @FindBy(id = "Green sign validation")
    private WebElement confirmPassGreenValidation;


    public ChangePasswordPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        load();
    }

    //cazul in care user-ul isi schimba parola dupa prima logare

    public void fillFormsChange(String newPass, String confirmPass){

        newPassword.sendKeys(newPass);

        confirmPassword.sendKeys(confirmPass);
    }

    public void fillNewPassword(String newPass){

        newPassword.sendKeys(newPass);
    }
    //Change password with valid data
    public LoginPage pressChangePasswordButton() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(btnChangePassword));
        btnChangePassword.click();
        return new LoginPage(driver);

    }

    public boolean setErrorMessageWhenPassDontMatch(){

        if(! errorMessage.isDisplayed())
            return false;

        if(! confirmPassGreenValidation.isDisplayed())
            return false;

        if(! newPassGreenValidation.isDisplayed())
            return false;


        return true;
        // Assert.assertTrue("Error message is displayed: '!Passwords don't match'",errorMessage.isDisplayed());
        // Assert.assertTrue("Green sign is displayed for New password",newPassGreenValidation.isDisplayed());
        //  Assert.assertTrue("Red X is displayed for Confirm Password",confirmPassRedValidation.isDisplayed());
    }

    public boolean setErrorMessageWhenDontFillMandatoryFields(){

        if(! errorMessage.isDisplayed())
            return false;

        if(! confirmPassRedValidation.isDisplayed())
            return false;

        if(! newPassRedValidation.isDisplayed())
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
        return "Change password".equals(driver.getTitle());
    }


    protected void load() {
        driver.get("http://localhost:4200/change-password");
    }

    protected void isLoaded() throws Error {
        Assert.assertEquals("Change password", driver.getTitle());
    }


}
