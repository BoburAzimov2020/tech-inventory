package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SwichTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SwichType getSwichTypeSample1() {
        return new SwichType().id(1L).name("name1").info("info1");
    }

    public static SwichType getSwichTypeSample2() {
        return new SwichType().id(2L).name("name2").info("info2");
    }

    public static SwichType getSwichTypeRandomSampleGenerator() {
        return new SwichType().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).info(UUID.randomUUID().toString());
    }
}
