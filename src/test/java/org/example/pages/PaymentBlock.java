package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.ArrayList;
import java.util.List;

public class PaymentBlock extends BasePage {

    @FindBy(css = ".pay__wrapper h2")
    private WebElement blockTitle;

    @FindBy(css = ".select__header")
    private WebElement selectHeader;

    @FindBy(css = ".select__now")
    private WebElement selectedOption;

    @FindBy(id = "pay-connection")
    private WebElement connectionForm;

    @FindBy(id = "connection-phone")
    private WebElement phoneInput;

    @FindBy(id = "connection-sum")
    private WebElement sumInput;

    @FindBy(id = "connection-email")
    private WebElement emailInput;

    @FindBy(css = "#pay-connection .button__default")
    private WebElement continueButton;

    @FindBy(xpath = "//a[contains(text(), 'Подробнее о сервисе')]")
    private WebElement moreDetailsLink;

    @FindBy(css = ".pay__partners ul li img")
    private List<WebElement> paymentLogos;

    @FindBy(css = "label[for='connection-phone']")
    private WebElement phoneLabel;

    @FindBy(css = "label[for='connection-sum']")
    private WebElement sumLabel;

    public PaymentBlock(WebDriver driver) {
        super(driver);
    }

    public String getBlockTitle() {
        try {
            Thread.sleep(1000);
            String title = getElementText(blockTitle);
            return title.replace("\n", " ").replaceAll("\\s+", " ").trim();
        } catch (Exception e) {
            return "";
        }
    }

    public int getPaymentLogosCount() {
        try {
            return paymentLogos.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean areAllLogosDisplayed() {
        try {
            for (WebElement logo : paymentLogos) {
                if (!logo.isDisplayed()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getPaymentLogosAltText() {
        List<String> altTexts = new ArrayList<>();
        try {
            for (WebElement logo : paymentLogos) {
                String alt = logo.getAttribute("alt");
                if (alt != null && !alt.isEmpty()) {
                    altTexts.add(alt);
                }
            }
        } catch (Exception e) {
            // Игнорируем
        }
        return altTexts;
    }

    public void clickMoreDetailsLink() {
        try {
            Thread.sleep(1000);
            moreDetailsLink.click();
            Thread.sleep(2000);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось кликнуть по ссылке 'Подробнее о сервисе'", e);
        }
    }

    public void selectService(String serviceName) {
        try {
            Thread.sleep(1000);
            selectHeader.click();
            Thread.sleep(500);

            List<WebElement> options = driver.findElements(By.cssSelector(".select__list .select__item"));
            for (WebElement option : options) {
                if (option.getText().contains(serviceName)) {
                    option.click();
                    Thread.sleep(500);
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Не удалось выбрать услугу: " + serviceName, e);
        }
    }

    public List<String> getPlaceholdersForService(String serviceName) {
        selectService(serviceName);
        List<String> placeholders = new ArrayList<>();

        try {
            List<WebElement> inputs = driver.findElements(By.cssSelector("#pay-connection input[placeholder]"));
            for (WebElement input : inputs) {
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

    public String getSelectedOptionText() {
        try {
            return getElementText(selectedOption);
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isConnectionFormDisplayed() {
        try {
            Thread.sleep(500);
            return connectionForm.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void fillConnectionForm(String phone, String sum, String email) {
        try {
            Thread.sleep(1000);
            clearAndSendKeys(phoneInput, phone);
            clearAndSendKeys(sumInput, sum);
            clearAndSendKeys(emailInput, email);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось заполнить форму", e);
        }
    }

    public PaymentPopup clickContinueButton() {
        try {
            Thread.sleep(500);
            continueButton.click();
            Thread.sleep(2000);
            return new PaymentPopup(driver);
        } catch (Exception e) {
            return null;
        }
    }

    public String getPhoneValue() {
        return getInputValue(phoneInput);
    }

    public String getSumValue() {
        return getInputValue(sumInput);
    }

    public String getEmailValue() {
        return getInputValue(emailInput);
    }

    public String getPhoneLabelText() {
        return getElementText(phoneLabel);
    }

    public String getSumLabelText() {
        return getElementText(sumLabel);
    }

    public boolean isPhoneInputRequired() {
        try {
            String required = phoneInput.getAttribute("required");
            return required != null;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSumInputRequired() {
        try {
            String required = sumInput.getAttribute("required");
            return required != null;
        } catch (Exception e) {
            return false;
        }
    }

    public int getPhoneMaxLength() {
        try {
            String maxLength = phoneInput.getAttribute("maxlength");
            return maxLength != null ? Integer.parseInt(maxLength) : 0;
        } catch (Exception e) {
            return 0;
        }
    }
}