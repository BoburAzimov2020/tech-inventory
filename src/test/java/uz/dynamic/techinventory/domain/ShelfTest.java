package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.ShelfTestSamples.*;
import static uz.dynamic.techinventory.domain.ShelfTypeTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class ShelfTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shelf.class);
        Shelf shelf1 = getShelfSample1();
        Shelf shelf2 = new Shelf();
        assertThat(shelf1).isNotEqualTo(shelf2);

        shelf2.setId(shelf1.getId());
        assertThat(shelf1).isEqualTo(shelf2);

        shelf2 = getShelfSample2();
        assertThat(shelf1).isNotEqualTo(shelf2);
    }

    @Test
    void shelfTypeTest() throws Exception {
        Shelf shelf = getShelfRandomSampleGenerator();
        ShelfType shelfTypeBack = getShelfTypeRandomSampleGenerator();

        shelf.setShelfType(shelfTypeBack);
        assertThat(shelf.getShelfType()).isEqualTo(shelfTypeBack);

        shelf.shelfType(null);
        assertThat(shelf.getShelfType()).isNull();
    }
}
