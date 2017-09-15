package test;

import com.endava.pages.ChangePasswordPage;
import com.endava.pages.LoginPage;
import com.endava.pages.UserHomePage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by acotet on 8/30/2017.
 */
public class ChangePasswordTest {
    WebDriver driver;
    ChangePasswordPage changePasswordPage;
    LoginPage loginPage;
    UserHomePage userHomePage;

    @Before
    public void setup() throws IOException, InterruptedException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\test\\resources\\defaultConfig.properties"));
        String driverPath = properties.getProperty("chromedriverPath");
        System.setProperty("webdriver.chrome.driver",driverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("incognito");
        driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();
        openPage();
    }

    @After
    public void tearDown(){
        driver.manage().deleteAllCookies();
        driver.quit();
    }

    public void openPage() throws InterruptedException {
        loginPage = new LoginPage(driver).get();
        Assert.assertTrue("Login page Is Opened", loginPage.isOpened());
    }

    @Test
    public void whenIChangeThePasswordCorrectAtFirstLoginThenTheUserHomePageIsOpened() throws InterruptedException {
        loginPage.fillFormsLogin("oana.cotet@endava.com", "Parola#123");
        changePasswordPage=loginPage.pressLogInForFirstRegister();
        changePasswordPage.fillFormsChange("Test123&", "Test123&");
        userHomePage=changePasswordPage.pressChangePasswordButton();
        //changePasswordPage.validationsForPassword();
        Assert.assertTrue("The password is not changed and the User Home page is not opened",userHomePage.isOpened());

    }

    @Test
    public void whenIIntroduceAIncorrectFormatOfPasswordThenAHintIsDisplayed() throws InterruptedException {
        loginPage.fillFormsLogin("dany.cotet@endava.com", "Parola#123");
        changePasswordPage=loginPage.pressLogInForFirstRegister();
        changePasswordPage.fillFormsChange( "pass", "");
        Assert.assertTrue("Hint for the correct format of password and Red X are not displayed",changePasswordPage.hintFormatPassword());
    }

    @Test
    public void whenThePasswordsDontMatchThenAnErrorMessageIsDisplayed() throws InterruptedException {
        loginPage.fillFormsLogin("sebi.petrachi@endava.com", "Parola#123");
        changePasswordPage=loginPage.pressLogInForFirstRegister();
        changePasswordPage.fillFormsChange("Test123&", "Test");
        changePasswordPage.pressChangePassBtn();
        changePasswordPage.setErrorMessageWhenPassDontMatch();
    }

    @Test
    public void whenIPressTheChangePasswordButtonWithNullDataThenAnErrorMessageAndRedValidationAreDisplayed() throws InterruptedException {
        loginPage.fillFormsLogin("doru.dumitriu@endava.com", "Parola#123");
        changePasswordPage=loginPage.pressLogInForFirstRegister();
        changePasswordPage.fillFormsChange("", "");
        changePasswordPage.pressChangePassBtn();
        Assert.assertTrue("Error message is not displayed and red validation are displayed",changePasswordPage.isOpened());

    }
}
