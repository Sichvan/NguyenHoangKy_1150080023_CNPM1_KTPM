package api.tests;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UserApiTest extends ApiBaseTest {

    @Test
    public void testGetAllUsers() {
        given(requestSpec).when().get("/users").then().spec(responseSpec).statusCode(200).body("size()", equalTo(10));
    }

    @Test
    public void testGetSingleUser() {
        given(requestSpec).when().get("/users/1").then().spec(responseSpec).statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/UserSchema.json"));
    }
}