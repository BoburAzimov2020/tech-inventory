package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BuyurtmaRaqamTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BuyurtmaRaqam getBuyurtmaRaqamSample1() {
        return new BuyurtmaRaqam().id(1L).name("name1").numberOfOrder("numberOfOrder1");
    }

    public static BuyurtmaRaqam getBuyurtmaRaqamSample2() {
        return new BuyurtmaRaqam().id(2L).name("name2").numberOfOrder("numberOfOrder2");
    }

    public static BuyurtmaRaqam getBuyurtmaRaqamRandomSampleGenerator() {
        return new BuyurtmaRaqam()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .numberOfOrder(UUID.randomUUID().toString());
    }
}
