package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class StoykaTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoykaTypeDTO.class);
        StoykaTypeDTO stoykaTypeDTO1 = new StoykaTypeDTO();
        stoykaTypeDTO1.setId(1L);
        StoykaTypeDTO stoykaTypeDTO2 = new StoykaTypeDTO();
        assertThat(stoykaTypeDTO1).isNotEqualTo(stoykaTypeDTO2);
        stoykaTypeDTO2.setId(stoykaTypeDTO1.getId());
        assertThat(stoykaTypeDTO1).isEqualTo(stoykaTypeDTO2);
        stoykaTypeDTO2.setId(2L);
        assertThat(stoykaTypeDTO1).isNotEqualTo(stoykaTypeDTO2);
        stoykaTypeDTO1.setId(null);
        assertThat(stoykaTypeDTO1).isNotEqualTo(stoykaTypeDTO2);
    }
}
