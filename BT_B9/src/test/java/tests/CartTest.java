package tests;

import framework.base.BaseTest;
import framework.pages.CartPage;
import framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    @Test
    public void testAddSingleItemToCart() {
        getDriver().get("https://www.saucedemo.com");
        // Đây chính là Fluent Interface: Viết 1 mạch từ Login -> Add Item -> Đi tới Giỏ hàng
        CartPage cartPage = new LoginPage(getDriver())
                .login("standard_user", "secret_sauce")
                .addFirstItemToCart()
                .goToCart();

        Assert.assertEquals(cartPage.getItemCount(), 1, "Số lượng item trong giỏ không đúng");
    }

    @Test
    public void testRemoveItemFromCart() {
        getDriver().get("https://www.saucedemo.com");
        CartPage cartPage = new LoginPage(getDriver())
                .login("standard_user", "secret_sauce")
                .addFirstItemToCart()
                .goToCart()
                .removeFirstItem();

        Assert.assertEquals(cartPage.getItemCount(), 0, "Xóa item không thành công");
    }

    @Test
    public void testEmptyCartCount() {
        getDriver().get("https://www.saucedemo.com");
        CartPage cartPage = new LoginPage(getDriver())
                .login("standard_user", "secret_sauce")
                .goToCart(); // Chuyển thẳng tới giỏ hàng mà không add gì cả

        Assert.assertEquals(cartPage.getItemCount(), 0, "Giỏ hàng trống phải trả về 0");
    }
}