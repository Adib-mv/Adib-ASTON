package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.ArrayList;
import java.util.List;

public class PaymentPopup extends BasePage {

    @FindBy(css = ".card-info__sum, .payment-sum, .total-amount")
    private WebElement sumElement;

    @FindBy(css = ".card-info__phone, .payment-phone, .phone-number")
    private WebElement phoneElement;

    @FindBy(css = ".pay-form__btn span, .payment-button, button[type='submit']")
    private WebElement payButton;

    @FindBy(css = ".card-info__details input[placeholder], .payment-form input[placeholder]")
    private List<WebElement> cardPlaceholders;

    @FindBy(css = ".card-info__details img, .payment-icons img, .card-icon")
    private List<WebElement> cardIcons;

    @FindBy(css = ".popup, .modal, .payment-popup")
    private WebElement popupContainer;

    public PaymentPopup(WebDriver driver) {
        super(driver);
    }

    public boolean isPopupDisplayed() {
        try {
            Thread.sleep(1000);
            return popupContainer.isDisplayed() || sumElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getDisplayedSum() {
        try {
            return getElementText(sumElement);
        } catch (Exception e) {
            return "";
        }
    }

    public String getDisplayedPhone() {
        try {
            return getElementText(phoneElement);
        } catch (Exception e) {
            return "";
        }
    }

    public String getPayButtonText() {
        try {
            return getElementText(payButton);
        } catch (Exception e) {
            return "";
        }
    }

    public List<String> getCardPlaceholders() {
        List<String> placeholders = new ArrayList<>();
        try {
            for (WebElement input : cardPlaceholders) {
                String placeholder = input.getAttribute("placeholder");
                if (placeholder != null && !placeholder.isEmpty()) {
                    placeholders.add(placeholder);
                }
            }
        } catch (Exception e) {
            // Игнорируем
        }
        return placeholders;
    }

    public int getCardIconsCount() {
        try {
            return cardIcons.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean areCardIconsDisplayed() {
        try {
            for (WebElement icon : cardIcons) {
                if (!icon.isDisplayed()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}