package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class SwichDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SwichDTO.class);
        SwichDTO swichDTO1 = new SwichDTO();
        swichDTO1.setId(1L);
        SwichDTO swichDTO2 = new SwichDTO();
        assertThat(swichDTO1).isNotEqualTo(swichDTO2);
        swichDTO2.setId(swichDTO1.getId());
        assertThat(swichDTO1).isEqualTo(swichDTO2);
        swichDTO2.setId(2L);
        assertThat(swichDTO1).isNotEqualTo(swichDTO2);
        swichDTO1.setId(null);
        assertThat(swichDTO1).isNotEqualTo(swichDTO2);
    }
}
