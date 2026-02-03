//package fpoly;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.fail;

public class B2_PersonTest {

    // Cách 1: Dùng ExpectedException Rule (linh hoạt nhất)
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testRuleWay() {
        exception.expect(IllegalArgumentException.class);
        // exception.expectMessage("Invalid age: -5");   // có thể thêm nếu muốn check message
        new B2_Person("Fpoly", -5);
    }

    // Cách 2: Dùng @Test(expected = ...)
    @Test(expected = IllegalArgumentException.class)
    public void testExpectedAnnotationWay() {
        new B2_Person("Fpoly", -1);
    }

    // Cách 3: Dùng try-catch thủ công
    @Test
    public void testTryCatchWay() {
        try {
            new B2_Person("Fpoly", 0);
            fail("Should have thrown IllegalArgumentException because age is invalid!");
        } catch (IllegalArgumentException e) {
            // có thể assert message nếu muốn
            // assertEquals("Invalid age: 0", e.getMessage());
        }
    }
}