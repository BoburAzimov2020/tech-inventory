package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.AkumulatorTestSamples.*;
import static uz.dynamic.techinventory.domain.ObyektTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class AkumulatorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Akumulator.class);
        Akumulator akumulator1 = getAkumulatorSample1();
        Akumulator akumulator2 = new Akumulator();
        assertThat(akumulator1).isNotEqualTo(akumulator2);

        akumulator2.setId(akumulator1.getId());
        assertThat(akumulator1).isEqualTo(akumulator2);

        akumulator2 = getAkumulatorSample2();
        assertThat(akumulator1).isNotEqualTo(akumulator2);
    }

    @Test
    void obyektTest() throws Exception {
        Akumulator akumulator = getAkumulatorRandomSampleGenerator();
        Obyekt obyektBack = getObyektRandomSampleGenerator();

        akumulator.setObyekt(obyektBack);
        assertThat(akumulator.getObyekt()).isEqualTo(obyektBack);

        akumulator.obyekt(null);
        assertThat(akumulator.getObyekt()).isNull();
    }
}
