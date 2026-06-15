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
public class CheckoutPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(className = "summary_subtotal_label")
    private WebElement itemTotal;

    @FindBy(className = "complete-header")
    private WebElement successMessage;

    @FindBy(css = ".title")
    private WebElement checkoutTitle;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    private void setReactInput(String fieldId, String value) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(fieldId)));
        ((JavascriptExecutor) driver).executeScript(
            "var setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
            "setter.call(arguments[0], arguments[1]);" +
            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
            field, value
        );
    }

    public void enterFirstName(String firstName) {
        setReactInput("first-name", firstName);
    }

    public void enterLastName(String lastName) {
        setReactInput("last-name", lastName);
    }

    public void enterPostalCode(String postalCode) {
        setReactInput("postal-code", postalCode);
    }

    public void clickContinueButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.id("continue")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
    }

    public void clickFinishButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.id("finish")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        wait.until(ExpectedConditions.urlContains("checkout-complete.html"));
    }

    public String getItemTotal() {
        return wait.until(ExpectedConditions.visibilityOf(itemTotal)).getText();
    }

    public boolean isCheckoutSuccessful() {
        return wait.until(ExpectedConditions.visibilityOf(successMessage))
                .getText().contains("Thank you");
    }

    public String getCheckoutTitle() {
        return wait.until(ExpectedConditions.visibilityOf(checkoutTitle)).getText();
    }
}
