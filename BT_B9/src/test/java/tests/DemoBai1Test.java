package tests;

import framework.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoBai1Test extends BaseTest {

    @Test
    public void testMoTrinhDuyetThanhCong() {
        // Mở một trang web bất kỳ
        getDriver().get("https://www.google.com");
        System.out.println("Đã mở trang: " + getDriver().getTitle());
        
        // Assert true để test pass
        Assert.assertTrue(true);
    }

    @Test
    public void testCoTinhFailDeChupAnh() {
        getDriver().get("https://www.saucedemo.com");
        
        // Cố tình fail để kích hoạt hàm chụp ảnh trong BaseTest
        System.out.println("Đang cố tình đánh rớt test này...");
        Assert.fail("Cố tình báo lỗi để xem BaseTest có chụp ảnh màn hình lưu vào target/screenshots/ không!");
    }
}