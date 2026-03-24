package api.tests;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CommentApiTest extends ApiBaseTest {

    @Test
    public void testGetCommentsForPost() {
        given(requestSpec).when().get("/posts/1/comments").then().spec(responseSpec).statusCode(200)
            .body("size()", equalTo(5))
            .body("postId", everyItem(equalTo(1)))
            .body("id", everyItem(notNullValue()))
            .body("name", everyItem(notNullValue()))
            .body("email", everyItem(notNullValue()))
            .body("body", everyItem(notNullValue()));
    }
}