package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.ObyektTestSamples.*;
import static uz.dynamic.techinventory.domain.SwichTypeTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class SwichTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SwichType.class);
        SwichType swichType1 = getSwichTypeSample1();
        SwichType swichType2 = new SwichType();
        assertThat(swichType1).isNotEqualTo(swichType2);

        swichType2.setId(swichType1.getId());
        assertThat(swichType1).isEqualTo(swichType2);

        swichType2 = getSwichTypeSample2();
        assertThat(swichType1).isNotEqualTo(swichType2);
    }

    @Test
    void obyektTest() throws Exception {
        SwichType swichType = getSwichTypeRandomSampleGenerator();
        Obyekt obyektBack = getObyektRandomSampleGenerator();

        swichType.setObyekt(obyektBack);
        assertThat(swichType.getObyekt()).isEqualTo(obyektBack);

        swichType.obyekt(null);
        assertThat(swichType.getObyekt()).isNull();
    }
}
