package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.ObyektTestSamples.*;
import static uz.dynamic.techinventory.domain.StoykaTypeTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class StoykaTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoykaType.class);
        StoykaType stoykaType1 = getStoykaTypeSample1();
        StoykaType stoykaType2 = new StoykaType();
        assertThat(stoykaType1).isNotEqualTo(stoykaType2);

        stoykaType2.setId(stoykaType1.getId());
        assertThat(stoykaType1).isEqualTo(stoykaType2);

        stoykaType2 = getStoykaTypeSample2();
        assertThat(stoykaType1).isNotEqualTo(stoykaType2);
    }

    @Test
    void obyektTest() throws Exception {
        StoykaType stoykaType = getStoykaTypeRandomSampleGenerator();
        Obyekt obyektBack = getObyektRandomSampleGenerator();

        stoykaType.setObyekt(obyektBack);
        assertThat(stoykaType.getObyekt()).isEqualTo(obyektBack);

        stoykaType.obyekt(null);
        assertThat(stoykaType.getObyekt()).isNull();
    }
}
