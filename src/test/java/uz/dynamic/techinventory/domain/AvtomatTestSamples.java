package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AvtomatTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Avtomat getAvtomatSample1() {
        return new Avtomat().id(1L).name("name1").model("model1").group("group1").info("info1");
    }

    public static Avtomat getAvtomatSample2() {
        return new Avtomat().id(2L).name("name2").model("model2").group("group2").info("info2");
    }

    public static Avtomat getAvtomatRandomSampleGenerator() {
        return new Avtomat()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .model(UUID.randomUUID().toString())
            .group(UUID.randomUUID().toString())
            .info(UUID.randomUUID().toString());
    }
}
