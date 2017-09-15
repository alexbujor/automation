package utils;

import com.endava.pages.UploadDocumentPage;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class TestPage extends LoadableComponent<TestPage> {
    private WebDriver driver;

    @FindBy(id = "photo")
    private WebElement fileInput;

    @FindBy(xpath = "//*[@id=\"content\"]/form/fieldset/div[8]/a")
    private WebElement dldLink;

    @FindBy(xpath = "//*[@id=\"file-link\"]")
    private WebElement dldFileName;

    public void fillUp(){
        fileInput.sendKeys("C:\\Users\\abujor\\Desktop\\my_inserts.sql");
    }

    public void downloadFile(){
        dldLink.click();
    }

    public ChromeDownloadPage goToDownloadPage() throws InterruptedException {
        return new ChromeDownloadPage(driver);
    }

    public void uploadFile() throws InterruptedException, IOException {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(fileInput));
        fileInput.click();
        Thread.sleep(2000);
        Runtime.getRuntime().exec("C:\\Users\\abujor\\Documents\\AutoIt\\FileUploading.exe");
    }

    public TestPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        load();
    }

    public boolean isOpened() { return "Demo Form for practicing Selenium Automation".equals(driver.getTitle());
    }

    protected void load() { driver.get("http://toolsqa.com/automation-practice-form/");
    }

    protected void isLoaded() throws Error { Assert.assertEquals("Demo Form for practicing Selenium Automation", driver.getTitle());
    }

}
