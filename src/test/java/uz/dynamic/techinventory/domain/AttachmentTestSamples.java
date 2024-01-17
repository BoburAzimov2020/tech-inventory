package uz.dynamic.techinventory.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AttachmentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Attachment getAttachmentSample1() {
        return new Attachment()
            .id(1L)
            .path("path1")
            .originalFileName("originalFileName1")
            .fileName("fileName1")
            .contentType("contentType1")
            .fileSize("fileSize1")
            .info("info1");
    }

    public static Attachment getAttachmentSample2() {
        return new Attachment()
            .id(2L)
            .path("path2")
            .originalFileName("originalFileName2")
            .fileName("fileName2")
            .contentType("contentType2")
            .fileSize("fileSize2")
            .info("info2");
    }

    public static Attachment getAttachmentRandomSampleGenerator() {
        return new Attachment()
            .id(longCount.incrementAndGet())
            .path(UUID.randomUUID().toString())
            .originalFileName(UUID.randomUUID().toString())
            .fileName(UUID.randomUUID().toString())
            .contentType(UUID.randomUUID().toString())
            .fileSize(UUID.randomUUID().toString())
            .info(UUID.randomUUID().toString());
    }
}
