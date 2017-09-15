package utils;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChromeDownloadPage {
    private WebDriver driver;

    @FindBy(css = "downloads-manager /deep/ downloads-item /deep/ [id=file-link]")
    private WebElement dldFile;

    @FindBy(css = "downloads-manager /deep/ downloads-toolbar /deep/ [id=searchInput]")
    private WebElement search;

    @FindBy(css = "downloads-manager /deep/ downloads-item /deep/ [id=progress]")
    private WebElement progressBar;

    @FindBy(css = "downloads-manager /deep/ downloads-item /deep/ [id=show]")
    private WebElement showInFolder;

    public ChromeDownloadPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        load();
    }

    public boolean checkDownloadedFile(String fileName) throws InterruptedException {
        if(!showInFolder.isDisplayed()){
            while (progressBar.isDisplayed()){
            }
        }

        if(!showInFolder.isDisplayed()) return false;

        String actual = dldFile.getText().split("\\.")[0];

        if(!fileName.equalsIgnoreCase(actual)) return false;

        return true;
    }

    public boolean elementHasClasses(WebElement element, String active, String active2) {
        return element.getAttribute("class").contains(active)&&element.getAttribute("class").contains(active2);
    }

    public boolean isOpened() { return "Downloads".equals(driver.getTitle());
    }

    protected void load() { driver.get("chrome://downloads/");
    }

    protected void isLoaded() throws Error { Assert.assertEquals("Downloads", driver.getTitle());
    }
}
