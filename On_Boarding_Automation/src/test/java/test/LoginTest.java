package test;

import com.endava.pages.*;
import gherkin.lexer.Th;
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
 * Created by acotet on 8/29/2017.
 */
public class LoginTest {
    WebDriver driver;
    AdminHomePage adminHomePage;
    ChangePasswordPage changePasswordPage;
    LoginPage loginPage;
    UserHomePage userHomePage;
    ConfirmChecklistPage confirmChecklistPage;

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
        Thread.sleep(1000);
        Assert.assertTrue("Login page Is Opened", loginPage.isOpened());
        Thread.sleep(1000);
    }

    @Test
    public void loginPositiveFlow() throws InterruptedException {
        loginPage.fillFormsLogin("ana.cotet@endava.com", "Parola#123");
        Thread.sleep(4000);
        changePasswordPage=loginPage.pressLogInForFirstRegister();
        Assert.assertTrue("Change password page is not opened",changePasswordPage.isOpened());
        changePasswordPage.fillFormsChange("Test12#", "Test12#");
        Thread.sleep(3000);
        userHomePage=changePasswordPage.pressChangePasswordButton();
        Assert.assertTrue("The password is not changed and the User Home page is not opened",userHomePage.isOpened());
        loginPage=userHomePage.pressLogoutBtn();
        Assert.assertTrue("Login page is not opened",loginPage.isOpened());
        loginPage.fillFormsLogin("ana.cotet@endava.com", "Test12#");
        Thread.sleep(2000);
        userHomePage=loginPage.pressLogInForUserRegister();
        Thread.sleep(4000);
        Assert.assertTrue("The user has not successfully logged in and the User Home page is not opened",userHomePage.isOpened());
        userHomePage.pressLogoutBtn();
        Assert.assertTrue("Login page is not opened",loginPage.isOpened());
        loginPage.fillFormsLogin("adi.ionescu@endava.com", "Test12#");
        Thread.sleep(4000);
        userHomePage=loginPage.pressLogInForUserRegister();
        Thread.sleep(4000);
        userHomePage.verifyCheckBox();
        confirmChecklistPage= userHomePage.goToConfirmChecklistPage();
        Assert.assertTrue("The Confirm Checklist page is not opened",confirmChecklistPage.isOpened());
        userHomePage.pressLogoutBtn();
        loginPage.fillFormsLogin("dan.popescu@endava.com", "Test12#");
        Thread.sleep(2000);
        userHomePage=loginPage.pressLogInForUserRegister();
        Thread.sleep(4000);
        userHomePage.clickOnMap();
        userHomePage.scrollToMap();
        Thread.sleep(5000);
        userHomePage.clickOnQuiz();
        Thread.sleep(5000);
        userHomePage.clickOnNextAndBackPeopleOfInterest();
        Thread.sleep(5000);
        userHomePage.pressLogoutBtn();


    }

    @Test
    public void loginNegativeFlow() throws InterruptedException {
        loginPage.fillFormsLogin("ana.cot@endava.com", "^tvoRNzi2");
        loginPage.pressLogInButton();
        loginPage.setErrorMessage();
        loginPage.fillFormsLogin("", "");
        loginPage.pressLogInButton();
        Assert.assertTrue("Error message is displayed",loginPage.setErrorMessage());
        loginPage.fillFormsLogin("adi.ionescu@endava.com", "Parola#123");
        changePasswordPage=loginPage.pressLogInForFirstRegister();
        Assert.assertTrue("Change password page is not opened",changePasswordPage.isOpened());
        //introduc o parola care nu respecta formatul
        changePasswordPage.fillFormsChange( "pass", "");
        changePasswordPage.showPassword();

        changePasswordPage.hidePassword();
        Assert.assertTrue("Hint for the correct format of password and Red X are not displayed",changePasswordPage.hintFormatPassword());
        //cele 2 parole nu corespund
        changePasswordPage.fillFormsChange("Test123&", "Test");
        changePasswordPage.pressChangePassBtn();
        changePasswordPage.setErrorMessageWhenPassDontMatch();

        //schimb cu succces cele 2 parole
        changePasswordPage.fillFormsChange("Test123&", "Test123&");
        userHomePage=changePasswordPage.pressChangePasswordButton();
        Assert.assertTrue("The password is not changed and the User Home page is not opened",userHomePage.isOpened());
        userHomePage.pressLogoutBtn();

    }

    @Test
    public void whenILoginAsAdminThenTheAdminHomePageIsOpened() throws InterruptedException, IOException {

        loginPage.fillFormsLogin("admin@endava.com", "Parola#123");

        adminHomePage = loginPage.pressLogInForAdmin();
        Assert.assertTrue("Admin Home page is not opened",adminHomePage.isOpened());


    }

    @Test
    public void whenILoginAsAdminWithInvalidDataThenAnErrorMessageIsDisplayed() throws InterruptedException {
        loginPage.fillFormsLogin("admin@endava.com", "Parola#12");
        loginPage.pressLogInButton();
        loginPage.errorMessageInvalidLoginForAdmin();
    }

    @Test
    public void whenILoginAsUserWithInvalidCredentialsThenAnErrorMessageIsDisplyed() throws InterruptedException {
        loginPage.fillFormsLogin("ana.cot@endava.com", "^tvoRNzi2");
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
        loginPage.fillFormsLogin("ana.cotet@endava.com", "^tvoRNzi29");
        loginPage.pressLogInForFirstRegister();
        // System.out.println("R2. The error message: '!Your password has expired. Please contact the admin at admin@endava.com'");

    }

    @Test
    public void whenIFirstLoginAsUserWithValidCredentialsThenTheChangePasswordPageIsOpened() throws InterruptedException {
        loginPage.fillFormsLogin("paul.popescu@endava.com", "Parola#123");
        changePasswordPage=loginPage.pressLogInForFirstRegister();
        Assert.assertTrue("Change password page is not opened",changePasswordPage.isOpened());
    }

    @Test
    public void whenIFirstLoginAsUserWithNullDataThenAnErrorMessageAndRedValidationAreDisplayed() throws InterruptedException {
        loginPage.fillFormsLogin("", "");
        loginPage.pressLogInButton();
        Assert.assertTrue("Error message is not displayed",loginPage.setErrorMessage());

    }

    @Test
    public void whenILoginAsAnExistingUserWithTheOldPasswordThenAnErrorMessageIsDisplayed() throws InterruptedException {
        loginPage.fillFormsLogin("ana.cotet@endava.com", "^tvoRNzi29");
        loginPage.pressLogInButton();
        Assert.assertTrue("The Error message because the user try to login with the old password is not displayed",loginPage.setErrorMessage());

    }

    @Test
    public void whenILoginAsAnExistingUserWithInvalidFormatOfDataThenRedXValidationIsDisplayed() throws InterruptedException {
        loginPage.fillFormsLogin("ana.cotet@yahoo", "Parola123");
        loginPage.pressLogInButton();
        Assert.assertTrue("Error message is displayed",loginPage.setErrorMessageForIncorectFormatOfEmailAndPassword());

    }

    @Test
    public void whenILoginAsAnExistingUserWithValidDataThenTheUserHomePageIsOpened() throws InterruptedException {
        loginPage.fillFormsLogin("ana.cotet@endava.com", "Test12#");
        userHomePage=loginPage.pressLogInForUserRegister();
        Assert.assertTrue("The user has not successfully logged in and the User Home page is not opened",userHomePage.isOpened());
        userHomePage.pressLogoutBtn();
    }

    @Test
    public void whenIGoToLoginPageThenTheFieldsForEmailPassAndLoginButtonAreDisplayed(){
        Assert.assertTrue("The fields for Email, Password and Login Button aren't displayed in the Login Page",loginPage.checkPage());

    }




}

