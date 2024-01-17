package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ShelfTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ShelfType getShelfTypeSample1() {
        return new ShelfType().id(1L).name("name1").info("info1");
    }

    public static ShelfType getShelfTypeSample2() {
        return new ShelfType().id(2L).name("name2").info("info2");
    }

    public static ShelfType getShelfTypeRandomSampleGenerator() {
        return new ShelfType().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).info(UUID.randomUUID().toString());
    }
}
