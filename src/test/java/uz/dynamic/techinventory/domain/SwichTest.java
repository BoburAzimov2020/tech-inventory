package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.SwichTestSamples.*;
import static uz.dynamic.techinventory.domain.SwichTypeTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class SwichTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Swich.class);
        Swich swich1 = getSwichSample1();
        Swich swich2 = new Swich();
        assertThat(swich1).isNotEqualTo(swich2);

        swich2.setId(swich1.getId());
        assertThat(swich1).isEqualTo(swich2);

        swich2 = getSwichSample2();
        assertThat(swich1).isNotEqualTo(swich2);
    }

    @Test
    void swichTypeTest() throws Exception {
        Swich swich = getSwichRandomSampleGenerator();
        SwichType swichTypeBack = getSwichTypeRandomSampleGenerator();

        swich.setSwichType(swichTypeBack);
        assertThat(swich.getSwichType()).isEqualTo(swichTypeBack);

        swich.swichType(null);
        assertThat(swich.getSwichType()).isNull();
    }
}
