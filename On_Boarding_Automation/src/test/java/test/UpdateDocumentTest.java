package test;

import com.endava.database.DBConnection;
import com.endava.pages.AdminHomePage;
import com.endava.pages.LoginPage;
import com.endava.pages.UpdateDocumentPage;
import com.endava.pages.ViewDocumentsPage;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by acotet on 9/12/2017.
 */
public class UpdateDocumentTest {

    WebDriver driver;
    LoginPage loginPage;
    AdminHomePage adminHomePage;
    ViewDocumentsPage viewDocumentsPage;
    UpdateDocumentPage updateDocumentPage;

    @Before
    public void setup() throws IOException, InterruptedException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\test\\resources\\defaultConfig.properties"));
        String driverPath = properties.getProperty("chromedriverPath");
        System.setProperty("webdriver.chrome.driver", driverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("incognito");
        driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();
        openPage();
    }

    @After
    public void tearDown() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }

    public void openPage() throws InterruptedException, IOException {
        String adminEmail = "admin@endava.com";
        String adminPassword = "Parola#123";

        loginPage = new LoginPage(driver).get();


        Assert.assertTrue("'Login' is not opened", loginPage.isOpened());


        loginPage.fillFormsLogin(adminEmail, adminPassword);


        adminHomePage = loginPage.pressLogInForAdmin();


        Assert.assertTrue("'Admin home' is not opened", adminHomePage.isOpened());


        viewDocumentsPage = adminHomePage.goToDocumentsPage();


        Assert.assertTrue("'View documents' is not opened", viewDocumentsPage.isOpened());

    }


    @Test
    public void updateAnDocumentAndSeeIfTheValuesChange() throws InterruptedException, SQLException, IOException {
        List<String> actual = new ArrayList<>();
        List<String> expected;
        DBConnection dbConnection = new DBConnection();

        String query;
        String column;
        String title = "WHO WE ARE";
        String shortDescription = "Endava team..";
        String category = "General Info";
        viewDocumentsPage.searchFor("Who we are");
        updateDocumentPage=viewDocumentsPage.goToUpdateDocument();


        Assert.assertTrue("Title and short description fields are not visible", updateDocumentPage.checkUpdateDocumentChanges("WHO WE ARE", "Endava team.."));


        updateDocumentPage.updateTheTitle("WHO WE ARE??");
        viewDocumentsPage.searchFor("Who we are??");
        updateDocumentPage.updateTheDescription("Endava team..");
        updateDocumentPage.pressUploadButton();

        actual.clear();
        query = "select document_id from documents where title = '" + title + "' and description = '" + shortDescription + "' and category = '" + category + "';";
        column = "document_id";
        actual = dbConnection.getDbInfo(query, column);

        Assert.assertTrue("The document is not updated ", actual.containsAll(Arrays.asList(title, shortDescription, category)));

    }

    @Test
    public void whenIModifyTheTitleOfAnDocumentThenTheTitleMustBeUpdatedInDataBase() throws SQLException, IOException, InterruptedException {
        DBConnection dbConnection = new DBConnection();
        List<String> actualDocById = new ArrayList<>();

        String column;
        viewDocumentsPage.searchFor("Who we are");
        updateDocumentPage=viewDocumentsPage.goToUpdateDocument();
        updateDocumentPage.updateTheTitle("WHO WE ARE");
        updateDocumentPage.pressUploadButton();

        String query = "select title from documents WHERE document_id=1";
        column = "title";
        String expectedDocTitle = "WHO WE ARE";
        //Getting document title by Id
        actualDocById = dbConnection.getDbInfo(query, column);
        System.out.println("Document title retrieved from database :" + actualDocById);
        Assert.assertEquals(expectedDocTitle, actualDocById);
        //Assert.assertTrue("The document is updated in the Data Base", actualDocById.containsAll(Arrays.asList(title)));
    }
}