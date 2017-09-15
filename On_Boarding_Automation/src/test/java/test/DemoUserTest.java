package test;

import com.endava.pages.*;
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

public class DemoUserTest {

    WebDriver driver;
    AdminHomePage adminHomePage;
    ChangePasswordPage changePasswordPage;
    LoginPage loginPage;
    UserHomePage userHomePage;
    ConfirmChecklistPage confirmChecklistPage;
    ForgotPasswordPage forgotPasswordPage;


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
        Thread.sleep(4000);
        Assert.assertTrue("The password is not changed and the User Home page is not opened",userHomePage.isOpened());
        loginPage=userHomePage.pressLogoutBtn();
        Assert.assertTrue("Login page is not opened",loginPage.isOpened());
        loginPage.fillFormsLogin("ana.cotet@endava.com", "Test12#");
        Thread.sleep(3000);
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
        Thread.sleep(4000);
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
}
