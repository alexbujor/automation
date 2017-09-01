package test;

import com.endava.pages.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;

/**
 * Created by acotet on 8/29/2017.
 */
public class LoginTest {
    WebDriver driver;
    AdminHomePage adminHomePage;
    ChangePasswordPage changePasswordPage;
    LoginPage loginPage;
    UserHomePage userHomePage;
    public static final String PATH_TO_CHROMEDRIVER = "C:\\drivers\\chromedriver.exe";


    @Before
    public void setup(){

        System.setProperty("webdriver.chrome.driver",PATH_TO_CHROMEDRIVER);
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



    public void openPage(){
        loginPage = new LoginPage(driver).get();
        System.out.println("R1. Login page is opened");
        Assert.assertTrue("Login page Is Opened", loginPage.isOpened());
    }

    @Test
    public void whenILoginAsAdminThenTheAdminHomePageIsOpened() throws InterruptedException, IOException {

        Thread.sleep(1000);

        loginPage.fillFormsLogin("admin@endava.com", "Parola#123");

        Thread.sleep(1000);

        adminHomePage = loginPage.pressLogInForAdmin();

        Thread.sleep(1000);

        Assert.assertTrue("Admin Home page is not opened",adminHomePage.isOpened());

        Thread.sleep(1000);

    }

    @Test
    public void whenILoginAsAdminWithInvalidDataThenAnErrorMessageIsDisplayed() throws InterruptedException {
        loginPage.fillFormsLogin("admin@endava.com", "Parola#12");
        loginPage.pressLogInButton();
        Thread.sleep(5000);
        loginPage.errorMessageInvalidLoginForAdmin();
        Thread.sleep(1000);
    }

    @Test
    public void whenILoginAsUserWithInvalidCredentialsThenAnErrorMessageIsDisplyed() throws InterruptedException {
        loginPage.fillFormsLogin("ana.cot@endava.com", "Test0@655");
        // loginPage.pressLogInForFirstRegister();
        loginPage.pressLogInButton();
        loginPage.setErrorMessage();

    }

    @Test
    public void whenILoginAsUserWithInvalidFormatOfEmailAndPAsswordThenAnRedXValidationIsDisplyed() throws InterruptedException {
        loginPage.fillFormsLogin("ana.cot@endava.co", "Test0655");

        loginPage.setErrorMessageForIncorectFormatOfEmailAndPassword();


    }


    @Test
    public void whenILoginAsUserForTheFirstTimeWithExpiredCredentialsThenAnErrorMessageAndRedValidationAreDisplayed() throws InterruptedException {
        loginPage.fillFormsLogin("ana.cotet@endava.com", "Test0@655");
        loginPage.pressLogInForFirstRegister();
        // System.out.println("R2. The error message: '!Your password has expired. Please contact the admin at admin@endava.com'");
        //loginPage.setExpiredCredentialsMessage();

    }

    @Test
    public void whenIFirstLoginAsUserWithValidCredentialsThenTheChangePasswordPageIsOpened() throws InterruptedException {
        loginPage.fillFormsLogin("ionut.popa@endava.com", "Parola#123");
        changePasswordPage=loginPage.pressLogInForFirstRegister();
        Assert.assertTrue("Change password page is not opened",changePasswordPage.isOpened());
    }

    @Test
    public void whenIFirstLoginAsUserWithNullDataThenAnErrorMessageAndRedValidationAreDisplayed() throws InterruptedException {
        loginPage.fillFormsLogin("", "");
        loginPage.pressLogInButton();
        Assert.assertTrue("Error message is displayed",loginPage.setErrorMessage());

    }

    @Test
    public void whenILoginAsAnExistingUserWithInvalidDataThenAnErrorMessageIsDisplayed() throws InterruptedException {
        loginPage.fillFormsLogin("ionut.popa@endava.com", "Parola#123");
        loginPage.pressLogInButton();
        Assert.assertTrue("Error message is displayed",loginPage.setErrorMessage());

    }

    @Test
    public void whenILoginAsAnExistingUserWithInvalidFormatOfDataThenRedXValidationIsDisplayed() throws InterruptedException {
        loginPage.fillFormsLogin("ionut.popa@yahoo.com", "Parola123");
        loginPage.pressLogInButton();
        Assert.assertTrue("Error message is displayed",loginPage.setErrorMessageForIncorectFormatOfEmailAndPassword());

    }

    @Test
    public void whenILoginAsAnExistingUserThenTheUserHomePageIsOpened() throws InterruptedException {
        loginPage.fillFormsLogin("ana@endava.com", "Test123&");
        loginPage.pressLogInForUserRegister();
        Assert.assertTrue("The user has successfully logged in and the User Home page is opened",userHomePage.isOpened());

    }

    @Test
    public void whenIGoToLoginPageThenTheFieldsForEmailPassAndLoginButtonAreDisplayed(){
        Assert.assertTrue("The fields for Email, Password and Login Button aren't displayed in the Login Page",loginPage.checkPage());

    }
}

