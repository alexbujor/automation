package com.endava;

import com.endava.utils.TestConfig;
import com.endava.utils.WebdriverFactory;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReusableWebDriver extends EventFiringWebDriver {
    private static Logger log = LoggerFactory.getLogger(ReusableWebDriver.class);

    private static final TestConfig config = new TestConfig();
    private static final WebDriver REAL_DRIVER = WebdriverFactory.initDriver(config);


    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            try {
                REAL_DRIVER.close();
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
    };


    static {
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    public ReusableWebDriver() {
        super(REAL_DRIVER);
    }

    @Override
    public void close() {
        if (Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException("You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        super.close();
    }


//    @After
//    public void embedScreenshot(Scenario scenario) {
//        //log.info("WD embeded screenshot executed...");
//        try {
//            byte[] screenshot = getScreenshotAs(OutputType.BYTES);
//            scenario.embed(screenshot, "image/png");
//        } catch (WebDriverException somePlatformsDontSupportScreenshots) {
//            log.info(somePlatformsDontSupportScreenshots.getMessage());
//        }
//    }

}


