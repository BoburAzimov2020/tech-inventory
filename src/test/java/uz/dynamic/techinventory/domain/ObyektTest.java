package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.BuyurtmaRaqamTestSamples.*;
import static uz.dynamic.techinventory.domain.ObyektTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class ObyektTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Obyekt.class);
        Obyekt obyekt1 = getObyektSample1();
        Obyekt obyekt2 = new Obyekt();
        assertThat(obyekt1).isNotEqualTo(obyekt2);

        obyekt2.setId(obyekt1.getId());
        assertThat(obyekt1).isEqualTo(obyekt2);

        obyekt2 = getObyektSample2();
        assertThat(obyekt1).isNotEqualTo(obyekt2);
    }

    @Test
    void buyurtmaRaqamTest() throws Exception {
        Obyekt obyekt = getObyektRandomSampleGenerator();
        BuyurtmaRaqam buyurtmaRaqamBack = getBuyurtmaRaqamRandomSampleGenerator();

        obyekt.setBuyurtmaRaqam(buyurtmaRaqamBack);
        assertThat(obyekt.getBuyurtmaRaqam()).isEqualTo(buyurtmaRaqamBack);

        obyekt.buyurtmaRaqam(null);
        assertThat(obyekt.getBuyurtmaRaqam()).isNull();
    }
}
