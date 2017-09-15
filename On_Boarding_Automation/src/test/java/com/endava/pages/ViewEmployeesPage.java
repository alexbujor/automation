package com.endava.pages;

import com.endava.database.DBConnection;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class ViewEmployeesPage {
    private WebDriver driver;

    @FindBy(id = "logoutIcon")
    private WebElement logout;

    @FindBy(id = "addEmployee")
    private WebElement addEmployee;

    @FindBy(id = "searchBar")
    private WebElement searchBar;

    @FindBy(id = "disciplines")
    private WebElement cboDiscipline;

    @FindBy(id = "jobGrades")
    private WebElement cboJobGrade;

    @FindBy(id = "editIcon")
    private WebElement btnEdit;

    @FindBy(id = "deleteIcon")
    private WebElement btnArchive;

    @FindBy(css = ".table-border.table-hover.col-md-12")
    private WebElement gridEmployee;

    @FindBy(id = "backward")
    private WebElement btnBack;

    @FindBy(id = "forward")
    private WebElement btnForward;

    @FindBy(id = "items")
    private WebElement cboListings;

    @FindBy(id = "sortDesc")
    private WebElement btnSortAsc;

    @FindBy(id = "sortAsc")
    private WebElement btnSortDesc;

    @FindBy(id = "pages")
    private WebElement pages;

    @FindBy(id = "yes-button")
    private WebElement alert;

    public  boolean logoutIsVisible(){
        if(logout.isDisplayed()) return true;
        return false;
    }

    public void complete(WebElement elem, String str) throws InterruptedException {
        elem.clear();
        elem.sendKeys(str);
        Thread.sleep(1000);
    }

    public void completeDD(WebElement elem, String str) throws InterruptedException {
        Select dropDown;
        dropDown = new Select(elem);
        dropDown.selectByVisibleText(str);
        Thread.sleep(1000);
    }

    public ArrayList<String> getElemsFromGrid(WebElement elem, String listingNumber) throws InterruptedException {
        ArrayList<String> result = new ArrayList<>();

        completeDD(cboListings, listingNumber);

        elem.click();

        List<WebElement> allEmployees = gridEmployee.findElements(By.cssSelector(".ng-untouched.ng-pristine.ng-valid"));

        for(WebElement item:allEmployees) {
            String[] splited = item.getText().split("\\s+");
            result.add(splited[1] + " " + splited[2]);
        }
        return result;
    }

    public void getElemsFromGridIntoList(ArrayList<String> result) throws InterruptedException {

        List<WebElement> allEmployees = gridEmployee.findElements(By.cssSelector(".ng-untouched.ng-pristine.ng-valid"));

        for(WebElement item:allEmployees) {
            String[] splited = item.getText().split("\\s+");
            result.add(splited[1] + " " + splited[2]);
        }
    }

    public boolean verifySearchFunctionality(String searchTerm) throws SQLException, InterruptedException {
        ArrayList<String> actual = new ArrayList<>();
        List<String> expected;
        DBConnection dbConnection = new DBConnection();

        String query = "select concat(first_name, concat(' ', last_name)) from employees where archived=false and concat(first_name, last_name) Like '%" + searchTerm + "%'";
        String column = "concat";
        expected = dbConnection.getDbInfo(query, column);

        searchFor(searchTerm);
        getElemsFromGridIntoList(actual);

        while(actual.size()<expected.size()){
            btnForward.click();
            getElemsFromGridIntoList(actual);
        }

        Collections.sort(actual);
        Collections.sort(expected);

        return actual.equals(expected);
    }

    public boolean verifyDropDownsFunctionality(String string_discipline, String string_jobgrade) throws SQLException, InterruptedException {
        ArrayList<String> actual = new ArrayList<>();
        List<String> expected;
        DBConnection dbConnection = new DBConnection();

        String query = "select concat(first_name, concat(' ', last_name)) from employees e, job_details j where e.job_details_id = j.job_details_id and archived=false and discipline = '" + string_discipline + "' and job_grade = '" + string_jobgrade + "'";
        String column = "concat";
        expected = dbConnection.getDbInfo(query, column);

        completeDD(cboDiscipline, string_discipline);
        completeDD(cboJobGrade, string_jobgrade);

        getElemsFromGridIntoList(actual);

        while(actual.size()<expected.size()){
            btnForward.click();
            getElemsFromGridIntoList(actual);
        }

        Collections.sort(actual);
        Collections.sort(expected);

        return actual.equals(expected);
    }

    public boolean validateAscending(String listingNumber) throws InterruptedException, SQLException {
        ArrayList<String> actual;
        List<String> expected;
        DBConnection dbConnection = new DBConnection();

        String query = "select concat(first_name, concat(' ', last_name)) from employees where archived = false order by concat(first_name, last_name) asc limit " + listingNumber + ";";
        String column = "concat";
        expected = dbConnection.getDbInfo(query, column);

        completeDD(cboListings, listingNumber);

        actual = getElemsFromGrid(btnSortAsc,listingNumber);

        Collections.sort(actual);
        Collections.sort(expected);

        return actual.equals(expected);
    }

    public boolean validateDescending(String listingNumber) throws InterruptedException, SQLException {
        ArrayList<String> actual;
        List<String> expected;
        DBConnection dbConnection = new DBConnection();

        String query = "select concat(first_name, concat(' ', last_name)) from employees where archived = false order by concat(first_name, last_name) desc limit " + listingNumber + ";";
        String column = "concat";
        expected = dbConnection.getDbInfo(query, column);

        completeDD(cboListings, listingNumber);

        actual = getElemsFromGrid(btnSortDesc,listingNumber);

        Collections.sort(actual);
        Collections.sort(expected);

        return actual.equals(expected);
    }

    public boolean checkListings(String listingNumber) throws InterruptedException {
        completeDD(cboListings, listingNumber);

        List<WebElement> allEmployees = gridEmployee.findElements(By.cssSelector(".ng-untouched.ng-pristine.ng-valid"));

        if(listingNumber.compareToIgnoreCase(Integer.toString(allEmployees.size()))==0) return true;
        return false;
    }

    public ViewEmployeesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public AddEmployeePage goToAddEmployee() throws InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(addEmployee));
        addEmployee.click();
        Thread.sleep(2000);
        return new AddEmployeePage(driver);
    }

    public LoginPage goToLoginPage() throws InterruptedException {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(logout));
        logout.click();
        Thread.sleep(2000);
        return new LoginPage(driver);
    }

    public EditEmployeePage goToEditEmployee() throws IOException, InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(btnEdit));
        btnEdit.click();
        Thread.sleep(2000);
        return new EditEmployeePage(driver);
    }

    public void archiveEmployee() throws InterruptedException {
        btnArchive.click();
        Thread.sleep(2000);
        alert.click();
    }

    public void searchFor(String searchTerm) {
        searchBar.click();
        searchBar.clear();
        searchBar.sendKeys(searchTerm);
        searchBar.sendKeys(Keys.ENTER);
    }

    public boolean isOpened() {
        return btnEdit.isDisplayed();
    }
}
