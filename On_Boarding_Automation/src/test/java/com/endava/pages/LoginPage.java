package com.endava.pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by acotet on 8/22/2017.
 */

public class LoginPage extends LoadableComponent<LoginPage> {

    protected WebDriver driver;
    @FindBy(className = "login-form")
    private WebElement loginForm;

    @FindBy(id= "email")

    private WebElement txtbox_Email;

    @FindBy(id = "password")

    private WebElement txtbox_Password;

    @FindBy(id = "submit")

    private WebElement btn_Login ;

    @FindBy(id = "errorMsg")

    private WebElement errorMessage ;

    @FindBy(css = ".glyphicon.glyphicon-remove.form-control-feedback")

    private WebElement emailRedValidation;

    @FindBy(css = ".glyphicon.glyphicon-remove.form-control-feedback")

    private WebElement passwordRedValidation;

    @FindBy(id = "errorMsgExpiredCredentials")

    private WebElement expiredCredentials;

    @FindBy(css = ".right-forget")

    private WebElement linkResetPassword;

// This is a constructor, as every page need a base driver to find web elements

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        load();
    }

    //cazul in care user-ul se logheaza cu contul de admin

    public void fillFormsLogin(String sEmail, String sPassword) throws InterruptedException {
        txtbox_Email.clear();
        txtbox_Email.sendKeys(sEmail);
        Thread.sleep(1000);

        txtbox_Password.clear();
        txtbox_Password.sendKeys(sPassword);
        Thread.sleep(1000);

    }




    //dupa ce se logheaza cu contul de admin este redirectionat catre pagina de AdminHomePage
    public AdminHomePage pressLogInForAdmin() throws InterruptedException, IOException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(btn_Login));
        btn_Login.click();
        Thread.sleep(1000);
        return new AdminHomePage(driver);
    }

    public void pressLogInButton() throws InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(btn_Login));
        btn_Login.click();
        Thread.sleep(1000);

    }

    public ForgotPasswordPage goToResetPasswordPage() throws InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(linkResetPassword));
        linkResetPassword.click();
        Thread.sleep(1000);
        return new ForgotPasswordPage(driver);
    }
    public boolean errorMessageInvalidLoginForAdmin() {

        if(errorMessage.isDisplayed())
            return true;
        return false;
    }
    //La prima logare a user-ului trebuie sa fie redirectionat catre ChangePasswordPage
    public ChangePasswordPage pressLogInForFirstRegister() throws InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(btn_Login));
        btn_Login.click();
        Thread.sleep(1000);
        return new ChangePasswordPage(driver);

    }
    //dupa ce isi schimba parola user-ul e redirectionat catre pagina de UserHomePage

    public UserHomePage pressLogInForUserRegister() throws InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(btn_Login));
        btn_Login.click();
        Thread.sleep(1000);
        return new UserHomePage(driver);
    }

    public boolean setErrorMessage(){
        if(! errorMessage.isDisplayed())
            return false;

        return true;


    }

    public boolean setExpiredCredentialsMessage(){
        if(! expiredCredentials.isDisplayed())
            return false;

        if(! emailRedValidation.isDisplayed())
            return false;

        if(! passwordRedValidation.isDisplayed())
            return false;

        return true;


    }

    public boolean setErrorMessageForIncorectFormatOfEmailAndPassword(){

        if(! emailRedValidation.isDisplayed())
            return false;

        if(! passwordRedValidation.isDisplayed())
            return false;

        return true;


    }


    public boolean checkPage() {
        if(! btn_Login.isDisplayed())
            return false;

        if(! txtbox_Email.isDisplayed())
            return false;

        if(! txtbox_Password.isDisplayed())
            return false;

        return true;

    }

    public boolean isOpened() {
        return btn_Login.isDisplayed();
    }

    protected void load() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src\\test\\resources\\defaultConfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = properties.getProperty("baseURL");
        driver.get(url);
    }

    protected void isLoaded() throws Error { Assert.assertTrue(btn_Login.isDisplayed()); }
}
