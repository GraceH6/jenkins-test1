import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class AuthorizationTest {
    WebDriver driver;
    @Test
    void loginAndLogoutTest() {
        String expTitle = "Sample App";
        String userName = "Grace";
        String password = "pwd";
        String expLoginMessage = "Welcome, " + userName + "!";
        String expLogoutMessage = "User logged out.";

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        //настроить для запуска в дженкинсе
        options.addArguments("--headless");
        options.addArguments("--disable-dev-shm-using");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-debugging-port=9222");
        options.addArguments("--disable-setuid-sandbox");
        options.addArguments("--disable-web-security");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://uitestingplayground.com/sampleapp");
        String title = driver.getTitle();
        assertEquals(title, expTitle, "Sample App page is displayed");

        WebElement userNameInput = driver.findElement(By.xpath("//input[@name='UserName']"));
        userNameInput.sendKeys(userName, Keys.ENTER);
        WebElement passwordInput = driver.findElement(By.xpath("//input[@name='Password']"));
        passwordInput.sendKeys(password, Keys.ENTER);
        WebElement logButton = driver.findElement(By.xpath("//button[@id='login']"));
        logButton.click();
        WebElement status = driver.findElement(By.xpath("//label[@id='loginstatus']"));
        String loginMessage = status.getText();
        assertEquals(loginMessage, expLoginMessage, "Welcome page is displayed");

        logButton.click();
        String logoutMessage = status.getText();
        assertEquals(logoutMessage, expLogoutMessage, "User logged out message is displayed");

        driver.quit();
    }
}
