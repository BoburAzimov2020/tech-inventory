package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.ObyektTestSamples.*;
import static uz.dynamic.techinventory.domain.SvitaforDetectorTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class SvitaforDetectorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SvitaforDetector.class);
        SvitaforDetector svitaforDetector1 = getSvitaforDetectorSample1();
        SvitaforDetector svitaforDetector2 = new SvitaforDetector();
        assertThat(svitaforDetector1).isNotEqualTo(svitaforDetector2);

        svitaforDetector2.setId(svitaforDetector1.getId());
        assertThat(svitaforDetector1).isEqualTo(svitaforDetector2);

        svitaforDetector2 = getSvitaforDetectorSample2();
        assertThat(svitaforDetector1).isNotEqualTo(svitaforDetector2);
    }

    @Test
    void obyektTest() throws Exception {
        SvitaforDetector svitaforDetector = getSvitaforDetectorRandomSampleGenerator();
        Obyekt obyektBack = getObyektRandomSampleGenerator();

        svitaforDetector.setObyekt(obyektBack);
        assertThat(svitaforDetector.getObyekt()).isEqualTo(obyektBack);

        svitaforDetector.obyekt(null);
        assertThat(svitaforDetector.getObyekt()).isNull();
    }
}
