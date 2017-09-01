package test;

import com.endava.database.DBConnection;
import com.endava.email.EmailHelperA;
import com.endava.pages.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import utils.TextParser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by acotet on 8/30/2017.
 */
public class ForgotPasswordTest {
    WebDriver driver;
    AdminHomePage adminHomePage;
    ViewEmployeesPage viewEmployeesPage;
    LoginPage loginPage;
    UserHomePage userHomePage;
    ChangePasswordPage changePasswordPage;
    EditEmployeePage editEmployeePage;
    AddEmployeePage addEmployeePage;
    ForgotPasswordPage forgotPasswordPage;
    // public static final String PATH_TO_CHROMEDRIVER = "C:\\drivers\\chromedriver.exe";

    @Before
    public void setup(){
        //  System.setProperty("webdriver.chrome.driver",PATH_TO_CHROMEDRIVER);
        //  driver = new ChromeDriver();
        //  getProperties().get("baseUrl");
    }

    @After
    public void tearDown(){
        //  driver.quit();
    }

    @Test
    public void whenIResetMyPasswordAsAnEmployeeThenIMustReceiveAEmailWithANewPasswordAndIMustBeRedirectedOnChangePassPage() throws InterruptedException, SQLException, IOException {
        List<String> actual = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();
        TextParser textParser = new TextParser();
        EmailHelperA emailHelperA = new EmailHelperA();
        String email="ana.cotet@endava.com";
        forgotPasswordPage.fillEmail(email);

        forgotPasswordPage.resetPassword();
        if(textParser.stringExtractor(email,"\\b[\\w.%+-]+@gmail.[a-zA-Z]{2,6}\\b"))
            Assert.assertTrue("The email with the new password was not sent", emailHelperA.checkMail(email,"Testing1234"));

        Assert.assertTrue("Edit Employee page is not opened",changePasswordPage.isOpened());
    }

    @Test
    public void whenICompleteAWrongEmailThenAnErrorMessageMustBeDisplayed(){
        forgotPasswordPage.fillEmail("ana.mariacotet@yahoo.com");
        Assert.assertTrue("An error message is displayed: 'The email address is incorrect!'",forgotPasswordPage.errorMessageForWrongEmail());
    }
}
