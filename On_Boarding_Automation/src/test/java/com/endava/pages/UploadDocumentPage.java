package com.endava.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class UploadDocumentPage {

    private WebDriver driver;

    @FindBy(id = "submit")
    protected WebElement uploadBtn;

    @FindBy(id = "firstName")
    protected WebElement title;

    @FindBy(id = "description")
    protected WebElement shortDescription;

    @FindBy(id = "GeneralInfoLabel")
    protected WebElement generalInfo;

    @FindBy(id = "JobDescriptionLabel")
    protected WebElement jobDescription;

    @FindBy(className = "upload-button")
    protected WebElement browseFile;

    @FindBy(id = "backText")
    protected WebElement backToDocuments;

    @FindBy(id = "progress")
    protected WebElement progressBarStatus;

    public UploadDocumentPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ViewDocumentsPage pressUpload() throws IOException, InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(uploadBtn));
        uploadBtn.click();
        Thread.sleep(2000);
        return new ViewDocumentsPage(driver);
    }

    public void pressUploadWithNull() throws InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(uploadBtn));
        uploadBtn.click();
        Thread.sleep(2000);
    }

    public ViewDocumentsPage goToViewDocuments() throws IOException, InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(backToDocuments));
        backToDocuments.click();
        Thread.sleep(2000);
        return new ViewDocumentsPage(driver);
    }

    public boolean uploadWithBrowse() throws InterruptedException, IOException {
        browseFile.click();
        Thread.sleep(2000);
        Runtime.getRuntime().exec("C:\\Users\\abujor\\Documents\\AutoItScripts\\FileUploading.exe");
        Thread.sleep(6000);
        while(!progressBarStatus.getText().equalsIgnoreCase("Complete")){
        }
        return true;
    }

    public boolean uploadWithDragAndDrop() throws InterruptedException, IOException {
        Runtime.getRuntime().exec("C:\\Users\\abujor\\Documents\\AutoItScripts\\DragNDrop.exe");
        while(!progressBarStatus.getText().equalsIgnoreCase("Complete")){
        }
        return true;
    }

    public boolean fill(String stringTitle, String stringDescription) throws InterruptedException, IOException {
        title.clear();
        title.sendKeys(stringTitle);
        Thread.sleep(1000);

        shortDescription.clear();
        shortDescription.sendKeys(stringDescription);
        Thread.sleep(1000);

        generalInfo.click();
        Thread.sleep(1000);

        return uploadWithDragAndDrop();
    }

    public boolean checkField(WebElement elem, String str){
        if (elem.getAttribute("value").compareToIgnoreCase(str) != 0) return false;
        return  true;
    }

    public boolean checkDefaultFields() {
        if (!checkField(title,"")) return false;
        if (!checkField(shortDescription,"")) return false;
        if(!browseFile.isDisplayed()) return false;
        return true;
    }

    public boolean isOpened() { return uploadBtn.isDisplayed(); }
}
