package api.tests;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
public class PerformanceSLATest {

    private RequestSpecification reqresSpec;

    @BeforeClass
    public void setup() {
        reqresSpec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in")
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .addHeader("User-Agent", "PostmanRuntime/7.36.3") 
                .build();
    }
    @DataProvider(name = "slaData")
    public Object[][] slaData() {
        return new Object[][] {
            {"GET", "/users", null, 2000L, 200, "CHECK_SIZE"},
            {"GET", "/users/2", null, 1500L, 200, "CHECK_ID"},
            {"POST", "/users", "{\"name\":\"morpheus\",\"job\":\"leader\"}", 3000L, 201, "CHECK_CREATED_ID"},
            {"POST", "/login", "{\"email\":\"eve.holt@reqres.in\",\"password\":\"cityslicka\"}", 2000L, 200, "CHECK_TOKEN"},
            {"DELETE", "/users/2", null, 1000L, 204, "CHECK_EMPTY"}
        };
    }

    @Test(dataProvider = "slaData", description = "Kiểm thử SLA cho 5 API chính")
    public void testSlaForEndpoints(String method, String endpoint, String payload, long maxMs, int expectedStatus, String validationRule) {
        executeAndValidateSLA(method, endpoint, payload, maxMs, expectedStatus, validationRule);
    }
    @Step("Gọi {method} {endpoint} - SLA: {maxMs}ms")
    public void executeAndValidateSLA(String method, String endpoint, String payload, long maxMs, int expectedStatus, String validationRule) {
        long startTime = System.currentTimeMillis();
        ValidatableResponse response;
        if (method.equalsIgnoreCase("GET")) {
            response = given(reqresSpec).when().get(endpoint).then();
        } else if (method.equalsIgnoreCase("POST")) {
            response = given(reqresSpec).body(payload).when().post(endpoint).then();
        } else {
            response = given(reqresSpec).when().delete(endpoint).then();
        }

        long actualTime = System.currentTimeMillis() - startTime;
        
        System.out.println(String.format("[SLA Monitor] %s %s - Thời gian thực tế: %d ms (Cho phép: %d ms)", method, endpoint, actualTime, maxMs));
        response.statusCode(expectedStatus)
                .time(lessThan(maxMs)); 
        switch (validationRule) {
            case "CHECK_SIZE":
                response.body("data.size()", greaterThanOrEqualTo(1));
                break;
            case "CHECK_ID":
                response.body("data.id", equalTo(2));
                break;
            case "CHECK_CREATED_ID":
                response.body("id", notNullValue());
                break;
            case "CHECK_TOKEN":
                response.body("token", notNullValue());
                break;
            case "CHECK_EMPTY":
                response.body("$", anEmptyMap()); 
                break;
        }
    }
    @Test(description = "Chạy API 10 lần liên tiếp để tính average/min/max response time")
    public void testMonitoringSimulation() {
        System.out.println("=== BẮT ĐẦU CHẠY MONITORING 10 LẦN CHO GET /api/users ===");
        List<Long> responseTimes = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            long startTime = System.currentTimeMillis();
            given(reqresSpec).when().get("/users").then();          
            long duration = System.currentTimeMillis() - startTime;
            responseTimes.add(duration);
            System.out.println("Lần " + i + ": " + duration + " ms");
        }
        long max = Collections.max(responseTimes);
        long min = Collections.min(responseTimes);
        double avg = responseTimes.stream().mapToLong(val -> val).average().orElse(0.0);

        System.out.println("--- KẾT QUẢ MONITORING ---");
        System.out.println("Min Response Time: " + min + " ms");
        System.out.println("Max Response Time: " + max + " ms");
        System.out.println("Average Response Time: " + avg + " ms");
    }
}