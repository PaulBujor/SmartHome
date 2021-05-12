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
        Measurement testAlarm = new Measurement(measurement.getTimestamp(), measurement.getAlarm());

        controller.addMeasurement(deviceId, measurement);

        List<Measurement> temperatures = controller.getMeasurements(deviceId, "temperature");
        List<Measurement> co2 = controller.getMeasurements(deviceId, "co2");
        List<Measurement> humidity = controller.getMeasurements(deviceId, "humidity");
        List<Measurement> alarm = controller.getMeasurements(deviceId, "alarm");

        assertTrue(temperatures.contains(testTemp));
        assertTrue(co2.contains(testCo2));
        assertTrue(humidity.contains(testHumid));
        assertTrue(alarm.contains(testAlarm));
    }

    private MeasurementSet generateMeasurementSet() {
        var measurement = new MeasurementSet();

        measurement.setAlarm((int) new Random().nextDouble() * 2);
        measurement.setCo2(new Random().nextDouble() * 2000);
        measurement.setHumidity(new Random().nextDouble() * 100);
        measurement.setTemperature(10 + new Random().nextDouble() * 20);
        measurement.setTimestamp(new Date(System.currentTimeMillis()));

        return measurement;
    }
}
