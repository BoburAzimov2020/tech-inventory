package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ShelfTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Shelf getShelfSample1() {
        return new Shelf().id(1L).serialNumber("serialNumber1").digitNumber("digitNumber1").info("info1");
    }

    public static Shelf getShelfSample2() {
        return new Shelf().id(2L).serialNumber("serialNumber2").digitNumber("digitNumber2").info("info2");
    }

    public static Shelf getShelfRandomSampleGenerator() {
        return new Shelf()
            .id(longCount.incrementAndGet())
            .serialNumber(UUID.randomUUID().toString())
            .digitNumber(UUID.randomUUID().toString())
            .info(UUID.randomUUID().toString());
    }
}
