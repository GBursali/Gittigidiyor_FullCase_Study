package com.gittigidiyor.pages;

import com.gittigidiyor.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BasketPage extends BasePage {

    @FindBy(css = "select.amount")
    public static WebElement dropdownAmount;

    @FindBy(css = "input.gg-d-24.gg-ui-btn-primary.btn-pay")
    public static WebElement buttonCompleteTransaction;

    @FindBy(css = "a.gg-ui-btn-default.btn-add-to-basket")
    public static List<WebElement> buttonsAddToBasketFromFavorites;

    public BasketPage increaseQuantity2(Integer index){
        By optionBy = By.cssSelector(String.format("option[value='%d']",index));
        waitForLoad(dropdownAmount).click();
        clickElement(driver.findElement(optionBy));
        return this;
    }

    public PaymentPage navigateToPayment(){
        clickElement(buttonCompleteTransaction);
        return new PaymentPage();
    }

    public BasketPage addProductFromFavorites(int index){
        clickElement(buttonsAddToBasketFromFavorites.get(index));
        return this;
    }
}
