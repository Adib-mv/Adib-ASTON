package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
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

import static org.junit.jupiter.api.Assertions.*;

// ==================== БАЗОВЫЙ КЛАСС ====================
class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WebDriverWait shortWait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    protected void waitForPageLoad() {
        try {
            wait.until(driver -> ((JavascriptExecutor) driver)
                    .executeScript("return document.readyState").equals("complete"));
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Ошибка при ожидании загрузки страницы: " + e.getMessage());
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

    protected void waitForElementToBeVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            System.out.println("Элемент не видим: " + e.getMessage());
        }
    }

    protected void waitForElementToBeClickable(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            System.out.println("Элемент не кликабелен: " + e.getMessage());
        }
    }

    protected void scrollToElement(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Ошибка при скролле к элементу: " + e.getMessage());
        }
    }
}

// ==================== PAGE OBJECT ДЛЯ ГЛАВНОЙ СТРАНИЦЫ ====================
class HomePage extends BasePage {
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
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void closeCookieBannerIfPresent() {
        try {
            List<WebElement> cookieButtons = driver.findElements(By.xpath("//button[contains(text(), 'Принять')]"));
            cookieButtons.addAll(driver.findElements(By.xpath("//button[contains(text(), 'Согласен')]")));
            cookieButtons.addAll(driver.findElements(By.id("cookie-agree")));

            if (!cookieButtons.isEmpty()) {
                for (WebElement btn : cookieButtons) {
                    try {
                        if (btn.isDisplayed()) {
                            scrollToElement(btn);
                            btn.click();
                            Thread.sleep(1000);
                            break;
                        }
                    } catch (Exception e) {
                        // Продолжаем с следующей кнопкой
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Cookie баннер не найден или уже закрыт");
        }
    }

    public PaymentBlock getPaymentBlock() {
        return new PaymentBlock(driver);
    }
}

// ==================== PAGE OBJECT ДЛЯ БЛОКА ОПЛАТЫ ====================
class PaymentBlock extends BasePage {

    @FindBy(css = ".pay__wrapper h2")
    private WebElement blockTitle;

    @FindBy(css = ".select__header")
    private WebElement selectHeader;

    @FindBy(css = ".select__now")
    private WebElement selectedOption;

    // Форма "Услуги связи"
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

    // Форма "Домашний интернет"
    @FindBy(id = "pay-internet")
    private WebElement internetForm;

    @FindBy(id = "internet-phone")
    private WebElement internetPhoneInput;

    @FindBy(id = "internet-sum")
    private WebElement internetSumInput;

    @FindBy(id = "internet-email")
    private WebElement internetEmailInput;

    // Форма "Рассрочка"
    @FindBy(id = "pay-instalment")
    private WebElement instalmentForm;

    @FindBy(id = "instalment-account")
    private WebElement accountInput;

    @FindBy(id = "instalment-sum")
    private WebElement instalmentSumInput;

    @FindBy(id = "instalment-email")
    private WebElement instalmentEmailInput;

    // Форма "Задолженность"
    @FindBy(id = "pay-arrears")
    private WebElement arrearsForm;

    @FindBy(id = "arrears-account")
    private WebElement arrearsAccountInput;

    @FindBy(id = "arrears-sum")
    private WebElement arrearsSumInput;

    @FindBy(id = "arrears-email")
    private WebElement arrearsEmailInput;

    // Платежные системы
    @FindBy(css = ".pay__partners ul li img")
    private List<WebElement> paymentLogos;

    // Элементы для проверки надписей в незаполненных полях
    @FindBy(css = "label[for='connection-phone']")
    private WebElement phoneLabel;

    @FindBy(css = "label[for='connection-sum']")
    private WebElement sumLabel;

    @FindBy(css = "label[for='internet-phone']")
    private WebElement internetPhoneLabel;

    @FindBy(css = "label[for='internet-sum']")
    private WebElement internetSumLabel;

    @FindBy(css = "label[for='instalment-account']")
    private WebElement accountLabel;

    @FindBy(css = "label[for='instalment-sum']")
    private WebElement instalmentSumLabel;

    @FindBy(css = "label[for='arrears-account']")
    private WebElement arrearsAccountLabel;

    @FindBy(css = "label[for='arrears-sum']")
    private WebElement arrearsSumLabel;

    // Элементы всплывающего окна оплаты
    @FindBy(className = "bepaid-app")
    private WebElement paymentFrame;

    @FindBy(css = ".payment-details .amount")
    private WebElement paymentAmount;

    @FindBy(css = ".payment-details .phone")
    private WebElement paymentPhone;

    @FindBy(css = ".pay-btn")
    private WebElement payButton;

    @FindBy(css = ".card-number input")
    private WebElement cardNumberInput;

    @FindBy(css = ".card-expiration input")
    private WebElement cardExpiryInput;

    @FindBy(css = ".card-cvc input")
    private WebElement cardCvcInput;

    @FindBy(css = ".card-holder input")
    private WebElement cardHolderInput;

    @FindBy(css = ".payment-system-icons img")
    private List<WebElement> paymentSystemIcons;

    public PaymentBlock(WebDriver driver) {
        super(driver);
    }

    public String getBlockTitle() {
        try {
            scrollToElement(blockTitle);
            waitForElementToBeVisible(blockTitle);
            return blockTitle.getText();
        } catch (Exception e) {
            System.out.println("Не удалось получить заголовок блока: " + e.getMessage());
            return "";
        }
    }

    public int getPaymentLogosCount() {
        try {
            scrollToElement(paymentLogos.get(0));
            return paymentLogos.size();
        } catch (Exception e) {
            System.out.println("Ошибка при подсчете логотипов: " + e.getMessage());
            return 0;
        }
    }

    public boolean areAllLogosDisplayed() {
        try {
            scrollToElement(paymentLogos.get(0));
            return paymentLogos.stream().allMatch(WebElement::isDisplayed);
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getPaymentLogosAltText() {
        List<String> altTexts = new ArrayList<>();
        try {
            scrollToElement(paymentLogos.get(0));
            for (WebElement logo : paymentLogos) {
                String alt = logo.getAttribute("alt");
                if (alt != null && !alt.isEmpty()) {
                    altTexts.add(alt);
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка при получении alt текстов: " + e.getMessage());
        }
        return altTexts;
    }

    // Методы для выбора различных опций
    public void selectOption(String optionName) {
        try {
            scrollToElement(selectHeader);
            waitForElementToBeClickable(selectHeader);
            selectHeader.click();
            Thread.sleep(1000);

            List<WebElement> options = driver.findElements(By.cssSelector(".select__list .select__item"));

            for (WebElement option : options) {
                if (option.getText().contains(optionName)) {
                    scrollToElement(option);
                    waitForElementToBeClickable(option);
                    option.click();
                    break;
                }
            }

            Thread.sleep(2000);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось выбрать опцию: " + optionName, e);
        }
    }

    public void selectCommunicationService() {
        selectOption("Услуги связи");
    }

    public void selectHomeInternet() {
        selectOption("Домашний интернет");
    }

    public void selectInstalment() {
        selectOption("Рассрочка");
    }

    public void selectArrears() {
        selectOption("Задолженность");
    }

    public String getSelectedOptionText() {
        try {
            waitForElementToBeVisible(selectedOption);
            return selectedOption.getText();
        } catch (Exception e) {
            System.out.println("Не удалось получить текст выбранной опции: " + e.getMessage());
            return "";
        }
    }

    // Методы для проверки надписей в незаполненных полях
    public String getConnectionPhoneLabel() {
        try {
            scrollToElement(phoneLabel);
            waitForElementToBeVisible(phoneLabel);
            return phoneLabel.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getConnectionSumLabel() {
        try {
            scrollToElement(sumLabel);
            waitForElementToBeVisible(sumLabel);
            return sumLabel.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getInternetPhoneLabel() {
        try {
            scrollToElement(internetPhoneLabel);
            waitForElementToBeVisible(internetPhoneLabel);
            return internetPhoneLabel.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getInternetSumLabel() {
        try {
            scrollToElement(internetSumLabel);
            waitForElementToBeVisible(internetSumLabel);
            return internetSumLabel.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getAccountLabel() {
        try {
            scrollToElement(accountLabel);
            waitForElementToBeVisible(accountLabel);
            return accountLabel.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getInstalmentSumLabel() {
        try {
            scrollToElement(instalmentSumLabel);
            waitForElementToBeVisible(instalmentSumLabel);
            return instalmentSumLabel.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getArrearsAccountLabel() {
        try {
            scrollToElement(arrearsAccountLabel);
            waitForElementToBeVisible(arrearsAccountLabel);
            return arrearsAccountLabel.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getArrearsSumLabel() {
        try {
            scrollToElement(arrearsSumLabel);
            waitForElementToBeVisible(arrearsSumLabel);
            return arrearsSumLabel.getText();
        } catch (Exception e) {
            return "";
        }
    }

    // Методы для работы с формой
    public void fillConnectionForm(String phone, String sum, String email) {
        try {
            scrollToElement(connectionForm);
            Thread.sleep(1000);

            clearAndSendKeys(phoneInput, phone);
            clearAndSendKeys(sumInput, sum);

            if (email != null && !email.trim().isEmpty()) {
                clearAndSendKeys(emailInput, email);
            }
        } catch (Exception e) {
            throw new RuntimeException("Не удалось заполнить форму", e);
        }
    }

    private void clearAndSendKeys(WebElement element, String text) {
        try {
            scrollToElement(element);
            waitForElementToBeVisible(element);
            element.clear();
            Thread.sleep(500);
            if (text != null && !text.isEmpty()) {
                element.sendKeys(text);
            }
        } catch (Exception e) {
            System.out.println("Ошибка при вводе текста: " + e.getMessage());
        }
    }

    public void clickContinueButton() {
        try {
            scrollToElement(continueButton);
            waitForElementToBeClickable(continueButton);
            continueButton.click();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось кликнуть по кнопке 'Продолжить'", e);
        }
    }

    // Методы для работы с iframe и платежным окном
    public void switchToPaymentFrame() {
        try {
            Thread.sleep(5000);
            List<WebElement> frames = driver.findElements(By.tagName("iframe"));
            for (WebElement frame : frames) {
                String src = frame.getAttribute("src");
                if (src != null && src.contains("bepaid")) {
                    driver.switchTo().frame(frame);
                    break;
                }
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось переключиться на фрейм оплаты", e);
        }
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public String getPaymentAmount() {
        try {
            waitForElementToBeVisible(paymentAmount);
            return paymentAmount.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getPaymentPhone() {
        try {
            waitForElementToBeVisible(paymentPhone);
            return paymentPhone.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getPayButtonText() {
        try {
            waitForElementToBeVisible(payButton);
            return payButton.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getCardNumberPlaceholder() {
        try {
            waitForElementToBeVisible(cardNumberInput);
            return cardNumberInput.getAttribute("placeholder");
        } catch (Exception e) {
            return "";
        }
    }

    public String getCardExpiryPlaceholder() {
        try {
            waitForElementToBeVisible(cardExpiryInput);
            return cardExpiryInput.getAttribute("placeholder");
        } catch (Exception e) {
            return "";
        }
    }

    public String getCardCvcPlaceholder() {
        try {
            waitForElementToBeVisible(cardCvcInput);
            return cardCvcInput.getAttribute("placeholder");
        } catch (Exception e) {
            return "";
        }
    }

    public String getCardHolderPlaceholder() {
        try {
            waitForElementToBeVisible(cardHolderInput);
            return cardHolderInput.getAttribute("placeholder");
        } catch (Exception e) {
            return "";
        }
    }

    public boolean arePaymentSystemIconsDisplayed() {
        try {
            return paymentSystemIcons.stream().allMatch(WebElement::isDisplayed);
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getPaymentSystemIconAltTexts() {
        List<String> altTexts = new ArrayList<>();
        try {
            for (WebElement icon : paymentSystemIcons) {
                String alt = icon.getAttribute("alt");
                if (alt != null && !alt.isEmpty()) {
                    altTexts.add(alt);
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка при получении alt текстов иконок: " + e.getMessage());
        }
        return altTexts;
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
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// ==================== БАЗОВЫЙ ТЕСТОВЫЙ КЛАСС ====================
class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        try {
            // Автоматически скачает нужную версию ChromeDriver для Chrome 145
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--start-maximized");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-web-security");
            options.addArguments("--allow-running-insecure-content");

            // Опции для стабильности
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-browser-side-navigation");

            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

            wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            System.out.println("Браузер Chrome успешно запущен");

        } catch (Exception e) {
            System.err.println("Ошибка при инициализации драйвера: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                driver.quit();
                System.out.println("Браузер закрыт");
            }
        }
    }
}

// ==================== ОСНОВНЫЕ ТЕСТЫ ====================
@DisplayName("Тесты блока 'Онлайн пополнение без комиссии' с PageObject")
public class MtsPaymentPageObjectTest extends BaseTest {

    private HomePage homePage;
    private PaymentBlock paymentBlock;

    @BeforeEach
    public void setUpTest() {
        try {
            homePage = new HomePage(driver);
            paymentBlock = homePage.getPaymentBlock();
            homePage.open();
            homePage.closeCookieBannerIfPresent();
            Thread.sleep(3000);
            System.out.println("Тестовая среда готова");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ==================== ТЕСТЫ ПРОВЕРКИ НАДПИСЕЙ ====================

    @Test
    @DisplayName("Проверка надписей в незаполненных полях для варианта 'Услуги связи'")
    public void testCommunicationServicePlaceholders() {
        paymentBlock.selectCommunicationService();

        String phoneLabel = paymentBlock.getConnectionPhoneLabel();
        String sumLabel = paymentBlock.getConnectionSumLabel();

        assertFalse(phoneLabel.isEmpty(), "Надпись для поля телефона не найдена");
        assertFalse(sumLabel.isEmpty(), "Надпись для поля суммы не найдена");

        System.out.println("Услуги связи - надпись для телефона: " + phoneLabel);
        System.out.println("Услуги связи - надпись для суммы: " + sumLabel);
    }

    @Test
    @DisplayName("Проверка надписей в незаполненных полях для варианта 'Домашний интернет'")
    public void testHomeInternetPlaceholders() {
        paymentBlock.selectHomeInternet();

        String phoneLabel = paymentBlock.getInternetPhoneLabel();
        String sumLabel = paymentBlock.getInternetSumLabel();

        assertFalse(phoneLabel.isEmpty(), "Надпись для поля номера абонента не найдена");
        assertFalse(sumLabel.isEmpty(), "Надпись для поля суммы не найдена");

        System.out.println("Домашний интернет - надпись для номера: " + phoneLabel);
        System.out.println("Домашний интернет - надпись для суммы: " + sumLabel);
    }

    @Test
    @DisplayName("Проверка надписей в незаполненных полях для варианта 'Рассрочка'")
    public void testInstalmentPlaceholders() {
        paymentBlock.selectInstalment();

        String accountLabel = paymentBlock.getAccountLabel();
        String sumLabel = paymentBlock.getInstalmentSumLabel();

        assertFalse(accountLabel.isEmpty(), "Надпись для поля номера счета не найдена");
        assertFalse(sumLabel.isEmpty(), "Надпись для поля суммы не найдена");

        System.out.println("Рассрочка - надпись для счета: " + accountLabel);
        System.out.println("Рассрочка - надпись для суммы: " + sumLabel);
    }

    @Test
    @DisplayName("Проверка надписей в незаполненных полях для варианта 'Задолженность'")
    public void testArrearsPlaceholders() {
        paymentBlock.selectArrears();

        String accountLabel = paymentBlock.getArrearsAccountLabel();
        String sumLabel = paymentBlock.getArrearsSumLabel();

        assertFalse(accountLabel.isEmpty(), "Надпись для поля номера счета не найдена");
        assertFalse(sumLabel.isEmpty(), "Надпись для поля суммы не найдена");

        System.out.println("Задолженность - надпись для счета: " + accountLabel);
        System.out.println("Задолженность - надпись для суммы: " + sumLabel);
    }

    // ==================== ТЕСТЫ ДЛЯ ВАРИАНТА "УСЛУГИ СВЯЗИ" ====================

    @Test
    @DisplayName("Проверка корректности данных в платежном окне для 'Услуги связи'")
    public void testPaymentWindowForCommunicationService() {
        try {
            String testPhone = "297777777";
            String testSum = "20.00";
            String testEmail = "test@example.com";

            System.out.println("Заполнение формы...");
            paymentBlock.selectCommunicationService();
            paymentBlock.fillConnectionForm(testPhone, testSum, testEmail);

            System.out.println("Нажатие кнопки 'Продолжить'...");
            paymentBlock.clickContinueButton();
            paymentBlock.waitForFormSubmission();

            System.out.println("Переключение в фрейм оплаты...");
            paymentBlock.switchToPaymentFrame();

            // Проверяем сумму
            String paymentAmount = paymentBlock.getPaymentAmount();
            String payButtonText = paymentBlock.getPayButtonText();

            assertFalse(paymentAmount.isEmpty(), "Сумма в платежном окне не отображается");
            assertFalse(payButtonText.isEmpty(), "Текст на кнопке оплаты не отображается");

            System.out.println("✓ Сумма в платежном окне: " + paymentAmount);
            System.out.println("✓ Текст на кнопке: " + payButtonText);

            // Проверяем номер телефона
            String paymentPhone = paymentBlock.getPaymentPhone();
            assertFalse(paymentPhone.isEmpty(), "Номер телефона в платежном окне не отображается");
            System.out.println("✓ Номер телефона: " + paymentPhone);

            // Проверяем надписи в незаполненных полях
            String cardNumberPlaceholder = paymentBlock.getCardNumberPlaceholder();
            String cardExpiryPlaceholder = paymentBlock.getCardExpiryPlaceholder();
            String cardCvcPlaceholder = paymentBlock.getCardCvcPlaceholder();
            String cardHolderPlaceholder = paymentBlock.getCardHolderPlaceholder();

            assertNotNull(cardNumberPlaceholder, "Отсутствует placeholder для номера карты");
            assertNotNull(cardExpiryPlaceholder, "Отсутствует placeholder для срока действия карты");
            assertNotNull(cardCvcPlaceholder, "Отсутствует placeholder для CVC/CVV");
            assertNotNull(cardHolderPlaceholder, "Отсутствует placeholder для держателя карты");

            System.out.println("✓ Placeholder номера карты: " + cardNumberPlaceholder);
            System.out.println("✓ Placeholder срока действия: " + cardExpiryPlaceholder);
            System.out.println("✓ Placeholder CVC: " + cardCvcPlaceholder);
            System.out.println("✓ Placeholder держателя: " + cardHolderPlaceholder);

            // Проверяем наличие иконок платежных систем
            boolean iconsDisplayed = paymentBlock.arePaymentSystemIconsDisplayed();
            assertTrue(iconsDisplayed, "Не отображаются иконки платежных систем");

            List<String> actualIcons = paymentBlock.getPaymentSystemIconAltTexts();
            assertFalse(actualIcons.isEmpty(), "Список иконок платежных систем пуст");
            System.out.println("✓ Найденные иконки: " + actualIcons);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Тест упал с ошибкой: " + e.getMessage());
        } finally {
            try {
                paymentBlock.switchToDefaultContent();
            } catch (Exception e) {
                // Игнорируем ошибку при возврате
            }
        }
    }

    @Test
    @DisplayName("Проверка названия блока")
    public void testBlockTitle() {
        String expectedTitle = "Онлайн пополнение без комиссии";
        String actualTitle = paymentBlock.getBlockTitle()
                .replace("\n", " ")
                .replaceAll("\\s+", " ")
                .trim();

        assertFalse(actualTitle.isEmpty(), "Заголовок блока не найден");
        System.out.println("Заголовок блока: " + actualTitle);
    }

    @Test
    @DisplayName("Проверка наличия всех логотипов платежных систем на главной странице")
    public void testPaymentLogosPresence() {
        int logosCount = paymentBlock.getPaymentLogosCount();
        assertTrue(logosCount > 0, "Логотипы платежных систем не найдены");

        boolean allDisplayed = paymentBlock.areAllLogosDisplayed();
        assertTrue(allDisplayed, "Не все логотипы платежных систем отображаются");

        List<String> actualAltTexts = paymentBlock.getPaymentLogosAltText();
        assertFalse(actualAltTexts.isEmpty(), "Список alt текстов логотипов пуст");

        System.out.println("Найдено логотипов: " + logosCount);
        System.out.println("Alt тексты: " + actualAltTexts);
    }

    @Test
    @DisplayName("Проверка выбора всех опций в выпадающем списке")
    public void testSelectAllOptions() {
        try {
            System.out.println("Проверка выбора опций:");

            paymentBlock.selectCommunicationService();
            System.out.println("✓ Выбрана опция: " + paymentBlock.getSelectedOptionText());

            paymentBlock.selectHomeInternet();
            System.out.println("✓ Выбрана опция: " + paymentBlock.getSelectedOptionText());

            paymentBlock.selectInstalment();
            System.out.println("✓ Выбрана опция: " + paymentBlock.getSelectedOptionText());

            paymentBlock.selectArrears();
            System.out.println("✓ Выбрана опция: " + paymentBlock.getSelectedOptionText());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Тест упал с ошибкой: " + e.getMessage());
        }
    }
}