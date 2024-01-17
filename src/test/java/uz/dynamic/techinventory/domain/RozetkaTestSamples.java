package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RozetkaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Rozetka getRozetkaSample1() {
        return new Rozetka().id(1L).name("name1").model("model1").info("info1");
    }

    public static Rozetka getRozetkaSample2() {
        return new Rozetka().id(2L).name("name2").model("model2").info("info2");
    }

    public static Rozetka getRozetkaRandomSampleGenerator() {
        return new Rozetka()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .model(UUID.randomUUID().toString())
            .info(UUID.randomUUID().toString());
    }
}
