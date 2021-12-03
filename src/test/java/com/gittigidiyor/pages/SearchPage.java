package com.gittigidiyor.pages;

import com.gittigidiyor.base.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SearchPage extends BasePage {
    @FindBy(css = "input[data-cy='header-search-input']")
    public static WebElement inputSearchBox;

    @FindBy(css = "button[data-cy='search-find-button']")
    public static WebElement buttonSearch;

    @FindBy(css = "body")
    public static WebElement documentBody;

    @FindBy(css = "div[data-cy='product-favorite']")
    public static List<WebElement> buttonsFavorite;

    @FindBy(css = "button.qjixn8-0.sc-1bydi5r-0.dGNqQc.pXiHf.sc-1n49x8z-3.bhXnM")
    public static List<WebElement> buttonsAddToBasket;

    @FindBy(css = "a.sc-1n49x8z-1.izbuTw")
    public static List<WebElement> buttonProductDivs;

    public static final By selectorFavorited = By.cssSelector("div.dchmcc");
    public static final By selectorBasketCount = By.cssSelector("span.gekhq4-7.jYBgHc");

    public SearchPage(){
        PageFactory.initElements(driver,this);
    }

    public SearchPage performSearch(String query){
        waitForLoad(inputSearchBox).clear();
        inputSearchBox.sendKeys(query);
        clickElement(buttonSearch);
        return this;
    }

    public SearchPage scrollToBottom(){
        //((JavascriptExecutor) driver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
        waitForLoad(documentBody).sendKeys(Keys.CONTROL,Keys.END);
        return this;
    }
    public SearchPage clickFavoriteRandomProducts(Integer count){
        Random rng = new Random();
        List<WebElement> selectedProducts = new ArrayList<>();
        final int size = buttonsFavorite.toArray().length;

        for (int i = 1; i <= count; i++) {
            int selectedIndex = rng.nextInt(size);
            WebElement product = buttonsFavorite.get(selectedIndex);

            if(selectedProducts.contains(product)){
               i--;
               continue;
            }
            try{
                clickElement(product);
                selectedProducts.add(product);
            }
            catch (ElementClickInterceptedException ecie){
                i--;
            }
        }
        return this;
    }

    public SearchPage addToBasket(Integer index){
        Actions actions = new Actions(driver);
        WebElement btn = buttonsAddToBasket.get(index - 1);

        actions.moveToElement(buttonProductDivs.get(index-1)).build().perform();
        clickElement(btn);

        return this;
    }

    public void assertFavoriteCount(int count){
        int favoriteCount = driver.findElements(selectorFavorited).size();
        Assertions.assertEquals(count,favoriteCount,"You already had favourites or some of them are not signed.");
    }

    public void assertBasketNotEmpty(){
        boolean basketHasProduct = elementShownOnPageBySelector(selectorBasketCount);
        Assertions.assertTrue(basketHasProduct,"Adding product to the basket failed. Basket is empty.");
    }


}
