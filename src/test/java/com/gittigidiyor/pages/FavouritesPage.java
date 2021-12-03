package com.gittigidiyor.pages;

import com.gittigidiyor.base.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FavouritesPage extends BasePage {
    @FindBy(css = "#watch-products-item-checkbox-1")
    public static WebElement chbxSecondFavourite;

    @FindBy(css = ".delete-watch-products.robot-delete-all-button")
    public static WebElement buttonDeleteFavourite;

    public static final By selectorProductRemoved = By.cssSelector("div.gg-ui-alert-secondary-confirm");

    public FavouritesPage(){
        PageFactory.initElements(driver,this);
    }
    public FavouritesPage selectSecondFavourite(){
        clickElement(chbxSecondFavourite.findElement(By.xpath("./../..")));
        return this;
    }
    public FavouritesPage clickDeleteFavourite(){
        clickElement(buttonDeleteFavourite);
        return this;
    }

    public void assertProductRemoved(){
        boolean shownProductRemovedInfo = elementShownOnPageBySelector(selectorProductRemoved);
        Assertions.assertTrue(shownProductRemovedInfo);
    }
}
