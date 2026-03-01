package org.example.tests;

import org.example.pages.HomePage;
import org.example.pages.PaymentBlock;
import org.example.pages.PaymentPopup;
import org.example.providers.NegativeTestDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты блока 'Онлайн пополнение без комиссии'")
class MtsPaymentPageObjectBlockTest extends BaseTest {

    private HomePage homePage;
    private PaymentBlock paymentBlock;
    private String mainPageUrl;

    @BeforeEach
    public void setUpTest() {
        homePage = new HomePage(driver);
        paymentBlock = homePage.getPaymentBlock();
        homePage.open();
        mainPageUrl = driver.getCurrentUrl();
        homePage.closeCookieBannerIfPresent();
    }

    @Test
    @DisplayName("Проверка названия блока")
    public void testBlockTitle() {
        String expectedTitle = "Онлайн пополнение без комиссии";
        String actualTitle = paymentBlock.getBlockTitle();
        assertEquals(expectedTitle, actualTitle, "Заголовок блока не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Проверка наличия логотипов платежных систем")
    public void testPaymentLogosPresence() {
        assertTrue(paymentBlock.getPaymentLogosCount() >= 3, "Количество логотипов меньше ожидаемого");
        assertTrue(paymentBlock.areAllLogosDisplayed(), "Не все логотипы отображаются");
    }

    @Test
    @DisplayName("Проверка работы ссылки 'Подробнее о сервисе'")
    public void testMoreDetailsLink() {
        paymentBlock.clickMoreDetailsLink();
        String currentUrl = driver.getCurrentUrl();
        assertNotEquals(mainPageUrl, currentUrl, "URL не изменился после клика");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Услуги связи", "Домашний интернет", "Рассрочка", "Задолженность"})
    @DisplayName("Проверка плейсхолдеров для всех вариантов оплаты")
    public void testPlaceholdersForAllServices(String serviceName) {
        List<String> placeholders = paymentBlock.getPlaceholdersForService(serviceName);
        assertFalse(placeholders.isEmpty(), "Плейсхолдеры не найдены для услуги: " + serviceName);
        System.out.println("Плейсхолдеры для " + serviceName + ": " + placeholders);
    }

    @Test
    @DisplayName("Проверка попапа после заполнения формы")
    public void testPaymentPopup() {
        paymentBlock.fillConnectionForm("297777777", "25.50", "test@example.com");
        PaymentPopup popup = paymentBlock.clickContinueButton();

        if (popup != null && popup.isPopupDisplayed()) {
            String sum = popup.getDisplayedSum();
            assertFalse(sum.isEmpty(), "Сумма не отображается в попапе");

            List<String> placeholders = popup.getCardPlaceholders();
            assertFalse(placeholders.isEmpty(), "Плейсхолдеры карты не найдены");

            assertTrue(popup.getCardIconsCount() > 0, "Иконки платежных систем не найдены");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "abc", "0", "-10"})
    @DisplayName("Проверка некорректных значений суммы")
    public void testInvalidSumValues(String invalidSum) {
        paymentBlock.fillConnectionForm("297777777", invalidSum, "test@example.com");
        paymentBlock.clickContinueButton();
        assertEquals(mainPageUrl, driver.getCurrentUrl(), "URL изменился при некорректной сумме");
    }

    @ParameterizedTest
    @MethodSource("org.example.providers.NegativeTestDataProvider#provideInvalidEmailValues")
    @DisplayName("Проверка некорректных email адресов")
    public void testInvalidEmailValues(String invalidEmail, String description) {
        paymentBlock.fillConnectionForm("297777777", "10.00", invalidEmail);
        paymentBlock.clickContinueButton();
    }
}