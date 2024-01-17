package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CameraTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Camera getCameraSample1() {
        return new Camera().id(1L).name("name1").model("model1").serialNumber("serialNumber1").ip("ip1").info("info1");
    }

    public static Camera getCameraSample2() {
        return new Camera().id(2L).name("name2").model("model2").serialNumber("serialNumber2").ip("ip2").info("info2");
    }

    public static Camera getCameraRandomSampleGenerator() {
        return new Camera()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .model(UUID.randomUUID().toString())
            .serialNumber(UUID.randomUUID().toString())
            .ip(UUID.randomUUID().toString())
            .info(UUID.randomUUID().toString());
    }
}
