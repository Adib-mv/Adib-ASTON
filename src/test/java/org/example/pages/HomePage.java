package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    private static final String URL = "https://www.mts.by/";

    @FindBy(id = "cookie-agree")
    private WebElement cookieButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        try {
            driver.get(URL);
            waitForPageLoad();
            Thread.sleep(1000); // Небольшая задержка для полной загрузки
        } catch (Exception e) {
            throw new RuntimeException("Не удалось открыть страницу: " + URL, e);
        }
    }

    public void closeCookieBannerIfPresent() {
        try {
            if (isElementPresent(By.id("cookie-agree"))) {
                cookieButton.click();
                Thread.sleep(500);
            }
        } catch (Exception e) {
            // Игнорируем, если баннер не появился
        }
    }

    public PaymentBlock getPaymentBlock() {
        return new PaymentBlock(driver);
    }
}