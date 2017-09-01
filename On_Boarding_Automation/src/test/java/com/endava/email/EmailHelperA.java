package com.endava.email;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class EmailHelperA {

    public boolean checkMail(String user, String pass) throws InterruptedException, IOException {

        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\test\\resources\\defaultConfig.properties"));
        String driverPath = properties.getProperty("chromedriverPath");
        System.setProperty("webdriver.chrome.driver",driverPath);
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get("https://accounts.google.com/ServiceLogin?");

        // gmail login
        driver.findElement(By.id("identifierId")).sendKeys(user);
        driver.findElement(By.className("CwaK9")).click();
        driver.findElement(By.name("password")).sendKeys(pass);
        driver.findElement(By.className("CwaK9")).click();

        Thread.sleep(5000);

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
                    System.out.println("Yes, we have got mail from " + MyMailer);
                    driver.close();
                    return true;
                    // also you can perform more actions here
                    // like if you want to open email form the mailer
                }
            }
        }
        System.out.println("No mail from " + MyMailer);
        driver.close();
        return false;
    }

    public static void main(String[] args) throws InterruptedException, IOException {

        EmailHelperA emailHelperA = new EmailHelperA();
        System.out.println(emailHelperA.checkMail("testingemailautomation@gmail.com","Testing1234"));
    }
}
