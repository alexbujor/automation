package com.endava.pages;

import com.endava.database.DBConnection;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserHomePage {
    private WebDriver driver;

    @FindBy(id = "logoutIcon")
    private WebElement logout;

    @FindBy(className = "toggle-map-button")
    private WebElement btnMap;

    @FindBy(className = "displayed")
    private WebElement mapDisplayed;

    @FindBy(className = "option")
    private WebElement btnQuiz;

    @FindBy(id = "rightArrow")
    private WebElement btnNext;

    @FindBy(id = "leftArrow")
    private WebElement btnBack;

    @FindBy(id = "days")
    private WebElement days;

    @FindBy(id = "hours")
    private WebElement hours;

    @FindBy(id = "progressBars")
    private WebElement progresBar;

    @FindBy(className = "paging")
    private WebElement scrollDone;

    @FindBy(className = "title")
    private WebElement scrollToQuiz;

    @FindBy(id = "viewAllBtn")
    private WebElement scrollToPeopleOfInterest;

    @FindBy(id = "finishText")
    private WebElement confirmSentDocPAge;

    @FindBy(id = "finishedBtn")
    private WebElement btnSentAllDoc;

    @FindBy(id="profileBtn")
    private WebElement btnProfile;

    @FindBy(id="checklist")
    private WebElement btnChecklist;

    @FindBy(className = "checkBox")
    private WebElement checkBox;


    @FindBy(id = "daysNumber")
    private WebElement daysNr;

    @FindBy(className = "date-name-label")
    private WebElement col;

    @FindBy(className = "twoLineDaysText")
    private WebElement oldEmpText;

    public UserHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public LoginPage goToLoginPage() throws InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(logout));
        logout.click();
        Thread.sleep(2000);
        return new LoginPage(driver);
    }

    public void printDate(){
        System.out.println(days.getText());
        System.out.println(hours.getText());
    }

    public LoginPage pressLogoutBtn() throws InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(logout));
        logout.click();
        Thread.sleep(1000);
        return new LoginPage(driver);
    }

    public boolean validateCountdownFirstDay() throws InterruptedException, SQLException {

        if(days.getText().compareToIgnoreCase("1")!=0 && col.getText().compareToIgnoreCase("DAY")!=0) return false;
        return true;
    }

    public boolean validateCountdownOldEmployee(String fname, String lname) throws InterruptedException, SQLException {
        String actual;
        List<String> expected;
        DBConnection dbConnection = new DBConnection();

        String query = "select current_date::date - e.start_date::date as days from employees e where first_name = '" + fname + "' and last_name = '" + lname + "';";
        String column = "days";
        expected = dbConnection.getDbInfo(query, column);

        actual = daysNr.getText();

        if(!actual.equals(expected.get(0)) && oldEmpText.getText().compareToIgnoreCase("days at Endava")!=0) return false;
        return true;
    }

    public boolean validateCountdownFutureEmployee(String fname, String lname) throws InterruptedException, SQLException {
        String actual;
        List<String> expected;
        DBConnection dbConnection = new DBConnection();

        String query = "select e.start_date::date - current_date ::date -1 as days from employees e where first_name = '" + fname + "' and last_name = '" + lname + "';";
        String column = "days";
        expected = dbConnection.getDbInfo(query, column);

        actual = days.getText();

        if(actual.substring(0,1).compareToIgnoreCase("0")==0)
            if(actual.substring(1).compareToIgnoreCase(expected.get(0))!=0) return false;
        if(actual.substring(0,1).compareToIgnoreCase("0")!=0)
            if(actual.compareToIgnoreCase(expected.get(0))!=0) return false;
        return true;
    }

    public void verifyCheckBox() throws InterruptedException {

        btnProfile.click();
        Thread.sleep(1000);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(btnChecklist));
        btnChecklist.click();
        Thread.sleep(1000);
        WebElement checkListTable = driver.findElement(By.id("checkList"));
        List<WebElement> listCheckBoxes = checkListTable.findElements(By.className("checkBox"));
        int counter = 0;
        for (WebElement element : listCheckBoxes) {
            counter++;
            Thread.sleep(1000);
            element.click();
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            if(counter == 14) {
                jse.executeScript("window.scrollBy(0,450)", "");
                Thread.sleep(1000);
                jse.executeScript("window.scrollBy(0,-450)", "");
                counter = 0;
            }

        }

    }

    public void scrollToMap(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", mapDisplayed);
    }




    public ConfirmChecklistPage goToConfirmChecklistPage() throws InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(btnSentAllDoc));
        btnSentAllDoc.click();
        Thread.sleep(1000);
        return new ConfirmChecklistPage(driver);
    }

    public void clickOnQuiz(){
        btnQuiz.click();

    }

    public void scrollToQuiz(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", scrollToQuiz);
    }

    public void clickOnMap() throws InterruptedException {
        btnMap.click();
        Thread.sleep(5000);
        mapDisplayed.isDisplayed();

    }

    public void clickOnNextAndBackPeopleOfInterest() throws InterruptedException {
        btnNext.click();
        Thread.sleep(5000);
        btnBack.click();
    }

    public void scrollToPeopleOfInterest(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", scrollToPeopleOfInterest);
    }

    public boolean isOpened() {return "User Home".equals(driver.getTitle());


    }
}
