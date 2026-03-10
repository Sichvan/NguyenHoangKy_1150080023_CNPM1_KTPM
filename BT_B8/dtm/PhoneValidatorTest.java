package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PhoneValidatorTest {

    @Test(description = "TC1 (N1-T): Chuỗi null hoặc rỗng")
    public void testNullOrEmpty() {
        Assert.assertFalse(PhoneValidator.isValid(null));
        Assert.assertFalse(PhoneValidator.isValid("   "));
    }

    @Test(description = "TC2 (N2-T): Chứa ký tự không hợp lệ")
    public void testInvalidChars() {
        Assert.assertFalse(PhoneValidator.isValid("0912abc345"));
        Assert.assertFalse(PhoneValidator.isValid("0912-345-678"));
    }

    @Test(description = "TC3 (N3-T): Sai độ dài sau khi chuẩn hóa")
    public void testInvalidLength() {
        Assert.assertFalse(PhoneValidator.isValid("091234567")); // 9 số
        Assert.assertFalse(PhoneValidator.isValid("+84 912 345 6789")); // 11 số
    }

    @Test(description = "TC4 (N4-F): Sai đầu số mạng")
    public void testInvalidPrefix() {
        Assert.assertFalse(PhoneValidator.isValid("0212345678")); // Đầu 02
        Assert.assertFalse(PhoneValidator.isValid("0412345678")); // Đầu 04
    }

    @Test(description = "TC5 (N4-T): Số điện thoại hợp lệ (Các trường hợp biên)")
    public void testValidPhone() {
        Assert.assertTrue(PhoneValidator.isValid("0912345678")); // Chuẩn
        Assert.assertTrue(PhoneValidator.isValid("+84 912 345 678")); // Có +84 và khoảng trắng
        Assert.assertTrue(PhoneValidator.isValid("035 123 4567")); // Đầu 03, có khoảng trắng
    }
}