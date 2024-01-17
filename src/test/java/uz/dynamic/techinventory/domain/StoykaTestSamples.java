package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StoykaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Stoyka getStoykaSample1() {
        return new Stoyka().id(1L).name("name1").info("info1");
    }

    public static Stoyka getStoykaSample2() {
        return new Stoyka().id(2L).name("name2").info("info2");
    }

    public static Stoyka getStoykaRandomSampleGenerator() {
        return new Stoyka().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).info(UUID.randomUUID().toString());
    }
}
