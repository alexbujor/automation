package test;

import com.endava.database.DBConnection;
import com.endava.email.EmailHelperA;
import com.endava.pages.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.GetDate;
import utils.TextParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class EditEmployeeTest {
    WebDriver driver;
    LoginPage loginPage;
    AdminHomePage adminHomePage;
    ViewEmployeesPage viewEmployeesPage;
    EditEmployeePage editEmployeePage;
    String id;

    @Before
    public void setup() throws IOException, InterruptedException, SQLException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\test\\resources\\defaultConfig.properties"));
        String driverPath = properties.getProperty("chromedriverPath");
        System.setProperty("webdriver.chrome.driver",driverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("incognito");
        driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();
        openPage();
    }

    @After
    public void tearDown(){
        driver.manage().deleteAllCookies();
        driver.quit();
    }

    public void openPage() throws IOException, InterruptedException, SQLException {
        String adminEmail = "admin@endava.com";
        String adminPassword = "Parola#123";
        String first_name = "Alex";
        String last_name = "Bujor";
        DBConnection dbConnection = new DBConnection();
        List<String> actual;

        loginPage = new LoginPage(driver).get();

        Thread.sleep(2000);

        Assert.assertTrue("'Login' is not opened", loginPage.isOpened());

        Thread.sleep(2000);

        loginPage.fillFormsLogin(adminEmail,adminPassword);

        Thread.sleep(2000);

        adminHomePage = loginPage.pressLogInForAdmin();

        Thread.sleep(2000);

        Assert.assertTrue("'Admin home' is not opened", adminHomePage.isOpened());

        Thread.sleep(2000);

        viewEmployeesPage = adminHomePage.goToEmployees();

        Thread.sleep(2000);

        Assert.assertTrue("'View employees' is not opened", viewEmployeesPage.isOpened());

        Thread.sleep(2000);

        viewEmployeesPage.searchFor(first_name + " " + last_name);

        String query = "select employee_id from employees where first_name = '"+ first_name +"' and last_name = '"+ last_name +"';";
        String column = "employee_id";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("User not found", actual.size()>0);
        id = actual.get(0);

        Thread.sleep(2000);

        editEmployeePage = viewEmployeesPage.goToEditEmployee();

        Thread.sleep(2000);

        Assert.assertTrue("Edit Employee page is not opened",editEmployeePage.isOpened());

        Thread.sleep(2000);
    }

    @Test
    public void resetThePasswordForAnEmployee() throws InterruptedException, SQLException, IOException {
        TextParser textParser = new TextParser();
        EmailHelperA emailHelperA = new EmailHelperA();
        GetDate getDate = new GetDate();

        Thread.sleep(2000);

        if(getDate.startIsDateGreaterThanToday(editEmployeePage.getStartDate())){
            String mail = editEmployeePage.resetPassword();
            if (textParser.stringExtractor(mail, "\\b[\\w.%+-]+@gmail.[a-zA-Z]{2,6}\\b"))
                Assert.assertTrue("The email with the new password was not sent", emailHelperA.checkMail(mail, "Testing1234"));
        }
    }

    @Test
    public void editAnEmployeeAndSeeIfTheValuesChange() throws InterruptedException, SQLException, IOException {
        List<String> actual = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();

        String query;
        String column;

        String fn = "Alex";
        String ln = "Bujor";
        String ee = "Alexandru.Bujor@endava.com";
        String pe = "testingemailautomation@gmail.com";
        String country = "Romania";
        String city = "Iasi";
        String office = "UBC Palas";
        String floor = "4";
        String table = "1";
        String chair = "5";
        String bunit = "ISD";
        String discipline = "Development";
        String grade = "Intern";
        String title = "Developer";
        String groups = "Java, .NET";
        String head = "Stefana Munteanu";
        String buddy = "Andreea Croitoru";
        String sdate = "04152017";
        String edate = "09152017";

        Assert.assertTrue("General info fields are not visible",editEmployeePage.checkGeneralInfo());
        Assert.assertTrue("Location fields are not visible",editEmployeePage.checkLocation());
        Assert.assertTrue("Job details fields are not visible",editEmployeePage.checkJobDetails());

        Thread.sleep(2000);

        editEmployeePage.fillGeneralInfo(fn, ln, ee, pe);
        editEmployeePage.fillLocation(country,city,office,floor,table,chair);
        editEmployeePage.fillJobDetails(bunit,discipline,grade,title,groups,head,buddy,sdate,edate);

        viewEmployeesPage = editEmployeePage.pressEditEmployee();

        Thread.sleep(2000);

        Assert.assertTrue("View Employees page is not opened",viewEmployeesPage.isOpened());

        Thread.sleep(2000);

        actual.clear();
        query = "select employee_id from employees where first_name = '"+ fn +"' and last_name = '"+ ln +"';";
        column = "employee_id";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("User not found", actual.size()>0);
        String id2=actual.get(0);
        viewEmployeesPage.searchFor(fn + " " + ln);

        Thread.sleep(2000);

        Assert.assertTrue("The user's information did not update",id.compareToIgnoreCase(id2)==0);

        editEmployeePage = viewEmployeesPage.goToEditEmployee();

        Thread.sleep(2000);

        Assert.assertTrue("Edit Employee page is not opened",editEmployeePage.isOpened());

        Thread.sleep(2000);

        Assert.assertTrue("General info fields from Edit Employee are not changed",editEmployeePage.checkGeneralInfoChanges(fn, ln, ee, pe));

        Assert.assertTrue("Location fields from Edit Employee are not changed",editEmployeePage.checkLocationChanges(country,city,office,floor,table,chair));

        Assert.assertTrue("Job details fields from Edit Employee are not changed",editEmployeePage.checkJobDetailsChanges(bunit,discipline,grade,title,groups,head,buddy,sdate,edate));
    }

    @Test
    public void checkBackToEmployeesButton() throws InterruptedException, SQLException, IOException {
        Assert.assertTrue("General info fields are not visible",editEmployeePage.checkGeneralInfo());
        Assert.assertTrue("Location fields are not visible",editEmployeePage.checkLocation());
        Assert.assertTrue("Job details fields are not visible",editEmployeePage.checkJobDetails());

        Thread.sleep(2000);

        viewEmployeesPage = editEmployeePage.goToViewEmployees();

        Thread.sleep(2000);

        Assert.assertTrue("View Employees page is not opened",viewEmployeesPage.isOpened());

        Thread.sleep(2000);
    }

    @Test
    public void validateGeneralInfoFields() throws InterruptedException {
        //Positive

        editEmployeePage.fillGeneralInfo("Alexandru","Bujor","Alexandru.Bujor@endava.com","alexbujor94@gmail.com");

        Thread.sleep(2000);

        Assert.assertTrue("Endava email is badly checked when entering positive data",editEmployeePage.endavaEmailHasClasses("has-success","has-feedback"));

        Assert.assertTrue("Personal email is badly checked when entering positive data",editEmployeePage.personalEmailHasClasses("has-success","has-feedback"));


        //Negative

        editEmployeePage.fillGeneralInfo("Alexandru","Bujor","Alexandru.Bujor@endavacom","alexbujor94gmail.com");

        Thread.sleep(2000);

        Assert.assertTrue("Endava email is badly checked when entering negative data",editEmployeePage.endavaEmailHasClasses("has-error","has-feedback"));

        Assert.assertTrue("Personal email is badly checked when entering negative data",editEmployeePage.personalEmailHasClasses("has-error","has-feedback"));
    }

    @Test
    public void validateLocationFields() throws InterruptedException {
        String country = "Romania";
        String city = "Iasi";
        String office = "UBC Palas";
        String floor_nr = "4";

        Assert.assertTrue("The City field is disabled when selecting a new country",editEmployeePage.validateCountryField(country));
        Assert.assertTrue("The Office, Floor nr., Business unit and Head of discipline fields are enabled when selecting a new country",editEmployeePage.validateCountryField2(country));

        Assert.assertTrue("The Office field is disabled when selecting a new city",editEmployeePage.validateCityField(country, city));
        Assert.assertTrue("The Floor nr., Business unit and Head of discipline fields are enabled when selecting a new city",editEmployeePage.validateCityField2(country, city));

        Assert.assertTrue("The Floor nr. field is disabled when selecting a new office",editEmployeePage.validateOfficeField(country, city, office));
        Assert.assertTrue("The Business unit and Head of discipline fields are enabled when selecting a new office",editEmployeePage.validateOfficeField2(country, city, office));


        //Positive test

        editEmployeePage.fillLocation(country, city, office,floor_nr,"100", "5");

        Thread.sleep(2000);

        Assert.assertTrue("Table nr. is badly checked when entering positive data", editEmployeePage.tableNrHasClasses("has-success","has-feedback"));

        Assert.assertTrue("Chair nr. is badly checked when entering positive data", editEmployeePage.chairNrHasClasses("has-success","has-feedback"));


        //Negative test

        editEmployeePage.fillLocation(country, city, office,floor_nr,"101", "6");

        Thread.sleep(2000);

        Assert.assertEquals("Table nr. is badly checked when entering negative data", editEmployeePage.tableNrHasClasses("has-error","has-feedback"));

        Assert.assertEquals("Chair nr. is badly checked when entering negative data", editEmployeePage.chairNrHasClasses("has-error","has-feedback"));


        //Negative test

        editEmployeePage.fillLocation(country, city, office,floor_nr,"0", "0");

        Thread.sleep(2000);

        Assert.assertEquals("Table nr. is badly checked when entering negative data", editEmployeePage.tableNrHasClasses("has-error","has-feedback"));

        Assert.assertEquals("Chair nr. is badly checked when entering negative data", editEmployeePage.chairNrHasClasses("has-error","has-feedback"));


        //Negative test

        editEmployeePage.fillLocation(country, city, office,floor_nr,"-1", "-1");

        Thread.sleep(2000);

        Assert.assertEquals("Table nr. is badly checked when entering negative data", editEmployeePage.tableNrHasClasses("has-error","has-feedback"));

        Assert.assertEquals("Chair nr. is badly checked when entering negative data", editEmployeePage.chairNrHasClasses("has-error","has-feedback"));
    }

    @Test
    public void validateJobDetailsFieldsWithPositiveData() throws InterruptedException {
        String country = "Romania";
        String city = "Iasi";
        String office = "UBC Palas";
        String business_unit = "ISD";
        String discipline = "Development";

        Thread.sleep(2000);

        Assert.assertTrue("The Head of discipline field is disabled when selecting a new Business unit and Discipline is selected",
                editEmployeePage.validateBusinessUnitField(country, city, office, business_unit));
        Assert.assertTrue("The Head of discipline field is enabled when selecting a new Business unit and Discipline is not selected",
                editEmployeePage.validateBusinessUnitField2(country, city, office, business_unit));

        Assert.assertTrue("The Head of discipline field is disabled when selecting a new Discipline and Business unit is selected",
                editEmployeePage.validateDisciplineField(country, city, office, discipline));
        Assert.assertTrue("The Head of discipline field is enabled when selecting a new Discipline and Business unit is not selected",
                editEmployeePage.validateDisciplineField2(country, city, office, discipline));
        Assert.assertTrue("The Job title field is disabled when selecting a new Discipline",
                editEmployeePage.validateDisciplineField3(country, city, office, discipline));


        //Positive test
        editEmployeePage.fillJobDetails("ISD","Development","Intern","Developer",
                "Java, .NET","Stefana Munteanu","Andreea Croitoru","04152017","09152017");

        Thread.sleep(2000);

        Assert.assertEquals("End date is badly checked when entering positive data", editEmployeePage.endDateHasClasses("has-success","has-feedback"));
    }

    @Test
    public void validateJobDetailsFieldsWithNegativeData() throws InterruptedException {
        String country = "Romania";
        String city = "Iasi";
        String office = "UBC Palas";

        Thread.sleep(2000);

        editEmployeePage.fillLocation(country,city,office,"1","1","1");

        //Negative test
        editEmployeePage.fillJobDetails("ISD","Development","Intern","Developer",
                "Java, .NET","Stefana Munteanu","Andreea Croitoru","04152017","04142017");

        Thread.sleep(2000);

        Assert.assertEquals("End date is badly checked when entering positive data", editEmployeePage.endDateHasClasses("has-error","has-feedback"));
    }

    @Test
    public void dropDownValuesCheck() throws ClassNotFoundException, SQLException {

        List<String> actual = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();
        String query = "select distinct country from locations;";
        String column = "country";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Country drop-down values are not correct", actual.containsAll(Arrays.asList("Romania", "Columbia", "Republic of Moldova", "Serbia", "Republic of Macedonia", "Bulgaria")));
        actual.clear();

        query = "select distinct city from locations;";
        column = "city";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("City drop-down values are not correct", actual.containsAll(Arrays.asList("Iasi", "Cluj", "Bucharest","Chisinau","Belgrade","Skopje","Sofia","Bogota")));
        actual.clear();

        query = "select distinct office from locations;";
        column = "office";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Office drop-down values are not correct", actual.containsAll(Arrays.asList("UBC Palas", "Office1", "Office2", "Office3", "Office4", "Office5", "Office6", "Office7")));
        actual.clear();

        query = "select distinct business_unit from job_details;";
        column = "business_unit";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Business unit drop-down values are not correct", actual.containsAll(Arrays.asList("ISD", "CLD", "BHD", "MDD", "BGD", "SKD", "SFD", "BOD")));
        actual.clear();

        query = "select distinct discipline from job_details;";
        column = "discipline";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Discipline drop-down values are not correct", actual.containsAll(Arrays.asList("Analysis", "Applications Management", "Architecture", "Development", "Testing", "Project Management", "Managed Services")));
        actual.clear();

        query = "select distinct job_title from job_details where discipline = 'Analysis';";
        column = "job_title";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Job title for Analysis drop-down values are not correct", actual.containsAll(Arrays.asList("UX Developer", "Head of Analysis Iasi", "Senior Business Analyst", "Analyst Consultant", "Business Analyst", "BI Analyst Developer", "BI Application Consultant", "Senior UX Developer", "Quality and Security Consultant", "Senior Analyst")));
        actual.clear();

        query = "select distinct job_title from job_details where discipline = 'Applications Management';";
        column = "job_title";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Job title for Applications Management drop-down values are not correct", actual.containsAll(Arrays.asList("AM Team Leader","Senior AM Engineer","AM Engineer","Client Support Manager","Service Delivery Manager","Head of Applications Management","Senior DevOp Engineer","DevOps Engineer","Junior AM Engineer")));
        actual.clear();

        query = "select distinct job_title from job_details where discipline = 'Architecture';";
        column = "job_title";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Job title for Architecture drop-down values are not correct", actual.containsAll(Arrays.asList("Senior Architect","Architect/System Analyst","Senior Design Lead","Architect","Design Lead","Head of Architecture&Analysis")));
        actual.clear();

        query = "select distinct job_title from job_details where discipline = 'Development';";
        column = "job_title";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Job title for Development drop-down values are not correct", actual.containsAll(Arrays.asList("Senior Development Consultant","Design Lead","Development Consultant","Junior developer","Head of Development Iasi","Development Lead","Senior Developer","Delivery Manager","Discipline Lead","Developer")));
        actual.clear();

        query = "select distinct job_title from job_details where discipline = 'Testing';";
        column = "job_title";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Job title for Testing drop-down values are not correct", actual.containsAll(Arrays.asList("Senior Tester","Junior Tester","Head of Testing Iasi","Senior Test Consultant","Tester","Test Lead","Test Manager","Test Consultant","Discipline Lead")));
        actual.clear();

        query = "select distinct job_title from job_details where discipline = 'Project Management';";
        column = "job_title";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Job title for Project Management drop-down values are not correct", actual.containsAll(Arrays.asList("Delivery Manager","Project Manager","Senior Project Manager","Head of Project Management","Discipline Lead","Project Leader","Junior Project Manager","Delivery Partner")));
        actual.clear();

        query = "select distinct job_title from job_details where discipline = 'Managed Services';";
        column = "job_title";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Job title for Managed Services drop-down values are not correct", actual.containsAll(Arrays.asList("Service Delivery Manager","Senior Support Engineer","Service Management Tools PHP Developer","Application Support Senior Engineer","Senior Systems Engineer","Support Engineer","Knowledge Manager","IT Support & Monitoring Engineer","Tools Manager","Technical Group Leader","MSD Operations Manager RO & MD","Technology Group Support Manager","Senior Network Engineer","Service Team Leader","Technical Systems Consultant","Senior Systems Consultant","IT Infrastructure Project Manager","ITIL Service Transition Manager","ITIL Process Consultant","ITIL Process Analyst","Senior IT Support Engineer","Service Transition Manager","Incident and Change Manager","Project Support Officer")));
        actual.clear();

        query = "select distinct job_grade from job_details;";
        column = "job_grade";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Job grades drop-down values are not correct", actual.containsAll(Arrays.asList("Director","Business Director","Business Manager","Senior Manager","Manager","Senior Consultant","Consultant","Senior Engineer","Engineer","Senior Technician","Technician","Junior Technician","Intern")));
        actual.clear();

        /*query = "select concat(first_name, concat(' ', last_name)) from employees e, job_details j where e.job_details_id=j.job_details_id and j.job_title like '%Head of' and j.business_unit = 'ISD';";
        column = "text";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Head of discipline for ISD business unit drop-down values are not correct", actual.containsAll(Arrays.asList("Bogdan Buhaceanu","Alexandru Moise","Adrian Miron","Stefana Munteanu","Bogdan Darabaneanu","Bogdan Buhaceanu")));
        actual.clear();

        query = "select concat(first_name, concat(' ', last_name)) from employees e, job_details j where e.job_details_id=j.job_details_id and j.job_title like '%Head of' and j.business_unit = 'CLD';";
        column = "text";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Head of discipline for CLD business unit drop-down values are not correct", actual.containsAll(Arrays.asList("Bogdan Clujeanu","Alexandru Clujeanu","Adrian Clujeanu","Stefana Clujeanu","Bogdan Purcel","Bogdan Rionisei")));
        actual.clear();

        query = "select concat(first_name, concat(' ', last_name)) from employees e, job_details j where e.job_details_id=j.job_details_id and j.job_title like '%Head of' and j.business_unit = 'BHD';";
        column = "text";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Head of discipline for BHD business unit drop-down values are not correct", actual.containsAll(Arrays.asList("Bogdan Bucuresteanu","Costel Balan","Carmen Lopata","Mirel Codita","Pavel Bartos","Anca Sef")));
        actual.clear();

        query = "select concat(first_name, concat(' ', last_name)) from employees e, job_details j where e.job_details_id=j.job_details_id and j.job_title like '%Head of' and j.business_unit = 'MDD';";
        column = "text";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Head of discipline for MDD business unit drop-down values are not correct", actual.containsAll(Arrays.asList("Peggie Reliford","Fletcher Wince","Marva Deramus","Kermit Chenault","Nicola Colman","Winter Okeeffe")));
        actual.clear();

        query = "select concat(first_name, concat(' ', last_name)) from employees e, job_details j where e.job_details_id=j.job_details_id and j.job_title like '%Head of' and j.business_unit = 'BGD';";
        column = "text";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Head of discipline for BGD business unit drop-down values are not correct", actual.containsAll(Arrays.asList("Emilia Blohm","Keturah Niles","Phung Herzig","Aletha Drinnon","Toshiko Atkinson","Ryann Seals")));
        actual.clear();

        query = "select concat(first_name, concat(' ', last_name)) from employees e, job_details j where e.job_details_id=j.job_details_id and j.job_title like '%Head of' and j.business_unit = 'SKD';";
        column = "text";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Head of discipline for SKD business unit drop-down values are not correct", actual.containsAll(Arrays.asList("Tyrone Sheriff","Mercy Degraffenreid","Cher Redden","Aron Bublitz","Kanisha Litteral","Olympia Aston")));
        actual.clear();

        query = "select concat(first_name, concat(' ', last_name)) from employees e, job_details j where e.job_details_id=j.job_details_id and j.job_title like '%Head of' and j.business_unit = 'SFD';";
        column = "text";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Head of discipline for SFD business unit drop-down values are not correct", actual.containsAll(Arrays.asList("Clarita Pass","Kortney Schermerhorn","Fausto Hackworth","Lawanda Joiner","Sherwood Hatmaker","Shondra August")));
        actual.clear();

        query = "select concat(first_name, concat(' ', last_name)) from employees e, job_details j where e.job_details_id=j.job_details_id and j.job_title like '%Head of' and j.business_unit = 'BOD';";
        column = "text";
        actual = dbConnection.getDbInfo(query, column);
        Assert.assertTrue("Head of discipline for BOD business unit drop-down values are not correct", actual.containsAll(Arrays.asList("Mirtha Maharaj","Hee Auger","Rolanda Kolstad","Ardelia Danielson","Fumiko Moy","Teddy Bhakta")));
        actual.clear();*/

    }
}
