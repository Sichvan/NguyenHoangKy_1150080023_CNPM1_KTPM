package api.tests;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostGetTest extends ApiBaseTest {

    @Test(description = "Test 1: GET /posts (Lấy danh sách bài đăng)")
    public void testGetPosts() {
        given(requestSpec)
        .when()
            .get("/posts")
        .then()
            .spec(responseSpec)
            .statusCode(200)
            .body("size()", greaterThan(0)); 
    }

    @Test(description = "Test 2: GET /posts?userId=1 (Lọc bài đăng theo user)")
    public void testGetPostsByUser() {
        given(requestSpec)
            .queryParam("userId", 1)
        .when()
            .get("/posts")
        .then()
            .spec(responseSpec)
            .statusCode(200)
            .body("userId", everyItem(equalTo(1)))
            .body("id", everyItem(notNullValue()))
            .body("title", everyItem(notNullValue()))
            .body("body", everyItem(notNullValue()));
    }

    @Test(description = "Test 3: GET /posts/1 (Lấy chi tiết 1 bài đăng)")
    public void testGetSinglePost() {
        given(requestSpec)
        .when()
            .get("/posts/1")
        .then()
            .spec(responseSpec)
            .statusCode(200)
            .body("id", equalTo(1))
            .body("userId", equalTo(1))
            .body("title", not(emptyString()));
    }

    @Test(description = "Test 4: GET /posts/9999 (Resource không tồn tại)")
    public void testPostNotFound() {
        given(requestSpec)
        .when()
            .get("/posts/9999")
        .then()
            .statusCode(404)
            .body("$", anEmptyMap()); 
    }
}