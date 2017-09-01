package com.endava.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class AddEmployeePage {

    protected WebDriver driver;

    @FindBy(id = "backText")
    protected WebElement backToEmployees;

    @FindBy(id = "submit")
    protected WebElement addEmployee;

    @FindBy(id = "firstName")
    protected WebElement firstName;

    @FindBy(id = "lastName")
    protected WebElement lastName;

    @FindBy(id = "endavaEmail")
    protected WebElement endavaEmail;

    @FindBy(id = "personalEmail")
    protected WebElement personalEmail;

    @FindBy(id = "tableNr-test")
    protected WebElement tableNr;

    @FindBy(id = "chairNr-test")
    protected WebElement chairNr;

    @FindBy(id = "buddy")
    protected WebElement buddy;

    @FindBy(id = "country")
    protected WebElement country;

    @FindBy(id = "city")
    protected WebElement city;

    @FindBy(id = "office")
    protected WebElement office;

    @FindBy(id = "floor")
    protected WebElement floorNr;

    @FindBy(id = "bUnit")
    protected WebElement businessUnit;

    @FindBy(id = "discipline")
    protected WebElement discipline;

    @FindBy(id = "grade")
    protected WebElement jobGrade;

    @FindBy(id = "title")
    protected WebElement jobTitle;

    @FindBy(id = "group")
    protected WebElement groups;

    @FindBy(id = "head")
    protected WebElement headOfDiscipline;

    @FindBy(id = "startDate")
    protected WebElement startDate;

    @FindBy(id = "endDate")
    protected WebElement endDate;

    @FindBy(id = "table-nr-test")
    protected WebElement tableNrHint;

    @FindBy(id = "chair-nr-test")
    protected WebElement chairNrHint;

    @FindBy(id = "endava-email-test")
    protected WebElement endavaEmailHint;

    @FindBy(id = "personal-email-test")
    protected WebElement personalEmailHint;

    @FindBy(id = "end-date-test")
    protected WebElement endDateHint;

    @FindBy(id = "first-name-test")
    protected WebElement firstNameHint;

    @FindBy(id = "last-name-test")
    protected WebElement lastNameHint;

    @FindBy(id = "country-test")
    protected WebElement countryHint;

    @FindBy(id = "city-test")
    protected WebElement cityHint;

    @FindBy(id = "office-test")
    protected WebElement officeHint;

    @FindBy(id = "floor-test")
    protected WebElement floorNrHint;

    @FindBy(id = "bUnit-test")
    protected WebElement businessUnitHint;

    @FindBy(id = "discipline-test")
    protected WebElement disciplineHint;

    @FindBy(id = "job-grade-test")
    protected WebElement jobGradeHint;

    @FindBy(id = "job-title-test")
    protected WebElement jobTitleHint;

    @FindBy(id = "head-test")
    protected WebElement headOfDisciplineHint;

    @FindBy(id = "start-date-test")
    private WebElement startDateHint;

    public boolean elementHasClasses(WebElement element, String active, String active2) {
        return element.getAttribute("class").contains(active)&&element.getAttribute("class").contains(active2);
    }

    public boolean endavaEmailHasClasses(String class1,String class2){
        if (!elementHasClasses(endavaEmailHint,class1,class2))
            return false;
        return true;
    }

    public boolean personalEmailHasClasses(String class1,String class2){
        if (!elementHasClasses(personalEmailHint,class1,class2))
            return false;
        return true;
    }

    public boolean tableNrHasClasses(String class1,String class2){
        if (!elementHasClasses(tableNrHint,class1,class2))
            return false;
        return true;
    }

    public boolean chairNrHasClasses(String class1,String class2){
        if (!elementHasClasses(chairNrHint,class1,class2))
            return false;
        return true;
    }

    public boolean endDateHasClasses(String class1,String class2){
        if (!elementHasClasses(endDateHint,class1,class2))
            return false;
        return true;
    }

    public boolean checkNullMandatoryFields() {
        if (!elementHasClasses(firstNameHint,"has-error","has-feedback"))
            return false;
        if (!elementHasClasses(lastNameHint,"has-error","has-feedback"))
            return false;
        if (!elementHasClasses(endavaEmailHint,"has-error","has-feedback"))
            return false;
        if (!elementHasClasses(personalEmailHint,"has-error","has-feedback"))
            return false;
        if (!elementHasClasses(countryHint,"has-error","has-feedback"))
            return false;
        if (!elementHasClasses(cityHint,"has-error","has-feedback"))
            return false;
        if (!elementHasClasses(officeHint,"has-error","has-feedback"))
            return false;
        if (!elementHasClasses(floorNrHint,"has-error","has-feedback"))
            return false;
        if (!elementHasClasses(tableNrHint,"has-error","has-feedback"))
            return false;
        if (!elementHasClasses(chairNrHint,"has-error","has-feedback"))
            return false;
        if (!elementHasClasses(businessUnitHint,"has-error","has-feedback"))
            return false;
        if (!elementHasClasses(disciplineHint,"has-error","has-feedback"))
            return false;
        if (!elementHasClasses(jobGradeHint,"has-error","has-feedback"))
            return false;
        if (!elementHasClasses(jobTitleHint,"has-error","has-feedback"))
            return false;
        if (!elementHasClasses(headOfDisciplineHint,"has-error","has-feedback"))
            return false;
        if (!elementHasClasses(startDateHint,"has-error","has-feedback"))
            return false;

        return true;
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

    public AddEmployeePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void fillGeneralInfo(String first_name, String last_name, String endava_email, String personal_email)
            throws InterruptedException {

        complete(firstName,first_name);
        complete(lastName,last_name);
        complete(endavaEmail,endava_email);
        complete(personalEmail,personal_email);
    }

    public void fillLocation(String string_country, String string_city, String string_office, String floor_nr,
                             String table_nr, String chair_nr) throws InterruptedException {

        completeDD(country,string_country);
        completeDD(city,string_city);
        completeDD(office,string_office);
        completeDD(floorNr,floor_nr);
        complete(tableNr,table_nr);
        complete(chairNr,chair_nr);
    }

    public void fillJobDetails(String business_unit, String string_discipline, String job_grade, String job_title,
                               String string_groups, String head_of_discipline, String buddy_name, String start_date,
                               String end_date) throws InterruptedException {

        completeDD(businessUnit,business_unit);
        completeDD(discipline,string_discipline);
        completeDD(jobGrade,job_grade);
        completeDD(jobTitle,job_title);
        complete(groups,string_groups);
        completeDD(headOfDiscipline,head_of_discipline);
        complete(buddy,buddy_name);
        startDate.sendKeys(start_date);
        Thread.sleep(1000);
        endDate.sendKeys(end_date);
        Thread.sleep(1000);
    }

    public ViewEmployeesPage pressAddEmployee() throws IOException, InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(addEmployee));
        addEmployee.click();
        Thread.sleep(2000);
        return new ViewEmployeesPage(driver);
    }

    public void pressAddEmployeeWithNull() throws InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(addEmployee));
        addEmployee.click();
        Thread.sleep(2000);
    }

    public ViewEmployeesPage goToViewEmployees() throws IOException, InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(addEmployee));
        backToEmployees.click();
        Thread.sleep(2000);
        return new ViewEmployeesPage(driver);
    }

    public boolean checkGeneralInfo() {
        if (!firstName.isDisplayed()) {
            System.out.println("First name field not present");
            return false;
        }
        if (!lastName.isDisplayed()) {
            System.out.println("Last name field not present");
            return false;
        }
        if (!endavaEmail.isDisplayed()) {
            System.out.println("Endava email field not present");
            return false;
        }
        if (!personalEmail.isDisplayed()) {
            System.out.println("Personal email field not present");
            return false;
        }
        return true;
    }

    public boolean checkLocation() {
        if (!country.isDisplayed()) {
            System.out.println("Country field not present");
            return false;
        }
        if (!city.isDisplayed()) {
            System.out.println("City field not present");
            return false;
        }
        if (!office.isDisplayed()) {
            System.out.println("Office field not present");
            return false;
        }
        if (!floorNr.isDisplayed()) {
            System.out.println("Floor nr. field not present");
            return false;
        }
        if (!tableNr.isDisplayed()) {
            System.out.println("Table nr. field not present");
            return false;
        }
        if (!chairNr.isDisplayed()) {
            System.out.println("Chair nr. field not present");
            return false;
        }
        return true;
    }

    public boolean checkJobDetails() {
        if (!businessUnit.isDisplayed()) {
            System.out.println("Business unit field not present");
            return false;
        }
        if (!discipline.isDisplayed()) {
            System.out.println("Discipline field not present");
            return false;
        }
        if (!jobGrade.isDisplayed()) {
            System.out.println("Job grade field not present");
            return false;
        }
        if (!jobTitle.isDisplayed()) {
            System.out.println("Job title field not present");
            return false;
        }
        if (!groups.isDisplayed()) {
            System.out.println("Group field not present");
            return false;
        }
        if (!headOfDiscipline.isDisplayed()) {
            System.out.println("Head of discipline field not present");
            return false;
        }
        if (!buddy.isDisplayed()) {
            System.out.println("Buddy field not present");
            return false;
        }
        if (!startDate.isDisplayed()) {
            System.out.println("Start date field not present");
            return false;
        }
        if (!endDate.isDisplayed()) {
            System.out.println("End date field not present");
            return false;
        }
        return true;
    }

    public boolean validateCountryField(String string_country) throws InterruptedException {
        completeDD(country,string_country);

        if (!city.isEnabled()) {
            System.out.println("City is not enabled when Country is selected");
            return false;
        }

        return true;
    }

    public boolean validateCountryField2(String string_country) throws InterruptedException {
        completeDD(country,string_country);

        if (office.isEnabled()) {
            System.out.println("Office is enabled when a new Country is selected");
            return false;
        }

        if (floorNr.isEnabled()) {
            System.out.println("Floor nr. is enabled when a new Country is selected");
            return false;
        }

        if (businessUnit.isEnabled()) {
            System.out.println("Business unit is enabled when a new Country is selected");
            return false;
        }

        if (headOfDiscipline.isEnabled()) {
            System.out.println("Head of discipline is enabled when a new Country is selected");
            return false;
        }

        return true;
    }

    public boolean validateCityField(String string_country, String string_city) throws InterruptedException {
        completeDD(country,string_country);
        completeDD(city,string_city);

        if (!office.isEnabled()) {
            System.out.println("Office is not enabled when City is selected");
            return false;
        }

        return true;
    }

    public boolean validateCityField2(String string_country, String string_city) throws InterruptedException {
        completeDD(country,string_country);
        completeDD(city,string_city);

        if (floorNr.isEnabled()) {
            System.out.println("Floor nr. is enabled when a new City is selected");
            return false;
        }

        if (businessUnit.isEnabled()) {
            System.out.println("Business unit is enabled when a new City is selected");
            return false;
        }

        if (headOfDiscipline.isEnabled()) {
            System.out.println("Head of discipline is enabled when a new City is selected");
            return false;
        }

        return true;
    }

    public boolean validateOfficeField(String string_country, String string_city, String string_office) throws InterruptedException {
        completeDD(country,string_country);
        completeDD(city,string_city);
        completeDD(office,string_office);

        if (!floorNr.isEnabled()) {
            System.out.println("Floor nr. is not enabled when Office is selected");
            return false;
        }

        return true;
    }

    public boolean validateOfficeField2(String string_country, String string_city, String string_office) throws InterruptedException {
        completeDD(country,string_country);
        completeDD(city,string_city);
        completeDD(office,string_office);

        if (headOfDiscipline.isEnabled()) {
            System.out.println("Head of discipline is enabled when a new Office is selected");
            return false;
        }

        return true;
    }

    public boolean validateBusinessUnitField(String string_country, String string_city, String string_office,
                                             String business_unit) throws InterruptedException {
        completeDD(country,string_country);
        completeDD(city,string_city);
        completeDD(office,string_office);
        completeDD(businessUnit,business_unit);

        if (discipline.isSelected() && !headOfDiscipline.isEnabled()) {
            System.out.println("Discipline is disabled when Business unit and Discipline are selected");
            return false;
        }

        return true;
    }

    public boolean validateBusinessUnitField2(String string_country, String string_city, String string_office,
                                              String business_unit) throws InterruptedException {
        completeDD(country,string_country);
        completeDD(city,string_city);
        completeDD(office,string_office);
        completeDD(businessUnit,business_unit);

        if (discipline.isSelected() && !headOfDiscipline.isEnabled()) {
            System.out.println("Discipline is disabled when Business unit and Discipline are selected");
            return false;
        }

        return true;
    }

    public boolean validateDisciplineField(String string_country, String string_city, String string_office,
                                           String string_discipline) throws InterruptedException {
        completeDD(country,string_country);
        completeDD(city,string_city);
        completeDD(office,string_office);
        completeDD(discipline,string_discipline);

        Thread.sleep(1000);

        if (businessUnit.isSelected() && !headOfDiscipline.isEnabled()) {
            System.out.println("Head of discipline is disabled when Business unit and Discipline are selected");
            return false;
        }

        return true;
    }

    public boolean validateDisciplineField2(String string_country, String string_city, String string_office,
                                            String string_discipline) throws InterruptedException {
        completeDD(country,string_country);
        completeDD(city,string_city);
        completeDD(office,string_office);
        completeDD(discipline,string_discipline);

        Select dropDown;
        dropDown = new Select(businessUnit);

        if (dropDown.getFirstSelectedOption().getText().compareToIgnoreCase("") == 0 && headOfDiscipline.isEnabled()) {
            System.out.println("Head of discipline is enabled when only Discipline is selected");
            return false;
        }

        return true;
    }

    public boolean validateDisciplineField3(String string_country, String string_city, String string_office,
                                            String string_discipline) throws InterruptedException {
        completeDD(country,string_country);
        completeDD(city,string_city);
        completeDD(office,string_office);
        completeDD(discipline,string_discipline);

        if (!jobTitle.isEnabled()) {
            System.out.println("Job title is not enabled when Discipline is selected");
            return false;
        }

        return true;
    }

    public boolean checkField(WebElement elem, String str){
        if (elem.getAttribute("value").compareToIgnoreCase(str) != 0) return false;
        return  true;
    }

    public boolean checkFieldDD(WebElement elem, String str){
        Select dropDown;
        dropDown = new Select(elem);
        if (dropDown.getFirstSelectedOption().getText().compareToIgnoreCase(str) != 0) return false;
        return  true;
    }

    public boolean checkGeneralInfoDefault() {
        if (!checkField(firstName,"")) return false;
        if (!checkField(lastName,"")) return false;
        if (!checkField(endavaEmail,"")) return false;
        if (!checkField(personalEmail,"")) return false;
        return true;
    }

    public boolean checkLocationDefault() {
        if (!checkFieldDD(country,"")) return false;
        if (!checkFieldDD(city,"")) return false;
        if (!checkFieldDD(office,"")) return false;
        if (!checkFieldDD(floorNr,"")) return false;
        if (!checkField(tableNr,"")) return false;
        if (!checkField(chairNr,"")) return false;
        return true;
    }

    public boolean checkJobDetailsDefault() {
        if (!checkFieldDD(businessUnit,"")) return false;
        if (!checkFieldDD(discipline,"")) return false;
        if (!checkFieldDD(jobGrade,"")) return false;
        if (!checkFieldDD(jobTitle,"")) return false;
        if (!checkField(groups,"")) return false;
        if (!checkFieldDD(headOfDiscipline,"")) return false;
        //if (!checkField(buddy,"")) return false;
        if (!checkField(startDate,"")) return false;
        if (!checkField(endDate,"")) return false;
        return true;
    }

    public boolean isOpened() {
        return "Add Employee".equals(driver.getTitle());
    }
}
