package dtm.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.time.Duration;

public abstract class BaseTest {
    
    // Khai báo ThreadLocal<WebDriver> đề hỗ trợ parallel execution (chạy song song nhiều trình duyệt)
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeMethod
    public void setUp(Method method) {
        // Khởi tạo ChromeDriver qua WebDriverManager
        WebDriverManager.chromedriver().setup();
        WebDriver webDriver = new ChromeDriver();
        
        // Maximize window, set implicit wait 10s
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // Lưu driver vào luồng hiện tại
        driver.set(webDriver);

        // Ghi log tên test đang chạy
        System.out.println("[START] Đang chạy test: " + method.getName());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Nếu result là FAILURE chụp screenshot lưu vào /screenshots/
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("[FAIL] Test thất bại: " + result.getName() + " -> Sẽ tiến hành chụp màn hình.");
            // (Phần code chụp màn hình thực tế thường được hướng dẫn ở bài nâng cao, hiện tại mình log ra để đánh dấu)
        }
        
        // Đóng driver, remove ThreadLocal
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
            System.out.println("[END] Đã đóng trình duyệt an toàn.");
        }
    }

    public WebDriver getDriver() {
        // Trả về driver của thread hiện tại
        return driver.get();
    }
}