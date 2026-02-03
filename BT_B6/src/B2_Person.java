public class B2_Person {
    private final String name;
    private final int age;

    public B2_Person(String name, int age) {
        this.name = name;
        this.age = age;
        if (age <= 0) {
            throw new IllegalArgumentException("Invalid age: " + age);
        }
    }

    // getter nếu cần (không bắt buộc cho lab)
    public String getName() { return name; }
    public int getAge() { return age; }
}