import org.junit.Test;
import remote.api.MeasurementController;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class EFLockingTest {
    private MeasurementController temp_controller = new MeasurementController("temperature");
    private MeasurementController co2_controller = new MeasurementController("co2");
    private MeasurementController humidity_controller = new MeasurementController("humidity");

    @Test
    public void testAddingToExistingDevice() throws IOException, InterruptedException {
        AtomicIntegerArray measurement = new AtomicIntegerArray(3);
        Thread t1 = new Thread(() -> {
            try {
                measurement.set(0, (int) temp_controller.getLatestMeassurement(1).getValue());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                measurement.set(1, (int) co2_controller.getLatestMeassurement(1).getValue());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                measurement.set(2, (int) humidity_controller.getLatestMeassurement(1).getValue());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println(measurement);
    }

//    @Test
//    public void testAddingToNonExistingDevice() throws IOException, InterruptedException {
//        var measurement = new Measurement();
//        measurement.setValue(10 + new Random().nextDouble() * 20);
//        measurement.setTimestamp(new Date(System.currentTimeMillis()));
//        temp_controller.addMeasurement(randomId, measurement);
//        assertTrue(temp_controller.getMeasurements(randomId).contains(measurement));
//    }
}
