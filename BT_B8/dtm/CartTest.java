package dtm;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTest {
    
    @BeforeMethod
    public void setUp() {
        DriverFactory.initDriver("chrome");
    }

    @Test
    public void testCart() throws InterruptedException {
        WebDriver driver = DriverFactory.getDriver();
        driver.get("https://google.com"); // Mở trang khác để dễ phân biệt
        System.out.println("CartTest chạy trên Thread ID: " + Thread.currentThread().getId());
        
        // Dừng 5 giây để kịp chụp ảnh
        Thread.sleep(5000);
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}