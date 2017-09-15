package test;

import com.endava.database.DBConnection;
import com.endava.pages.AdminHomePage;
import com.endava.pages.LoginPage;
import com.endava.pages.ViewDocumentsPage;
import com.endava.pages.ViewEmployeesPage;
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
 * Created by acotet on 9/11/2017.
 */
public class ViewDocumentsTest {

    WebDriver driver;
    LoginPage loginPage;
    AdminHomePage adminHomePage;
    ViewDocumentsPage viewDocumentsPage;


    @Before
    public void setup() throws IOException, InterruptedException, SQLException {
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
    public void tearDown() throws InterruptedException {

        viewDocumentsPage.goToLoginPage();

        Assert.assertTrue("'Login' is not opened", loginPage.isOpened());

        driver.manage().deleteAllCookies();
        driver.quit();
    }

    public void openPage() throws IOException, InterruptedException, SQLException {
        String adminEmail = "admin@endava.com";
        String adminPassword = "Parola#123";

        loginPage = new LoginPage(driver).get();


        Assert.assertTrue("'Login' is not opened", loginPage.isOpened());


        loginPage.fillFormsLogin(adminEmail,adminPassword);


        adminHomePage = loginPage.pressLogInForAdmin();


        Assert.assertTrue("'Admin home' is not opened", adminHomePage.isOpened());


        viewDocumentsPage = adminHomePage.goToDocumentsPage();


        Assert.assertTrue("'View documents' is not opened", viewDocumentsPage.isOpened());

    }

    @Test
    public void searchBarChecking() throws InterruptedException, SQLException {
        viewDocumentsPage.verifySearchFunctionality("a");
    }

    @Test
    public void dropDownValuesCheck() throws ClassNotFoundException, SQLException, InterruptedException {

        List<String> actual = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();
        String query = "SELECT DISTINCT category FROM documents;";
        String column = "category";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Category drop-down values are not correct", actual.containsAll(Arrays.asList("General Info", "Job Description")));
        actual.clear();

        viewDocumentsPage.getElems("10");
        Assert.assertTrue("The drop-down lists from 'View only' are not working correctly", viewDocumentsPage.verifyDropDownFunctionality("General Info"));
    }

    @Test
    public void deleteDocumentChecking() throws InterruptedException, SQLException {
        DBConnection dbConnection = new DBConnection();
        String title = "Who we are";

        viewDocumentsPage.searchFor(title);
        //Verific ca documentul cautat este in BD

        String query = "select document_id from documents where title = '"+ title  +"';";
        String column = "document_id";
        List<String> actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Document not found", actual.size()>0);


        viewDocumentsPage.deleteDocument();
        //Sterg documentul cautat anterior si apoi verific ca nu mai este in DB

        query = "select document_id from documents where title = '"+ title  +"';";
        column = "document_id";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("The document deleted is not removed from the Data Base", actual.isEmpty());
        actual.clear();
    }

    @Test
    public void listingCorrectnessChecking() throws InterruptedException {
        Assert.assertTrue("Listing not working correctly", viewDocumentsPage.checkListings("10"));
    }

    @Test
    public void validateOrderedNamesAsc() throws InterruptedException, SQLException {
        Assert.assertTrue("Documents are not ordered correctly ascending by their title", viewDocumentsPage.sortAscending("10"));
    }

    @Test
    public void validateOrderedNamesDesc() throws InterruptedException, SQLException {
        Assert.assertTrue("Documents are not ordered correctly descending by their title", viewDocumentsPage.sortDescending("10"));
    }

}
