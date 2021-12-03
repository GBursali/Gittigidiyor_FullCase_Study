package com.gittigidiyor.pages;

import com.gittigidiyor.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WindowType;

public class TabPage extends BasePage {
    public HomePage focusOnPage(){
        return new HomePage();
    }
    public void closeTab(){
        driver.close();
    }
    public TabPage openNewTab(){
        driver
                .switchTo().newWindow(WindowType.TAB)
                .navigate().to("https://www.gittigidiyor.com/");
        return this;
    }
}
