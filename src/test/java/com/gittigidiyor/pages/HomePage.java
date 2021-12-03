package com.gittigidiyor.pages;

import com.gittigidiyor.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
    @FindBy(css = ".gekhq4-5[data-cy='header-user-menu']")
    public static WebElement divGirisYap;

    @FindBy(css = "a[data-cy='header-login-button']")
    public static WebElement buttonGirisYap;

    @FindBy(css = "div[data-cy='header-user-menu']")
    public static WebElement buttonMyAccount;

    public HomePage(){
        PageFactory.initElements(driver,this);
        closePopupCookie();
    }
    public LoginPage navigateToLogin(){
        clickElement(divGirisYap);
        clickElement(buttonGirisYap);
        return new LoginPage();
    }
    public FavouritesPage navigateToFavourites(){
        Actions actions = new Actions(driver);
        actions.moveToElement(buttonMyAccount).pause(2000).build().perform();
        clickElement(driver.findElement(By.cssSelector("a[title='Favorilerim']")));
        return new FavouritesPage();
    }
    public HomePage logOut(){
        closePopupCookie();
        Actions actions = new Actions(driver);
        actions.moveToElement(buttonMyAccount).pause(2000).build().perform();
        actions.click(waitForLoad(driver.findElement(By.cssSelector("div[name='accountExit']"))))
                .click().pause(1000).build().perform();

        return this;
    }
}
