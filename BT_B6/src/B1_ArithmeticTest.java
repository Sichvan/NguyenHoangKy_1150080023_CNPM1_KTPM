import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class B1_ArithmeticTest {

    String message = "Fpoly exception";
    B1_JunitMessage junitMessage = new B1_JunitMessage(message);

    @Test(expected = ArithmeticException.class)
    public void testPrintMessage() {
        System.out.println("Fpoly JUnit Message exception is printing");
        junitMessage.printMessage();  // sẽ ném ArithmeticException → test pass
    }

    @Test
    public void testPrintHiMessage() {
        String expected = "Hi! " + message;
        System.out.println("Fpoly JUnit Message is printing");
        assertEquals(expected, junitMessage.printHiMessage());
    }
}