package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.CabelTypeTestSamples.*;
import static uz.dynamic.techinventory.domain.ObyektTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class CabelTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CabelType.class);
        CabelType cabelType1 = getCabelTypeSample1();
        CabelType cabelType2 = new CabelType();
        assertThat(cabelType1).isNotEqualTo(cabelType2);

        cabelType2.setId(cabelType1.getId());
        assertThat(cabelType1).isEqualTo(cabelType2);

        cabelType2 = getCabelTypeSample2();
        assertThat(cabelType1).isNotEqualTo(cabelType2);
    }

    @Test
    void obyektTest() throws Exception {
        CabelType cabelType = getCabelTypeRandomSampleGenerator();
        Obyekt obyektBack = getObyektRandomSampleGenerator();

        cabelType.setObyekt(obyektBack);
        assertThat(cabelType.getObyekt()).isEqualTo(obyektBack);

        cabelType.obyekt(null);
        assertThat(cabelType.getObyekt()).isNull();
    }
}
