package SauceLabs;

import SauceLabs.pom.*;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class FirstTest extends BaseTest {

    @Test
    public void testLoginSuccess() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");
        assertTrue(loginPage.isLoginSuccessful(), "Login failed: Expected to be on inventory page");
    }

    @Test
    public void testLoginFailure() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login("invalid_user", "wrong_password");
        assertEquals(loginPage.getErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service",
                "Expected error message not displayed");
    }

    @Test
    public void testDashboardNavigation() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");
        assertTrue(dashboardPage.getDashboardTitle().contains("Products"),
                "Dashboard title does not contain 'Products'");
    }

    @Test
    public void testAddItemsToCart() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");
        dashboardPage.addBackpackToCart();
        dashboardPage.addBikeLightToCart();
        assertEquals(dashboardPage.getCartItemCount(), 2, "Incorrect number of items in cart");
    }

    @Test
    public void testVerifyItemsInCart() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        CartPage cartPage = new CartPage(driver);
        loginPage.navigate();
        loginPage.login ("standard_user", "secret_sauce");
        dashboardPage.addBackpackToCart();
        dashboardPage.addBikeLightToCart();
        dashboardPage.clickShoppingCartIcon();
        assertEquals(cartPage.getCartItemCount(), 2, "Incorrect number of items in cart");
        List<String> itemNames = cartPage.getItemNames();
        List<String> itemPrices = cartPage.getItemPrices();
        assertTrue(itemNames.contains("Sauce Labs Backpack"), "Sauce Labs Backpack not found in cart");
        assertTrue(itemNames.contains("Sauce Labs Bike Light"), "Sauce Labs Bike Light not found in cart");
        assertTrue(itemPrices.contains("$29.99"), "Price $29.99 not found in cart");
        assertTrue(itemPrices.contains("$9.99"), "Price $9.99 not found in cart");
    }

    @Test
    public void testCheckout() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");
        dashboardPage.addBackpackToCart();
        dashboardPage.addBikeLightToCart();
        dashboardPage.clickShoppingCartIcon();
        cartPage.clickCheckoutButton();
        assertTrue(checkoutPage.getCheckoutTitle().contains("Checkout: Your Information"),
                "Checkout: Your Information page not loaded");
        checkoutPage.enterFirstName("John");
        checkoutPage.enterLastName("Doe");
        checkoutPage.enterPostalCode("12345");
        checkoutPage.clickContinueButton();
        assertTrue(checkoutPage.getCheckoutTitle().contains("Checkout: Overview"),
                "Checkout: Overview page not loaded");
        assertTrue(checkoutPage.getItemTotal().contains("Item total: $39.98"),
                "Incorrect item total");
    }

    @Test
    public void testFinishCheckout() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");
        dashboardPage.addBackpackToCart();
        dashboardPage.addBikeLightToCart();
        dashboardPage.clickShoppingCartIcon();
        cartPage.clickCheckoutButton();
        checkoutPage.enterFirstName("John");
        checkoutPage.enterLastName("Doe");
        checkoutPage.enterPostalCode("12345");
        checkoutPage.clickContinueButton();
        checkoutPage.clickFinishButton();
        assertTrue(checkoutPage.isCheckoutSuccessful(), "Checkout not successful: Success message not displayed");
    }

    @Test
    public void testEndToEndFlow() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        LogoutPage logoutPage = new LogoutPage(driver);

        // Login
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");
        assertTrue(loginPage.isLoginSuccessful(), "Login failed");

        // Add items to cart
        dashboardPage.addBackpackToCart();
        dashboardPage.addBikeLightToCart();
        assertEquals(dashboardPage.getCartItemCount(), 2, "Incorrect number of items in cart");

        // Verify cart
        dashboardPage.clickShoppingCartIcon();
        assertEquals(cartPage.getCartItemCount(), 2, "Incorrect number of items in cart page");
        List<String> itemNames = cartPage.getItemNames();
        List<String> itemPrices = cartPage.getItemPrices();
        assertTrue(itemNames.contains("Sauce Labs Backpack"), "Sauce Labs Backpack not found");
        assertTrue(itemNames.contains("Sauce Labs Bike Light"), "Sauce Labs Bike Light not found");
        assertTrue(itemPrices.contains("$29.99"), "Price $29.99 not found");
        assertTrue(itemPrices.contains("$9.99"), "Price $9.99 not found");

        // Checkout
        cartPage.clickCheckoutButton();
        checkoutPage.enterFirstName("John");
        checkoutPage.enterLastName("Doe");
        checkoutPage.enterPostalCode("12345");
        checkoutPage.clickContinueButton();
        assertTrue(checkoutPage.getItemTotal().contains("Item total: $39.98"), "Incorrect item total");

        // Finish checkout
        checkoutPage.clickFinishButton();
        assertTrue(checkoutPage.isCheckoutSuccessful(), "Checkout not successful");

        // Logout
        logoutPage.clickMenuButton();
        logoutPage.clickLogoutButton();
        assertTrue(logoutPage.isLoggedOut(), "Logout failed: Login button not displayed");
    }
}