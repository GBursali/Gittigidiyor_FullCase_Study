package com.gittigidiyor.base;

import com.gittigidiyor.pages.BasketPage;
import com.gittigidiyor.pages.HomePage;
import com.gittigidiyor.pages.SearchPage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class BasePage extends BaseTest {
    @FindBy(css = "a[title='GittiGidiyor']")
    public static WebElement buttonLogoHomepage;

    @FindBy(css = "[href='https://www.gittigidiyor.com/sepetim']")
    public static WebElement buttonGoToBasket;

    public static final By selectorAccountUsername = By.cssSelector("div.gekhq4-4.egoSnI span");
    public static final By selectorCookiePopupClose = By.cssSelector("section.tyj39b-4.jZoSqD");
    public void closePopupCookie(){
        if(!driver.findElements(selectorCookiePopupClose).isEmpty())
            driver.findElement(selectorCookiePopupClose).click();
        /*
        try{
            clickElement(driver.findElement(selectorCookiePopupClose));
        }
        catch (NoSuchElementException nsee){
            log.info("Çerez penceresi bulunamadı. Devam ediliyor....");
            //nvm that error and continue
        }*/
    }
    public HomePage navigateToHomepage(){
        clickElement(buttonLogoHomepage);
        closePopupCookie();
        return new HomePage();
    }
    public WebElement waitForLoad(WebElement element) {
        if(checkCaptchaExists()){
            log.warn("Captcha bulundu. Kullanıcı bekleniyor...");
        }
        return waiter.until(ExpectedConditions.visibilityOf(element));
    }
    public void clickElement(WebElement element) {
        waiter.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
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
            String username = waitForLoad(driver.findElement(selectorAccountUsername)).getText();//Log
            TimeUnit.SECONDS.sleep(2);
            boolean loginStatus = username.equals("veya Üye Ol");
            Assertions.assertEquals(shouldLogged,loginStatus);
            System.out.println(username);
        }
        catch (NoSuchElementException | InterruptedException nsee){
            Assertions.fail("Giriş yapılamadı/kullanıcı adı bulunamadı");
        }
    }

    public boolean elementShownOnPageBySelector(By selector){
        return !driver.findElements(selector).isEmpty();
    }

}
