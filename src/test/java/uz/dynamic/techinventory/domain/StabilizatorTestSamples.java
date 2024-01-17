package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StabilizatorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Stabilizator getStabilizatorSample1() {
        return new Stabilizator().id(1L).name("name1").model("model1").power("power1").info("info1");
    }

    public static Stabilizator getStabilizatorSample2() {
        return new Stabilizator().id(2L).name("name2").model("model2").power("power2").info("info2");
    }

    public static Stabilizator getStabilizatorRandomSampleGenerator() {
        return new Stabilizator()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .model(UUID.randomUUID().toString())
            .power(UUID.randomUUID().toString())
            .info(UUID.randomUUID().toString());
    }
}
