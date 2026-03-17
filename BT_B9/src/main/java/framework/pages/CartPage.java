package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(xpath = "//button[text()='Remove']")
    private List<WebElement> removeButtons;

    @FindBy(css = ".inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getItemCount() {
        return cartItems.size(); 
    }

    public CartPage removeFirstItem() {
        if (!removeButtons.isEmpty()) {
            waitAndClick(removeButtons.get(0));
        }
        return this;
    }

    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        for (WebElement item : itemNames) {
            names.add(getText(item));
        }
        return names;
    }

    public CheckoutPage goToCheckout() {
        waitAndClick(checkoutButton);
        return new CheckoutPage(driver);
    }
}