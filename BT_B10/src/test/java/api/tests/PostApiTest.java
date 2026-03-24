package api.tests;

import api.models.PostRequest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PostApiTest extends ApiBaseTest {

    @Test
    public void testGetAllPosts() {
        given(requestSpec).when().get("/posts").then().spec(responseSpec)
            .statusCode(200).body("size()", equalTo(100));
    }

    @Test
    public void testGetSinglePost() {
        given(requestSpec).when().get("/posts/1").then().spec(responseSpec)
            .statusCode(200).body(matchesJsonSchemaInClasspath("schemas/PostSchema.json"));
    }

    @Test
    public void testCreatePost() {
        PostRequest newPost = new PostRequest("title test", "body test", 1);
        given(requestSpec).body(newPost).when().post("/posts").then().spec(responseSpec)
            .statusCode(201).body("id", notNullValue());
    }

    @Test
    public void testUpdatePost() {
        PostRequest updatePost = new PostRequest("title updated", "body updated", 1);
        given(requestSpec).body(updatePost).when().put("/posts/1").then().spec(responseSpec)
            .statusCode(200).body("title", equalTo("title updated"));
    }

    @Test
    public void testDeletePost() {
        given(requestSpec).when().delete("/posts/1").then().spec(responseSpec).statusCode(200);
    }
}