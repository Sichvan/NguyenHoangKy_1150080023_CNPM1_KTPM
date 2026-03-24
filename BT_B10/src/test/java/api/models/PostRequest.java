package api.models;

public class PostRequest {
    private String title;
    private String body;
    private int userId;

    public PostRequest(String title, String body, int userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    // Các hàm Getters bắt buộc phải có để thư viện Jackson hoạt động
    public String getTitle() { return title; }
    public String getBody() { return body; }
    public int getUserId() { return userId; }
}