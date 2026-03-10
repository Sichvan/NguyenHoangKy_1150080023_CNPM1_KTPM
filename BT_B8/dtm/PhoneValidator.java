package dtm;

public class PhoneValidator {
    public static boolean isValid(String phone) {
        // N1: Kiểm tra null hoặc rỗng
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        
        // N2: Chỉ cho phép số, dấu cộng (+) và khoảng trắng
        if (!phone.matches("^[0-9+\\s]+$")) {
            return false;
        }
        
        // Chuẩn hóa: Xóa khoảng trắng
        String normalized = phone.replace(" ", "");
        
        // Chuẩn hóa: Đổi +84 ở đầu thành số 0
        if (normalized.startsWith("+84")) {
            normalized = "0" + normalized.substring(3);
        }
        
        // N3: Kiểm tra độ dài phải đúng 10 số
        if (normalized.length() != 10) {
            return false;
        }
        
        // N4: Kiểm tra đầu số hợp lệ (03, 05, 07, 08, 09)
        if (normalized.matches("^(03|05|07|08|09)[0-9]{8}$")) {
            return true;
        }
        
        return false;
    }
}