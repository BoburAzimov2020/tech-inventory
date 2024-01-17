package uz.dynamic.techinventory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.dynamic.techinventory.web.rest.TestUtil;

class SwichTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SwichTypeDTO.class);
        SwichTypeDTO swichTypeDTO1 = new SwichTypeDTO();
        swichTypeDTO1.setId(1L);
        SwichTypeDTO swichTypeDTO2 = new SwichTypeDTO();
        assertThat(swichTypeDTO1).isNotEqualTo(swichTypeDTO2);
        swichTypeDTO2.setId(swichTypeDTO1.getId());
        assertThat(swichTypeDTO1).isEqualTo(swichTypeDTO2);
        swichTypeDTO2.setId(2L);
        assertThat(swichTypeDTO1).isNotEqualTo(swichTypeDTO2);
        swichTypeDTO1.setId(null);
        assertThat(swichTypeDTO1).isNotEqualTo(swichTypeDTO2);
    }
}
