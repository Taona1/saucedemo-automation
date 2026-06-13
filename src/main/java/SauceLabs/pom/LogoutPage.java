package SauceLabs.pom;

import lombok.Getter;
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
        wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();
    }

    public void clickLogoutButton() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }

    public boolean isLoggedOut() {
        return wait.until(ExpectedConditions.visibilityOf(loginButton)).isDisplayed();
    }
}
