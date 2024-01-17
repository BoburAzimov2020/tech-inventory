package uz.dynamic.techinventory.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.dynamic.techinventory.domain.DistrictTestSamples.*;
import static uz.dynamic.techinventory.domain.RegionTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class DistrictTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(District.class);
        District district1 = getDistrictSample1();
        District district2 = new District();
        assertThat(district1).isNotEqualTo(district2);

        district2.setId(district1.getId());
        assertThat(district1).isEqualTo(district2);

        district2 = getDistrictSample2();
        assertThat(district1).isNotEqualTo(district2);
    }

    @Test
    void regionTest() throws Exception {
        District district = getDistrictRandomSampleGenerator();
        Region regionBack = getRegionRandomSampleGenerator();

        district.setRegion(regionBack);
        assertThat(district.getRegion()).isEqualTo(regionBack);

        district.region(null);
        assertThat(district.getRegion()).isNull();
    }
}
