package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {


    @FindBy(id = "user-name")
    private WebElement usernameField; 

    @FindBy(id = "password")
    private WebElement passwordField; 

    @FindBy(id="login-button")
    private WebElement loginButton; 

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) { 
        super(driver); 
    }

    public InventoryPage login(String username, String password) {
        waitAndType(usernameField, username); // [cite: 232]
        waitAndType(passwordField, password); // [cite: 233]
        waitAndClick(loginButton); // [cite: 234]
        return new InventoryPage(driver); // [cite: 235]
    }

    public LoginPage loginExpectingFailure(String username, String password) {
        waitAndType(usernameField, username); // [cite: 239]
        waitAndType(passwordField, password); // [cite: 240]
        waitAndClick(loginButton); // [cite: 244]
        return this; // [cite: 245]
    }

    public String getErrorMessage() {
        return getText(errorMessage); // [cite: 247]
    }

    public boolean isErrorDisplayed() {
        return isElementVisible(By.cssSelector("[data-test='error']")); // [cite: 250]
    }
}