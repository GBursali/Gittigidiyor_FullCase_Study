package com.gittigidiyor.base;

import com.gittigidiyor.pages.BasketPage;
import com.gittigidiyor.pages.SearchPage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class BasePage extends BaseTest {
    @FindBy(css = "[href='https://www.gittigidiyor.com/sepetim']")
    public static WebElement buttonGoToBasket;

    public static final By selectorAccountUsername = By.cssSelector("div.gekhq4-4.egoSnI span");
    public static final By selectorCookiePopupClose = By.cssSelector("section.tyj39b-4.jZoSqD");

    public void closePopupCookie(){
        if(elementShownOnPageBySelector(selectorCookiePopupClose))
            driver.findElement(selectorCookiePopupClose).click();
    }
    public WebElement waitForLoad(WebElement element) {
        if(checkCaptchaExists()){
            log.warn("Captcha bulundu. Kullanıcı bekleniyor...");
        }
        return waiter.until(ExpectedConditions.visibilityOf(element));
    }
    public WebElement waitForLoad(By selector) {
        if(checkCaptchaExists()){
            log.warn("Captcha bulundu. Kullanıcı bekleniyor...");
        }
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
    public String openNewTabAndGetHandle(){
        String handleid = driver.getWindowHandle();
        String a = "window.open('https://www.gittigidiyor.com/','_blank');";
        ((JavascriptExecutor)driver).executeScript(a);
        String newHandleid = driver.getWindowHandle();
        driver.switchTo().window(handleid);
        return newHandleid;
    }
    public void closeTab(String handle){
        driver.switchTo().window(handle).close();
    }
    public void assertLoginStatus(boolean shouldLogged){
        try{
            String username = waitForLoad(selectorAccountUsername).getText();//Log
            TimeUnit.SECONDS.sleep(3);
            boolean loginStatus = !username.equals("veya Üye Ol");
            Assertions.assertEquals(shouldLogged,loginStatus);
            System.out.println(username);
        }
        catch (NoSuchElementException | InterruptedException nsee){
            Assertions.fail("Giriş yapılamadı/kullanıcı adı bulunamadı");
        }
    }

    public static boolean elementShownOnPageBySelector(By selector){
        return !driver.findElements(selector).isEmpty();
    }

    public static Boolean checkCaptchaExists(){
        Boolean pageHasCaptcha = elementShownOnPageBySelector(By.id("recaptcha-anchor-label"));
        Boolean pageHasLoading = elementShownOnPageBySelector(By.cssSelector(".loading.recaptcha-loading"));
        return pageHasCaptcha || pageHasLoading;
    }
}
