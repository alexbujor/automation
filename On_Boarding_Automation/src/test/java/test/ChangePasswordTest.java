package test;

import com.endava.pages.AdminHomePage;
import com.endava.pages.ChangePasswordPage;
import com.endava.pages.LoginPage;
import com.endava.pages.UserHomePage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by acotet on 8/30/2017.
 */
public class ChangePasswordTest {
    WebDriver driver;
    AdminHomePage adminHomePage;
    ChangePasswordPage changePasswordPage;
    LoginPage loginPage;
    UserHomePage userHomePage;
    // public static final String PATH_TO_CHROMEDRIVER = "C:\\drivers\\chromedriver.exe";

    @Before
    public void setup(){
        //  System.setProperty("webdriver.chrome.driver",PATH_TO_CHROMEDRIVER);
        driver = new ChromeDriver();
        // getProperties().get("baseUrl");
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void whenChangePassIsCorrectAtFirstLoginThenTheUserHomePageIsOpened(){
        changePasswordPage.fillFormsChange("Test123&", "Test123&");
        changePasswordPage.pressChangePasswordButton();
        changePasswordPage.validationsForPassword();
        Assert.assertTrue("The password has been changed and the User Home page is opened",userHomePage.isOpened());

    }
    @Test
    public void testWhenIncorrectFormatPasswordAHintIsDisplayed(){
        changePasswordPage.fillNewPassword("Test1");
        Assert.assertTrue("Hint for the correct format of password and Red X is displayed for Email Validation",changePasswordPage.hintFormatPassword());

    }

    @Test
    public void testWhenPasswordDontMatchAnErrorMessageIsDisplayed(){
        changePasswordPage.fillFormsChange("Test123&", "Test");
        changePasswordPage.setErrorMessageWhenPassDontMatch();
    }

    @Test
    public void whenIPressTheChangePasswordButtonWithNullDataThenAnErrorMessageAndRedValidationAreDisplayed(){
        changePasswordPage.fillFormsChange("", "");
        changePasswordPage.pressChangePasswordButton();
        Assert.assertTrue("Error message is displayed and red validation are displayed",changePasswordPage.setErrorMessageWhenDontFillMandatoryFields());

    }
}
