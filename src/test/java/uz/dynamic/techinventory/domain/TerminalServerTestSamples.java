package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TerminalServerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TerminalServer getTerminalServerSample1() {
        return new TerminalServer().id(1L).name("name1").model("model1").ip("ip1").info("info1");
    }

    public static TerminalServer getTerminalServerSample2() {
        return new TerminalServer().id(2L).name("name2").model("model2").ip("ip2").info("info2");
    }

    public static TerminalServer getTerminalServerRandomSampleGenerator() {
        return new TerminalServer()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .model(UUID.randomUUID().toString())
            .ip(UUID.randomUUID().toString())
            .info(UUID.randomUUID().toString());
    }
}
