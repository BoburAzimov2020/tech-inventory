package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class UpsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UpsDTO.class);
        UpsDTO upsDTO1 = new UpsDTO();
        upsDTO1.setId(1L);
        UpsDTO upsDTO2 = new UpsDTO();
        assertThat(upsDTO1).isNotEqualTo(upsDTO2);
        upsDTO2.setId(upsDTO1.getId());
        assertThat(upsDTO1).isEqualTo(upsDTO2);
        upsDTO2.setId(2L);
        assertThat(upsDTO1).isNotEqualTo(upsDTO2);
        upsDTO1.setId(null);
        assertThat(upsDTO1).isNotEqualTo(upsDTO2);
    }
}
