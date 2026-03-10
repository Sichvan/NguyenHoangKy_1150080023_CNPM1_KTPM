package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PhiShipBasisPathTest {

    @Test(description = "Path 1 (Baseline): Nội thành, <= 2kg, Không member")
    public void testPath1_Baseline() {
        double expected = 15000;
        Assert.assertEquals(PhiShip.tinhPhiShip(1, "noi_thanh", false), expected, 0.01);
    }

    @Test(description = "Path 2: Trọng lượng không hợp lệ (<= 0)")
    public void testPath2_InvalidWeight() {
        Assert.assertThrows(IllegalArgumentException.class, 
            () -> PhiShip.tinhPhiShip(-1, "noi_thanh", false));
    }

    @Test(description = "Path 3: Ngoại thành, <= 2kg, Không member")
    public void testPath3_NgoaiThanh_Nhe_KhongMember() {
        double expected = 25000;
        Assert.assertEquals(PhiShip.tinhPhiShip(1, "ngoai_thanh", false), expected, 0.01);
    }

    @Test(description = "Path 4: Tỉnh khác, <= 2kg, Không member")
    public void testPath4_TinhKhac_Nhe_KhongMember() {
        double expected = 50000;
        Assert.assertEquals(PhiShip.tinhPhiShip(1, "tinh_khac", false), expected, 0.01);
    }

    @Test(description = "Path 5: Nội thành, > 5kg, Không member")
    public void testPath5_NoiThanh_NangHon5_KhongMember() {
        // Phí cơ bản: 15000 + (6-5)*2000 = 17000
        // Phí vượt 2kg: (6-2)*5000 = 20000
        // Tổng: 37000
        double expected = 37000;
        Assert.assertEquals(PhiShip.tinhPhiShip(6, "noi_thanh", false), expected, 0.01);
    }

    @Test(description = "Path 6: Ngoại thành, > 3kg, Không member")
    public void testPath6_NgoaiThanh_NangHon3_KhongMember() {
        // Phí cơ bản: 25000 + (4-3)*3000 = 28000
        // Phí vượt 2kg: (4-2)*5000 = 10000
        // Tổng: 38000
        double expected = 38000;
        Assert.assertEquals(PhiShip.tinhPhiShip(4, "ngoai_thanh", false), expected, 0.01);
    }

    @Test(description = "Path 7: Nội thành, 2 < TL <= 5kg, Không member")
    public void testPath7_NoiThanh_Tu2Den5_KhongMember() {
        // Phí cơ bản: 15000
        // Phí vượt 2kg: (3-2)*5000 = 5000
        // Tổng: 20000
        double expected = 20000;
        Assert.assertEquals(PhiShip.tinhPhiShip(3, "noi_thanh", false), expected, 0.01);
    }

    @Test(description = "Path 8: Nội thành, <= 2kg, Là Member")
    public void testPath8_NoiThanh_Nhe_LaMember() {
        // Phí cơ bản: 15000
        // Member giảm 10%: 15000 * 0.9 = 13500
        double expected = 13500;
        Assert.assertEquals(PhiShip.tinhPhiShip(1, "noi_thanh", true), expected, 0.01);
    }
}