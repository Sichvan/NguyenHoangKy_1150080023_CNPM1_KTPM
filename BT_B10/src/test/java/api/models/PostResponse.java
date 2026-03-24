package api.models;

public class PostResponse {
    private int id;
    private String title;
    private String body;
    private int userId;

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getBody() { return body; }
    public int getUserId() { return userId; }
}