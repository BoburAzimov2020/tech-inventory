package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.AttachmentTestSamples.*;
import static uz.dynamic.techinventory.domain.ObyektTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class AttachmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attachment.class);
        Attachment attachment1 = getAttachmentSample1();
        Attachment attachment2 = new Attachment();
        assertThat(attachment1).isNotEqualTo(attachment2);

        attachment2.setId(attachment1.getId());
        assertThat(attachment1).isEqualTo(attachment2);

        attachment2 = getAttachmentSample2();
        assertThat(attachment1).isNotEqualTo(attachment2);
    }

    @Test
    void obyektTest() throws Exception {
        Attachment attachment = getAttachmentRandomSampleGenerator();
        Obyekt obyektBack = getObyektRandomSampleGenerator();

        attachment.setObyekt(obyektBack);
        assertThat(attachment.getObyekt()).isEqualTo(obyektBack);

        attachment.obyekt(null);
        assertThat(attachment.getObyekt()).isNull();
    }
}
