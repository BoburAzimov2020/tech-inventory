package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.ObjectTasnifiTestSamples.*;
import static uz.dynamic.techinventory.domain.ObjectTasnifiTuriTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class ObjectTasnifiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObjectTasnifi.class);
        ObjectTasnifi objectTasnifi1 = getObjectTasnifiSample1();
        ObjectTasnifi objectTasnifi2 = new ObjectTasnifi();
        assertThat(objectTasnifi1).isNotEqualTo(objectTasnifi2);

        objectTasnifi2.setId(objectTasnifi1.getId());
        assertThat(objectTasnifi1).isEqualTo(objectTasnifi2);

        objectTasnifi2 = getObjectTasnifiSample2();
        assertThat(objectTasnifi1).isNotEqualTo(objectTasnifi2);
    }

    @Test
    void bjectTasnifiTuriTest() throws Exception {
        ObjectTasnifi objectTasnifi = getObjectTasnifiRandomSampleGenerator();
        ObjectTasnifiTuri objectTasnifiTuriBack = getObjectTasnifiTuriRandomSampleGenerator();

        objectTasnifi.setBjectTasnifiTuri(objectTasnifiTuriBack);
        assertThat(objectTasnifi.getBjectTasnifiTuri()).isEqualTo(objectTasnifiTuriBack);

        objectTasnifi.bjectTasnifiTuri(null);
        assertThat(objectTasnifi.getBjectTasnifiTuri()).isNull();
    }
}
