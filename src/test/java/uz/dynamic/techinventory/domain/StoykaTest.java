package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.StoykaTestSamples.*;
import static uz.dynamic.techinventory.domain.StoykaTypeTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class StoykaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stoyka.class);
        Stoyka stoyka1 = getStoykaSample1();
        Stoyka stoyka2 = new Stoyka();
        assertThat(stoyka1).isNotEqualTo(stoyka2);

        stoyka2.setId(stoyka1.getId());
        assertThat(stoyka1).isEqualTo(stoyka2);

        stoyka2 = getStoykaSample2();
        assertThat(stoyka1).isNotEqualTo(stoyka2);
    }

    @Test
    void stoykaTypeTest() throws Exception {
        Stoyka stoyka = getStoykaRandomSampleGenerator();
        StoykaType stoykaTypeBack = getStoykaTypeRandomSampleGenerator();

        stoyka.setStoykaType(stoykaTypeBack);
        assertThat(stoyka.getStoykaType()).isEqualTo(stoykaTypeBack);

        stoyka.stoykaType(null);
        assertThat(stoyka.getStoykaType()).isNull();
    }
}
