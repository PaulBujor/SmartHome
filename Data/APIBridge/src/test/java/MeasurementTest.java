import model.Measurement;
import model.MeasurementSet;
import org.junit.Test;
import remote.api.MeasurementController;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class MeasurementTest {
    private MeasurementController controller = new MeasurementController();

    @Test
    public void testAddingToExistingDevice() throws IOException, InterruptedException {
        var measurement = generateMeasurementSet();
        long deviceId = Test_Config.getTestDeviceId();

        Measurement testTemp = new Measurement(measurement.getTimestamp(), measurement.getTemperature());
        Measurement testCo2 = new Measurement(measurement.getTimestamp(), measurement.getCo2());
        Measurement testHumid = new Measurement(measurement.getTimestamp(), measurement.getHumidity());
        Measurement testSound = new Measurement(measurement.getTimestamp(), measurement.getSound());

        controller.addMeasurement(deviceId, measurement);

        List<Measurement> temperatures = controller.getMeasurements(deviceId, "temperature");
        List<Measurement> co2 = controller.getMeasurements(deviceId, "co2");
        List<Measurement> humidity = controller.getMeasurements(deviceId, "humidity");
        List<Measurement> sound = controller.getMeasurements(deviceId, "sound");

        assertTrue(temperatures.contains(testTemp));
        assertTrue(co2.contains(testCo2));
        assertTrue(humidity.contains(testHumid));
        assertTrue(sound.contains(testSound));
    }

    private MeasurementSet generateMeasurementSet() {
        var measurement = new MeasurementSet();

        measurement.setSound(new Random().nextDouble() * 100);
        measurement.setCo2(new Random().nextDouble() * 2000);
        measurement.setHumidity(new Random().nextDouble() * 100);
        measurement.setTemperature(10 + new Random().nextDouble() * 20);
        measurement.setTimestamp(new Date(System.currentTimeMillis()));

        return measurement;
    }
}
