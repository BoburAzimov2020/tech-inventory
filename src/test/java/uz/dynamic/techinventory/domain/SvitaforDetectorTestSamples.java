package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SvitaforDetectorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SvitaforDetector getSvitaforDetectorSample1() {
        return new SvitaforDetector().id(1L).name("name1").model("model1").portNumber("portNumber1").info("info1");
    }

    public static SvitaforDetector getSvitaforDetectorSample2() {
        return new SvitaforDetector().id(2L).name("name2").model("model2").portNumber("portNumber2").info("info2");
    }

    public static SvitaforDetector getSvitaforDetectorRandomSampleGenerator() {
        return new SvitaforDetector()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .model(UUID.randomUUID().toString())
            .portNumber(UUID.randomUUID().toString())
            .info(UUID.randomUUID().toString());
    }
}
