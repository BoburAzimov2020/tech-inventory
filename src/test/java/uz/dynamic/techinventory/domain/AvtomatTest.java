package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.AvtomatTestSamples.*;
import static uz.dynamic.techinventory.domain.ObyektTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class AvtomatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Avtomat.class);
        Avtomat avtomat1 = getAvtomatSample1();
        Avtomat avtomat2 = new Avtomat();
        assertThat(avtomat1).isNotEqualTo(avtomat2);

        avtomat2.setId(avtomat1.getId());
        assertThat(avtomat1).isEqualTo(avtomat2);

        avtomat2 = getAvtomatSample2();
        assertThat(avtomat1).isNotEqualTo(avtomat2);
    }

    @Test
    void obyektTest() throws Exception {
        Avtomat avtomat = getAvtomatRandomSampleGenerator();
        Obyekt obyektBack = getObyektRandomSampleGenerator();

        avtomat.setObyekt(obyektBack);
        assertThat(avtomat.getObyekt()).isEqualTo(obyektBack);

        avtomat.obyekt(null);
        assertThat(avtomat.getObyekt()).isNull();
    }
}
