package org.example.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        try {
            String driverPath = findChromeDriver();

            if (driverPath == null) {
                System.out.println("Поиск ChromeDriver в системном PATH...");
            } else {
                System.setProperty("webdriver.chrome.driver", driverPath);
                System.out.println("✅ Используется ChromeDriver: " + driverPath);
            }

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-notifications");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--accept-lang=ru");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.setExperimentalOption("useAutomationExtension", false);

            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.manage().window().maximize();

            wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            System.out.println("✅ Браузер успешно запущен");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Не удалось запустить браузер: " + e.getMessage() +
                    "\n\n💡 Решение: Убедитесь, что ChromeDriver версии 145.0.7632.116 находится в папке drivers/");
        }
    }

    private String findChromeDriver() {
        String[] searchPaths = {
                "drivers/chromedriver.exe",
                "src/test/resources/drivers/chromedriver.exe",
                "src/main/resources/drivers/chromedriver.exe",
                "../drivers/chromedriver.exe",
                "chromedriver.exe"
        };

        String userDir = System.getProperty("user.dir");

        for (String path : searchPaths) {
            File file = new File(userDir, path);
            if (file.exists()) {
                return file.getAbsolutePath();
            }
        }
        File parentDir = new File(userDir).getParentFile();
        if (parentDir != null) {
            File driversDir = new File(parentDir, "drivers/chromedriver.exe");
            if (driversDir.exists()) {
                return driversDir.getAbsolutePath();
            }
        }

        return null;
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                driver.quit();
                System.out.println("✅ Браузер закрыт");
            }
        }
    }
}