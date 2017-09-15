package test;

import com.endava.database.DBConnection;
import com.endava.email.EmailHelperA;
import com.endava.pages.ChangePasswordPage;
import com.endava.pages.ForgotPasswordPage;
import com.endava.pages.LoginPage;
import com.endava.pages.UserHomePage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.TextParser;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by acotet on 8/30/2017.
 */
public class ForgotPasswordTest {
    WebDriver driver;
    LoginPage loginPage;
    ChangePasswordPage changePasswordPage;
    ForgotPasswordPage forgotPasswordPage;
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


        forgotPasswordPage = loginPage.goToResetPasswordPage();

        Assert.assertTrue("'Reset password page' is not opened", forgotPasswordPage.isOpened());

    }

    @Test
    public void whenIResetMyPasswordAsAnEmployeeThenIMustReceiveAnEmailWithANewPasswordAndIMustBeRedirectedOnChangePassPage() throws InterruptedException, SQLException, IOException, AWTException {
        TextParser textParser = new TextParser();
        EmailHelperA emailHelperA = new EmailHelperA();
        //GetDate getDate = new GetDate();

        forgotPasswordPage.fillEmail("Ana.Cotet@endava.com");
        Thread.sleep(4000);
        forgotPasswordPage.resetPassword();

        String personalEmail = "testingemailautomation@gmail.com";


        //getDate.startIsDateGreaterThanToday(editEmployeePage.getStartDate()) &&
        if (textParser.stringExtractor(personalEmail, "\\b[\\w.%+-]+@gmail.[a-zA-Z]{2,6}\\b")) {

            //Open a new tab
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_T);
            r.keyRelease(KeyEvent.VK_CONTROL);
            r.keyRelease(KeyEvent.VK_T);

        }

        //Switch to the new tab
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1)); //switches to new tab

        changePasswordPage = emailHelperA.checkMailForResetAsAdmin(personalEmail, "Testing1234", driver);
        Assert.assertNotNull("Mail or token not found", changePasswordPage);

        //changing the tab
        tabs = new ArrayList<>(driver.getWindowHandles());
        driver.close();
        driver.switchTo().window(tabs.get(2));

        Assert.assertTrue("'Change password' is not opened", changePasswordPage.isOpened());

        changePasswordPage.fillFormsChange("Test12#", "Test12#");

        userHomePage = changePasswordPage.pressChangePasswordButton();

        Assert.assertTrue("'User home' is not opened", userHomePage.isOpened());

        userHomePage.pressLogoutBtn();

        driver.close();
        driver.switchTo().window(tabs.get(0));

    }












    @Test
    public void whenICompleteAWrongEmailThenAnErrorMessageMustBeDisplayed() throws InterruptedException {
        forgotPasswordPage.fillEmail("ana.mariacotet@yahoo.com");
        forgotPasswordPage.resetPassword();
        Assert.assertTrue("An error message is displayed: 'The email address is incorrect!'",forgotPasswordPage.errorMessageForWrongEmail());

    }

    @Test
    public void whenIResetMyPasswordAndISentTheEndavaEmailThenInDataBaseTheTokenMustBeSentAtTheEmailAddressThatIHadIntroducedBefore() throws SQLException {
    List<String> actual = new ArrayList<>();
    DBConnection dbConnection = new DBConnection();
    String query = "SELECT email from tokens inner join employees on tokens.email=employees.endava_email Where employee_id=1";
    String column = "email";
    actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("The token is not send at the email address introduced in Forgot password form", actual.containsAll(Arrays.asList( "ana.cotet@endava.com")));
        actual.clear();
}
}
