package tests;

import framework.base.BaseTest;
import framework.pages.LoginPage;
import framework.utils.ExcelReader;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginDDTTest extends BaseTest implements ITest {
    
    private ThreadLocal<String> testName = new ThreadLocal<>();

    @Override
    public String getTestName() {
        return testName.get() != null ? testName.get() : super.toString();
    }

    String excelPath = "src/test/resources/testdata/login_data.xlsx";

    @DataProvider(name = "smokeData")
    public Object[][] getSmokeData() {
        return ExcelReader.getData(excelPath, "SmokeCases");
    }

    @DataProvider(name = "negativeData")
    public Object[][] getNegativeData() {
        return ExcelReader.getData(excelPath, "NegativeCases");
    }

    @DataProvider(name = "boundaryData")
    public Object[][] getBoundaryData() {
        return ExcelReader.getData(excelPath, "BoundaryCases");
    }

    @Test(dataProvider = "smokeData", groups = {"smoke", "regression"})
    public void testSmokeCases(String username, String password, String expectedUrl, String description) {
        testName.set(description); // Set tên test hiển thị trong Report
        
        getDriver().get("https://www.saucedemo.com");
        LoginPage loginPage = new LoginPage(getDriver());
        
        boolean isLoaded = loginPage.login(username, password).isLoaded();
        Assert.assertTrue(isLoaded, "Lỗi tại test: " + description);
    }

    @Test(dataProvider = "negativeData", groups = {"regression"})
    public void testNegativeCases(String username, String password, String expectedError, String description) {
        testName.set(description);
        
        getDriver().get("https://www.saucedemo.com");
        LoginPage loginPage = new LoginPage(getDriver());
        
        loginPage.loginExpectingFailure(username, password);
        Assert.assertTrue(loginPage.getErrorMessage().contains(expectedError), "Lỗi sai thông báo tại: " + description);
    }

    @Test(dataProvider = "boundaryData", groups = {"regression"})
    public void testBoundaryCases(String username, String password, String expectedError, String description) {
        testName.set(description);
        
        getDriver().get("https://www.saucedemo.com");
        LoginPage loginPage = new LoginPage(getDriver());
        
        loginPage.loginExpectingFailure(username, password);
        Assert.assertTrue(loginPage.getErrorMessage().contains(expectedError), "Lỗi sai thông báo tại: " + description);
    }
}