package tests;
import model.WaterQualityReport;
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
}