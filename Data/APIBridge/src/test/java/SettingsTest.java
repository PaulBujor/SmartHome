import model.Settings;
import org.junit.Test;
import remote.api.SettingsController;

import java.io.IOException;

public class SettingsTest {
    private SettingsController controller = new SettingsController();

    @Test
    public void testSettings() {
        try {
            Settings settings = controller.getDeviceSettings(Test_Config.getTestDeviceId());

            String hexDeviceId = convertToHex(Test_Config.getTestDeviceId(), 16);

            String deviceConfiguration = convertToHex(settings.isDeviceConfiguration() ? 1 : 0, 2);
            String minHumidity = convertToHex(settings.getMinHumidity(), 4);
            String maxHumidity = convertToHex(settings.getMaxHumidity(), 4);
            String minTemperature = convertToHex(settings.getMinTemperature(), 4);
            String maxTemperature = convertToHex(settings.getMaxTemperature(), 4);
            String minCo2 = convertToHex(settings.getMinCo2(), 4);
            String maxCo2 = convertToHex(settings.getMaxCo2(), 4);

            String payload = "".concat(minHumidity).concat(maxHumidity).concat(minTemperature).concat(maxTemperature).concat(minCo2).concat(maxCo2).concat(deviceConfiguration);

            System.out.println(payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String convertToHex(long sourceNum, int targetLength) {
        String source = Long.toHexString(sourceNum);
        while (source.length() < targetLength) {
            source = "0".concat(source);
        }
        return source;
    }

    private String convertToHex(int sourceNum, int targetLength) {
        return convertToHex((long) sourceNum, targetLength);
    }
}
