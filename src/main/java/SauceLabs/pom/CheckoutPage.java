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
public class CheckoutPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement postalCodeInput;

    @FindBy(id = "continue")
    private WebElement continueButton ;

    @FindBy(id = "finish")
    private WebElement finishButton;

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

    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOf(firstNameInput)).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameInput.sendKeys(lastName);
    }

    public void enterPostalCode(String postalCode) {
        postalCodeInput.sendKeys(postalCode);
    }

    public void clickContinueButton() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    public void clickFinishButton() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
    }

    public String getItemTotal() {
        return wait.until(ExpectedConditions.visibilityOf(itemTotal)).getText();
    }

    public boolean isCheckoutSuccessful() {
        return wait.until(ExpectedConditions.visibilityOf(successMessage))
                .getText().equals("THANK YOU FOR YOUR ORDER");
    }

    public String getCheckoutTitle() {
        return wait.until(ExpectedConditions.visibilityOf(checkoutTitle)).getText();
    }
}