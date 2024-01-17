package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.CabelTestSamples.*;
import static uz.dynamic.techinventory.domain.CabelTypeTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class CabelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cabel.class);
        Cabel cabel1 = getCabelSample1();
        Cabel cabel2 = new Cabel();
        assertThat(cabel1).isNotEqualTo(cabel2);

        cabel2.setId(cabel1.getId());
        assertThat(cabel1).isEqualTo(cabel2);

        cabel2 = getCabelSample2();
        assertThat(cabel1).isNotEqualTo(cabel2);
    }

    @Test
    void cabelTypeTest() throws Exception {
        Cabel cabel = getCabelRandomSampleGenerator();
        CabelType cabelTypeBack = getCabelTypeRandomSampleGenerator();

        cabel.setCabelType(cabelTypeBack);
        assertThat(cabel.getCabelType()).isEqualTo(cabelTypeBack);

        cabel.cabelType(null);
        assertThat(cabel.getCabelType()).isNull();
    }
}
