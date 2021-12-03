package com.gittigidiyor.pages;

import com.gittigidiyor.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class LoginPage extends BasePage {
    @FindBy(css = "#L-UserNameField")
    public static WebElement inputUsername;
    @FindBy(css = "#L-PasswordField")
    public static WebElement inputPassword;
    @FindBy(css = "#gg-login-enter")
    public static WebElement inputLoginFormSubmit;

    public LoginPage(){
        PageFactory.initElements(driver,this);
        if(checkCaptchaExists()){
            log.info("Waiting for user to fulfill captcha");
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public HomePage performSuccessfulLogin(String username, String password){
        waitForLoad(inputUsername).sendKeys(username);
        waitForLoad(inputPassword).sendKeys(password);
        clickElement(inputLoginFormSubmit);
        return new HomePage();
    }
}
