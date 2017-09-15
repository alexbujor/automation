package test;

import com.endava.pages.ConfirmChecklistPage;
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
import java.sql.SQLException;
import java.util.Properties;

public class UserHomePageTest {
    WebDriver driver;
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

        Assert.assertTrue("Login page Is Opened", loginPage.isOpened());

    }

    @Test
    public void validateCoundownForFutureEmployee() throws InterruptedException, SQLException {
        loginPage.fillFormsLogin("marian.solomonescu@endava.com", "Parola#123");

        userHomePage = loginPage.pressLogInForUserRegister();


        Assert.assertTrue("The user has successfully logged in and the User Home page is opened",userHomePage.isOpened());

        Assert.assertTrue("The countdown for a future employee is not showing the correct values",userHomePage.validateCountdownFutureEmployee("Marian","Solomonescu"));
    }

    @Test
    public void validateCoundownForFirstDayEmployee() throws InterruptedException, SQLException {
        loginPage.fillFormsLogin("roxanaf.ungureanu@endava.com", "Parola#123");

        userHomePage = loginPage.pressLogInForUserRegister();

        Assert.assertTrue("The user has successfully logged in and the User Home page is opened",userHomePage.isOpened());


        Assert.assertTrue("The countdown for a first day employe is not showing the correct values",userHomePage.validateCountdownFirstDay());
    }

    @Test
    public void validateCoundownForOldEmployee() throws InterruptedException, SQLException {
        loginPage.fillFormsLogin("ioana.sitaru@endava.com", "Parola#123");


        userHomePage = loginPage.pressLogInForUserRegister();


        Assert.assertTrue("The user has successfully logged in and the User Home page is opened",userHomePage.isOpened());


        Assert.assertTrue("The countdown for an old employe is not showing the correct values",userHomePage.validateCountdownOldEmployee("Ioana","Sitaru"));
    }

    @Test
    public void whenICompleteAllTheCheckBoxThenTheConfirmChecklistPageMustBeOpened() throws InterruptedException, SQLException {

        loginPage.fillFormsLogin("ana.cotet@endava.com","Test12#");

        userHomePage = loginPage.pressLogInForUserRegister();

        Assert.assertTrue("The user has not successfully logged in and the User Home page is not opened",userHomePage.isOpened());


        userHomePage.verifyCheckBox();

        confirmChecklistPage= userHomePage.goToConfirmChecklistPage();

        Assert.assertTrue("The Confirm Checklist page is not opened",confirmChecklistPage.isOpened());



    }



}
