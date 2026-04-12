package ru.andrmuratov.NauJava.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import ru.andrmuratov.NauJava.entity.User;
import ru.andrmuratov.NauJava.repository.UserRepository;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class LoginUiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private WebDriver driver;
    private WebDriverWait wait;
    private String baseUrl;
    private User testUser;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        baseUrl = "http://localhost:" + port;
        driver.manage().window().maximize();

        // Создаем тестового пользователя
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword(passwordEncoder.encode("testpass123"));
        testUser.setEmail("test@example.com");
        testUser.setRole("USER");
        userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        // Очищаем тестовые данные
        if (testUser != null && testUser.getId() != null) {
            userRepository.delete(testUser);
        }
    }

    @Test
    void testSuccessfulLogin() {
        driver.get(baseUrl + "/login");

        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        usernameInput.clear();
        usernameInput.sendKeys("testuser");

        passwordInput.clear();
        passwordInput.sendKeys("testpass123");

        loginButton.click();

        wait.until(ExpectedConditions.urlContains("/users/list"));

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/users/list"), "Should be redirected to user list page");

        WebElement pageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        assertEquals("User List", pageTitle.getText());
    }

    @Test
    void testLogout() {
        // Сначала входим
        driver.get(baseUrl + "/login");

        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        usernameInput.sendKeys("testuser");
        passwordInput.sendKeys("testpass123");
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("/users/list"));

        // Ищем кнопку logout (обычно это форма с кнопкой)
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[type='submit']")
        ));

        logoutButton.click();

        wait.until(ExpectedConditions.urlContains("/login"));

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/login"), "Should be redirected to login page");
    }

    @Test
    void testLoginPageLoads() {
        driver.get(baseUrl + "/login");

        String pageTitle = driver.getTitle();
        assertTrue(pageTitle.contains("Login") || pageTitle.contains("login"), "Page should have login in title");

        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordInput = driver.findElement(By.name("password"));

        assertNotNull(usernameInput, "Username input should be present");
        assertNotNull(passwordInput, "Password input should be present");
    }
}