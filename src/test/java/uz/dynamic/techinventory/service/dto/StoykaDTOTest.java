package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class StoykaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoykaDTO.class);
        StoykaDTO stoykaDTO1 = new StoykaDTO();
        stoykaDTO1.setId(1L);
        StoykaDTO stoykaDTO2 = new StoykaDTO();
        assertThat(stoykaDTO1).isNotEqualTo(stoykaDTO2);
        stoykaDTO2.setId(stoykaDTO1.getId());
        assertThat(stoykaDTO1).isEqualTo(stoykaDTO2);
        stoykaDTO2.setId(2L);
        assertThat(stoykaDTO1).isNotEqualTo(stoykaDTO2);
        stoykaDTO1.setId(null);
        assertThat(stoykaDTO1).isNotEqualTo(stoykaDTO2);
    }
}
