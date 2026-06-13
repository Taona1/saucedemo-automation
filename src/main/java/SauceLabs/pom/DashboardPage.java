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
public class DashboardPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = ".app_logo")
    private WebElement appLogo;

    @FindBy(css = ".title")
    private WebElement dashboardTitle;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement addBackpackButton;

    @FindBy(id = "add-to-cart-sauce-labs-bike-light")
    private WebElement addBikeLightButton;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartLink;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    public String getDashboardTitle() {
        return wait.until(ExpectedConditions.visibilityOf(dashboardTitle)).getText();
    }

    public void addBackpackToCart() {
        wait.until(ExpectedConditions.urlContains("inventory"));
        wait.until(ExpectedConditions.elementToBeClickable(addBackpackButton)).click();
    }

    public void addBikeLightToCart() {
        wait.until(ExpectedConditions.urlContains("inventory"));
        wait.until(ExpectedConditions.elementToBeClickable(addBikeLightButton)).click();
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(wait.until(ExpectedConditions.visibilityOf(cartBadge)).getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public void clickShoppingCartIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
    }
}