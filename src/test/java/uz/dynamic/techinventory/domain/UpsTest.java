package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.ObyektTestSamples.*;
import static uz.dynamic.techinventory.domain.UpsTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class UpsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ups.class);
        Ups ups1 = getUpsSample1();
        Ups ups2 = new Ups();
        assertThat(ups1).isNotEqualTo(ups2);

        ups2.setId(ups1.getId());
        assertThat(ups1).isEqualTo(ups2);

        ups2 = getUpsSample2();
        assertThat(ups1).isNotEqualTo(ups2);
    }

    @Test
    void obyektTest() throws Exception {
        Ups ups = getUpsRandomSampleGenerator();
        Obyekt obyektBack = getObyektRandomSampleGenerator();

        ups.setObyekt(obyektBack);
        assertThat(ups.getObyekt()).isEqualTo(obyektBack);

        ups.obyekt(null);
        assertThat(ups.getObyekt()).isNull();
    }
}
