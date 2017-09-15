package test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import utils.ChromeDownloadPage;
import utils.TestPage;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class TestPageTest  {
    WebDriver driver;
    TestPage testPage;
    ChromeDownloadPage chromeDownloadPage;

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
    public void tearDown(){
        driver.quit();
    }

    public void openPage() throws InterruptedException {
        testPage = new TestPage(driver).get();
        Thread.sleep(1000);
        Assert.assertTrue("Page not opened", testPage.isOpened());
        Thread.sleep(1000);
    }

    @Test
    public void uploadTest() throws InterruptedException, IOException, AWTException {
        testPage.uploadFile();
        Thread.sleep(6000);
    }

    @Test
    public void downloadTest() throws InterruptedException, IOException, AWTException {

        testPage.downloadFile();

        //Open a new tab
        Robot r = new Robot();
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_T);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_T);

        //Switch to the new tab
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1)); //switches to new tab

        chromeDownloadPage = testPage.goToDownloadPage();

        Assert.assertTrue("Not opened",chromeDownloadPage.isOpened());

        Assert.assertTrue("The selected file was not downloaded successfully", chromeDownloadPage.checkDownloadedFile("OnlineStore"));

        Thread.sleep(2000);

        driver.close();

        Thread.sleep(2000);

        driver.switchTo().window(tabs.get(0)); //switches to old tab

        Thread.sleep(2000);
    }
}
