package com.endava.pages;

import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AdminHomePage {

    private WebDriver driver;
    @FindBy(id = "employeesBtn")
    private WebElement employees;

    @FindBy(id = "homeBtn")
    private WebElement home;

    public AdminHomePage(WebDriver driver) throws IOException {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ViewEmployeesPage goToEmployees() throws IOException, InterruptedException {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(employees));
        employees.click();
        Thread.sleep(2000);
        return new ViewEmployeesPage(driver);
    }

    public void goHome(){
        home.click();
    }

    public boolean isOpened() {
        return "Admin Welcome".equals(driver.getTitle());
    }
}
