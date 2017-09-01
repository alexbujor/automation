package test;

import com.endava.database.DBConnection;
import com.endava.pages.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ViewEmployeesTest {
    WebDriver driver;
    LoginPage loginPage;
    AdminHomePage adminHomePage;
    ViewEmployeesPage viewEmployeesPage;

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
    public void tearDown(){
        driver.manage().deleteAllCookies();
        driver.quit();
    }

    public void openPage() throws IOException, InterruptedException, SQLException {
        String adminEmail = "admin@endava.com";
        String adminPassword = "Parola#123";

        loginPage = new LoginPage(driver).get();

        Thread.sleep(2000);

        Assert.assertTrue("'Login' is not opened", loginPage.isOpened());

        Thread.sleep(2000);

        loginPage.fillFormsLogin(adminEmail,adminPassword);

        Thread.sleep(2000);

        adminHomePage = loginPage.pressLogInForAdmin();

        Thread.sleep(2000);

        Assert.assertTrue("'Admin home' is not opened", adminHomePage.isOpened());

        Thread.sleep(2000);

        viewEmployeesPage = adminHomePage.goToEmployees();

        Thread.sleep(2000);

        Assert.assertTrue("'View employees' is not opened", viewEmployeesPage.isOpened());

        Thread.sleep(2000);
    }

    @Test
    public void listingCorrectnessChecking() throws InterruptedException {
        Assert.assertTrue("Listing not working correctly", viewEmployeesPage.checkListings("10"));
    }

    @Test
    public void archiveEmployeeChecking() throws InterruptedException, SQLException {
        DBConnection dbConnection = new DBConnection();
        String first_name = "Bogdan";
        String last_name = "Buhaceanu";

        viewEmployeesPage.searchFor(first_name + " " + last_name);

        String query = "select employee_id from employees where first_name = '"+ first_name +"' and last_name = '"+ last_name +"';";
        String column = "employee_id";
        List<String> actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("User not found", actual.size()>0);

        Thread.sleep(2000);

        viewEmployeesPage.archiveEmployee();

        Thread.sleep(2000);

        query = "select concat(first_name, concat(' ', last_name)) FROM employees WHERE archived=true;";
        column = "concat";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("The employee deleted is not archived in the Data Base", actual.containsAll(Arrays.asList(first_name + " " + last_name)));
        actual.clear();
    }

    @Test
    public void searchBarChecking() throws InterruptedException, SQLException {
        viewEmployeesPage.verifySearchFunctionality("a");
    }

    @Test
    public void validateOrderedNamesAsc() throws InterruptedException, SQLException {
        Assert.assertTrue("Employees are not ordered correctly ascending by their name", viewEmployeesPage.validateAscending("20"));
    }

    @Test
    public void validateOrderedNamesDesc() throws InterruptedException, SQLException {
        Assert.assertTrue("Employees are not ordered correctly descending by their name", viewEmployeesPage.validateDescending("10"));
    }

    @Test
    public void dropDownValuesCheck() throws ClassNotFoundException, SQLException, InterruptedException {

        List<String> actual = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();
        String query = "SELECT DISTINCT discipline FROM job_details;";
        String column = "discipline";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Discipline drop-down values are not correct", actual.containsAll(Arrays.asList("Applications Management", "Testing", "Project Management", "Analysis", "Managed Services", "Architecture","Development")));
        actual.clear();

        query = " SELECT DISTINCT job_grade FROM job_details;";
        column = "job_grade";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Job Grade drop-down values are not correct", actual.containsAll(Arrays.asList("Senior Consultant", "Intern", "Manager", "Senior Manager", "Senior Technician", "Senior Engineer", "Consultant", "Engineer", "Junior Technician", "Technician", "Business Director", "Director", "Business Manager")));
        actual.clear();

        Assert.assertTrue("The drop-down lists from 'View only' are not working correctly", viewEmployeesPage.verifyDropDownsFunctionality("Analysis", "Intern"));
    }
}
