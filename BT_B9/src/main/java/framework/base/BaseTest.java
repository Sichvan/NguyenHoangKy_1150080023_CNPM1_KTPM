package framework.base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class BaseTest {
    // Dùng ThreadLocal để hỗ trợ chạy song song an toàn, không dùng static biến thường
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    protected WebDriver getDriver() {
        return tlDriver.get();
    }

    // Nhận 2 tham số browser và env từ file testng.xml, có giá trị mặc định bằng @Optional
    @Parameters({"browser", "env"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser, @Optional("dev") String env) {
        System.setProperty("env", env);

        WebDriver driver;
        if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        
        tlDriver.set(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        // Chụp ảnh màn hình nếu test bị FAIL
        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshot(result.getName());
        }

        if (getDriver() != null) {
            getDriver().quit();
            tlDriver.remove(); // Tránh memory leak khi chạy song song
        }
    }

    // Hàm hỗ trợ chụp ảnh màn hình lưu vào target/screenshots/
    private void takeScreenshot(String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String filePath = System.getProperty("user.dir") + "/target/screenshots/" + testName + "_" + timestamp + ".png";
        
        try {
            FileHandler.copy(srcFile, new File(filePath));
            System.out.println("Đã lưu ảnh màn hình lỗi tại: " + filePath);
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu ảnh chụp màn hình: " + e.getMessage());
        }
    }
}