package tests;

import framework.base.BaseTest;
import framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginSuccess() {
        getDriver().get("https://www.saucedemo.com");
        LoginPage loginPage = new LoginPage(getDriver());
        
        boolean isInventoryLoaded = loginPage.login("standard_user", "secret_sauce").isLoaded();
        Assert.assertTrue(isInventoryLoaded, "Đăng nhập không thành công, trang Inventory chưa load.");
    }

    @Test
    public void testLoginLockedOutUser() {
        getDriver().get("https://www.saucedemo.com");
        LoginPage loginPage = new LoginPage(getDriver());
        
        loginPage.loginExpectingFailure("locked_out_user", "secret_sauce");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Lỗi không hiển thị");
        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"), "Sai câu thông báo lỗi");
    }

    @Test
    public void testLoginEmptyPassword() {
        getDriver().get("https://www.saucedemo.com");
        new LoginPage(getDriver()).loginExpectingFailure("standard_user", "");
        Assert.assertTrue(new LoginPage(getDriver()).getErrorMessage().contains("Password is required"));
    }
}