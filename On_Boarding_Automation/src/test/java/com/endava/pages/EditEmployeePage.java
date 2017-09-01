package com.endava.pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EditEmployeePage extends AddEmployeePage{
    @FindBy(id = "!!!!!!!!!!!!!!!!!!")
    protected WebElement resetPassword;

    @FindBy(id = "!!!!!!!!!!!!!!!!!!")
    protected WebElement alert;

    @FindBy(id = "submitedit")
    protected WebElement editEmployee;

    public String resetPassword() throws InterruptedException {
        resetPassword.click();
        Thread.sleep(3000);
        alert.click();
        return personalEmail.getAttribute("value");
    }

    public String getStartDate(){
        return startDate.getAttribute("value");
    }

    public ViewEmployeesPage pressEditEmployee() throws IOException, InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(editEmployee));
        editEmployee.click();
        Thread.sleep(2000);
        return new ViewEmployeesPage(driver);
    }

    public void pressEditEmployeeWithNull() throws InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(editEmployee));
        editEmployee.click();
        Thread.sleep(2000);
    }

    public ViewEmployeesPage goToViewEmployees() throws IOException, InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(editEmployee));
        backToEmployees.click();
        Thread.sleep(2000);
        return new ViewEmployeesPage(driver);
    }

    public boolean checkGeneralInfoChanges(String first_name, String last_name, String endava_email, String personal_email) {

        if (!checkField(firstName,first_name)) return false;
        if (!checkField(lastName,last_name)) return false;
        if (!checkField(endavaEmail,endava_email)) return false;
        if (!checkField(personalEmail,personal_email)) return false;

        return true;
    }

    public boolean checkLocationChanges(String string_country, String string_city, String string_office,
                                        String floor_nr, String table_nr, String chair_nr) {

        if (!checkFieldDD(country,string_country)) return false;
        if (!checkFieldDD(city,string_city)) return false;
        if (!checkFieldDD(office,string_office)) return false;
        if (!checkFieldDD(floorNr,floor_nr)) return false;
        if (!checkField(tableNr,table_nr)) return false;
        if (!checkField(chairNr,chair_nr)) return false;

        return true;
    }

    public boolean checkJobDetailsChanges(String business_unit, String string_discipline, String job_grade,
                                          String job_title, String string_groups, String head_of_discipline,
                                          String buddy_name, String start_date, String end_date) {

        if (!checkFieldDD(businessUnit,business_unit)) return false;
        if (!checkFieldDD(discipline,string_discipline)) return false;
        if (!checkFieldDD(jobGrade,job_grade)) return false;
        if (!checkFieldDD(jobTitle,job_title)) return false;
        //if (!checkField(groups,string_groups)) return false;
        if (!checkFieldDD(headOfDiscipline,head_of_discipline)) return false;
        //if (!checkField(buddy,buddy_name)) return false;
        if (!checkField(startDate,start_date)) return false;
        if (!checkField(endDate,end_date)) return false;

        return true;
    }

    public EditEmployeePage(WebDriver driver) throws IOException {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public ViewEmployeesPage goToViewEmployeesPage() throws IOException, InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(backToEmployees));
        backToEmployees.click();
        Thread.sleep(2000);
        return new ViewEmployeesPage(driver);
    }

    public boolean isOpened() {
        return "Edit Employee".equals(driver.getTitle());
    }
}
