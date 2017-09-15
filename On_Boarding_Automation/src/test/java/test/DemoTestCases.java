package test;

import com.endava.email.EmailHelperA;
import com.endava.pages.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.GetDate;
import utils.TextParser;
import utils.ChromeDownloadPage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class DemoTestCases {
    WebDriver driver;
    LoginPage loginPage;
    AdminHomePage adminHomePage;
    ViewEmployeesPage viewEmployeesPage;
    ViewDocumentsPage viewDocumentsPage;
    AddEmployeePage addEmployeePage;
    UploadDocumentPage uploadDocumentPage;
    EditEmployeePage editEmployeePage;
    ChangePasswordPage changePasswordPage;
    UserHomePage userHomePage;
    ChromeDownloadPage chromeDownloadPage;
    ConfirmChecklistPage confirmChecklistPage;
    ForgotPasswordPage forgotPasswordPage;

    @Before
    public void setup() throws IOException, InterruptedException, SQLException {
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


    public void setupForUpload() throws IOException, InterruptedException, SQLException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\test\\resources\\defaultConfig.properties"));
        String driverPath = properties.getProperty("chromedriverPath");
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        org.openqa.selenium.Dimension dim = new org.openqa.selenium.Dimension(1450, 860);
        org.openqa.selenium.Point pt = new Point(150, 0);
        driver.manage().window().setPosition(pt);
        driver.manage().window().setSize(dim);
        openPage();
    }

    @After
    public void tearDown() throws InterruptedException {
        loginPage = viewEmployeesPage.goToLoginPage();

        Assert.assertTrue("'Login' is not opened", loginPage.isOpened());

        driver.manage().deleteAllCookies();
        driver.quit();
    }

    public void openPage() throws IOException, InterruptedException, SQLException {
        String adminEmail = "admin@endava.com";
        String adminPassword = "Parola#123";

        loginPage = new LoginPage(driver).get();

        Thread.sleep(1000);

        Assert.assertTrue("'Login' is not opened", loginPage.isOpened());

        loginPage.fillFormsLogin(adminEmail, adminPassword);

        Thread.sleep(1000);

        adminHomePage = loginPage.pressLogInForAdmin();

        Thread.sleep(1000);

        Assert.assertTrue("'Admin home' is not opened", adminHomePage.isOpened());
    }

    @Test
    public void addEmployeeFunctionality() throws SQLException, InterruptedException, IOException, AWTException {

        //ADD EMPLOYEE FUNCTIONALITY ********************************

        EmailHelperA emailHelperA = new EmailHelperA();
        TextParser textParser = new TextParser();
        GetDate getDate = new GetDate();

        String fn = "Alexandru";
        String ln = "Bujor";
        String ee = "Alexandru.Bujor@endava.com";
        String pe = "testingemailautomation@gmail.com";
        String passWord = "Testing1234";
        String startDate = "09202017";
        String newPassword = "Parola#123";

        viewEmployeesPage = adminHomePage.goToEmployees();

        Thread.sleep(2000);

        Assert.assertTrue("'View employees' is not opened", viewEmployeesPage.isOpened());

        addEmployeePage = viewEmployeesPage.goToAddEmployee();

        Thread.sleep(2000);

        Assert.assertTrue("Add Employee' is not opened", addEmployeePage.isOpened());

        Assert.assertTrue("General info fields are not visible", addEmployeePage.checkGeneralInfo());
        Assert.assertTrue("Location fields are not visible", addEmployeePage.checkLocation());
        Assert.assertTrue("Job details fields are not visible", addEmployeePage.checkJobDetails());

        addEmployeePage.fillGeneralInfo(fn, ln, ee, pe);
        addEmployeePage.fillLocation("Romania", "Iasi", "UBC Palas", "4", "1", "5");
        addEmployeePage.fillJobDetails("ISD", "Development", "Intern", "Developer",
                "Java, .NET", "Stefana Munteanu", "Andreea Croitoru", startDate, "12152017");

        String startDate2 = addEmployeePage.getStartDate();

        viewEmployeesPage = addEmployeePage.pressAddEmployee();

        Thread.sleep(2000);

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,-500)", "");

        Assert.assertTrue("View Employees page is not opened", viewEmployeesPage.isOpened());

        Thread.sleep(5000);

        if (getDate.startIsDateGreaterThanToday(startDate2) &&
                textParser.stringExtractor(pe, "\\b[\\w.%+-]+@gmail.[a-zA-Z]{2,6}\\b")) {

            //Open a new tab
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_T);
            r.keyRelease(KeyEvent.VK_CONTROL);
            r.keyRelease(KeyEvent.VK_T);

            //Switch to the new tab
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1)); //switches to new tab

            String receivedPassword = emailHelperA.getPasswordFromMail(pe, passWord, driver);

            System.out.println(receivedPassword);

            Assert.assertFalse("The email with credentials was not sent", receivedPassword.equalsIgnoreCase(""));

            driver.close();
            driver.switchTo().window(tabs.get(0));

            Thread.sleep(2000);

            loginPage = viewEmployeesPage.goToLoginPage();

            Assert.assertTrue("'Login' is not opened", loginPage.isOpened());

            loginPage.fillFormsLogin(ee, receivedPassword);

            Thread.sleep(2000);

            changePasswordPage = loginPage.pressLogInForFirstRegister();

            Thread.sleep(2000);

            Assert.assertTrue("'Login' is not opened", changePasswordPage.isOpened());

            changePasswordPage.fillFormsChange(newPassword, newPassword);

            Thread.sleep(2000);

            userHomePage = changePasswordPage.pressChangePasswordButton();

            Thread.sleep(2000);

            Assert.assertTrue("The password is not changed and the User Home page is not opened", userHomePage.isOpened());
        }
    }

    @Test
    public void resetPasswordAsAdminFunctionality() throws SQLException, InterruptedException, IOException, AWTException {

        //RESET PASSWORD AS ADMIN FUNCTIONALITY *************************************

        String first_name = "Alexandru";
        String last_name = "Bujor";
        String newPass = "Test123&";
        GetDate getDate = new GetDate();
        TextParser textParser = new TextParser();
        EmailHelperA emailHelperA = new EmailHelperA();

        viewEmployeesPage = adminHomePage.goToEmployees();

        Thread.sleep(2000);

        Assert.assertTrue("'View employees' is not opened", viewEmployeesPage.isOpened());

        viewEmployeesPage.searchFor(first_name + " " + last_name);

        Thread.sleep(2000);

        editEmployeePage = viewEmployeesPage.goToEditEmployee();

        Thread.sleep(2000);

        Assert.assertTrue("Edit Employee page is not opened", editEmployeePage.isOpened());

        String personalEmail = editEmployeePage.getPersonalEmail();
        String endavaEmail = editEmployeePage.getEndavaEmail();
        String startDate = editEmployeePage.getStartDate();

        Thread.sleep(2000);

        editEmployeePage.resetPassword();

        viewEmployeesPage = editEmployeePage.goToViewEmployees();

        Thread.sleep(2000);

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,-500)", "");

        Assert.assertTrue("View Employees page is not opened", viewEmployeesPage.isOpened());

        if (getDate.startIsDateGreaterThanToday(startDate) &&
                textParser.stringExtractor(personalEmail, "\\b[\\w.%+-]+@gmail.[a-zA-Z]{2,6}\\b")) {

            //Open a new tab
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_T);
            r.keyRelease(KeyEvent.VK_CONTROL);
            r.keyRelease(KeyEvent.VK_T);

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

            changePasswordPage.fillFormsChange(newPass, newPass);

            Thread.sleep(2000);

            userHomePage = changePasswordPage.pressChangePasswordButton();

            Thread.sleep(2000);

            Assert.assertTrue("'User home' is not opened", userHomePage.isOpened());

            loginPage = userHomePage.goToLoginPage();

            Thread.sleep(2000);

            loginPage.fillFormsLogin(endavaEmail, newPass);

            Thread.sleep(2000);

            userHomePage = loginPage.pressLogInForUserRegister();

            Thread.sleep(2000);

            Assert.assertTrue("'User home' is not opened", userHomePage.isOpened());

            driver.close();
            driver.switchTo().window(tabs.get(0));
        }
    }

    @Test
    public void editEmployeeAsAdminFunctionality() throws SQLException, InterruptedException, IOException, AWTException {

        //EDIT EMPLOYEE FUNCTIONALITY ******************************

        String first_name = "Alexandru";
        String last_name = "Bujor";
        String fn = "Alex";
        String ln = "Bucur";
        String ee = "Alex.Bucur@endava.com";
        String pe = "testingemailautomation@gmail.com";
        String country = "Romania";
        String city = "Iasi";
        String office = "UBC Palas";
        String floor = "1";
        String table = "10";
        String chair = "1";
        String bunit = "ISD";
        String discipline = "Development";
        String grade = "Intern";
        String title = "Developer";
        String groups = "Java";
        String head = "Stefana Munteanu";
        String buddy = "Andreea Croitoru";
        String sdate = "10152017";
        String edate = "12152017";
        String sdate2 = "2017-10-15";
        String edate2 = "2017-12-15";

        viewEmployeesPage = adminHomePage.goToEmployees();

        Thread.sleep(2000);

        Assert.assertTrue("'View employees' is not opened", viewEmployeesPage.isOpened());

        viewEmployeesPage.searchFor(first_name + " " + last_name);

        Thread.sleep(2000);

        editEmployeePage = viewEmployeesPage.goToEditEmployee();

        Thread.sleep(2000);

        Assert.assertTrue("Edit Employee page is not opened", editEmployeePage.isOpened());

        Assert.assertTrue("General info fields are not visible", editEmployeePage.checkGeneralInfo());
        Assert.assertTrue("Location fields are not visible", editEmployeePage.checkLocation());
        Assert.assertTrue("Job details fields are not visible", editEmployeePage.checkJobDetails());

        editEmployeePage.fillGeneralInfo(fn, ln, ee, pe);
        editEmployeePage.fillLocation(country, city, office, floor, table, chair);
        editEmployeePage.fillJobDetails(bunit, discipline, grade, title, groups, head, buddy, sdate, edate);

        viewEmployeesPage = editEmployeePage.pressEditEmployee();

        Thread.sleep(2000);

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,-500)", "");

        Assert.assertTrue("View Employees page is not opened", viewEmployeesPage.isOpened());

        Thread.sleep(2000);

        viewEmployeesPage.searchFor(fn + " " + ln);

        Thread.sleep(2000);

        editEmployeePage = viewEmployeesPage.goToEditEmployee();

        Thread.sleep(2000);

        Assert.assertTrue("Edit Employee page is not opened", editEmployeePage.isOpened());

        Assert.assertTrue("General info fields from Edit Employee are not changed", editEmployeePage.checkGeneralInfoChanges(fn, ln, ee, pe));

        Assert.assertTrue("Location fields from Edit Employee are not changed", editEmployeePage.checkLocationChanges(country, city, office, floor, table, chair));

        Assert.assertTrue("Job details fields from Edit Employee are not changed", editEmployeePage.checkJobDetailsChanges(bunit, discipline, grade, title, groups, head, buddy, sdate2, edate2));

        Thread.sleep(2000);

        viewEmployeesPage = editEmployeePage.goToViewEmployees();

        Thread.sleep(2000);

        Assert.assertTrue("View Employees page is not opened", viewEmployeesPage.isOpened());
    }

    @Test
    public void uploadDownloadAndDeleteDocumentFunctionalities() throws SQLException, InterruptedException, IOException, AWTException {


        //UPLOAD DOCUMENT FUNCTIONALITY *******************************


        String fileName = "Test file";

        viewDocumentsPage = adminHomePage.goToDocumentsPage();

        Thread.sleep(2000);

        Assert.assertTrue("'View documents' is not opened", viewDocumentsPage.isOpened());

        uploadDocumentPage = viewDocumentsPage.uploadDocument();

        Thread.sleep(2000);

        Assert.assertTrue("'Upload document' is not opened", uploadDocumentPage.isOpened());

        Assert.assertTrue("File not uploaded", uploadDocumentPage.fill(fileName, "This is a file uploaded for an automated test"));

        Thread.sleep(6000);

        viewDocumentsPage = uploadDocumentPage.pressUpload();
        Assert.assertTrue("'View documents' is not opened", viewDocumentsPage.isOpened());

        Assert.assertTrue("The right message is not shown when uploading a file correctly", viewDocumentsPage.successMsgEnabled());

        Thread.sleep(6000);

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,-500)", "");

        Thread.sleep(2000);

        viewDocumentsPage.searchFor(fileName);

        Thread.sleep(2000);

        Assert.assertTrue("The uploaded file is not found in the view table", viewDocumentsPage.searchedFileIsFound());


        //DOWNLOAD FILE FUNCTIONALITY ***************************


        Thread.sleep(2000);

        viewDocumentsPage.downloadFile();

        //Open a new tab
        Robot r = new Robot();
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_T);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_T);

        //Switch to the new tab
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1)); //switches to new tab

        chromeDownloadPage = viewDocumentsPage.goToDownloadPage();

        Assert.assertTrue("Not opened", chromeDownloadPage.isOpened());

        Assert.assertTrue("The selected file was not downloaded successfully", chromeDownloadPage.checkDownloadedFile(fileName));

        Thread.sleep(2000);

        driver.close();

        Thread.sleep(2000);

        driver.switchTo().window(tabs.get(0)); //switches to old tab

        Thread.sleep(5000);


        //DELETE FILE FUNCTIONALITY ****************************


        viewDocumentsPage.deleteDocument();

        Thread.sleep(2000);

        viewEmployeesPage = adminHomePage.goToEmployees();
    }

}