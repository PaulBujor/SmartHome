import model.Measurement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.*;

public class TemperatureTest {
    private MeasurementController temp_controller = new MeasurementController("temperature");
    private long randomId = new Random().nextLong();

    @Test
    public void testAddingToExistingDevice() throws IOException, InterruptedException {
        var measurement = new Measurement();
        measurement.setValue(10 + new Random().nextDouble() * 20);
        measurement.setTimestamp(new Date(System.currentTimeMillis()));
        temp_controller.addMeasurement(Test_Config.getTestDeviceId(), measurement);
        assertTrue(temp_controller.getMeasurements(Test_Config.getTestDeviceId()).contains(measurement));
    }

//    @Test
//    public void testAddingToNonExistingDevice() throws IOException, InterruptedException {
//        var measurement = new Measurement();
//        measurement.setValue(10 + new Random().nextDouble() * 20);
//        measurement.setTimestamp(new Date(System.currentTimeMillis()));
//        temp_controller.addMeasurement(randomId, measurement);
//        assertTrue(temp_controller.getMeasurements(randomId).contains(measurement));
//    }

    @Test
    public void getMeasurements() throws IOException, InterruptedException {
        var measurement = temp_controller.getMeasurements(Test_Config.getTestDeviceId()).get(0);
        assertNotNull(measurement);
        assertNotNull(measurement.getTimestamp());
        assertTrue(measurement.getValue() != 0);
    }

    @Test
    public void getLatestMeasurement() throws IOException, InterruptedException {
        var measurement = new Measurement();
        measurement.setValue(10 + new Random().nextDouble() * 20);
        measurement.setTimestamp(new Date(System.currentTimeMillis()));
        temp_controller.addMeasurement(Test_Config.getTestDeviceId(), measurement);
        assertEquals(temp_controller.getLatestMeassurement(Test_Config.getTestDeviceId()), measurement);
    }
}
