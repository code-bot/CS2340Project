package tests;
import model.WaterQualityReport;
import model.WaterSourceReport;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

/**
 * Created by msternberg on 11/13/16.
 */
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
}