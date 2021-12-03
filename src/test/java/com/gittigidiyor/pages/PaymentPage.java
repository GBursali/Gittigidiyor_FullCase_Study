package com.gittigidiyor.pages;

import com.gittigidiyor.base.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class PaymentPage extends BasePage {
    @FindBy(css = ".gg-btn.post-address.post-address-button")
    public static WebElement buttonSaveAddress;

    @FindBy(css = ".gg-input-error-text.gg-d-24")
    public static WebElement labelError;

    @FindBy(css = ".header-link[href='https://www.gittigidiyor.com/sepetim']")
    public static WebElement buttonEditBasket;

    public PaymentPage(){
        PageFactory.initElements(driver,this);
    }
    public PaymentPage clickSaveAddress(){
        clickElement(buttonSaveAddress);
        return this;
    }
    public PaymentPage assertErrorsExist(){
        Assertions.assertTrue(labelError.isDisplayed());
        return this;
    }

    public BasketPage clickEditBasket(){
        Actions actions = new Actions(driver);
        actions.moveToElement(buttonEditBasket).click()
                .build().perform();

        return new BasketPage();
    }

}
