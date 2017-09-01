package com.endava.utils;

import com.endava.utils.enums.BrowserType;
import com.endava.utils.enums.SeleniumServerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by oion on 4/7/2017.
 */
public class TestConfig {

    ResourceBundle prop = ResourceBundle.getBundle("defaultConfig");
    private Logger log = LoggerFactory.getLogger(TestConfig.class);


    private BrowserType browserType;
    private SeleniumServerType seleniumServerType;
    private String seleniumServerURL;
    private String baseURL;
    private int pageWidth;
    private int pageHeight;
    private String chromedriverPath;
    private String mozilladriverPath;

    public TestConfig() {

        if (System.getProperty("browserType") != null) {
            setBrowserType(System.getProperty("browserType"));
        } else {
            setBrowserType(prop.getString("browserType"));
        }

        if (System.getProperty("baseURL") != null) {
            setBaseURL(System.getProperty("baseURL"));
        } else {
            setBaseURL(prop.getString("baseURL"));
        }

        if (System.getProperty("seleniumServerType") != null) {
            setSeleniumServerType(System.getProperty("seleniumServerType"));
        } else {
            setSeleniumServerType(prop.getString("seleniumServerType"));
        }

        if (System.getProperty("seleniumServerURL") != null) {
            setSeleniumServerURL(System.getProperty("seleniumServerURL"));
        } else {
            setSeleniumServerURL(prop.getString("seleniumServerURL"));
        }

        if (System.getProperty("pageWidth") != null) {
            setPageWidth(Integer.parseInt(System.getProperty("pageWidth")));
        } else {
            setPageWidth(Integer.parseInt(prop.getString("pageWidth")));
        }


        if (System.getProperty("pageHeight") != null) {
            setPageHeight(Integer.parseInt(System.getProperty("pageHeight")));
        } else {
            setPageHeight(Integer.parseInt(prop.getString("pageHeight")));
        }


        if (System.getProperty("chromedriverPath") != null) {
            setChromedriverPath(System.getProperty("chromedriverPath"));
        } else {
            setChromedriverPath(prop.getString("chromedriverPath"));
        }

        if (System.getProperty("mozilladriverPath") != null) {
            setMozilladriverPath(System.getProperty("mozilladriverPath"));
        } else {
            setMozilladriverPath(prop.getString("mozilladriverPath"));
        }

    }

    private void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    private void setBrowserType(String browserType) {
        for (BrowserType b : BrowserType.values()) {
            if (b.toString().toLowerCase().equals(browserType.toLowerCase())) {
                this.browserType = b;
                break;
            }
        }
    }

    private void setChromedriverPath(String chromedriverPath) {
        this.chromedriverPath = chromedriverPath;
    }

    private void setMozilladriverPath(String mozilladriverPath) {
        this.mozilladriverPath = mozilladriverPath;
    }


    private void setPageHeight(int pageHeight) {
        this.pageHeight = pageHeight;
    }

    private void setPageWidth(int pageWidth) {
        this.pageWidth = pageWidth;
    }

    private void setSeleniumServerType(String seleniumServerType) {
        for (SeleniumServerType s : SeleniumServerType.values()) {
            if (s.toString().toLowerCase().equals(seleniumServerType.toLowerCase())) {
                this.seleniumServerType = s;
                break;
            }
        }
    }

    private void setSeleniumServerURL(String seleniumServerURL) {
        this.seleniumServerURL = seleniumServerURL;
    }


    public String getBaseURL() {
        return baseURL;
    }

    public BrowserType getBrowserType() {
        return browserType;
    }

    public String getChromedriverPath() {
        return chromedriverPath;
    }

    public String getMozilladriverPath() {
        return mozilladriverPath;
    }

    public int getPageHeight() {
        return pageHeight;
    }

    public int getPageWidth() {
        return pageWidth;
    }

    public SeleniumServerType getSeleniumServerType() {
        return seleniumServerType;
    }

    public URL getSeleniumServerURL() {
        try {
            return new URL(seleniumServerURL);
        } catch (MalformedURLException e) {
            log.error(e.toString());
            return null;
        }
    }


}
