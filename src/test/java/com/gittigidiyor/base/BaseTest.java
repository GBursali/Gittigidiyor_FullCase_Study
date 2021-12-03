package com.gittigidiyor.base;

import com.gittigidiyor.pages.HomePage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public abstract class BaseTest {
    public static WebDriver driver;
    public static WebDriverWait waiter;
    public static ChromeOptions driverOptions;
    public static final Logger log = LogManager.getRootLogger();

    @BeforeAll
    static void setupTest(){
        File f = new File("src/test/resources/chromedriver.exe");
        DOMConfigurator.configure("./log4jconfig.xml");
        log.info("%n Test flow started");
        System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
        driverOptions = new ChromeOptions();
        driverOptions.addArguments("start-maximized",
                "--ignore-certificate-errors",
                "--disable-popup-blocking",
                "--disable-notifications");
        driver = new ChromeDriver(driverOptions);
        waiter = new WebDriverWait(driver, Duration.ofSeconds(20));

    }
    @BeforeEach
    void baseURL(){
        if (!driver.getCurrentUrl().equalsIgnoreCase("https://www.gittigidiyor.com/")){
            driver.get("https://www.gittigidiyor.com/");
        }
    }

    public BaseTest(){
        PageFactory.initElements(driver,this);
    }

    @AfterAll
    static void killDriver(){
        driver.quit();
    }
}
