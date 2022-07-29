package com.gittigidiyor.tests;

import com.gittigidiyor.base.BaseTest;
import com.gittigidiyor.helpers.TestResultLogger;
import com.gittigidiyor.pages.HomePage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(TestResultLogger.class)
class TestGittiGidiyor extends BaseTest {

    public static final String EMAIL = "requ07@hotmail.com";
    public static final String PASSWORD = "";
    public static HomePage page = new HomePage();

    @Test
    @DisplayName("Perform Successful Login")
    @Order(1)
    @Tag("Account")
    void testLogin() {
        page
                .navigateToLogin()
                .performSuccessfulLogin(EMAIL, PASSWORD)
                .assertLoginStatus(true);
        log.info("Login successful");
    }
    @ParameterizedTest
    @CsvSource("Bardak,4")
    @DisplayName("Search on product and add favorites")
    @Order(2)
    void testSearchOnRandomProduct(String query,int count){

        page.focusOnSearchBox()
                .performSearch(query)
                .scrollToBottom()
                .clickFavoriteRandomProducts(count)
                .assertFavoriteCount(count);
        log.info("Favourite successful");

    }
    @ParameterizedTest
    @CsvSource("Çanta,7")
    @DisplayName("Search spesific product and add to basket")
    @Order(3)
    void test_Search_Product_Add_To_Basket(String query, int index){
        page.focusOnSearchBox()
                .performSearch(query)
                .addToBasket(index)
                .assertBasketNotEmpty();
        log.info("Basket has products");
    }
    @ParameterizedTest
    @CsvSource("2,2")
    @DisplayName("Nav-> Basket increase quantity empty address editBasket add product")
    @Order(4)
    void test_Basket_IncreaseQuantity_EmptyAddress_EditBasket(int quantity, int addIndex){
        page.goToBasket()
            .increaseQuantity2(quantity)
                .navigateToPayment()
                .clickSaveAddress()
                .assertErrorsExist()
                .clickEditBasket()
                .addProductFromFavorites(addIndex);
        log.info("Payment: Errors really exists");
    }
    @Test
    @DisplayName("nav->favorites and remove 3rd product ")
    @Order(5)
    void test_Search_Product_Add_To_Basket_Payment_Back(){
        page.navigateToFavourites()
                .selectSecondFavourite()
                .clickDeleteFavourite()
        .assertProductRemoved();
        log.info("Favourite product successfully removed");

    }
    @Test
    @DisplayName("Open homepage in new tab")
    @Order(6)
    @Tag("Account")
    void test_Tab_Switch_Logout() throws InterruptedException {
        page.focusOnTab()
                .openNewTab()
                .focusOnPage()
                .logOut()
                .assertLoginStatus(false);
        page.focusOnTab().closeTab();
        TimeUnit.SECONDS.sleep(2);
    }

}
