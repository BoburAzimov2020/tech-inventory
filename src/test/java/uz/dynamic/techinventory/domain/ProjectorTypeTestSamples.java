package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProjectorTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ProjectorType getProjectorTypeSample1() {
        return new ProjectorType().id(1L).name("name1").info("info1");
    }

    public static ProjectorType getProjectorTypeSample2() {
        return new ProjectorType().id(2L).name("name2").info("info2");
    }

    public static ProjectorType getProjectorTypeRandomSampleGenerator() {
        return new ProjectorType().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).info(UUID.randomUUID().toString());
    }
}
