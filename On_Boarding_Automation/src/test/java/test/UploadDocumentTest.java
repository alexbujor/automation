package test;

import com.endava.pages.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UploadDocumentTest {
    WebDriver driver;
    LoginPage loginPage;
    AdminHomePage adminHomePage;
    ViewDocumentsPage viewDocumentsPage;
    UploadDocumentPage uploadDocumentPage;

    @Before
    public void setup() throws IOException, InterruptedException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\test\\resources\\defaultConfig.properties"));
        String driverPath = properties.getProperty("chromedriverPath");
        System.setProperty("webdriver.chrome.driver",driverPath);
        driver = new ChromeDriver();
        Dimension dim = new Dimension(1450,860);
        Point pt = new Point(150,0);
        driver.manage().window().setPosition(pt);
        driver.manage().window().setSize(dim);
        openPage();
    }

    @After
    public void tearDown() throws InterruptedException {
        loginPage = viewDocumentsPage.goToLoginPage();

        Assert.assertTrue("'Login' is not opened", loginPage.isOpened());

        driver.manage().deleteAllCookies();
        driver.quit();
    }

    public void openPage() throws IOException, InterruptedException {
        String adminEmail = "admin@endava.com";
        String adminPassword = "Parola#123";

        loginPage = new LoginPage(driver).get();

        Assert.assertTrue("'Login' is not opened", loginPage.isOpened());

        loginPage.fillFormsLogin(adminEmail,adminPassword);

        adminHomePage = loginPage.pressLogInForAdmin();

        Assert.assertTrue("'Admin home' is not opened", adminHomePage.isOpened());

        viewDocumentsPage = adminHomePage.goToDocumentsPage();

        Assert.assertTrue("'View documents' is not opened", viewDocumentsPage.isOpened());

        uploadDocumentPage = viewDocumentsPage.uploadDocument();
        Assert.assertTrue("'Upload document' is not opened",uploadDocumentPage.isOpened());
    }

    @Test
    public void uploadVerificationWithPositiveData() throws IOException, InterruptedException {
        Assert.assertTrue("File not uploaded",uploadDocumentPage.fill("Test file","This is a file uploaded for an automated test"));

        viewDocumentsPage = uploadDocumentPage.pressUpload();
        Assert.assertTrue("'View documents' is not opened", viewDocumentsPage.isOpened());

        Assert.assertTrue("The right message is not shown when uploading a file correctly", viewDocumentsPage.successMsgEnabled());

        Thread.sleep(6000);

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,-500)", "");

        Thread.sleep(2000);

        viewDocumentsPage.searchFor("Test file");

        Thread.sleep(2000);

        Assert.assertTrue("The uploaded file is not found in the view table",viewDocumentsPage.searchedFileIsFound());
    }

    @Test
    public void uploadVerificationUploadingAFileAgain() throws IOException, InterruptedException {
        Assert.assertTrue("File not uploaded",uploadDocumentPage.fill("Test file","This is a file uploaded for an automated test"));

        viewDocumentsPage = uploadDocumentPage.pressUpload();
        Assert.assertTrue("'View documents' is not opened", viewDocumentsPage.isOpened());

        Assert.assertTrue("The right message is not shown when uploading a file again(duplicate)", viewDocumentsPage.errorMsgEnabled());

        Thread.sleep(2000);
    }

    @Test
    public void checkBackToDocumentsButton() throws InterruptedException, IOException {
        Assert.assertTrue("File not uploaded",uploadDocumentPage.fill("Test file","This is a file uploaded for an automated test"));

        viewDocumentsPage = uploadDocumentPage.goToViewDocuments();
        Assert.assertTrue("'View documents' is not opened", viewDocumentsPage.isOpened());

        uploadDocumentPage = viewDocumentsPage.uploadDocument();
        Assert.assertTrue("'Upload document' is not opened",uploadDocumentPage.isOpened());

        Assert.assertTrue("The fields are not empty when opening the upload file page",uploadDocumentPage.checkDefaultFields());
    }

    @Test
    public void checkUploadWithNull() throws InterruptedException, IOException {
        uploadDocumentPage.pressUploadWithNull();

        Assert.assertTrue("'Upload document' is not opened when submitting with NULL data",uploadDocumentPage.isOpened());
    }
}
