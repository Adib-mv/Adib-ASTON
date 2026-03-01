package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WebDriverWait shortWait;
    protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    protected void waitForPageLoad() {
        try {
            wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            // Игнорируем ошибки выполнения скрипта
        }
    }

    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected String getElementText(WebElement element) {
        try {
            return element.getText();
        } catch (Exception e) {
            return "";
        }
    }

    protected String getInputValue(WebElement input) {
        try {
            return input.getAttribute("value");
        } catch (Exception e) {
            return "";
        }
    }

    protected void clearAndSendKeys(WebElement element, String text) {
        try {
            element.clear();
            if (text != null && !text.isEmpty()) {
                element.sendKeys(text);
            }
        } catch (Exception e) {
            // Игнорируем ошибки ввода
        }
    }

    public String getCurrentUrl() {
        try {
            return driver.getCurrentUrl();
        } catch (Exception e) {
            return "";
        }
    }
}