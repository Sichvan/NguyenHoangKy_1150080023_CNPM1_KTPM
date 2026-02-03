//package fpoly.junit;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class B3_TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(B3_ErrorCollectorExample.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println("Kết quả tổng: " + (result.wasSuccessful() ? "PASS" : "FAIL"));
    }
}