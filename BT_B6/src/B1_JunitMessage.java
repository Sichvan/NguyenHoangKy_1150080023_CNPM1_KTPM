public class B1_JunitMessage {
    private String message;

    public B1_JunitMessage(String message) {
        this.message = message;
    }

    public void printMessage() {
        System.out.println(message);
        int divide = 1 / 0;  // cố ý gây ArithmeticException
    }

    public String printHiMessage() {
        message = "Hi! " + message;
        System.out.println(message);
        return message;
    }
}