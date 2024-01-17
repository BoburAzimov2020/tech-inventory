package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.ObyektTestSamples.*;
import static uz.dynamic.techinventory.domain.StabilizatorTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class StabilizatorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stabilizator.class);
        Stabilizator stabilizator1 = getStabilizatorSample1();
        Stabilizator stabilizator2 = new Stabilizator();
        assertThat(stabilizator1).isNotEqualTo(stabilizator2);

        stabilizator2.setId(stabilizator1.getId());
        assertThat(stabilizator1).isEqualTo(stabilizator2);

        stabilizator2 = getStabilizatorSample2();
        assertThat(stabilizator1).isNotEqualTo(stabilizator2);
    }

    @Test
    void obyektTest() throws Exception {
        Stabilizator stabilizator = getStabilizatorRandomSampleGenerator();
        Obyekt obyektBack = getObyektRandomSampleGenerator();

        stabilizator.setObyekt(obyektBack);
        assertThat(stabilizator.getObyekt()).isEqualTo(obyektBack);

        stabilizator.obyekt(null);
        assertThat(stabilizator.getObyekt()).isNull();
    }
}
