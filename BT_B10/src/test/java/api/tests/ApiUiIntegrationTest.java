package api.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static io.restassured.RestAssured.given;

public class ApiUiIntegrationTest {

    private WebDriver driver;
    private String apiToken = "";
    private boolean isApiAlive = false;

    // =========================================================
    // PHẦN A - API PRECONDITION -> UI VERIFICATION (1.0 ĐIỂM)
    // =========================================================

    @Test(description = "API Precondition: Gọi POST /login lấy token")
    public void testApiLoginPrecondition() {
        System.out.println("--- BƯỚC API: Gọi POST /login để lấy token ---");
        String body = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"cityslicka\"}";

        Response response = given()
                .baseUri("https://reqres.in")
                .contentType(ContentType.JSON)
                .body(body)
            .when()
                .post("/api/login");

        // Nếu API fail (khác 200), TestNG sẽ đánh FAIL test này, 
        // kéo theo test UI bên dưới bị SKIP tự động do dependsOnMethods.
        Assert.assertEquals(response.statusCode(), 200, "API Login thất bại!");

        apiToken = response.jsonPath().getString("token");
        System.out.println("Token lấy được từ API: " + apiToken);
    }

    @Test(description = "UI Verification: Đăng nhập SauceDemo", dependsOnMethods = "testApiLoginPrecondition")
    public void testUiLoginSauceDemo() {
        System.out.println("--- BƯỚC UI: Đăng nhập SauceDemo vì API Precondition đã Pass ---");
        setupWebDriver();

        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Verify URL và Title
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Lỗi: URL không chứa chữ inventory");
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Lỗi: Title trang không đúng");
    }

    // =========================================================
    // PHẦN B - LUỒNG TÍCH HỢP ĐẦY ĐỦ (1.0 ĐIỂM)
    // =========================================================

    @Test(description = "Kiểm tra API reqres.in còn sống không")
    public void checkApiAlive() {
        System.out.println("--- BƯỚC API: Kiểm tra Health Check GET /users ---");
        Response response = given()
                .baseUri("https://reqres.in")
            .when()
                .get("/api/users");

        if (response.statusCode() == 200) {
            isApiAlive = true;
            System.out.println("Xác nhận: API đang hoạt động tốt!");
        } else {
            System.out.println("Cảnh báo: API đang lỗi rổi!");
        }
    }

    @Test(description = "UI Test: Thêm 2 sản phẩm vào giỏ hàng", dependsOnMethods = "checkApiAlive")
    public void testFullUiFlow() {
        // Đánh dấu SKIP nếu API chết theo đúng yêu cầu đề bài
        if (!isApiAlive) {
            throw new SkipException("BỎ QUA TEST UI VÌ API ĐÃ CHẾT (isApiAlive = false)");
        }

        System.out.println("--- BƯỚC UI ACTION: Đăng nhập và mua hàng trên SauceDemo ---");
        setupWebDriver();
        driver.get("https://www.saucedemo.com/");

        // 1. Đăng nhập
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // 2. Thêm 2 sản phẩm
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();

        System.out.println("--- BƯỚC ASSERTION: Xác minh trên giao diện UI ---");
        
        // 3. Kiểm tra badge = 2
        WebElement badge = driver.findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals(badge.getText(), "2", "Số lượng hiển thị trên giỏ hàng không đúng!");

        // 4. Vào giỏ hàng xác nhận có 2 sản phẩm
        driver.findElement(By.className("shopping_cart_link")).click();
        int cartItems = driver.findElements(By.className("cart_item")).size();
        Assert.assertEquals(cartItems, 2, "Số lượng sản phẩm thực tế trong giỏ không đúng!");
    }

    // =========================================================
    // CÁC HÀM TIỆN ÍCH DÙNG CHUNG CHO SELENIUM
    // =========================================================

    private void setupWebDriver() {
        if (driver == null) {
            driver = new ChromeDriver(); // Selenium 4 tự động tìm ChromeDriver
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.manage().window().maximize();
        }
    }

    @AfterMethod
    public void tearDown() {
        // Tắt trình duyệt sau mỗi test UI để tránh lag máy
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}