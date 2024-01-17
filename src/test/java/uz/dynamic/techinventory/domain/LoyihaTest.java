package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.LoyihaTestSamples.*;
import static uz.dynamic.techinventory.domain.ObjectTasnifiTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class LoyihaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Loyiha.class);
        Loyiha loyiha1 = getLoyihaSample1();
        Loyiha loyiha2 = new Loyiha();
        assertThat(loyiha1).isNotEqualTo(loyiha2);

        loyiha2.setId(loyiha1.getId());
        assertThat(loyiha1).isEqualTo(loyiha2);

        loyiha2 = getLoyihaSample2();
        assertThat(loyiha1).isNotEqualTo(loyiha2);
    }

    @Test
    void objectTasnifiTest() throws Exception {
        Loyiha loyiha = getLoyihaRandomSampleGenerator();
        ObjectTasnifi objectTasnifiBack = getObjectTasnifiRandomSampleGenerator();

        loyiha.setObjectTasnifi(objectTasnifiBack);
        assertThat(loyiha.getObjectTasnifi()).isEqualTo(objectTasnifiBack);

        loyiha.objectTasnifi(null);
        assertThat(loyiha.getObjectTasnifi()).isNull();
    }
}
