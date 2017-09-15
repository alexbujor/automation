package com.endava.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by acotet on 9/11/2017.
 */
public class UpdateDocumentPage {
    private WebDriver driver;
    @FindBy(id = "btnUpload")
    private WebElement btnUpload;

    @FindBy(id = "title")
    private WebElement titleField;
    @FindBy(id = "titleofDocument")
    private WebElement titleOfDocument;

    @FindBy(id = "short_description")
    private WebElement short_descriptionField;

    @FindBy(id = "idCheckBox")
    private WebElement btnCheckBox;

    @FindBy(id = "link Documents")
    private WebElement btnGoToDocumentsPage;


    public UpdateDocumentPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ViewDocumentsPage goToViewDocPage() throws InterruptedException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(btnGoToDocumentsPage));
        btnGoToDocumentsPage.click();
        Thread.sleep(2000);
        return new ViewDocumentsPage(driver);
    }
    public void pressUploadButton(){
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(btnUpload));
        btnUpload.click();
    }

    public boolean checkField(WebElement elem, String str){
        if (elem.getAttribute("value").compareToIgnoreCase(str) != 0) return false;
        return  true;
    }
    public boolean checkUpdateDocumentChanges(String title, String short_description) {

        if (!checkField(titleField,title)) return false;
        if (!checkField(short_descriptionField,short_description)) return false;

        return true;
    }

    public void updateTheTitle(String title){
        titleField.clear();
        titleField.sendKeys(title);

    }

    public void updateTheDescription( String shortDescription){
        short_descriptionField.clear();
        short_descriptionField.sendKeys(shortDescription);

    }

    public void checkValueIfItIsNotSelected(){
        if (! btnCheckBox.isSelected() )
        {
            btnCheckBox.click();
        }
    }

    public boolean checkTheTitleOfTheDocument(String titleDoc){
        if (!checkField(titleOfDocument,titleDoc)) return false;
        return true;
    }

    public boolean isOpened() {
        return btnUpload.isDisplayed();
    }
}
