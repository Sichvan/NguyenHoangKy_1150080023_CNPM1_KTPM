package dtm;
import org.testng.annotations.Test;

public class CheckoutTest {
    @Test(groups = {"smoke", "regression"})
    public void testCheckoutSuccess() { 
        System.out.println("Chạy CheckoutTest -> testCheckoutSuccess (smoke)"); 
    }

    @Test(groups = {"regression"})
    public void testCheckoutMissingInfo() { 
        System.out.println("Chạy CheckoutTest -> testCheckoutMissingInfo (regression)"); 
    }
}