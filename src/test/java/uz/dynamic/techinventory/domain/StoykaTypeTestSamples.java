package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StoykaTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static StoykaType getStoykaTypeSample1() {
        return new StoykaType().id(1L).name("name1").info("info1");
    }

    public static StoykaType getStoykaTypeSample2() {
        return new StoykaType().id(2L).name("name2").info("info2");
    }

    public static StoykaType getStoykaTypeRandomSampleGenerator() {
        return new StoykaType().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).info(UUID.randomUUID().toString());
    }
}
