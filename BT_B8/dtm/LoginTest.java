package dtm;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
    
    @BeforeMethod
    public void setUp() {
        DriverFactory.initDriver("chrome");
    }

    @Test
    public void testLogin() throws InterruptedException {
        WebDriver driver = DriverFactory.getDriver();
        driver.get("https://www.saucedemo.com");
        System.out.println("LoginTest chạy trên Thread ID: " + Thread.currentThread().getId());
        
        // Dừng 5 giây để kịp chụp ảnh 2 trình duyệt mở cùng lúc
        Thread.sleep(5000); 
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}