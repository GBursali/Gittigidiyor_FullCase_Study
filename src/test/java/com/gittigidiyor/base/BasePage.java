package com.gittigidiyor.base;

import com.gittigidiyor.pages.BasketPage;
import com.gittigidiyor.pages.SearchPage;
import com.gittigidiyor.pages.TabPage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class BasePage extends BaseTest {
    @FindBy(css = "[href='https://www.gittigidiyor.com/sepetim']")
    public static WebElement buttonGoToBasket;

    private static final By selectorAccountUsername = By.cssSelector("div.gekhq4-4.egoSnI span");
    private static final By selectorCookiePopupClose = By.cssSelector("section.tyj39b-4.jZoSqD");
    private static final By selectorCaptchaBox = By.id("recaptcha-anchor-label");
    private static final By selectorCaptchaLoading = By.cssSelector(".loading.recaptcha-loading");

    public void closePopupCookie(){
        if(elementShownOnPageBySelector(selectorCookiePopupClose))
            driver.findElement(selectorCookiePopupClose).click();
    }
    public WebElement waitForLoad(WebElement element) {

        return waiter.until(ExpectedConditions.visibilityOf(element));
    }
    public WebElement waitForLoad(By selector) {

        return waiter.until(ExpectedConditions.visibilityOfElementLocated(selector));
    }
    public void clickElement(WebElement element) {

        try{
            waiter.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        }catch (TimeoutException te){
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();",element);
        }

    }
    public void clickElement(By selector) {
        WebElement element = driver.findElement(selector);
        clickElement(element);

    }

    public SearchPage focusOnSearchBox(){
        return new SearchPage();
    }
    public BasketPage goToBasket(){
        clickElement(buttonGoToBasket);
        return new BasketPage();
    }
    public TabPage focusOnTab(){
        return new TabPage();
    }
    public void assertLoginStatus(boolean shouldLogged){
        try{
            String username = waitForLoad(selectorAccountUsername).getText();//Log
            TimeUnit.SECONDS.sleep(3);
            boolean loginStatus = !username.equals("veya Üye Ol");
            Assertions.assertEquals(shouldLogged,loginStatus,"Logged out");
        }
        catch (NoSuchElementException | InterruptedException nsee){
            Assertions.fail("Couldn't logged in/ Username not found in display");
        }
    }

    public static boolean elementShownOnPageBySelector(By selector){
        return !driver.findElements(selector).isEmpty();
    }

    public static Boolean checkCaptchaExists(){
        Boolean pageHasCaptcha = elementShownOnPageBySelector(selectorCaptchaBox);
        Boolean pageHasLoading = elementShownOnPageBySelector(selectorCaptchaLoading);
        return pageHasCaptcha || pageHasLoading;
    }
}
