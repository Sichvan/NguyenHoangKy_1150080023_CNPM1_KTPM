package api.tests;

import api.models.PostRequest;
import api.models.PostResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostCrudTest extends ApiBaseTest {

    private int newPostId; 

    @Test(description = "1. POST - Tạo mới bài đăng")
    public void testCreatePost() {
        PostRequest newPost = new PostRequest("Tiêu đề bài viết", "Nội dung bài viết", 1);
        PostResponse response = given(requestSpec)
            .body(newPost) // Jackson tự động chuyển POJO thành JSON
        .when()
            .post("/posts")
        .then()
            .spec(responseSpec)
            .statusCode(201) // Status 201 Created
            .body("title", equalTo("Tiêu đề bài viết"))
            .body("id", notNullValue())
            .extract().as(PostResponse.class); // Map JSON vào POJO

        // Lưu ID lại để dùng cho kịch bản Chain API
        newPostId = response.getId();
        System.out.println("Đã tạo thành công Post có ID: " + newPostId);
    }

    @Test(description = "2. PUT - Cập nhật toàn bộ bài đăng")
    public void testUpdatePost() {
        PostRequest updatedPost = new PostRequest("Tiêu đề đã PUT", "Nội dung cập nhật", 1);

        given(requestSpec)
            .body(updatedPost)
        .when()
            .put("/posts/1") 
        .then()
            .spec(responseSpec)
            .statusCode(200)
            .body("title", equalTo("Tiêu đề đã PUT"))
            .body("body", equalTo("Nội dung cập nhật"));
    }

    @Test(description = "3. PATCH - Cập nhật một phần bài đăng")
    public void testPatchPost() {
        // Dùng Map để gửi 1 phần dữ liệu (chỉ gửi title)
        Map<String, String> partialData = new HashMap<>();
        partialData.put("title", "Tiêu đề đổi qua PATCH");

        given(requestSpec)
            .body(partialData)
        .when()
            .patch("/posts/1")
        .then()
            .spec(responseSpec)
            .statusCode(200)
            .body("title", equalTo("Tiêu đề đổi qua PATCH"));
    }

    @Test(description = "4. DELETE - Xóa bài đăng")
    public void testDeletePost() {
        given(requestSpec)
        .when()
            .delete("/posts/1")
        .then()
            // Yêu cầu gốc là 204, nhưng JSONPlaceholder trả 200 cho hàm DELETE
            .statusCode(200) 
            .body("$", anEmptyMap()); // Kiểm tra response body rỗng hoàn toàn
    }

    @Test(description = "5. API Chaining: POST -> GET xác nhận", dependsOnMethods = "testCreatePost")
    public void testPostAndGetChain() {
        System.out.println("Thực hiện gọi GET để kiểm tra ID vừa tạo: " + newPostId);
        
        // GHI CHÚ QUAN TRỌNG VÀO BÁO CÁO: 
        // JSONPlaceholder là API giả lập, nó nhận request POST và trả về ID=101 nhưng KHÔNG thực sự lưu vào Database.
        // Do đó, khi gọi GET /posts/101 sẽ bị trả về 404 (Not Found). 
        // Code ở đây kiểm tra status 404 để thể hiện đúng bản chất của fake API này.
        given(requestSpec)
        .when()
            .get("/posts/" + newPostId)
        .then()
            .statusCode(404); 
    }
}