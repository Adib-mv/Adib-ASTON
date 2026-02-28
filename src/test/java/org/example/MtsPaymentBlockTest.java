package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WebDriverWait shortWait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        PageFactory.initElements(driver, this);
    }

    protected void waitForPageLoad() {
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
    }

    protected boolean isElementPresent(By locator) {
        try {
            shortWait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}

class HomePage extends BasePage {
    private static final String URL = "https://www.mts.by/";

    @FindBy(id = "cookie-agree")
    private WebElement cookieButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(URL);
        waitForPageLoad();
    }

    public void closeCookieBannerIfPresent() {
        try {
            if (isElementPresent(By.id("cookie-agree"))) {
                WebElement cookieBtn = shortWait.until(ExpectedConditions.elementToBeClickable(
                        By.id("cookie-agree")));
                cookieBtn.click();
                shortWait.until(ExpectedConditions.invisibilityOf(cookieBtn));
            }
        } catch (Exception e) {

        }
    }

    public PaymentBlock getPaymentBlock() {
        return new PaymentBlock(driver);
    }
}

class PaymentBlock extends BasePage {

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
            return wait.until(ExpectedConditions.visibilityOf(blockTitle)).getText();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось получить заголовок блока", e);
        }
    }

    public int getPaymentLogosCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(paymentLogos));
            return paymentLogos.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean areAllLogosDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(paymentLogos));
            return paymentLogos.stream().allMatch(WebElement::isDisplayed);
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getPaymentLogosAltText() {
        List<String> altTexts = new ArrayList<>();
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(paymentLogos));
            for (WebElement logo : paymentLogos) {
                String alt = logo.getAttribute("alt");
                if (alt != null && !alt.isEmpty()) {
                    altTexts.add(alt);
                }
            }
        } catch (Exception e) {

        }
        return altTexts;
    }

    public void clickMoreDetailsLink() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(moreDetailsLink)).click();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось кликнуть по ссылке 'Подробнее о сервисе'", e);
        }
    }

    public void selectCommunicationService() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(selectHeader)).click();

            List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector(".select__list .select__item")));

            boolean optionFound = false;
            for (WebElement option : options) {
                if (option.getText().contains("Услуги связи")) {
                    wait.until(ExpectedConditions.elementToBeClickable(option)).click();
                    optionFound = true;
                    break;
                }
            }

            if (!optionFound) {
                throw new RuntimeException("Опция 'Услуги связи' не найдена");
            }

            wait.until(ExpectedConditions.visibilityOf(connectionForm));
        } catch (Exception e) {
            throw new RuntimeException("Не удалось выбрать услуги связи", e);
        }
    }

    public String getSelectedOptionText() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(selectedOption)).getText();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось получить текст выбранной опции", e);
        }
    }

    public boolean isConnectionFormDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(connectionForm)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void fillConnectionForm(String phone, String sum, String email) {
        try {
            wait.until(ExpectedConditions.visibilityOf(connectionForm));

            clearAndSendKeys(phoneInput, phone);

            clearAndSendKeys(sumInput, sum);

            if (email != null && !email.trim().isEmpty()) {
                clearAndSendKeys(emailInput, email);
            } else {
                emailInput.clear();
            }
        } catch (Exception e) {
            throw new RuntimeException("Не удалось заполнить форму", e);
        }
    }

    private void clearAndSendKeys(WebElement element, String text) {
        element.clear();
        if (text != null && !text.isEmpty()) {
            element.sendKeys(text);
        }
    }

    public void clickContinueButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось кликнуть по кнопке 'Продолжить'", e);
        }
    }

    public String getPhoneValue() {
        return phoneInput.getAttribute("value");
    }

    public String getSumValue() {
        return sumInput.getAttribute("value");
    }

    public String getEmailValue() {
        return emailInput.getAttribute("value");
    }

    public String getPhoneLabelText() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(phoneLabel)).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getSumLabelText() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(sumLabel)).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isPhoneInputRequired() {
        String required = phoneInput.getAttribute("required");
        return required != null && (required.equals("true") || required.equals("required"));
    }

    public boolean isSumInputRequired() {
        String required = sumInput.getAttribute("required");
        return required != null && (required.equals("true") || required.equals("required"));
    }

    public int getPhoneMaxLength() {
        String maxLength = phoneInput.getAttribute("maxlength");
        try {
            return maxLength != null ? Integer.parseInt(maxLength) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isRedirected(String originalUrl) {
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(originalUrl)));
            waitForPageLoad();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForFormSubmission() {
        try {
            Thread.sleep(500); // Небольшая задержка для обработки формы
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                driver.quit();
            }
        }
    }
}

class NegativeTestDataProvider {
    static Stream<Arguments> provideInvalidEmailValues() {
        return Stream.of(
                Arguments.of("test", "без @"),
                Arguments.of("test@", "только с @"),
                Arguments.of("@example.com", "без локальной части"),
                Arguments.of("test@example", "без домена верхнего уровня"),
                Arguments.of("test.example.com", "без @"),
                Arguments.of("test@.com", "с точкой после @"),
                Arguments.of(" ", "пробел")
        );
    }

    static Stream<Arguments> provideEmptyRequiredFields() {
        return Stream.of(
                Arguments.of("", "10.00", "test@example.com", "пустом поле телефона"),
                Arguments.of("297777777", "", "test@example.com", "пустом поле суммы"),
                Arguments.of("", "", "test@example.com", "пустых полях телефона и суммы")
        );
    }
}

@DisplayName("Тесты блока 'Онлайн пополнение без комиссии'")
class MtsPaymentBlockTest extends BaseTest {

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
        String actualTitle = paymentBlock.getBlockTitle()
                .replace("\n", " ")
                .replaceAll("\\s+", " ")
                .trim();

        assertEquals(expectedTitle, actualTitle,
                "Заголовок блока не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Проверка наличия всех логотипов платежных систем")
    public void testPaymentLogosPresence() {
        assertTrue(paymentBlock.getPaymentLogosCount() >= 5,
                "Количество логотипов платежных систем меньше ожидаемого");

        assertTrue(paymentBlock.areAllLogosDisplayed(),
                "Не все логотипы платежных систем отображаются");

        List<String> expectedAltTexts = List.of(
                "Visa",
                "Verified By Visa",
                "MasterCard",
                "MasterCard Secure Code",
                "Белкарт"
        );

        List<String> actualAltTexts = paymentBlock.getPaymentLogosAltText();

        for (String expectedAlt : expectedAltTexts) {
            assertTrue(actualAltTexts.contains(expectedAlt),
                    "Отсутствует логотип с alt-текстом: " + expectedAlt);
        }
    }

    @Test
    @DisplayName("Проверка работы ссылки 'Подробнее о сервисе'")
    public void testMoreDetailsLink() {
        String expectedUrlPart = "/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/";

        paymentBlock.clickMoreDetailsLink();

        assertTrue(paymentBlock.isRedirected(mainPageUrl),
                "Не произошло перенаправления после клика по ссылке");

        String currentUrl = paymentBlock.getCurrentUrl();
        assertTrue(currentUrl.contains(expectedUrlPart),
                "Ссылка ведет не на ожидаемую страницу. Текущий URL: " + currentUrl);
    }

    @Test
    @DisplayName("Проверка выбора опции 'Услуги связи' в выпадающем списке")
    public void testSelectCommunicationService() {
        // Проверяем, что по умолчанию выбрана опция "Услуги связи"
        assertEquals("Услуги связи", paymentBlock.getSelectedOptionText(),
                "По умолчанию не выбрана опция 'Услуги связи'");

        // Проверяем, что форма "Услуги связи" отображается
        assertTrue(paymentBlock.isConnectionFormDisplayed(),
                "Форма 'Услуги связи' не отображается");
    }

    @Test
    @DisplayName("Проверка структуры формы 'Услуги связи'")
    public void testConnectionFormStructure() {
        // Проверяем наличие меток
        assertTrue(paymentBlock.getPhoneLabelText().contains("+375"),
                "Метка поля телефона не содержит +375");
        assertTrue(paymentBlock.getSumLabelText().contains("Руб."),
                "Метка поля суммы не содержит 'Руб.'");
        assertTrue(paymentBlock.isPhoneInputRequired(),
                "Поле телефона должно быть обязательным");
        assertTrue(paymentBlock.isSumInputRequired(),
                "Поле суммы должно быть обязательным");
        int maxLength = paymentBlock.getPhoneMaxLength();
        assertTrue(maxLength >= 13,
                "Максимальная длина поля телефона должна быть не менее 13 символов");
    }

    @Test
    @DisplayName("Проверка заполнения формы 'Услуги связи'")
    public void testFillConnectionForm() {
        String testPhone = "297777777";
        String testSum = "15.50";
        String testEmail = "test@example.com";

        paymentBlock.fillConnectionForm(testPhone, testSum, testEmail);
        assertEquals(testPhone, paymentBlock.getPhoneValue(),
                "Поле телефона заполнено неверно");
        assertEquals(testSum, paymentBlock.getSumValue(),
                "Поле суммы заполнено неверно");
        assertEquals(testEmail, paymentBlock.getEmailValue(),
                "Поле email заполнено неверно");
    }

    @Test
    @DisplayName("Проверка работы кнопки 'Продолжить' с тестовыми данными")
    public void testContinueButtonWithValidData() {
        String testPhone = "297777777";
        String testSum = "20.00";
        String testEmail = "test@example.com";

        paymentBlock.fillConnectionForm(testPhone, testSum, testEmail);
        paymentBlock.clickContinueButton();
        paymentBlock.waitForFormSubmission();

        assertTrue(paymentBlock.isRedirected(mainPageUrl),
                "После отправки формы не произошло перенаправления");
        assertNotEquals(mainPageUrl, paymentBlock.getCurrentUrl(),
                "URL не изменился после отправки формы");
    }

    @Test
    @DisplayName("Проверка отправки формы без email")
    public void testSubmitFormWithoutEmail() {
        String testPhone = "297777777";
        String testSum = "30.00";
        paymentBlock.fillConnectionForm(testPhone, testSum, null);
        paymentBlock.clickContinueButton();
        paymentBlock.waitForFormSubmission();

        assertTrue(paymentBlock.isRedirected(mainPageUrl),
                "Форма не отправилась без email");
    }

    @Test
    @DisplayName("Проверка отправки формы с минимальной суммой")
    public void testSubmitFormWithMinimalSum() {
        String testPhone = "297777777";
        String testSum = "0.01";
        String testEmail = "test@example.com";

        paymentBlock.fillConnectionForm(testPhone, testSum, testEmail);
        paymentBlock.clickContinueButton();
        paymentBlock.waitForFormSubmission();

        assertTrue(paymentBlock.isRedirected(mainPageUrl),
                "Форма не отправилась с минимальной суммой 0.01");
    }

    @Test
    @DisplayName("Проверка отправки формы с максимальной суммой")
    public void testSubmitFormWithMaximalSum() {
        String testPhone = "297777777";
        String testSum = "999.99";
        String testEmail = "test@example.com";

        paymentBlock.fillConnectionForm(testPhone, testSum, testEmail);
        paymentBlock.clickContinueButton();
        paymentBlock.waitForFormSubmission();

        assertTrue(paymentBlock.isRedirected(mainPageUrl),
                "Форма не отправилась с суммой 999.99");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "abc", "!@#", "0", "-10", "0.0", "0,0", "1000000"})
    @DisplayName("Проверка ввода некорректных значений в поле суммы")
    public void testInvalidSumValues(String invalidSum) {
        // Убеждаемся, что мы на главной странице
        if (!driver.getCurrentUrl().equals(mainPageUrl)) {
            driver.get(mainPageUrl);
            homePage.closeCookieBannerIfPresent();
        }

        String testPhone = "297777777";
        String testEmail = "test@example.com";

        paymentBlock.fillConnectionForm(testPhone, invalidSum, testEmail);
        paymentBlock.clickContinueButton();
        paymentBlock.waitForFormSubmission();
        assertEquals(mainPageUrl, paymentBlock.getCurrentUrl(),
                "Форма отправилась с некорректной суммой: " + invalidSum);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "123", "abcdef", "+375", "29777777", "2977777777"})
    @DisplayName("Проверка ввода некорректных значений в поле телефона")
    public void testInvalidPhoneValues(String invalidPhone) {
        // Убеждаемся, что мы на главной странице
        if (!driver.getCurrentUrl().equals(mainPageUrl)) {
            driver.get(mainPageUrl);
            homePage.closeCookieBannerIfPresent();
        }

        String testSum = "10.00";
        String testEmail = "test@example.com";

        paymentBlock.fillConnectionForm(invalidPhone, testSum, testEmail);
        paymentBlock.clickContinueButton();
        paymentBlock.waitForFormSubmission();
        assertEquals(mainPageUrl, paymentBlock.getCurrentUrl(),
                "Форма отправилась с некорректным телефоном: " + invalidPhone);
    }

    @ParameterizedTest
    @MethodSource("org.example.NegativeTestDataProvider#provideInvalidEmailValues")
    @DisplayName("Проверка ввода некорректных email адресов")
    public void testInvalidEmailValues(String invalidEmail, String description) {
        // Убеждаемся, что мы на главной странице
        if (!driver.getCurrentUrl().equals(mainPageUrl)) {
            driver.get(mainPageUrl);
            homePage.closeCookieBannerIfPresent();
        }

        String testPhone = "297777777";
        String testSum = "10.00";

        paymentBlock.fillConnectionForm(testPhone, testSum, invalidEmail);
        paymentBlock.clickContinueButton();
        paymentBlock.waitForFormSubmission();

        assertNotNull(driver.getCurrentUrl(), "Страница не загрузилась после отправки формы");
    }

    @ParameterizedTest
    @MethodSource("org.example.NegativeTestDataProvider#provideEmptyRequiredFields")
    @DisplayName("Проверка отправки формы с пустыми обязательными полями")
    public void testSubmitWithEmptyRequiredFields(String phone, String sum, String email, String description) {
        // Убеждаемся, что мы на главной странице
        if (!driver.getCurrentUrl().equals(mainPageUrl)) {
            driver.get(mainPageUrl);
            homePage.closeCookieBannerIfPresent();
        }

        paymentBlock.fillConnectionForm(phone, sum, email);
        paymentBlock.clickContinueButton();
        paymentBlock.waitForFormSubmission();

        assertEquals(mainPageUrl, paymentBlock.getCurrentUrl(),
                "Форма отправилась при " + description);
    }
}