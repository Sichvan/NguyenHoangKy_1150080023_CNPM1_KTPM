package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

public class VayVonMCDCTest {

    @Test(description = "Row 2: Base Case (T-T-T-F) -> Đủ tuổi, đủ thu nhập, có tài sản, tín dụng thấp")
    public void testMCDC_BaseCase_ThoaManDieuKien() {
        boolean result = NganHang.duDieuKienVay(25, 15000000, true, 600);
        Assert.assertTrue(result, "Lỗi: Khách hàng đủ điều kiện cơ bản và có tài sản bảo lãnh nhưng bị từ chối!");
    }

    @Test(description = "Row 3: (T-T-F-T) -> Chứng minh Tín dụng (D) độc lập. Không tài sản nhưng tín dụng cao")
    public void testMCDC_TinDungDocLap_KhongTaiSan() {
        boolean result = NganHang.duDieuKienVay(25, 15000000, false, 750);
        Assert.assertTrue(result, "Lỗi: Khách hàng không có tài sản nhưng điểm tín dụng cao vẫn bị từ chối!");
    }

    @Test(description = "Row 4: (T-T-F-F) -> Chứng minh Tài sản (C) độc lập khi so với Row 2")
    public void testMCDC_TaiSanDocLap_KhongTaiSan_TinDungThap() {
        boolean result = NganHang.duDieuKienVay(25, 15000000, false, 600);
        Assert.assertFalse(result, "Lỗi: Khách hàng không có tài sản và tín dụng thấp nhưng lại được duyệt!");
    }

    @Test(description = "Row 6: (T-F-T-F) -> Chứng minh Thu nhập (B) độc lập khi so với Row 2")
    public void testMCDC_ThuNhapDocLap_ThapHon10Tr() {
        boolean result = NganHang.duDieuKienVay(25, 5000000, true, 600);
        Assert.assertFalse(result, "Lỗi: Khách hàng thu nhập dưới 10 triệu nhưng lại được duyệt!");
    }

    @Test(description = "Row 10: (F-T-T-F) -> Chứng minh Tuổi (A) độc lập khi so với Row 2")
    public void testMCDC_TuoiDocLap_ThapHon22() {
        boolean result = NganHang.duDieuKienVay(20, 15000000, true, 600);
        Assert.assertFalse(result, "Lỗi: Khách hàng dưới 22 tuổi nhưng lại được duyệt!");
    }
}