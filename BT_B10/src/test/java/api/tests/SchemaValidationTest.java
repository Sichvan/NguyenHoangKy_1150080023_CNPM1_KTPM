package api.tests;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SchemaValidationTest extends ApiBaseTest {

    @Test(description = "1. Validate cấu trúc danh sách bài đăng (GET /posts)")
    public void testPostListSchema() {
        given(requestSpec)
        .when()
            .get("/posts")
        .then()
            .spec(responseSpec)
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/post-list-schema.json"));
    }

    @Test(description = "2. Validate cấu trúc một bài đăng (GET /posts/1)")
    public void testSinglePostSchema() {
        given(requestSpec)
        .when()
            .get("/posts/1")
        .then()
            .spec(responseSpec)
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/post-schema.json"));
    }

    @Test(description = "3. Demo cố tình làm FAIL test bằng schema sai", expectedExceptions = AssertionError.class)
    public void testSchemaFailDemo() {
        System.out.println("=== CHẠY DEMO FAIL SCHEMA ===");
        given(requestSpec)
        .when()
            .get("/posts/1")
        .then()
            .body(matchesJsonSchemaInClasspath("schemas/post-fail-schema.json"));
    }
}