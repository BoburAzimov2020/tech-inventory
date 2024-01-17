package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CameraTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CameraType getCameraTypeSample1() {
        return new CameraType().id(1L).name("name1").info("info1");
    }

    public static CameraType getCameraTypeSample2() {
        return new CameraType().id(2L).name("name2").info("info2");
    }

    public static CameraType getCameraTypeRandomSampleGenerator() {
        return new CameraType().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).info(UUID.randomUUID().toString());
    }
}
