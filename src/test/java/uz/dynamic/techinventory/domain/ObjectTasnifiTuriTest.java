package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.DistrictTestSamples.*;
import static uz.dynamic.techinventory.domain.ObjectTasnifiTuriTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class ObjectTasnifiTuriTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObjectTasnifiTuri.class);
        ObjectTasnifiTuri objectTasnifiTuri1 = getObjectTasnifiTuriSample1();
        ObjectTasnifiTuri objectTasnifiTuri2 = new ObjectTasnifiTuri();
        assertThat(objectTasnifiTuri1).isNotEqualTo(objectTasnifiTuri2);

        objectTasnifiTuri2.setId(objectTasnifiTuri1.getId());
        assertThat(objectTasnifiTuri1).isEqualTo(objectTasnifiTuri2);

        objectTasnifiTuri2 = getObjectTasnifiTuriSample2();
        assertThat(objectTasnifiTuri1).isNotEqualTo(objectTasnifiTuri2);
    }

    @Test
    void districtTest() throws Exception {
        ObjectTasnifiTuri objectTasnifiTuri = getObjectTasnifiTuriRandomSampleGenerator();
        District districtBack = getDistrictRandomSampleGenerator();

        objectTasnifiTuri.setDistrict(districtBack);
        assertThat(objectTasnifiTuri.getDistrict()).isEqualTo(districtBack);

        objectTasnifiTuri.district(null);
        assertThat(objectTasnifiTuri.getDistrict()).isNull();
    }
}
