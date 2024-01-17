package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LoyihaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Loyiha getLoyihaSample1() {
        return new Loyiha().id(1L).name("name1");
    }

    public static Loyiha getLoyihaSample2() {
        return new Loyiha().id(2L).name("name2");
    }

    public static Loyiha getLoyihaRandomSampleGenerator() {
        return new Loyiha().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
