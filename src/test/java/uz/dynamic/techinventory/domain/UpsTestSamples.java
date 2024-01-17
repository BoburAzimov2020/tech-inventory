package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UpsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ups getUpsSample1() {
        return new Ups().id(1L).name("name1").model("model1").power("power1").info("info1");
    }

    public static Ups getUpsSample2() {
        return new Ups().id(2L).name("name2").model("model2").power("power2").info("info2");
    }

    public static Ups getUpsRandomSampleGenerator() {
        return new Ups()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .model(UUID.randomUUID().toString())
            .power(UUID.randomUUID().toString())
            .info(UUID.randomUUID().toString());
    }
}
