package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class StabilizatorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StabilizatorDTO.class);
        StabilizatorDTO stabilizatorDTO1 = new StabilizatorDTO();
        stabilizatorDTO1.setId(1L);
        StabilizatorDTO stabilizatorDTO2 = new StabilizatorDTO();
        assertThat(stabilizatorDTO1).isNotEqualTo(stabilizatorDTO2);
        stabilizatorDTO2.setId(stabilizatorDTO1.getId());
        assertThat(stabilizatorDTO1).isEqualTo(stabilizatorDTO2);
        stabilizatorDTO2.setId(2L);
        assertThat(stabilizatorDTO1).isNotEqualTo(stabilizatorDTO2);
        stabilizatorDTO1.setId(null);
        assertThat(stabilizatorDTO1).isNotEqualTo(stabilizatorDTO2);
    }
}
