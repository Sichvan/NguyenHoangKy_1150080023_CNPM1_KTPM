//package fpoly.junit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.junit.Assert.assertTrue;

public class B3_ErrorCollectorExample {

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testWithMultipleErrors() {
        collector.addError(new Throwable("Lỗi dòng 1: giá trị không đúng"));
        collector.addError(new Throwable("Lỗi dòng 2: kết quả âm"));

        System.out.println("Hello");

        try {
            assertTrue("A" == "B");  // thất bại → lỗi được thu thập
        } catch (Throwable t) {
            collector.addError(t);
        }

        System.out.println("world!!!!");

        // test vẫn chạy hết, lỗi được báo hết ở cuối
    }
}