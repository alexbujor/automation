package com.endava.pages;

import com.endava.database.DBConnection;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ChromeDownloadPage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by acotet on 9/11/2017.
 */
public class ViewDocumentsPage {

    private WebDriver driver;

    @FindBy(id = "uploadDocument")
    private WebElement btnUpload;

    @FindBy(id = "logoutIcon")
    private WebElement btnLogout;

    @FindBy(id = "downloadIcon")
    private WebElement btnDownload;

    @FindBy(id = "searchBar")
    private WebElement searchBar;

    @FindBy(id = "categories")
    private WebElement cboCategories;

    @FindBy(id = "Update..")
    private WebElement btnUpdateDoc;

    @FindBy(id = "deleteIcon")
    private WebElement btnDelete;

    @FindBy(css = ".table-border.table-hover.col-md-12")
    private WebElement gridDocuments;

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

    @FindBy(id = "success")
    protected WebElement successMsg;

    @FindBy(xpath = "error")
    protected WebElement errorMsg;

    public ViewDocumentsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public LoginPage goToLoginPage() throws InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(btnLogout));
        btnLogout.click();
        Thread.sleep(2000);
        return new LoginPage(driver);
    }

    public UpdateDocumentPage goToUpdateDocument() throws IOException, InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(btnUpdateDoc));
        btnUpdateDoc.click();
        Thread.sleep(2000);
        return new UpdateDocumentPage(driver);
    }

    public UploadDocumentPage uploadDocument() throws InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(btnUpload));
        btnUpload.click();
        Thread.sleep(2000);
        return new UploadDocumentPage(driver);
    }

    public void deleteDocument() throws InterruptedException {
        btnDelete.click();
        Thread.sleep(2000);
        alert.click();
    }

    public void downloadFile(){
        btnDownload.click();
    }

    public ChromeDownloadPage goToDownloadPage() throws InterruptedException {
        return new ChromeDownloadPage(driver);
    }

    public void downloadDocument() throws InterruptedException {
        btnDownload.click();
    }

    public void selectFromDropDown(WebElement elem, String str) throws InterruptedException {
        Select dropDown;
        dropDown = new Select(elem);
        dropDown.selectByVisibleText(str);
        Thread.sleep(1000);
    }

    public boolean searchedFileIsFound(){
        return btnDelete.isDisplayed();
    }

    public boolean successMsgEnabled(){
        return successMsg.getAttribute("class").equalsIgnoreCase("notification-enabled hideNotificationInTime");
    }

    public boolean errorMsgEnabled(){
        return successMsg.getAttribute("class").equalsIgnoreCase("notification-enabled hideNotificationInTime");
    }

    public boolean verifyDropDownFunctionality(String string_category) throws SQLException, InterruptedException {
        ArrayList<String> actual = new ArrayList<>();
        List<String> expected;
        DBConnection dbConnection = new DBConnection();

        String query = "select title from documents where category = '" + string_category + "';";
        String column = "title";
        expected = dbConnection.getDbInfo(query, column);

        selectFromDropDown(cboCategories, string_category);


        getElemsFromGridIntoList(actual);

        while(actual.size()<expected.size()){
            btnForward.click();
            getElemsFromGridIntoList(actual);
        }

        Collections.sort(actual);
        System.out.println(actual);
        Collections.sort(expected);
        System.out.println(expected);

        return actual.equals(expected);
    }

    public ArrayList<String> getElemsFromGrid(WebElement elem, String listingNumber) throws InterruptedException {
        ArrayList<String> result = new ArrayList<>();
        String fileName;

        selectFromDropDown(cboListings, listingNumber);


        List<WebElement> allDocuments = gridDocuments.findElements(By.cssSelector(".ng-untouched.ng-pristine.ng-valid"));

        for(WebElement item:allDocuments) {
            String[] splited = item.getText().split("\\s+");
            fileName = new String();
            fileName=fileName.concat(splited[1]);
            for(int i=2;i<splited.length;i++)
            {
                if(splited[i].compareTo("General")==0 || splited[i].compareTo("Job")==0)
                    break;
                fileName= fileName.concat(" " + splited[i]);
            }
            result.add(fileName);

        }
        System.out.println(result);
        return result;
    }

    public ArrayList<String> getElems( String listingNumber) throws InterruptedException {
        ArrayList<String> result = new ArrayList<>();
        String fileName;

        selectFromDropDown(cboListings, listingNumber);


        List<WebElement> allDocuments = gridDocuments.findElements(By.cssSelector(".ng-untouched.ng-pristine.ng-valid"));

        for(WebElement item:allDocuments) {
            String[] splited = item.getText().split("\\s+");
            fileName = new String();
            fileName= fileName.concat(splited[1]);
            for(int i=2;i<splited.length;i++)
            {
                if(splited[i].compareTo("General")==0 || splited[i].compareTo("Job")==0)
                    break;
                fileName=fileName.concat(" " + splited[i]);
            }
            result.add(fileName);

        }
        System.out.println(result);
        return result;
    }

    public void getElemsFromGridIntoList(ArrayList<String> result) throws InterruptedException {

        List<WebElement> allDocuments = gridDocuments.findElements(By.cssSelector(".ng-untouched.ng-pristine.ng-valid"));
        String fileName;

        for(WebElement item:allDocuments) {
            String[] splited = item.getText().split("\\s+");
            fileName = new String();
            fileName= fileName.concat(splited[1]);
            for(int i=2;i<splited.length;i++)
            {
                if(splited[i].compareTo("General")==0 || splited[i].compareTo("Job")==0)
                    break;

                fileName=  fileName.concat(" " + splited[i]);
            }
            result.add(fileName);

        }
    }

    public void searchFor(String searchTerm) {
        searchBar.click();
        searchBar.clear();
        searchBar.sendKeys(searchTerm);
        searchBar.sendKeys(Keys.ENTER);
    }

    public boolean verifySearchFunctionality(String searchTerm) throws SQLException, InterruptedException {
        ArrayList<String> actual = new ArrayList<>();
        List<String> expected;
        DBConnection dbConnection = new DBConnection();

        String query = "select title from documents where title Like '%" + searchTerm + "%'";
        String column = "title";
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

    public boolean sortAscending(String listingNumber) throws InterruptedException, SQLException {
        ArrayList<String> actual;
        List<String> expected;
        DBConnection dbConnection = new DBConnection();

        String query = "select title from documents order by title asc limit " + listingNumber + ";";
        String column = "title";
        expected = dbConnection.getDbInfo(query, column);

        selectFromDropDown(cboListings, listingNumber);

        actual = getElemsFromGrid(btnSortAsc,listingNumber);


        Collections.sort(actual);
        Collections.sort(expected);

        return actual.equals(expected);
    }

    public boolean sortDescending(String listingNumber) throws InterruptedException, SQLException {
        ArrayList<String> actual;
        List<String> expected;
        DBConnection dbConnection = new DBConnection();

        String query = "select title from documents order by title desc limit " + listingNumber + ";";
        String column = "title";
        expected = dbConnection.getDbInfo(query, column);

        selectFromDropDown(cboListings, listingNumber);

        actual = getElemsFromGrid(btnSortDesc,listingNumber);

        Collections.sort(actual);
        Collections.sort(expected);

        return actual.equals(expected);
    }

    public boolean checkListings(String listingNumber) throws InterruptedException {
        selectFromDropDown(cboListings, listingNumber);

        List<WebElement> allDocuments = gridDocuments.findElements(By.cssSelector(".ng-untouched.ng-pristine.ng-valid"));

        if(listingNumber.compareToIgnoreCase(Integer.toString(allDocuments.size()))==0) return true;
        return false;
    }

    public boolean isOpened() {
        return btnUpload.isDisplayed();
    }
}
