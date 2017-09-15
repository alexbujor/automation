package com.endava.email;

import com.endava.pages.ChangePasswordPage;
import com.endava.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class EmailHelperA {

    public String getPasswordFromMail(String user, String pass, WebDriver driver) throws InterruptedException, IOException {

        String result = "";

        driver.get("https://accounts.google.com/ServiceLogin?");

        // gmail login
        driver.findElement(By.id("identifierId")).sendKeys(user);
        Thread.sleep(2000);
        driver.findElement(By.className("CwaK9")).click();
        Thread.sleep(2000);
        driver.findElement(By.name("password")).sendKeys(pass);
        Thread.sleep(2000);
        driver.findElement(By.className("CwaK9")).click();
        Thread.sleep(2000);

        // some optional actions for reaching gmail inbox
        driver.findElement(By.className("WaidBe")).click();

        // now taking un-read emails form inbox into a list
        List<WebElement> unreademail = driver.findElements(By.cssSelector(".zA.zE"));

        // Mailer name for which i want to check do i have an email in my inbox
        String MyMailer = "internship2017java1";

        // real logic starts here
        for (int i = 0; i < unreademail.size(); i++) {
            if (unreademail.get(i).getText().split("\\s+")[0].equals(MyMailer)) {
                result = unreademail.get(i).getText().split("\\s+")[13];
                unreademail.get(i).click();
            }
        }
        return result;
    }

    public ChangePasswordPage checkMailForResetAsAdmin(String user, String pass, WebDriver driver) throws InterruptedException, IOException {

        driver.get("https://accounts.google.com/ServiceLogin?");

        // gmail login
        driver.findElement(By.id("identifierId")).sendKeys(user);
        Thread.sleep(2000);
        driver.findElement(By.className("CwaK9")).click();
        Thread.sleep(2000);
        driver.findElement(By.name("password")).sendKeys(pass);
        Thread.sleep(2000);
        driver.findElement(By.className("CwaK9")).click();
        Thread.sleep(2000);

        // some optional actions for reaching gmail inbox
        driver.findElement(By.className("WaidBe")).click();

        // now taking un-read emails form inbox into a list
        List<WebElement> unreademail = driver.findElements(By.className("zF"));

        // Mailer name for which i want to check do i have an email in my inbox
        String MyMailer = "internship2017java1";

        // real logic starts here
        for (int i = 0; i < unreademail.size(); i++) {
            if (unreademail.get(i).isDisplayed() == true) {
                // now verify if you have got mail form a specific mailer (Note Un-read mails)
                // for read mails xpath loactor will change but logic will remain same
                if (unreademail.get(i).getText().equals(MyMailer)) {
                    unreademail.get(i).click();
                    Thread.sleep(2000);
                    if(driver.findElement(By.cssSelector("a[href*='password']")).isDisplayed()) {
                        driver.findElement(By.cssSelector("a[href*='password']")).click();
                        Thread.sleep(2000);
                        return new ChangePasswordPage(driver);
                        // also you can perform more actions here
                        // like if you want to open email form the mailer
                    }
                }
            }
        }
        return null;
    }

    public void getPassword(String user, String pass) throws InterruptedException, IOException {

        String result = "";

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get("https://accounts.google.com/ServiceLogin?");


        // gmail login
        driver.findElement(By.id("identifierId")).sendKeys(user);
        Thread.sleep(2000);
        driver.findElement(By.className("CwaK9")).click();
        Thread.sleep(2000);
        driver.findElement(By.name("password")).sendKeys(pass);
        Thread.sleep(2000);
        driver.findElement(By.className("CwaK9")).click();
        Thread.sleep(2000);

        // some optional actions for reaching gmail inbox
        driver.findElement(By.className("WaidBe")).click();

        // now taking un-read emails form inbox into a list
        List<WebElement> unreademail = driver.findElements(By.cssSelector(".zA.zE"));

        // Mailer name for which i want to check do i have an email in my inbox
        String MyMailer = "internship2017java1";

        // real logic starts here
        for (int i = 0; i < unreademail.size(); i++) {
            if (unreademail.get(i).getText().split("\\s+")[0].equals(MyMailer)) {
                result = unreademail.get(i).getText().split("\\s+")[13];
                unreademail.get(i).click();
            }
        }

        driver.close();

        System.out.println(result);
    }

    public static void main(String[] args) throws InterruptedException, IOException {

        EmailHelperA emailHelperA = new EmailHelperA();
        emailHelperA.getPassword("testingemailautomation@gmail.com","Testing1234");
    }
}
