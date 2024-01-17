package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.BuyurtmaRaqamTestSamples.*;
import static uz.dynamic.techinventory.domain.LoyihaTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class BuyurtmaRaqamTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuyurtmaRaqam.class);
        BuyurtmaRaqam buyurtmaRaqam1 = getBuyurtmaRaqamSample1();
        BuyurtmaRaqam buyurtmaRaqam2 = new BuyurtmaRaqam();
        assertThat(buyurtmaRaqam1).isNotEqualTo(buyurtmaRaqam2);

        buyurtmaRaqam2.setId(buyurtmaRaqam1.getId());
        assertThat(buyurtmaRaqam1).isEqualTo(buyurtmaRaqam2);

        buyurtmaRaqam2 = getBuyurtmaRaqamSample2();
        assertThat(buyurtmaRaqam1).isNotEqualTo(buyurtmaRaqam2);
    }

    @Test
    void loyihaTest() throws Exception {
        BuyurtmaRaqam buyurtmaRaqam = getBuyurtmaRaqamRandomSampleGenerator();
        Loyiha loyihaBack = getLoyihaRandomSampleGenerator();

        buyurtmaRaqam.setLoyiha(loyihaBack);
        assertThat(buyurtmaRaqam.getLoyiha()).isEqualTo(loyihaBack);

        buyurtmaRaqam.loyiha(null);
        assertThat(buyurtmaRaqam.getLoyiha()).isNull();
    }
}
