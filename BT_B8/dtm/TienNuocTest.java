package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TienNuocTest {

    @Test(description = "TC1: soM3 <= 0 -> N1-True")
    public void testSoM3AmHoacBangKhong() {
        Assert.assertEquals(TienNuoc.tinhTienNuoc(0, "dan_cu"), 0.0);
    }

    @Test(description = "TC2: loaiKhachHang = ho_ngheo -> N2-True")
    public void testHoNgheo() {
        Assert.assertEquals(TienNuoc.tinhTienNuoc(5, "ho_ngheo"), 25000.0);
    }

    @Test(description = "TC3: dan_cu va soM3 <= 10 -> N4-True")
    public void testDanCuDuoi10Khoi() {
        Assert.assertEquals(TienNuoc.tinhTienNuoc(5, "dan_cu"), 37500.0);
    }

    @Test(description = "TC4: dan_cu va 10 < soM3 <= 20 -> N5-True")
    public void testDanCuTu11Den20Khoi() {
        Assert.assertEquals(TienNuoc.tinhTienNuoc(15, "dan_cu"), 148500.0);
    }

    @Test(description = "TC5: dan_cu va soM3 > 20 -> N5-False")
    public void testDanCuTren20Khoi() {
        Assert.assertEquals(TienNuoc.tinhTienNuoc(25, "dan_cu"), 285000.0);
    }

    @Test(description = "TC6: loaiKhachHang = kinh_doanh -> N3-False")
    public void testKinhDoanh() {
        Assert.assertEquals(TienNuoc.tinhTienNuoc(10, "kinh_doanh"), 220000.0);
    }
}