package SauceLabs.pom;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
@Getter
public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = ".cart_item .inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(css = ".cart_item .inventory_item_price")
    private List<WebElement> itemPrices;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public int getCartItemCount() {
        return wait.until(ExpectedConditions.visibilityOfAllElements(cartItems)).size();
    }

    public List<String> getItemNames() {
        return wait.until(ExpectedConditions.visibilityOfAllElements(itemNames))
                .stream().map(WebElement::getText).toList();
    }

    public List<String> getItemPrices() {
        return wait.until(ExpectedConditions.visibilityOfAllElements(itemPrices))
                .stream().map(WebElement::getText).toList();
    }

    public void clickCheckoutButton() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }
}