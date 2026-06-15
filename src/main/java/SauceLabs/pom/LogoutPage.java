package SauceLabs.pom;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Getter
public class LogoutPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutButton;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    public LogoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    public void clickMenuButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));
    }

    public void clickLogoutButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
    }

    public boolean isLoggedOut() {
        return driver.findElement(By.id("login-button")).isDisplayed();
    }
}
