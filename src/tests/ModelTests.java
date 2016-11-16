package tests;
import model.UserLevel;
import model.WaterQualityReport;
import model.WaterSourceReport;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertEqualsNoOrder;

/**
 * Created by msternberg on 11/13/16.
 */
@SuppressWarnings("ALL")
public class ModelTests {

    @Test
    public void stringToSafetyTests() {
        WaterQualityReport.WaterSafety safetyLevel;
        safetyLevel = WaterQualityReport.stringToSafety("SAFE");
            assertEquals(safetyLevel, WaterQualityReport.WaterSafety.SAFE);
        safetyLevel = WaterQualityReport.stringToSafety("TREATABLE");
            assertEquals(safetyLevel, WaterQualityReport.WaterSafety.TREATABLE);
        safetyLevel = WaterQualityReport.stringToSafety("UNSAFE");
            assertEquals(safetyLevel, WaterQualityReport.WaterSafety.UNSAFE);
        safetyLevel = WaterQualityReport.stringToSafety("dangerous");
            assertEquals(safetyLevel, WaterQualityReport.WaterSafety.UNSAFE);
    }

    @Test
    public void waterSourceEqualsTests() {
        final double ATLLAT = 33.7490;
        final double ATLLONG = -84.3880;

        WaterSourceReport sourceReport1 = new WaterSourceReport("11/14/16", "12:12:12", "Sahaj",
        ATLLAT, ATLLONG, WaterSourceReport.WaterType.BOTTLED, WaterSourceReport.WaterCondition.POTABLE);
        WaterSourceReport sourceReport2 = new WaterSourceReport("11/14/16", "12:12:12", "Sahaj",
                ATLLAT, ATLLONG, WaterSourceReport.WaterType.BOTTLED, WaterSourceReport.WaterCondition.POTABLE);
        WaterSourceReport sameNumSourceReport = new WaterSourceReport(1, "11/13/16", "11:11:11", "Not Sahaj",
                ATLLAT * -1, ATLLONG * -1, WaterSourceReport.WaterType.LAKE, WaterSourceReport.WaterCondition.TREATABLECLEAR);

        Assert.assertTrue(!sourceReport1.equals(null),
                "A WaterSourceReport cannot be equal to a null object");
        Assert.assertTrue(!sourceReport1.equals("Not a WaterSourceReport"),
                "A WaterSourceReport can only be compared to other WaterSourceReports");
        Assert.assertEquals(sourceReport1.getNum(), sourceReport2.getNum() - 1,
                "A new WaterSourceReport created without a num parameter should automatically increment the reportNum");
        Assert.assertTrue(!sourceReport1.equals(sourceReport2),
                "WaterSourceReports with different report numbers are different WaterSourceReports");
        Assert.assertTrue(sourceReport1.equals(sameNumSourceReport),
                "WaterSourceReports with the same report numbers are considered equal even if the other data is different");
    }

    @Test
    public void waterSourceReportStringToWaterTypeTest() {
        WaterSourceReport.WaterType waterType;
        waterType = WaterSourceReport.stringToWaterType("BOTTLED");
        assertEquals(waterType, WaterSourceReport.WaterType.BOTTLED);
        waterType = WaterSourceReport.stringToWaterType("WELL");
        assertEquals(waterType, WaterSourceReport.WaterType.WELL);
        waterType = WaterSourceReport.stringToWaterType("STREAM");
        assertEquals(waterType, WaterSourceReport.WaterType.STREAM);
        waterType = WaterSourceReport.stringToWaterType("LAKE");
        assertEquals(waterType, WaterSourceReport.WaterType.LAKE);
        waterType = WaterSourceReport.stringToWaterType("SPRING");
        assertEquals(waterType, WaterSourceReport.WaterType.SPRING);
        waterType = WaterSourceReport.stringToWaterType("OTHER");
        assertEquals(waterType, WaterSourceReport.WaterType.OTHER);
    }

    @Test
    public void userLevelStringToUserLevel() {
        UserLevel level;
        level = UserLevel.stringToUserLevel("NORMAL");
        assertEquals(level, UserLevel.NORMAL);
        level = UserLevel.stringToUserLevel("ADMIN");
        assertEquals(level, UserLevel.ADMIN);
        level = UserLevel.stringToUserLevel("WORKER");
        assertEquals(level, UserLevel.WORKER);
        level = UserLevel.stringToUserLevel("MANAGER");
        assertEquals(level, UserLevel.MANAGER);
        level = UserLevel.stringToUserLevel("Aman");
        assertEquals(level, null);
        level = UserLevel.stringToUserLevel(null);
        assertEquals(level, null);
    }
}