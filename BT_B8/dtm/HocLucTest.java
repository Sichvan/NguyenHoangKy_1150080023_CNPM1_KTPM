package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HocLucTest {

    @Test(description = "TC1: N1-T")
    public void testDiemKhongHopLe() {
        Assert.assertEquals(HocLuc.xepLoai(-1, false), "Diem khong hop le");
    }

    @Test(description = "TC2: N3-T")
    public void testDiemGioi() {
        Assert.assertEquals(HocLuc.xepLoai(9, false), "Gioi");
    }

    @Test(description = "TC3: N5-T")
    public void testDiemKha() {
        Assert.assertEquals(HocLuc.xepLoai(8, false), "Kha");
    }

    @Test(description = "TC4: N7-T")
    public void testDiemTrungBinh() {
        Assert.assertEquals(HocLuc.xepLoai(6, false), "Trung Binh");
    }

    @Test(description = "TC5: N9-T")
    public void testThiLai() {
        Assert.assertEquals(HocLuc.xepLoai(4, true), "Thi lai");
    }

    @Test(description = "TC6: N9-F")
    public void testYeuHocLai() {
        Assert.assertEquals(HocLuc.xepLoai(4, false), "Yeu - Hoc lai");
    }
}