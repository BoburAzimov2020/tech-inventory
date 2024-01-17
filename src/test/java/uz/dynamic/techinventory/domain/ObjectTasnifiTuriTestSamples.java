package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ObjectTasnifiTuriTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ObjectTasnifiTuri getObjectTasnifiTuriSample1() {
        return new ObjectTasnifiTuri().id(1L).name("name1");
    }

    public static ObjectTasnifiTuri getObjectTasnifiTuriSample2() {
        return new ObjectTasnifiTuri().id(2L).name("name2");
    }

    public static ObjectTasnifiTuri getObjectTasnifiTuriRandomSampleGenerator() {
        return new ObjectTasnifiTuri().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
