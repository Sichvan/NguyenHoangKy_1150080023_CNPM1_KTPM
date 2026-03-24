package api.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthAndErrorTest {
    private RequestSpecification reqresSpec;
    @BeforeClass
    public void setupReqres() {
        reqresSpec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in")
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .addHeader("User-Agent", "PostmanRuntime/7.36.3") // Né Cloudflare
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    @Test(description = "Test login thành công -> 200, có token")
    public void testLoginSuccess() {
        String body = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"cityslicka\"}";
        given(reqresSpec)
            .body(body)
        .when()
            .post("/login")
        .then()
            .statusCode(200)
            .body("token", notNullValue());
    }

    @Test(description = "Test login thiếu password -> 400, báo lỗi")
    public void testLoginMissingPassword() {
        String body = "{\"email\":\"eve.holt@reqres.in\"}";
        given(reqresSpec)
            .body(body)
        .when()
            .post("/login")
        .then()
            .statusCode(400)
            .body("error", equalTo("Missing password"));
    }

    @Test(description = "Test register thành công -> 200, có id và token")
    public void testRegisterSuccess() {
        String body = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"pistol\"}";
        given(reqresSpec)
            .body(body)
        .when()
            .post("/register")
        .then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("token", notNullValue());
    }

    @Test(description = "Test register thiếu password -> 400, báo lỗi")
    public void testRegisterMissingPassword() {
        String body = "{\"email\":\"sydney@fife\"}";
        given(reqresSpec)
            .body(body)
        .when()
            .post("/register")
        .then()
            .statusCode(400)
            .body("error", equalTo("Missing password"));
    }
    @DataProvider(name = "loginScenarios")
    public Object[][] loginScenarios() {
        return new Object[][] {
                // {email, password, expectedStatus, expectedError}
                {"eve.holt@reqres.in", "cityslicka", 200, null},
                {"eve.holt@reqres.in", "", 400, "Missing password"},
                {"", "cityslicka", 400, "Missing email or username"},
                {"notexist@reqres.in", "wrongpass", 400, "user not found"},
                {"invalid-email", "pass123", 400, "user not found"}
        };
    }

    @Test(dataProvider = "loginScenarios", description = "Kiểm thử Data-Driven cho nhiều kịch bản Login")
    public void testLoginScenarios(String email, String password, int expectedStatus, String expectedError) {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        if (!password.isEmpty()) {
            body.put("password", password);
        }

        ValidatableResponse response = given(reqresSpec)
            .body(body)
        .when()
            .post("/login")
        .then()
            .statusCode(expectedStatus);
        if (expectedError != null) {
            response.body("error", containsString(expectedError));
        }
    }
}