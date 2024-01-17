package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.ObyektTestSamples.*;
import static uz.dynamic.techinventory.domain.ShelfTypeTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class ShelfTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShelfType.class);
        ShelfType shelfType1 = getShelfTypeSample1();
        ShelfType shelfType2 = new ShelfType();
        assertThat(shelfType1).isNotEqualTo(shelfType2);

        shelfType2.setId(shelfType1.getId());
        assertThat(shelfType1).isEqualTo(shelfType2);

        shelfType2 = getShelfTypeSample2();
        assertThat(shelfType1).isNotEqualTo(shelfType2);
    }

    @Test
    void obyektTest() throws Exception {
        ShelfType shelfType = getShelfTypeRandomSampleGenerator();
        Obyekt obyektBack = getObyektRandomSampleGenerator();

        shelfType.setObyekt(obyektBack);
        assertThat(shelfType.getObyekt()).isEqualTo(obyektBack);

        shelfType.obyekt(null);
        assertThat(shelfType.getObyekt()).isNull();
    }
}
