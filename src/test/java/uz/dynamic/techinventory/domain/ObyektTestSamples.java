package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ObyektTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Obyekt getObyektSample1() {
        return new Obyekt()
            .id(1L)
            .name("name1")
            .home("home1")
            .street("street1")
            .latitude("latitude1")
            .longitude("longitude1")
            .info("info1");
    }

    public static Obyekt getObyektSample2() {
        return new Obyekt()
            .id(2L)
            .name("name2")
            .home("home2")
            .street("street2")
            .latitude("latitude2")
            .longitude("longitude2")
            .info("info2");
    }

    public static Obyekt getObyektRandomSampleGenerator() {
        return new Obyekt()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .home(UUID.randomUUID().toString())
            .street(UUID.randomUUID().toString())
            .latitude(UUID.randomUUID().toString())
            .longitude(UUID.randomUUID().toString())
            .info(UUID.randomUUID().toString());
    }
}
