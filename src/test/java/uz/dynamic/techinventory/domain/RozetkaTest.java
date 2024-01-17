package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.ObyektTestSamples.*;
import static uz.dynamic.techinventory.domain.RozetkaTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class RozetkaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rozetka.class);
        Rozetka rozetka1 = getRozetkaSample1();
        Rozetka rozetka2 = new Rozetka();
        assertThat(rozetka1).isNotEqualTo(rozetka2);

        rozetka2.setId(rozetka1.getId());
        assertThat(rozetka1).isEqualTo(rozetka2);

        rozetka2 = getRozetkaSample2();
        assertThat(rozetka1).isNotEqualTo(rozetka2);
    }

    @Test
    void obyektTest() throws Exception {
        Rozetka rozetka = getRozetkaRandomSampleGenerator();
        Obyekt obyektBack = getObyektRandomSampleGenerator();

        rozetka.setObyekt(obyektBack);
        assertThat(rozetka.getObyekt()).isEqualTo(obyektBack);

        rozetka.obyekt(null);
        assertThat(rozetka.getObyekt()).isNull();
    }
}
