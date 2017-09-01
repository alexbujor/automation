package com.endava.utils;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

/**
 * Created by oion on 4/7/2017.
 */
public class ScreenShotRemoteWebDriver extends RemoteWebDriver implements TakesScreenshot {
    public ScreenShotRemoteWebDriver(URL url, DesiredCapabilities capabilities) {
        super(url, capabilities);
    }

    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        if ((Boolean) getCapabilities().getCapability(CapabilityType.TAKES_SCREENSHOT)) {
            return target.convertFromBase64Png(execute(DriverCommand.SCREENSHOT).getValue().toString());
        }
        return null;
    }

    @Override
    public WebElement findElement(By by) {
        try {
            return super.findElement(by);
        } catch (Exception e) {
            Assert.fail("unable to find element:" + by.toString());
            return null;
        }
    }
}