package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ObjectTasnifiTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ObjectTasnifi getObjectTasnifiSample1() {
        return new ObjectTasnifi().id(1L).name("name1");
    }

    public static ObjectTasnifi getObjectTasnifiSample2() {
        return new ObjectTasnifi().id(2L).name("name2");
    }

    public static ObjectTasnifi getObjectTasnifiRandomSampleGenerator() {
        return new ObjectTasnifi().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
