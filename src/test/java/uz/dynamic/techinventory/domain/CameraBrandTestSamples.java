package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CameraBrandTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CameraBrand getCameraBrandSample1() {
        return new CameraBrand().id(1L).name("name1").info("info1");
    }

    public static CameraBrand getCameraBrandSample2() {
        return new CameraBrand().id(2L).name("name2").info("info2");
    }

    public static CameraBrand getCameraBrandRandomSampleGenerator() {
        return new CameraBrand().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).info(UUID.randomUUID().toString());
    }
}
